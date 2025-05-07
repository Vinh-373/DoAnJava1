package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.BillImportBUS;
import com.quanlybanlaptop.dto.BillExportDTO;
import com.quanlybanlaptop.dto.BillImportDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class StockTableR {
    public static DefaultTableModel tableModel;
    public static RoundedTable importTable;//Nhâp lên cưa hàng
    public static RoundedTable IPTable;
    public static JLabel titleLabel;
    public static JPanel createStockPanel(BillImportBUS billImportBUS) {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"MÃ SP", "TÊN NHÂN VIÊN", "TỔNG SP","NGÀY XUẤT","ĐƠN GIÁ"};
        tableModel = new DefaultTableModel(columnNames, 0);

        importTable = new RoundedTable(tableModel);
        importTable.setColumnWidths(10, 180, 70,100);
        importTable.setFocusable(false); // Không cho focus
        importTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        importTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(importTable);
        titleLabel = new JLabel("LỊCH SỬ PHIẾU NHẬP KHO");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD,20f));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        loadBillImExTable(billImportBUS,0); // Tải dữ liệu ban đầu
        return panel;
    }
    public static void loadBillImExTable(BillImportBUS billImportBUS, int status) {
        tableModel.setRowCount(0);
        try {
            ArrayList<BillImportDTO> bills;
            bills = billImportBUS.getAllBillIm();
            if(bills.size() > 0) {
                for(BillImportDTO bill : bills) {

                    if(status == 0 && bill.getQuantity() < 0) {
                        titleLabel.setText("LỊCH SỬ PHIẾU NHẬP KHO");
                        Object[] rowData = {
                                bill.getIdProduct(),
                                bill.getNameAdmin()+"-" + bill.getIdAdmin(),
                                bill.getQuantity()*-1,
                                bill.getImportDate(),
                                bill.getUnitPrice()
                        };
                        tableModel.addRow(rowData);
                    }
                    if(status == 1 && bill.getQuantity() > 0) {
                        titleLabel.setText("LỊCH SỬ PHIẾU XUẤT KHO");

                        Object[] rowData = {
                                bill.getIdProduct(),
                                bill.getNameAdmin(),
                                bill.getQuantity(),
                                bill.getImportDate(),
                        };
                        tableModel.addRow(rowData);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadBillExData1( ArrayList<BillImportDTO> bills) {
        tableModel.setRowCount(0);

        for (BillImportDTO bill : bills) {
            if(bill.getQuantity() < 0) {
                tableModel.addRow(new Object[]{
                        bill.getIdProduct(),
                        bill.getNameAdmin(),
                        bill.getQuantity()*(-1),
                        bill.getImportDate(),
                });
            }else{
                tableModel.addRow(new Object[]{
                        bill.getIdProduct(),
                        bill.getNameAdmin(),
                        bill.getQuantity(),
                        bill.getImportDate(),
                });
            }

        }
    }


}
