package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TopProductPanel {//panel các nút

    public static JPanel createButtonPanel(ProductBUS productBUS) {
        JPanel buttonControlPanel = new JPanel(new GridBagLayout());
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        RoundedButton btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        RoundedButton btnDelete = new RoundedButton("Tắt HĐ",  ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnSeeDetail = new RoundedButton("Chi tiết",ImageLoader.loadResourceImage("/img/detail_control.png"));
        btnSeeDetail.setImageSize(32, 32);
        RoundedButton btnSeeImeis = new RoundedButton("Ds Seri", ImageLoader.loadResourceImage("/img/serinumber.png"));
        btnSeeImeis.setImageSize(32, 32);
        RoundedButton btnImportEx = new RoundedButton("Xuất PDF",ImageLoader.loadResourceImage("/img/pdf.png"));
        btnImportEx.setImageSize(32, 32);
        RoundedButton btnExportEx = new RoundedButton("Nhập Excel", ImageLoader.loadResourceImage("/img/export_control.png"));
        btnExportEx.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);
        btnRefresh.addActionListener(e -> ProductTable.loadProductData(productBUS));

        String[] companyList = {"Hoạt động", "Ngừng HĐ"};
        JComboBox<String> companycb = RoundedComponent.createRoundedComboBox(companyList, 10);
        companycb.setPreferredSize(new Dimension(100, 35));

        JTextField tfName = RoundedComponent.createRoundedTextField(10);
        tfName.setPreferredSize(new Dimension(200, 35));

        // Sự kiện cho nút Xóa
        btnDelete.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            System.out.println("Selected row: " + selectedRow); // Debug để kiểm tra
            if (selectedRow >= 0) {
                String idProduct = (String) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct); // Debug để kiểm tra
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn tắt HĐ sản phẩm ID " + idProduct + " khỏi cửa hàng?",
                        "Xác nhận tắt", JOptionPane.YES_NO_OPTION);
                System.out.println("Confirm result: " + confirm); // Debug để kiểm tra
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = productBUS.deleteProduct(idProduct);
                        if (success) {
                            ProductTable.loadProductData(productBUS);
                            JOptionPane.showMessageDialog(null, "Đã xóa sản phẩm khỏi cửa hàng thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa sản phẩm thất bại: Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        //Sự kiện nút xem chi tiết
        btnSeeDetail.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            if (selectedRow >= 0) {
                String idProduct = (String) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct);
                try {
                    ProductDTO product = productBUS.getProductById(idProduct);
                    if (product != null) {
                        ProductDetailPanel.showProductDetailDialog(product);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) { // Dòng 82: Lỗi ở đây
                    JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        //Su kienj nút thêm
        btnAdd.addActionListener(e -> {
            AddProductPanel.showAddProduct();


        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; buttonControlPanel.add(btnAdd, gbc);
        gbc.gridx = 1; buttonControlPanel.add(btnEdit, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnDelete, gbc);
        gbc.gridx = 3; buttonControlPanel.add(btnSeeDetail, gbc);
        gbc.gridx = 4; buttonControlPanel.add(btnSeeImeis, gbc);
        gbc.gridx = 5; buttonControlPanel.add(btnImportEx, gbc);
        gbc.gridx = 6; buttonControlPanel.add(btnExportEx, gbc);
        gbc.gridx = 7; buttonControlPanel.add(btnRefresh, gbc);
        gbc.gridx = 8; gbc.gridwidth = 2; buttonControlPanel.add(companycb, gbc);
        gbc.gridx = 10; gbc.gridwidth = 4; buttonControlPanel.add(tfName, gbc);

        return buttonControlPanel;
    }
}