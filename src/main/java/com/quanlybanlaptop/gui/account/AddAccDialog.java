package com.quanlybanlaptop.gui.account;

import com.quanlybanlaptop.bus.AdminBUS;
import com.quanlybanlaptop.dao.AdminDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.gui.product.ProductTable;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class AddAccDialog {
    private static JTextField tentf,emailtf,sdttf,passtf;
    private static JRadioButton rdoNam,rdoNu;
    private static  RoundedButton btnAdd,btnEdit;
    private static JLabel heading;
    public static void createDialog(String chucnang,AdminDTO admin) {
        JDialog addDialog = new JDialog();
        addDialog.setTitle("Thêm tài khoản");
        addDialog.setModal(true);
        addDialog.setSize(450, 500);
        addDialog.setLocationRelativeTo(null);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addDialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 20, 12, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Tahoma", Font.PLAIN, 15);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 15);
        Font titleFont = new Font("Tahoma", Font.BOLD, 20);

        // Tiêu đề
         heading = new JLabel("THÊM TÀI KHOẢN", SwingConstants.CENTER);
        heading.setFont(titleFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addDialog.add(heading, gbc);
        gbc.gridwidth = 1;

        // Tên
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblTen = new JLabel("Tên:");
        lblTen.setFont(labelFont);
        addDialog.add(lblTen, gbc);

        gbc.gridx = 1;
         tentf = RoundedComponent.createRoundedTextField(10);
        tentf.setFont(fieldFont);
        tentf.setPreferredSize(new Dimension(250, 30));
        addDialog.add(tentf, gbc);

        // Giới tính
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(labelFont);
        addDialog.add(lblGioiTinh, gbc);

        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setOpaque(false);
         rdoNam = new JRadioButton("Nam");
         rdoNu = new JRadioButton("Nữ");
        rdoNam.setFont(fieldFont);
        rdoNu.setFont(fieldFont);
        ButtonGroup groupGender = new ButtonGroup();
        groupGender.add(rdoNam);
        groupGender.add(rdoNu);
        genderPanel.add(rdoNam);
        genderPanel.add(rdoNu);
        addDialog.add(genderPanel, gbc);

        // Email
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        addDialog.add(lblEmail, gbc);

        gbc.gridx = 1;
         emailtf = RoundedComponent.createRoundedTextField(10);
        emailtf.setFont(fieldFont);
        emailtf.setPreferredSize(new Dimension(250, 30));
        addDialog.add(emailtf, gbc);

        // Liên hệ
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblSdt = new JLabel("Liên hệ:");
        lblSdt.setFont(labelFont);
        addDialog.add(lblSdt, gbc);

        gbc.gridx = 1;
         sdttf = RoundedComponent.createRoundedTextField(10);
        sdttf.setFont(fieldFont);
        sdttf.setPreferredSize(new Dimension(250, 30));
        addDialog.add(sdttf, gbc);

        // Mật khẩu
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(labelFont);
        addDialog.add(lblPass, gbc);

        gbc.gridx = 1;
         passtf = RoundedComponent.createRoundedTextField(10);
        passtf.setFont(fieldFont);
        passtf.setPreferredSize(new Dimension(250, 30));
        addDialog.add(passtf, gbc);

        // Nút Thêm và Hủy
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
         btnAdd = new RoundedButton("Thêm");
        btnEdit = new RoundedButton("Sửa");
        btnEdit.setPreferredSize(new Dimension(70, 30));
        btnEdit.setVisible(false);
        RoundedButton btnCancel = new RoundedButton("Hủy");
        btnAdd.setFont(labelFont);
        btnAdd.setPreferredSize(new Dimension(70, 30));
        btnAdd.setVisible(true);
        btnCancel.setFont(labelFont);
        btnCancel.setPreferredSize(new Dimension(70, 30));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnCancel);
        addDialog.add(buttonPanel, gbc);


        // Sự kiện nút Hủy
        btnCancel.addActionListener(e -> addDialog.dispose());
        btnAdd.addActionListener(e -> {
            String name = tentf.getText().trim();
            String gender = rdoNam.isSelected() ? "Nam" : rdoNu.isSelected() ? "Nữ" : "";
            String email = emailtf.getText().trim();
            String contact = sdttf.getText().trim();
            String password = passtf.getText().trim();

            if (name.isEmpty() || gender.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AdminDTO adminDTO = new AdminDTO(0, name, gender, email, contact, password, 1);
            try {
                Connection cnn = DatabaseConnection.getConnection();
                AdminDAO adminDAO = new AdminDAO(cnn);
                AdminBUS adminBUS = new AdminBUS(adminDAO);

                boolean success = adminBUS.addAdmin(adminDTO);
                ArrayList<AdminDTO> ds = adminBUS.getAllAdminDTOS();
                if (success) {
                    JOptionPane.showMessageDialog(addDialog, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    AccountTable.loadTable(ds);
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Thêm tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addDialog, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        if(chucnang.equals("Sửa")){
            btnEdit.setVisible(true);
            showEditDialog(admin);
        }
        btnEdit.addActionListener(e -> {
            String name = tentf.getText().trim();
            String gender = rdoNam.isSelected() ? "Nam" : rdoNu.isSelected() ? "Nữ" : "";
            String email = emailtf.getText().trim();
            String contact = sdttf.getText().trim();
            String password = passtf.getText().trim();

            if (name.isEmpty() || gender.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Connection cnn = DatabaseConnection.getConnection();
                AdminDAO adminDAO = new AdminDAO(cnn);
                AdminBUS adminBUS = new AdminBUS(adminDAO);

                int selectedRow = AccountTable.getTableModel().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy dòng để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(AccountTable.getTableModel().getValueAt(selectedRow, 0).toString());

                AdminDTO updatedAdmin = new AdminDTO(id, name, gender, email, contact, password, 1);

                boolean success = adminBUS.updateAdmin(updatedAdmin);
                if (success) {
                    ArrayList<AdminDTO> list = adminBUS.getAllAdminDTOS();
                    AccountTable.loadTable(list);
                    JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Hiển thị dialog
        addDialog.setVisible(true);
    }
    public static void showEditDialog(AdminDTO adminDTO) {
        int selectedRow = AccountTable.getTableModel().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }
        heading.setText("SỬA TÀI KHOẢN");
        btnAdd.setVisible(false);
        try {
            System.out.println(adminDTO.toString());
            tentf.setText(adminDTO.getName());
            emailtf.setText(adminDTO.getEmail());
            sdttf.setText(adminDTO.getContact());
            passtf.setText(adminDTO.getPassword());
            if(adminDTO.getGender().equals("Nam")){
                rdoNam.setSelected(true);
            }else{
                rdoNu.setSelected(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }


    }


}
