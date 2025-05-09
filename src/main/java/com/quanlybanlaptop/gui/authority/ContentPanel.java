package com.quanlybanlaptop.gui.authority;

import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.RoleDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class ContentPanel {
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color SELECTED_COLOR = new Color(173, 216, 230); // Màu xanh trời nhẹ
    private static final Color HOVER_COLOR = new Color(190, 230, 240);   // Màu xanh trời nhạt hơn cho hover
    private static JButton selectedButton = null;
    private static JPanel contentRPanel;
    private static final String[] AUTHORITIES = {
        "Quản lý sản phẩm",
        "Quản lý loại hãng",
        "Quản lý khách hàng",
        "Quản lý tài khoản",
        "Quản lý hóa đơn",
        "Quản lý kho hàng",
        "Quản lý bảo hành",
        "Quản lý thống kê",
        "Quản lý phân quyền"
    };

    public static JPanel createContentPanel() {
        // Initialize database connection and BUS
        Connection cnn = DatabaseConnection.getConnection();
        RoleDAO roleDAO = new RoleDAO(cnn);
        RoleBUS roleBUS = new RoleBUS(roleDAO);
        ArrayList<RoleDTO> rolesList = new ArrayList<>();
        try {
            rolesList = roleBUS.getAllRoles();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách vai trò: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Top panel (placeholder, can be customized)
        JPanel contentTPanel = new JPanel();
        contentTPanel.setPreferredSize(new Dimension(0, 25));
        contentTPanel.setBackground(new Color(239, 237, 237));
        contentPanel.add(contentTPanel, BorderLayout.NORTH);

        // Use JSplitPane to remove padding between left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(150); // Width of left panel
        splitPane.setDividerSize(0); // No divider padding
        splitPane.setBorder(null); // No border for split pane

        // Left panel: Role buttons
        JPanel contentLPanel = new JPanel();
        contentLPanel.setLayout(new BoxLayout(contentLPanel, BoxLayout.Y_AXIS));
        contentLPanel.setBackground(Color.WHITE);
        contentLPanel.setPreferredSize(new Dimension(150, 0));

        // Add role buttons
        JButton firstButton = null; // To track the first button
        for (int i = 0; i < rolesList.size(); i++) {
            RoleDTO role = rolesList.get(i);
            JButton roleBtn = new JButton(role.getNameRole() + " (" + role.getIdRole() + ")");
            roleBtn.setPreferredSize(new Dimension(150, 40));
            roleBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            roleBtn.setBackground(DEFAULT_COLOR);
            roleBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            roleBtn.setBorder(null); // No border
            roleBtn.setFocusPainted(false);
            roleBtn.setOpaque(true); // Ensure background color is visible

            // Store the first button
            if (i == 0) {
                firstButton = roleBtn;
            }

            // Action listener for button click
            roleBtn.addActionListener(e -> {
                if (selectedButton != null && selectedButton != roleBtn) {
                    selectedButton.setBackground(DEFAULT_COLOR);
                }
                selectedButton = roleBtn;
                roleBtn.setBackground(SELECTED_COLOR);
                updateContentArea(role.getNameRole());
            });

            // Mouse listener for hover effect
            roleBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (roleBtn != selectedButton) {
                        roleBtn.setBackground(HOVER_COLOR);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (roleBtn != selectedButton) {
                        roleBtn.setBackground(DEFAULT_COLOR);
                    }
                }
            });

            contentLPanel.add(roleBtn);
            contentLPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing between buttons
        }

        // Wrap left panel in JScrollPane for scrolling
        JScrollPane leftScrollPane = new JScrollPane(contentLPanel);
        leftScrollPane.setBorder(null);
        splitPane.setLeftComponent(leftScrollPane);

        // Right panel: Content area (white background initially, light blue when role selected)
        contentRPanel = new JPanel(new BorderLayout());
        contentRPanel.setBackground(Color.WHITE); // Default white
        contentRPanel.add(new JLabel("Chọn một vai trò để hiển thị nội dung", SwingConstants.CENTER), BorderLayout.CENTER);
        splitPane.setRightComponent(contentRPanel);

        contentPanel.add(splitPane, BorderLayout.CENTER);

        // Select the first button by default if it exists
        if (firstButton != null && !rolesList.isEmpty()) {
            selectedButton = firstButton;
            firstButton.setBackground(SELECTED_COLOR);
            updateContentArea(rolesList.get(0).getNameRole());
        }

        return contentPanel;
    }

    // Update right content area when a role is clicked
    private static void updateContentArea(String roleName) {
        contentRPanel.removeAll();
        contentRPanel.setBackground(SELECTED_COLOR); // Light blue when role selected

        // Create panel for authorities list
        JPanel authoritiesPanel = new JPanel();
        authoritiesPanel.setLayout(new BoxLayout(authoritiesPanel, BoxLayout.Y_AXIS));
        authoritiesPanel.setBackground(SELECTED_COLOR);
        authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between rows
        // Add each authority as a row with checkbox and label
        for (String authority : AUTHORITIES) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            rowPanel.setBackground(SELECTED_COLOR);
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBackground(SELECTED_COLOR);
            checkBox.setPreferredSize(new Dimension(50, 50));

            JLabel label = new JLabel(authority);
            label.setFont(new Font("Tahoma", Font.PLAIN, 15));

            rowPanel.add(checkBox);
            rowPanel.add(label);
            authoritiesPanel.add(rowPanel);
            authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between rows
        }

        // Wrap authorities panel in JScrollPane for scrolling
        JScrollPane authoritiesScrollPane = new JScrollPane(authoritiesPanel);
        authoritiesScrollPane.setBorder(null);
        contentRPanel.add(authoritiesScrollPane, BorderLayout.CENTER);

        contentRPanel.revalidate();
        contentRPanel.repaint();
    }
}