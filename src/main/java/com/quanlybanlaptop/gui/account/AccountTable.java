package com.quanlybanlaptop.gui.account;

import com.quanlybanlaptop.bus.AdminBUS;
import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.RoleDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountTable {
    private static DefaultTableModel tableModel;
    private static RoundedTable table;
    public static JScrollPane createAccountTable(AdminDTO adminDTO,AdminBUS adminBUS) {
        String[] columnNames = {"MÃ", "TÊN", "GIỚI TÍNH", "EMAIL", "LIÊN HỆ","VAI TRÒ", "TRẠNG THÁI"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new RoundedTable(tableModel);
        table.setColumnWidths(10, 160, 40, 90, 60,60,60);
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
          Connection cnn = DatabaseConnection.getConnection();
        RoleDAO roleDAO = new RoleDAO(cnn);
        RoleBUS roleBUS = new RoleBUS(roleDAO);
        for (AdminDTO admin : adminList) {
            try {
                RoleDTO roleDTO = roleBUS.getRoleById(admin.getIdRole());
                String nameRole = roleDTO.getNameRole();
                tableModel.addRow(new Object[]{
                    admin.getId(),
                    admin.getName(),
                    admin.getGender(),
                    admin.getEmail(),
                    admin.getContact(),
                    nameRole,
                    admin.getStatus() == 1 ? "Hoạt Động" : "Ngừng Hoạt Động"

            });
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }
    }
    public static JTable getTableModel() {
        return table;
    }
}
