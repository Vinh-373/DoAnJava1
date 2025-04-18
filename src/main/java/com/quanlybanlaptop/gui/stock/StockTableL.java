package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StockTable {
    public static DefaultTableModel tableModel;
    public static RoundedTable stockTable;
    public static JScrollPane createStockTable(ProductBUS productBUS) {
        String[] columnNames = {"MÃ", "TÊN", "SỐ LƯỢNG"};
        tableModel = new DefaultTableModel(columnNames, 0);

        stockTable = new RoundedTable(tableModel);
        stockTable.setColumnWidths(10, 180, 70);
        stockTable.setFocusable(false); // Không cho focus
        stockTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        stockTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(stockTable);

//        loadProductData(productBUS,1,null); // Tải dữ liệu ban đầu
        return scrollPane;
    }
}
