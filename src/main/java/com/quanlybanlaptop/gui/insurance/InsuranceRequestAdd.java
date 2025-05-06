package com.quanlybanlaptop.gui.insurance;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.quanlybanlaptop.bus.InsuranceBUS;
import com.quanlybanlaptop.bus.InsuranceClaimBUS;

public class InsuranceRequestAdd extends JDialog {
    private JTable insuredProductTable;
    private JTextArea descriptionArea;
    private JButton btnSave;
    private JButton btnCancel;

    public InsuranceRequestAdd(JFrame parent, InsuranceBUS insuranceBUS, InsuranceClaimBUS insuranceClaimBUS)  {
        super(parent, "Thêm yêu cầu bảo hành", true);
        setSize(900, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Lấy dữ liệu từ BUS (giả sử list trả về đủ các cột cần thiết)
        String[] columnNames = {"Khách hàng", "Tên sản phẩm", "Số Seri", "Ngày bắt đầu", "Ngày kết thúc", "id_insurance", "id_admin", "id_product"};
        List<Object[]> list = insuranceBUS.getInsuredSeriInfo();
        Object[][] data = new Object[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < columnNames.length; j++) {
                data[i][j] = list.get(i)[j];
            }
        }

        insuredProductTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        // Ẩn các cột id_insurance, id_admin, id_product
        insuredProductTable.removeColumn(insuredProductTable.getColumnModel().getColumn(7));
        insuredProductTable.removeColumn(insuredProductTable.getColumnModel().getColumn(6));
        insuredProductTable.removeColumn(insuredProductTable.getColumnModel().getColumn(5));
        insuredProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        insuredProductTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        insuredProductTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        insuredProductTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        insuredProductTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        insuredProductTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        insuredProductTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane tableScroll = new JScrollPane(insuredProductTable);
        tableScroll.setPreferredSize(new Dimension(700, 320));

        // Panel nhập nội dung yêu cầu bên phải
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblDesc = new JLabel("Nội dung yêu cầu:");
        descriptionArea = new JTextArea(7, 18);
        JScrollPane descScroll = new JScrollPane(descriptionArea);

        JPanel descPanel = new JPanel(new BorderLayout(5, 5));
        descPanel.add(lblDesc, BorderLayout.NORTH);
        descPanel.add(descScroll, BorderLayout.CENTER);

        // Nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        rightPanel.add(descPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Layout chia đôi
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.add(tableScroll);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            int selectedRow = insuredProductTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm đã bảo hành!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String description = descriptionArea.getText().trim();
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung yêu cầu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Lấy lại index thực tế của các cột ẩn
            int modelRow = insuredProductTable.convertRowIndexToModel(selectedRow);
            Object[] rowData = list.get(modelRow);
            int idInsurance = Integer.parseInt(rowData[5].toString());
            int idAdmin = Integer.parseInt(rowData[6].toString());
            int idProduct = Integer.parseInt(rowData[7].toString());
            String numSeri = rowData[2].toString();

            // Gọi qua BUS để thêm yêu cầu bảo hành
            boolean ok = insuranceClaimBUS.addInsuranceClaim(idInsurance, idAdmin, idProduct, numSeri, description, "Đang xử lý");
            if (ok) {
                JOptionPane.showMessageDialog(this, "Thêm yêu cầu bảo hành thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm yêu cầu bảo hành!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}