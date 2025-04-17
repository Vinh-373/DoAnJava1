package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopProductPanel {//panel các nút

    public static JPanel createButtonPanel(ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS) {
        JPanel buttonControlPanel = new JPanel(new GridBagLayout());
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        RoundedButton btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        RoundedButton btnDelete = new RoundedButton("Tắt HĐ",  ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnRestore = new RoundedButton("Bật HĐ",  ImageLoader.loadResourceImage("/img/restore_control.png"));
        btnRestore.setImageSize(32, 32);
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

        String[] statusList = {"Hoạt động", "Ngừng HĐ"};
        JComboBox<String> statuscb = RoundedComponent.createRoundedComboBox(statusList, 10);
        statuscb.setPreferredSize(new Dimension(100, 35));

        JTextField tfName = RoundedComponent.createRoundedTextField(10);
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
                String selectedOs = (String) statuscb.getSelectedItem();
                int status;
                if(selectedOs.equals("Hoạt động")){
                    status = 1;
                }else {
                    status = 0;
                }
                System.out.println(status);
                // Gọi hàm load dữ liệu tìm kiếm
                ProductTable.loadProductData(productBUS, status, keyword);
            }
        });



        // Sự kiện cho nút Xóa
        btnDelete.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            System.out.println("Selected row: " + selectedRow); // Debug để kiểm tra
            if (selectedRow >= 0) {
                int idProduct = (int) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct); // Debug để kiểm tra
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn tắt HĐ sản phẩm ID " + idProduct + " khỏi cửa hàng?",
                        "Xác nhận tắt", JOptionPane.YES_NO_OPTION);
                System.out.println("Confirm result: " + confirm); // Debug để kiểm tra
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = productBUS.setStatusProduct(idProduct,0);
                        if (success) {
                            if(statuscb.equals("Hoạt động")){
                                ProductTable.loadProductData(productBUS,0,tfName.getText());
                            }else{
                                ProductTable.loadProductData(productBUS,1,tfName.getText());
                            }
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
                int idProduct = (int) ProductTable.getTableModel().getValueAt(selectedRow, 0);
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
            try {
                AddProductPanel.showAddProduct(productBUS,categoryBUS,companyBUS,"Thêm");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        });
        btnEdit.addActionListener(e -> {
            try {
                AddProductPanel.showAddProduct(productBUS,categoryBUS,companyBUS,"Sửa");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        statuscb.addActionListener(e -> {
            String selectedOs = (String) statuscb.getSelectedItem();
            if(selectedOs.equals("Hoạt động")){
                ProductTable.loadProductData(productBUS, 1,tfName.getText());
                btnAdd.setEnabled(true);
                btnDelete.setVisible(true);
                btnRestore.setEnabled(false);
                btnEdit.setEnabled(true);
            }else {
                ProductTable.loadProductData(productBUS,0,tfName.getText());
                btnAdd.setEnabled(false);
                btnDelete.setVisible(false);
                btnRestore.setVisible(true);
                btnRestore.setEnabled(true);
                btnEdit.setEnabled(false);

            }
        });
        btnRefresh.addActionListener(e -> {
            String selectedOs = (String) statuscb.getSelectedItem();
            if(selectedOs.equals("Hoạt động")){
                ProductTable.loadProductData(productBUS, 1,tfName.getText());
            }else{
                ProductTable.loadProductData(productBUS,0,tfName.getText());
            }
        });
        btnRestore.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            System.out.println("Selected row: " + selectedRow); // Debug để kiểm tra
            if (selectedRow >= 0) {
                int idProduct = (int) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct); // Debug để kiểm tra
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn bật HĐ sản phẩm ID " + idProduct + " ở cửa hàng?",
                        "Xác nhận bật", JOptionPane.YES_NO_OPTION);
                System.out.println("Confirm result: " + confirm); // Debug để kiểm tra
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = productBUS.setStatusProduct(idProduct,1);
                        if (success) {
                            ProductTable.loadProductData(productBUS,0,tfName.getText());
                            JOptionPane.showMessageDialog(null, "Đã bật sản phẩm ở cửa hàng thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Bật sản phẩm thất bại: Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi bật sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để bật!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; buttonControlPanel.add(btnAdd, gbc);
        gbc.gridx = 1; buttonControlPanel.add(btnEdit, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnDelete, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnRestore, gbc);
        gbc.gridx = 3; buttonControlPanel.add(btnSeeDetail, gbc);
        gbc.gridx = 4; buttonControlPanel.add(btnSeeImeis, gbc);
        gbc.gridx = 5; buttonControlPanel.add(btnImportEx, gbc);
        gbc.gridx = 6; buttonControlPanel.add(btnExportEx, gbc);
        gbc.gridx = 7; buttonControlPanel.add(btnRefresh, gbc);
        gbc.gridx = 8; gbc.gridwidth = 2; buttonControlPanel.add(statuscb, gbc);
        gbc.gridx = 10; gbc.gridwidth = 4; buttonControlPanel.add(tfName, gbc);

        return buttonControlPanel;
    }
}