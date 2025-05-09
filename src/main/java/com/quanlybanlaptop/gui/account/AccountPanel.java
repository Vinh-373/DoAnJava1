package com.quanlybanlaptop.gui.account;

import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dao.AdminDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.product.TopProductPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AccountPanel {
    private static JPanel buttonControlPanel;
    private static JScrollPane tableScrollPane;
    public static void createAccountPanel(AdminDTO adminDTO,JPanel contentArea) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));
        buttonControlPanel = TopButton.createButtonPanel(adminDTO);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        Connection conn = DatabaseConnection.getConnection();
        AdminDAO adminDAO = new AdminDAO(conn);
        AdminBUS adminBUS = new AdminBUS(adminDAO);
        tableScrollPane = AccountTable.createAccountTable(adminBUS);
        contentArea.add(buttonControlPanel, BorderLayout.NORTH);
        contentArea.add(tableScrollPane, BorderLayout.CENTER);
    }
}
