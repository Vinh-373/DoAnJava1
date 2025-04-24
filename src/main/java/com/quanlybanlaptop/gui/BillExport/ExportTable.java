package com.quanlybanlaptop.gui.BillExport;

import com.quanlybanlaptop.bus.BillExDetailBUS;
import com.quanlybanlaptop.bus.BillExportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.dto.BillExportDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExportTable {
    private static DefaultTableModel tableModel;
    private static RoundedTable BillExportTable;
    public static JScrollPane createTable(BillExportBUS billExportBUS, ProductBUS productBUS,BillExDetailBUS billExDetailBUS) {
        String[] columnNames = {"MÃ", "TÊN NGƯỜI BÁN(ID)", "TÊN KHÁCH HÀNG(ID)", "TỔNG TIỀN"};
        tableModel = new DefaultTableModel(columnNames, 0);

        BillExportTable = new RoundedTable(tableModel);
        BillExportTable.setColumnWidths(10, 180, 180, 100);
        BillExportTable.setFocusable(false); // Không cho focus
        BillExportTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        BillExportTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        BillExportTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = BillExportTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String id = BillExportTable.getValueAt(selectedRow, 0).toString();
                        String nameAdmin = BillExportTable.getValueAt(selectedRow, 1).toString();
                        String nameCustomer = BillExportTable.getValueAt(selectedRow, 2).toString();
                        String exportDate = "";
                        try {
                            exportDate = String.valueOf((billExportBUS.getBillExportById(Integer.parseInt(id))).getDateEx());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        ExportRight.getTxtID().setText(id);
                        ExportRight.getTxtNV().setText(nameAdmin);
                        ExportRight.getTxtDate().setText(exportDate);
                        ExportRight.getTxtKH().setText(nameCustomer);

                            ExportRight.loadBillExportDetails(billExDetailBUS,productBUS);
                            ExportRight.isVisible(1);

                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(BillExportTable);

        loadBillExData(billExportBUS); // Tải dữ liệu ban đầu
        return scrollPane;
    }
    public static void loadBillExData(BillExportBUS billExportBUS) {
        tableModel.setRowCount(0);
        try {
            ArrayList<BillExportDTO> billExs;

            billExs = billExportBUS.getAllBillExports();
            DecimalFormat df = new DecimalFormat("#,###");
            if (billExs != null) {
                for (BillExportDTO billEx : billExs) {
                    Object[] rowData = {
                            billEx.getIdBillEx(),
                            billEx.getNameAdmin() + " (" + billEx.getIdAdmin() + ")",
                            billEx.getNameCustomer() + " (" + billEx.getIdCustomer() + ")",
                            df.format( billEx.getTotalPrice()) + " VNĐ",
                            billEx.getTotalProduct(),
                            billEx.getStatus()
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
    public static void loadBillExData1( ArrayList<BillExportDTO> bills) {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###");
        for (BillExportDTO bill : bills) {
            tableModel.addRow(new Object[]{
                    bill.getIdBillEx(),
                    bill.getNameAdmin() + " (" + bill.getIdAdmin() + ")",
                    bill.getNameCustomer() + " (" + bill.getIdCustomer() + ")",
                    df.format( bill.getTotalPrice()) + " VNĐ",
                    bill.getTotalProduct(),
                    bill.getStatus()
            });
        }
    }
}
