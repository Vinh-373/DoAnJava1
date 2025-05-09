package com.quanlybanlaptop.gui.account;


import com.quanlybanlaptop.bus.AdminBUS;
import com.quanlybanlaptop.bus.CTQBUS;
import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.dao.AdminDAO;
import com.quanlybanlaptop.dao.CTQDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.gui.product.ProductTable;
import com.quanlybanlaptop.util.ImageLoader;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class TopButton {
    private static JTextField tfName;
    private static JComboBox<String> searchcb, statuscb;
    private static Connection cnn = DatabaseConnection.getConnection();
    private static AdminDAO adminDAO = new AdminDAO(cnn);
    private static AdminBUS adminBUS = new AdminBUS(adminDAO);
    private static CTQBUS ctqbus = new CTQBUS(new CTQDAO(cnn));

    public static JPanel createButtonPanel(AdminDTO AdminDTO) {

        JPanel buttonControlPanel = new JPanel(new GridBagLayout());
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        btnAdd.setEnabled(ctqbus.isChecked(AdminDTO.getIdRole(), 4, 1));
        RoundedButton btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        btnEdit.setEnabled(ctqbus.isChecked(AdminDTO.getIdRole(), 4, 3));
        RoundedButton btnDelete = new RoundedButton("Tắt HĐ",  ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        btnDelete.setEnabled(ctqbus.isChecked(AdminDTO.getIdRole(), 4, 2));
        RoundedButton btnRestore = new RoundedButton("Bật HĐ",  ImageLoader.loadResourceImage("/img/restore_control.png"));
        btnRestore.setImageSize(32, 32);

        RoundedButton btndelete = new RoundedButton("Xóa",ImageLoader.loadResourceImage("/img/delete_control.png"));
        btndelete.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);

        String[] statusList = {"Hoạt động", "Ngừng HĐ"};
        statuscb = RoundedComponent.createRoundedComboBox(statusList, 10);
        statuscb.setPreferredSize(new Dimension(100, 35));

        String[] searchList = {"Tên", "Mã", "Email"};
      searchcb = RoundedComponent.createRoundedComboBox(searchList, 10);
        searchcb.setPreferredSize(new Dimension(100, 35));

         tfName = RoundedComponent.createRoundedTextField(10);
        tfName.setPreferredSize(new Dimension(200, 35));
        tfName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }

            private void performSearch() {
                String keyword = tfName.getText().trim();
                String selectedField = (String) searchcb.getSelectedItem();
                String field = "";

                switch (selectedField) {
                    case "Tên":
                        field = "name";
                        break;
                    case "Mã":
                        field = "id_admin";
                        break;
                    case "Email":
                        field = "email";
                        break;
                }

                // Dùng đúng comboBox trạng thái
                int status = ((JComboBox<String>) statuscb).getSelectedItem().equals("Hoạt động") ? 1 : 0;

                try {
                    ArrayList<AdminDTO> result = adminBUS.searchAdmin(keyword, field, status);
                    AccountTable.loadTable(result);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm!");
                }
            }
        });

        btnRestore.addActionListener(e -> {
            JTable table = AccountTable.getTableModel();
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để Bật hoạt động");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            try {
                boolean success = adminBUS.offAcc(id,1);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Bật hoạt động thành công!");
                    AccountTable.loadTable(adminBUS.getActiveAdmin(0)); // cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Bật hoạt động thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi Bật hoạt động!");
            }
        });

        // Sự kiện cho nút Xóa
        btnDelete.addActionListener(e -> {
            JTable table = AccountTable.getTableModel();
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để tắt hoạt động");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            try {
                boolean success = adminBUS.offAcc(id,0);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Tắt hoạt động thành công!");
                    AccountTable.loadTable(adminBUS.getActiveAdmin(1)); // cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Tắt hoạt động thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi tắt hoạt động!");
            }
        });

        btnAdd.addActionListener(e -> {
            AddAccDialog.createDialog("Thêm",null); // Mở form "Thêm"
        });
        btndelete.addActionListener(e -> {
            JTable table = AccountTable.getTableModel();
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để xóa");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            try {
                boolean success = adminBUS.offAcc(id,-1);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    AccountTable.loadTable(adminBUS.getActiveAdmin(0)); // cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi Xóa!");
            }
        });
        btnEdit.addActionListener(e -> {
            JTable table = AccountTable.getTableModel();
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một hàng để sửa");
                return;
            }
            // Lấy thông tin admin từ bảng
            AdminDTO adminDTO = new AdminDTO(
                    Integer.parseInt(table.getValueAt(row, 0).toString()),  // ID
                    table.getValueAt(row, 6).toString().equals("Chủ") ? 1 : 
                    table.getValueAt(row, 6).toString().equals("Quản Lý") ? 2 : 3, // Trạng thái,
                    table.getValueAt(row, 1).toString(),                     // Tên
                    table.getValueAt(row, 2).toString(),                     // Giới tính
                    table.getValueAt(row, 3).toString(),                     // Email
                    table.getValueAt(row, 4).toString(),                     // Liên hệ
                    table.getValueAt(row, 5).toString(),                     // Mật khẩu
                    table.getValueAt(row, 7).toString().equals("Hoạt động") ? 1 : 0 // Trạng thái
            );
            AddAccDialog.createDialog("Sửa",adminDTO); // Mở form "Sửa"
        });



        statuscb.addActionListener(e -> {
            String selectedOs = (String) statuscb.getSelectedItem();
            if(selectedOs.equals("Hoạt động")){
                ArrayList<AdminDTO> list = new ArrayList<>();

                try {
                     list = adminBUS.getActiveAdmin(1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(adminBUS.toString());
                AccountTable.loadTable(list);
                btnAdd.setVisible(true);
                btndelete.setVisible(false);
                btnDelete.setVisible(true);
                btnRestore.setEnabled(false);
                tfName.setText("");
                btnEdit.setVisible(true);
            }else {
                ArrayList<AdminDTO> list = new ArrayList<>();

                try {
                    list = adminBUS.getActiveAdmin(0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                AccountTable.loadTable(list);
                btnAdd.setVisible(false);
                btndelete.setVisible(true);
                btnDelete.setVisible(false);
                btnRestore.setVisible(true);
                btnRestore.setEnabled(true);
                tfName.setText("");
                btnEdit.setVisible(false);
            }
        });

        btnRefresh.addActionListener(e -> {

            try {
                AccountTable.loadTable(adminBUS.getActiveAdmin(1));
                statuscb.setSelectedItem("Hoạt động");
                tfName.setText("");
                btnAdd.setVisible(true);
                btndelete.setVisible(false);
                btnDelete.setVisible(true);
                btnRestore.setEnabled(false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnRestore.addActionListener(e -> {

        });


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; buttonControlPanel.add(btnAdd, gbc);
        gbc.gridy = 0;buttonControlPanel.add(btndelete, gbc);
        gbc.gridx = 1; buttonControlPanel.add(btnEdit, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnDelete, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnRestore, gbc);
        gbc.gridx = 3; buttonControlPanel.add(btnRefresh, gbc);
        gbc.gridx = 4; gbc.gridwidth = 2; buttonControlPanel.add(statuscb, gbc);
        gbc.gridx = 6; gbc.gridwidth = 2; buttonControlPanel.add(searchcb, gbc);
        gbc.gridx = 8; gbc.gridwidth = 4; buttonControlPanel.add(tfName, gbc);

        return buttonControlPanel;
    }


}
