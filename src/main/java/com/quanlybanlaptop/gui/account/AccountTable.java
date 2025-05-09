package com.quanlybanlaptop.gui.account;

import com.quanlybanlaptop.bus.AdminBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountTable {
    private static DefaultTableModel tableModel;
    private static RoundedTable table;
    public static JScrollPane createAccountTable(AdminBUS adminBUS) {
        String[] columnNames = {"MÃ", "TÊN", "GIỚI TÍNH", "EMAIL", "LIÊN HỆ", "MẬT KHẨU","VAI TRÒ", "TRẠNG THÁI"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new RoundedTable(tableModel);
        table.setColumnWidths(10, 140, 30, 90, 50, 60,50,50);
        table.setFocusable(false); // Không cho focus
        table.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        table.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(table);
        try {
            ArrayList<AdminDTO> adminDTOS = adminBUS.getActiveAdmin(1);
            loadTable(adminDTOS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scrollPane;
    }
    public static void loadTable(ArrayList<AdminDTO> adminList) {
        tableModel.setRowCount(0);
        for (AdminDTO admin : adminList) {
            tableModel.addRow(new Object[]{
                    admin.getId(),
                    admin.getName(),
                    admin.getGender(),
                    admin.getEmail(),
                    admin.getContact(),
                    admin.getPassword(),
                    admin.getIdRole() == 1 ? "Chủ" :
                    admin.getIdRole() == 2 ? "Quản Lý": "Nhân Viên",
                    admin.getStatus() == 1 ? "Hoạt Động" : "Ngừng Hoạt Động"

            });
        }
    }
    public static JTable getTableModel() {
        return table;
    }
}
