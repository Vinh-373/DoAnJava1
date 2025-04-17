package com.quanlybanlaptop.gui.category_brand;

import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class CategoryBrandPanel {
    private static JPanel contentSwitcherPanel;
    private static CardLayout cardLayout;

    public static void createCategoryBrandContent(JPanel contentArea, CategoryBUS categoryBUS, CompanyBUS companyBUS) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        RoundedButton btnCategory = new RoundedButton("Loại", ImageLoader.loadResourceImage("/img/category.png"));
        RoundedButton btnBrand = new RoundedButton("Hãng", ImageLoader.loadResourceImage("/img/brand.png"));
        btnCategory.setPreferredSize(new Dimension(80, 70));
        btnBrand.setPreferredSize(new Dimension(80, 70));
        buttonPanel.add(btnCategory);
        buttonPanel.add(btnBrand);
        buttonPanel.setBackground(new Color(239, 237, 237));
        contentArea.add(buttonPanel, BorderLayout.NORTH);

        // Panel chứa 2 panel chính và dùng CardLayout để chuyển đổi
        cardLayout = new CardLayout();
        contentSwitcherPanel = new JPanel(cardLayout);

        JPanel categoryPanel = CategoryPanel.createCategoryPanel(contentArea, categoryBUS);
        JPanel brandPanel = BrandPanel.createBrandPanel(contentArea, companyBUS);

        contentSwitcherPanel.add(categoryPanel, "Category");
        contentSwitcherPanel.add(brandPanel, "Brand");

        contentArea.add(contentSwitcherPanel, BorderLayout.CENTER);

        // Sự kiện bấm nút
        btnCategory.addActionListener(e -> cardLayout.show(contentSwitcherPanel, "Category"));
        btnBrand.addActionListener(e -> cardLayout.show(contentSwitcherPanel, "Brand"));
    }
}
