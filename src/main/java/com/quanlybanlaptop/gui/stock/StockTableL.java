package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockTableL {
    public static DefaultTableModel tableModel;
    public static RoundedTable stockTable;
    public static JPanel createStockTable(ProductBUS productBUS) {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"MÃ", "TÊN", "SỐ LƯỢNG"};
        tableModel = new DefaultTableModel(columnNames, 0);

        stockTable = new RoundedTable(tableModel);
        stockTable.setColumnWidths(10, 180, 70);
        stockTable.setFocusable(false); // Không cho focus
        stockTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        stockTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô

        JScrollPane scrollPane = new JScrollPane(stockTable);
        JLabel titleLabel = new JLabel("TÌNH TRẠNG KHO HÀNG");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD,20f));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        loadStockTable(productBUS); // Tải dữ liệu ban đầu

        return panel;
    }
    public static void loadStockTable(ProductBUS productBUS) {
        tableModel.setRowCount(0);
        try {
            ArrayList<ProductDTO> products;
            products = productBUS.getActiveProducts();
            if(products.size() > 0) {
                for(ProductDTO product : products) {
                    Object[] rowData = {
                            product.getIdProduct(),
                            product.getName(),
                            product.getQuantityStock(),
                    };

                        tableModel.addRow(rowData);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadStockTable1(ArrayList<ProductDTO> products) {
        tableModel.setRowCount(0);


            if(products.size() > 0) {
                for(ProductDTO product : products) {
                    Object[] rowData = {
                            product.getIdProduct(),
                            product.getName(),
                            product.getQuantityStock(),
                    };

                    tableModel.addRow(rowData);
                }
            }


    }
    // Lấy chỉ số hàng được chọn
    public static int getSelectedRow() {
        return stockTable.getSelectedRow();
    }

    // Lấy table model để truy cập dữ liệu
    public static DefaultTableModel getTableModel() {
        return tableModel;
    }
}
