
package com.quanlybanlaptop.gui.authority;

import com.quanlybanlaptop.bus.AuthoritiesBUS;
import com.quanlybanlaptop.bus.CTQBUS;
import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.dao.AuthoritiesDAO;
import com.quanlybanlaptop.dao.CTQDAO;
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

    private static final String[][] BUTTON = {
        {"Thêm", "Sửa", "Xóa", "Chi tiết", "DS Seri", "Nhập Ex", "Xuất Ex"}, // Quản lý sản phẩm
        {"Thêm", "Sửa", "Xóa"}, // Quản lý loại hãng
        {"Thêm", "Sửa", "Xóa", "Xuất Ex"}, // Quản lý khách hàng
        {"Thêm", "Sửa", "Xóa"}, // Quản lý tài khoản
        {"Thêm", "Xóa", "Xuất PDF"}, // Quản lý hóa đơn
        {"Nhập", "Xuất"}, // Quản lý kho hàng
        {"Thêm", "Xóa", "ThêmYC", "Xuất Ex"}, // Quản lý bảo hành
        {"TQuan", "KHàng", "Tồn", "DThu", "SPhẩm"}, // Quản lý thống kê
        {} // Quản lý phân quyền
    };

    public static JPanel createContentPanel() {
        // Initialize database connection and BUS
        Connection cnn = DatabaseConnection.getConnection();
        RoleDAO roleDAO = new RoleDAO(cnn);
        RoleBUS roleBUS = new RoleBUS(roleDAO);
        AuthoritiesDAO authoritiesDAO = new AuthoritiesDAO(cnn);
        AuthoritiesBUS authoritiesBUS = new AuthoritiesBUS(authoritiesDAO);
        CTQDAO ctqDAO = new CTQDAO(cnn);
        CTQBUS ctqBUS = new CTQBUS(ctqDAO);
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
        JPanel contentTPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        contentTPanel.setPreferredSize(new Dimension(0, 40));
        contentTPanel.setBackground(new Color(239, 237, 237));
        RoundedButton saveButton = new RoundedButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setBackground(new Color(50, 150, 50)); // Green color for save
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        contentTPanel.add(saveButton);
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
                updateContentArea(role.getIdRole(), authoritiesBUS,ctqBUS);
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
            updateContentArea(rolesList.get(0).getIdRole(), authoritiesBUS,ctqBUS);
        }
        saveButton.addActionListener(e -> {
            if (selectedButton == null) return;
        
            // Lấy roleId từ nút đang chọn
            String btnText = selectedButton.getText(); // "Tên (id)"
            int roleId = Integer.parseInt(btnText.substring(btnText.indexOf("(") + 1, btnText.indexOf(")")));
        
            // Tạo 2 danh sách tạm
            ArrayList<JCheckBox> allCheckBoxes = new ArrayList<>();
        
            // Đệ quy duyệt tất cả components trong contentRPanel
            collectAllCheckBoxes(contentRPanel, allCheckBoxes);
        
            for (JCheckBox cb : allCheckBoxes) {
                String name = cb.getName();
                if (name == null) continue;
        
                if (!name.contains("_")) {
                    // Quyền chính
                    int idQuyen = Integer.parseInt(name);
                    if (cb.isSelected()) {
                        authoritiesBUS.updateIsChecked(roleId, idQuyen,true);
                    } else {
                        authoritiesBUS.updateIsChecked(roleId, idQuyen,false);
                    }
                } else {
                    // Quyền phụ
                    String[] parts = name.split("_");
                    int idQuyen = Integer.parseInt(parts[0]);
                    int idCt = Integer.parseInt(parts[1]);
        
                    if (cb.isSelected()) {
                        ctqBUS.updateIsChecked(roleId, idQuyen, idCt,true);
                    } else {
                        ctqBUS.updateIsChecked(roleId, idQuyen, idCt,false);

                    }
                }
            }
        
            JOptionPane.showMessageDialog(null, "Lưu quyền thành công!");
        });
        
        return contentPanel;
    }

    // Update right content area when a role is clicked
    private static void updateContentArea(int roleId, AuthoritiesBUS authoritiesBUS,CTQBUS ctqBUS) {
        contentRPanel.removeAll();
        contentRPanel.setBackground(SELECTED_COLOR); // Light blue when role selected

        // Create panel for authorities list
        JPanel authoritiesPanel = new JPanel();
        authoritiesPanel.setLayout(new BoxLayout(authoritiesPanel, BoxLayout.Y_AXIS));
        authoritiesPanel.setBackground(SELECTED_COLOR);
        authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between rows
        int i = 1;
        // Add each authority as a row with checkbox and label
        for (String authority : AUTHORITIES) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            rowPanel.setBackground(SELECTED_COLOR);
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBackground(SELECTED_COLOR);
            checkBox.setPreferredSize(new Dimension(30, 30));
            boolean isChecked = authoritiesBUS.isChecked(roleId, i);
            checkBox.setSelected(isChecked);
            checkBox.setSelected(isChecked);
            checkBox.setName(String.valueOf(i));
            JLabel label = new JLabel(authority);
            label.setFont(new Font("Tahoma", Font.PLAIN, 15));
            label.setPreferredSize(new Dimension(140, 30));
            rowPanel.add(checkBox);
            rowPanel.add(label);
            for(int j = 0; j < BUTTON[i - 1].length; j++) {
                JLabel labelItem = new JLabel(BUTTON[i - 1][j]);
                labelItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
                labelItem.setPreferredSize(new Dimension(70, 30));

                JCheckBox button = new JCheckBox();
                button.setBackground(SELECTED_COLOR);
                button.setPreferredSize(new Dimension(30, 30));
                button.setSelected(ctqBUS.isChecked(roleId, i, j+1));
                button.setName(i + "_" + (j+1));
                rowPanel.add(button);
                rowPanel.add(labelItem);
            }
            authoritiesPanel.add(rowPanel);
            authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between rows
            i++;
        }

        // Wrap authorities panel in JScrollPane for scrolling
        JScrollPane authoritiesScrollPane = new JScrollPane(authoritiesPanel);
        authoritiesScrollPane.setBorder(null);
        contentRPanel.add(authoritiesScrollPane, BorderLayout.CENTER);

        contentRPanel.revalidate();
        contentRPanel.repaint();
    }
    private static void collectAllCheckBoxes(Container container, ArrayList<JCheckBox> list) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JCheckBox cb) {
                list.add(cb);
            } else if (comp instanceof Container child) {
                collectAllCheckBoxes(child, list);
            }
        }
    }
    
}
