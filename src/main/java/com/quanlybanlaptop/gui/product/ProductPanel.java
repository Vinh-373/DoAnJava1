package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;

import javax.swing.*;
import java.awt.*;

public class ProductPanel {//panel cha
    private static JPanel buttonControlPanel;

    public static void createProductContent(JPanel contentArea, ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS, SeriProductBUS seriProductBUS) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));
        buttonControlPanel = TopProductPanel.createButtonPanel(productBUS,categoryBUS,companyBUS,seriProductBUS);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentArea.add(buttonControlPanel, BorderLayout.NORTH);

        JPanel contentPrd = new JPanel(new BorderLayout());
        contentPrd.setBackground(Color.WHITE);
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentPrd.add(spacer, BorderLayout.NORTH);

        JScrollPane scrollPane = ProductTable.createProductTable(productBUS);
        contentPrd.add(scrollPane, BorderLayout.CENTER);
        contentArea.add(contentPrd, BorderLayout.CENTER);
    }

    public static void adjustButtonPanelLayout() {
        if (buttonControlPanel != null) {
            buttonControlPanel.revalidate();
            buttonControlPanel.repaint();
        }
    }
}