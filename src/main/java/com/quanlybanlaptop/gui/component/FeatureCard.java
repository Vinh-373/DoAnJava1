package com.quanlybanlaptop.gui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class FeatureCard extends JPanel {
    private ImageIcon originalIcon;
    private JLabel iconLabel;
    private JLabel titleLabel;
    private JLabel descLabel;
    private static final double ICON_SIZE_PERCENTAGE = 0.5; // Ảnh chiếm 50% kích thước cha
    private static final float TITLE_FONT_PERCENTAGE = 0.1f; // Font tiêu đề chiếm 10% chiều rộng
    private static final float DESC_FONT_PERCENTAGE = 0.07f; // Font mô tả chiếm 7% chiều rộng

    public FeatureCard(String title, ImageIcon icon, String description) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.originalIcon = (icon != null) ? icon : createDefaultIcon();

        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateIconSize();

        titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTitleFontSize();

        descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateDescFontSize();

        add(iconLabel);
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(descLabel);

        // Lắng nghe thay đổi kích thước của FeatureCard
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateIconSize();
                updateTitleFontSize();
                updateDescFontSize();
            }
        });

        if (icon == null) {
            System.err.println("Không thể tải biểu tượng cho: " + title);
        }
    }

    private void updateIconSize() {
        int parentWidth = getWidth();
        int parentHeight = getHeight();
        if (parentWidth <= 0 || parentHeight <= 0) {
            parentWidth = 100;
            parentHeight = 100;
        }
        int size = (int) (Math.min(parentWidth, parentHeight) * ICON_SIZE_PERCENTAGE);
        Image scaledImage = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void updateTitleFontSize() {
        int width = getWidth();
        if (width <= 0) width = 100; // Giá trị mặc định
        float fontSize = width * TITLE_FONT_PERCENTAGE;
        titleLabel.setFont(new Font("Arial", Font.BOLD, Math.max(12, (int) fontSize))); // Font tối thiểu 12
    }

    private void updateDescFontSize() {
        int width = getWidth();
        if (width <= 0) width = 100; // Giá trị mặc định
        float fontSize = width * DESC_FONT_PERCENTAGE;
        descLabel.setFont(new Font("Arial", Font.PLAIN, Math.max(10, (int) fontSize))); // Font tối thiểu 10
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage defaultImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = defaultImage.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();
        return new ImageIcon(defaultImage);
    }
}