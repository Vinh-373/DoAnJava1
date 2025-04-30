package com.quanlybanlaptop.gui.customer;

import com.quanlybanlaptop.bus.CustomerBUS;
import javax.swing.*;
import java.awt.*;

public class CustomerPanel {
    private static JPanel buttonControlPanel;
    

    public static void createCustomerContent(JPanel contentArea, CustomerBUS customerBUS) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));

        // Tạo panel điều khiển phía trên
        buttonControlPanel = new CustomerTopPanel();
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentArea.add(buttonControlPanel, BorderLayout.NORTH);

        // Panel nội dung bảng khách hàng
        JPanel contentCus = new JPanel(new BorderLayout());
        contentCus.setBackground(Color.WHITE);

        // Spacer tạo khoảng cách
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentCus.add(spacer, BorderLayout.NORTH);

        // Bảng khách hàng
        JScrollPane scrollPane = CustomerTable.createCustomerTable(customerBUS);
        contentCus.add(scrollPane, BorderLayout.CENTER);
        contentArea.add(contentCus, BorderLayout.CENTER);
    }

    public static void adjustButtonPanelLayout() {
        if (buttonControlPanel != null) {
            buttonControlPanel.revalidate();
            buttonControlPanel.repaint();
        }
    }
}
