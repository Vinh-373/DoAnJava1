package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.gui.customer.CustomerTable;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.util.ExcelExporter;
import com.quanlybanlaptop.util.ImageLoader;
import java.io.File;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.FileInputStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.DataFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopProductPanel {//panel các nút

    public static JPanel createButtonPanel(ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS,SeriProductBUS seriProductBUS) {
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
       RoundedButton btnExportEx = new RoundedButton("Xuất Excell",ImageLoader.loadResourceImage("/img/xuatExcel.png"));
       btnExportEx.setImageSize(32, 32);
        RoundedButton btnImportEx = new RoundedButton("Nhập Excell", ImageLoader.loadResourceImage("/img/export_control.png"));
        btnImportEx.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);

        String[] statusList = {"Hoạt động", "Ngừng HĐ", "Cần Nhập"};
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
                }else if(selectedOs.equals("Ngừng HĐ")){
                    status = 0;
                }else{
                    status = 2;
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
            }else if(selectedOs.equals("Ngừng HĐ")){
                ProductTable.loadProductData(productBUS,0,tfName.getText());
                btnAdd.setEnabled(false);
                btnDelete.setVisible(false);
                btnRestore.setVisible(true);
                btnRestore.setEnabled(true);
                btnEdit.setEnabled(false);

            }else{
                ProductTable.loadProductData(productBUS,2,tfName.getText());
                btnAdd.setEnabled(true);
                btnDelete.setVisible(true);
                btnRestore.setEnabled(false);
                btnEdit.setEnabled(true);
            }
        });
        btnSeeImeis.addActionListener(e -> {
            try {
                SeriListDialog.showDialog(productBUS,seriProductBUS);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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
        btnImportEx.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("D:/Doanjava/QuanLyBanLaptop/src/main/resources/importData"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(file);
                     XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    // Sử dụng DataFormatter để chuyển tất cả giá trị ô thành chuỗi
                    DataFormatter formatter = new DataFormatter();

                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) continue; // Bỏ qua header

                        ProductDTO product = new ProductDTO();
                        product.setIdProduct(0);

                        // Đọc tất cả giá trị ô dưới dạng chuỗi
                        product.setName(formatter.formatCellValue(row.getCell(0)));
                        product.setCpu(formatter.formatCellValue(row.getCell(1)));
                        product.setRam(formatter.formatCellValue(row.getCell(2)));
                        product.setRom(formatter.formatCellValue(row.getCell(3)));
                        product.setGraphicsCard(formatter.formatCellValue(row.getCell(4)));
                        product.setBattery(formatter.formatCellValue(row.getCell(5)));
                        product.setWeight(formatter.formatCellValue(row.getCell(6)));

                        // Cột 7: Price (chuyển từ chuỗi thành BigDecimal)
                        String priceStr = formatter.formatCellValue(row.getCell(7));
                        try {
                            product.setPrice(new BigDecimal(priceStr));
                        } catch (NumberFormatException ex) {
                            product.setPrice(BigDecimal.ZERO);
                            System.err.println("Giá trị Price không hợp lệ tại hàng " + (row.getRowNum() + 1) + ": " + priceStr);
                        }

                        // Thiết lập quantity
                        product.setQuantity(0);
                        product.setQuantityStock(0);

                        // Cột 8: idCategory (chuyển từ chuỗi thành int)
                        String categoryIdStr = formatter.formatCellValue(row.getCell(8));
                        try {
                            product.setIdCategory(Integer.parseInt(categoryIdStr));
                        } catch (NumberFormatException ex) {
                            product.setIdCategory(0);
                            System.err.println("Giá trị idCategory không hợp lệ tại hàng " + (row.getRowNum() + 1) + ": " + categoryIdStr);
                        }

                        // Cột 9: idCompany (chuyển từ chuỗi thành int)
                        String companyIdStr = formatter.formatCellValue(row.getCell(9));
                        try {
                            product.setIdCompany(Integer.parseInt(companyIdStr));
                        } catch (NumberFormatException ex) {
                            product.setIdCompany(0);
                            System.err.println("Giá trị idCompany không hợp lệ tại hàng " + (row.getRowNum() + 1) + ": " + companyIdStr);
                        }

                        product.setImage(formatter.formatCellValue(row.getCell(10)));
                        product.setSizeScreen(formatter.formatCellValue(row.getCell(11)));
                        product.setOperatingSystem(formatter.formatCellValue(row.getCell(12)));

                        // Thiết lập status
                        product.setStatus(1);

                        // Lấy tên danh mục và hãng
                        product.setNameCategory(categoryBUS.getCategoryById(product.getIdCategory()).getCategoryName());
                        product.setNameCompany(companyBUS.getCompanyById(product.getIdCompany()).getCompanyName());

                        // Thêm sản phẩm
                        productBUS.addProduct(product);
                    }

                    JOptionPane.showMessageDialog(null, "Đã nhập Excel thành công!");
                    ProductTable.loadProductData(productBUS, 1, ""); // Refresh bảng
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi nhập Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        btnExportEx.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        
            int userSelection = fileChooser.showSaveDialog(buttonControlPanel); // Đổi this -> buttonControlPanel
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
        
                // Lấy dữ liệu từ bảng
                javax.swing.table.TableModel model = ProductTable.getTableModel();
                java.util.List<String> headers = new ArrayList<>();
                for (int i = 0; i < model.getColumnCount(); i++) {
                    headers.add(model.getColumnName(i));
                }
        
                java.util.List<java.util.List<Object>> data = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    java.util.List<Object> row = new ArrayList<>();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        row.add(model.getValueAt(i, j));
                    }
                    data.add(row);
                }
        
                try {
                    ExcelExporter.exportToExcel(headers, data, "DanhSachSanPham", filePath);
                    JOptionPane.showMessageDialog(buttonControlPanel, "Xuất Excel thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(buttonControlPanel, "Lỗi khi xuất Excel: " + ex.getMessage());
                    ex.printStackTrace();
                }
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