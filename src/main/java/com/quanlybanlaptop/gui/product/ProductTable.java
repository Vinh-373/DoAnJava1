package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.dto.ProductDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductTable {//bảng sản phẩm
    private static DefaultTableModel tableModel;
    private static RoundedTable productTable;

    public static JScrollPane createProductTable(ProductBUS productBUS) {
        String[] columnNames = {"MÃ", "TÊN", "SỐ LƯỢNG", "DANH MỤC", "HÃNG", "SELLING PRICE"};
        tableModel = new DefaultTableModel(columnNames, 0);

        productTable = new RoundedTable(tableModel);
        productTable.setColumnWidths(10, 180, 70, 50, 70, 100);
        productTable.setFocusable(false); // Không cho focus
        productTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        productTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(productTable);

        loadProductData(productBUS,1,null); // Tải dữ liệu ban đầu
        return scrollPane;
    }

    public static void loadProductData(ProductBUS productBUS, int status, String keyword) {
        tableModel.setRowCount(0);
        try {
            ArrayList<ProductDTO> products;
            if (status == 1 && (keyword == null || keyword.isEmpty())) {
                products = productBUS.getActiveProducts();
            }else if(status == 0 && (keyword == null || keyword.isEmpty())) {
                products = productBUS.getInactiveProducts();
            }else if(status == 1 && (keyword != null || !keyword.isEmpty())) {
                products = productBUS.searchProductsByName(keyword,"Hoạt động");
            }else if(status == 0 && (keyword != null || !keyword.isEmpty())) {
                products = productBUS.searchProductsByName(keyword,"Ngừng HĐ");
            }else if(status == 2 && (keyword == null || keyword.isEmpty())) {
                products = productBUS.getOutOfStock(1);
            }else{
                products = productBUS.searchProductsByName(keyword,"Cần nhập");
            }
            DecimalFormat df = new DecimalFormat("#,###");
            if (products != null) {
                for (ProductDTO product : products) {
                    Object[] rowData = {
                            product.getIdProduct(),
                            product.getName(),
                            product.getQuantity(),
                            product.getNameCategory(),
                            product.getNameCompany(),
                            df.format(product.getPrice()) + " VNĐ"
                    };
                            tableModel.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy chỉ số hàng được chọn
    public static int getSelectedRow() {
        return productTable.getSelectedRow();
    }

    // Lấy table model để truy cập dữ liệu
    public static DefaultTableModel getTableModel() {
        return tableModel;
    }


}