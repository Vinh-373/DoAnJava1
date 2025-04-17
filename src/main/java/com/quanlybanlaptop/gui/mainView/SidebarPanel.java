package com.quanlybanlaptop.gui.mainView;

import com.quanlybanlaptop.util.ImageLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SidebarPanel extends JPanel {
    private JPanel buttonPanel;
    private ContentChangeListener listener;
    private JButton selectedButton;
    private JLabel userLabel;
    private static final float FONT_SIZE_PERCENTAGE = 0.08f; // Font chiếm 8% chiều rộng

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        setPreferredSize(new Dimension(200, 600));

        // Tải và hiển thị ảnh đại diện
        ImageIcon icon = ImageLoader.loadResourceImage("/img/manager.png");
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel avatarLabel = new JLabel(new ImageIcon(scaledImage));
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createVerticalStrut(20));
            add(avatarLabel);
        } else {
            System.err.println("Không thể tải ảnh avatar.png");
        }

        // Tên người dùng
        userLabel = new JLabel("Quang Vinh");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateUserFontSize(); // Cập nhật kích thước font ban đầu
        userLabel.setForeground(new Color(50, 50, 50));
        add(Box.createVerticalStrut(10));
        add(userLabel);
        add(Box.createVerticalStrut(20));

        // Danh sách các nút và đường dẫn ảnh
        String[][] menuItems = {
                {"Trang chủ", "/img/home.png"},
                {"Sản phẩm", "/img/product.png"},
                {"Loại, Hãng", "/img/brand.png"},
                {"Tài khoản", "/img/account.png"},
                {"Khách hàng", "/img/customer.png"},
                {"Đơn hàng", "/img/order.png"},
                {"Hóa đơn", "/img/order.png"},
                {"Nhà cung cấp", "/img/supplier.png"},
                {"Kho hàng", "/img/import.png"},
                {"Bảo hành", "/img/warranty.png"},
                {"Thống kê", "/img/statistics.png"},
                {"Đăng xuất", "/img/logout.png"}
        };

        buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(false);
        buttonPanel.add(Box.createVerticalGlue());

        for (String[] item : menuItems) {
            ImageIcon iconItem = ImageLoader.loadResourceImage(item[1]);
            if (iconItem != null) {
                Image scaledImageItem = iconItem.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                ImageIcon scaledIconItem = new ImageIcon(scaledImageItem);

                JButton button = new JButton(item[0], scaledIconItem) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(getBackground());
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                        super.paintComponent(g);
                        g2.dispose();
                    }

                    @Override
                    protected void paintBorder(Graphics g) {}
                };

                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(180, 40));
                button.setPreferredSize(new Dimension(180, 40));
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                updateButtonFontSize(button); // Cập nhật kích thước font ban đầu
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setHorizontalAlignment(SwingConstants.LEFT);

                Color defaultColor = Color.WHITE;
                Color hoverColor = defaultColor.darker();
                Color selectedColor = new Color(0, 128, 255);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (button != selectedButton) {
                            button.setBackground(hoverColor);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (button != selectedButton) {
                            button.setBackground(defaultColor);
                        }
                    }
                });

                button.addActionListener(e -> {
                    if (selectedButton != null && selectedButton != button) {
                        selectedButton.setBackground(defaultColor);
                    }
                    selectedButton = button;
                    button.setBackground(selectedColor);
                    if (listener != null) {
                        listener.onContentChange(item[0]);
                    }
                });

                if (item[0].equals("Trang chủ")) {
                    selectedButton = button;
                    button.setBackground(selectedColor);
                }

                buttonPanel.add(button);
                buttonPanel.add(Box.createVerticalStrut(5));
            } else {
                System.err.println("Không thể tải ảnh: " + item[1]);
            }
        }

        add(buttonPanel);

        // Lắng nghe thay đổi kích thước của SidebarPanel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateUserFontSize();
                for (Component comp : buttonPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        updateButtonFontSize((JButton) comp);
                        int buttonWidth = getWidth() - 40;
                        buttonWidth = Math.max(buttonWidth, 180);
                        comp.setMaximumSize(new Dimension(buttonWidth, 40));
                        comp.setPreferredSize(new Dimension(buttonWidth, 40));
                    }
                }
                buttonPanel.revalidate();
            }
        });
    }

    private void updateUserFontSize() {
        int width = getWidth();
        if (width <= 0) width = 200; // Giá trị mặc định
        float fontSize = width * FONT_SIZE_PERCENTAGE;
        userLabel.setFont(new Font("Arial", Font.BOLD, Math.max(12, (int) fontSize))); // Đảm bảo font không nhỏ hơn 12
    }

    private void updateButtonFontSize(JButton button) {
        int width = getWidth();
        if (width <= 0) width = 200; // Giá trị mặc định
        float fontSize = width * FONT_SIZE_PERCENTAGE;
        button.setFont(new Font("Arial", Font.PLAIN, Math.max(10, (int) fontSize))); // Đảm bảo font không nhỏ hơn 10
    }

    public void setContentChangeListener(ContentChangeListener listener) {
        this.listener = listener;
    }
}