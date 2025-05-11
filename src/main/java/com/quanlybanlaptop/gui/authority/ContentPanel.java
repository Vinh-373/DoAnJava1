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
    private static final Color SELECTED_COLOR = new Color(173, 216, 230); // Light blue
    private static final Color HOVER_COLOR = new Color(190, 230, 240);   // Lighter blue for hover
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
        ArrayList<RoleDTO> rolesList;
        try {
            rolesList = roleBUS.getAllRoles();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách vai trò: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            rolesList = new ArrayList<>();
        }

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Top panel
        JPanel contentTPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        contentTPanel.setPreferredSize(new Dimension(0, 40));
        contentTPanel.setBackground(new Color(239, 237, 237));
        RoundedButton addButton = new RoundedButton("Thêm quyền");
        addButton.setPreferredSize(new Dimension(130, 30));
        addButton.setBackground(new Color(50, 150, 50)); // Green
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        RoundedButton editButton = new RoundedButton("Sửa tên quyền");
        editButton.setPreferredSize(new Dimension(130, 30));
        editButton.setBackground(new Color(50, 150, 50)); // Green
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        RoundedButton deleteButton = new RoundedButton("Xóa quyền");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setBackground(new Color(150, 50, 50)); // Red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        RoundedButton saveButton = new RoundedButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setBackground(new Color(50, 150, 50)); // Green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveButton.setFocusPainted(false);

        contentTPanel.add(addButton);
        contentTPanel.add(editButton);
        contentTPanel.add(deleteButton);
        contentTPanel.add(saveButton);

        contentPanel.add(contentTPanel, BorderLayout.NORTH);

        // Use JSplitPane for left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        // Left panel: Role buttons
        JPanel contentLPanel = new JPanel();
        contentLPanel.setLayout(new BoxLayout(contentLPanel, BoxLayout.Y_AXIS));
        contentLPanel.setBackground(Color.WHITE);
        contentLPanel.setPreferredSize(new Dimension(150, 0));

        // Create role buttons and track the first button
        JButton[] firstButton = new JButton[1];
        createRoleButtons(contentLPanel, rolesList, authoritiesBUS, ctqBUS, firstButton, roleBUS);

        // Wrap left panel in JScrollPane
        JScrollPane leftScrollPane = new JScrollPane(contentLPanel);
        leftScrollPane.setBorder(null);
        splitPane.setLeftComponent(leftScrollPane);

        // Right panel: Content area
        contentRPanel = new JPanel(new BorderLayout());
        contentRPanel.setBackground(Color.WHITE);
        contentRPanel.add(new JLabel("Chọn một vai trò để hiển thị nội dung", SwingConstants.CENTER), BorderLayout.CENTER);
        splitPane.setRightComponent(contentRPanel);

        contentPanel.add(splitPane, BorderLayout.CENTER);

        // Select the first button by default
        if (firstButton[0] != null && !rolesList.isEmpty()) {
            selectedButton = firstButton[0];
            firstButton[0].setBackground(SELECTED_COLOR);
            updateContentArea(rolesList.get(0).getIdRole(), authoritiesBUS, ctqBUS);
        }

        // Save button action
        saveButton.addActionListener(e -> {
            if (selectedButton == null) return;

            String btnText = selectedButton.getText();
            int roleId = Integer.parseInt(btnText.substring(btnText.indexOf("(") + 1, btnText.indexOf(")")));

            ArrayList<JCheckBox> allCheckBoxes = new ArrayList<>();
            collectAllCheckBoxes(contentRPanel, allCheckBoxes);

            for (JCheckBox cb : allCheckBoxes) {
                String name = cb.getName();
                if (name == null) continue;

                if (!name.contains("_")) {
                    int idQuyen = Integer.parseInt(name);
                    authoritiesBUS.updateIsChecked(roleId, idQuyen, cb.isSelected());
                } else {
                    String[] parts = name.split("_");
                    int idQuyen = Integer.parseInt(parts[0]);
                    int idCt = Integer.parseInt(parts[1]);
                    ctqBUS.updateIsChecked(roleId, idQuyen, idCt, cb.isSelected());
                }
            }

            JOptionPane.showMessageDialog(null, "Lưu quyền thành công!");
        });

        // Add button action
        addButton.addActionListener(e -> {
            String newRoleName = JOptionPane.showInputDialog(null, "Nhập tên quyền mới:");
            if (newRoleName != null && !newRoleName.trim().isEmpty()) {
                try {
                    
                    int idRole = roleBUS.addRole(new RoleDTO(0,newRoleName, 1));
                    authoritiesBUS.insert(idRole);
                    ctqBUS.insert(idRole);
                    ArrayList<RoleDTO> updatedRoles = roleBUS.getAllRoles();
                    contentLPanel.removeAll();
                    firstButton[0] = null;
                    createRoleButtons(contentLPanel, updatedRoles, authoritiesBUS, ctqBUS, firstButton, roleBUS);
                    contentLPanel.revalidate();
                    contentLPanel.repaint();
                    JOptionPane.showMessageDialog(null, "Thêm quyền thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Edit button action
        editButton.addActionListener(e -> {
            if (selectedButton == null) return;

            try {
                String btnText = selectedButton.getText();
                int roleId = Integer.parseInt(btnText.substring(btnText.indexOf("(") + 1, btnText.indexOf(")")));
                RoleDTO roleDTO = roleBUS.getRoleById(roleId);

                String newRoleName = JOptionPane.showInputDialog(null, "Nhập tên quyền mới:", roleDTO.getNameRole());
                if (newRoleName != null && !newRoleName.trim().isEmpty()) {
                    roleBUS.updateNameRole(newRoleName, roleId);
                    ArrayList<RoleDTO> updatedRoles = roleBUS.getAllRoles();
                    contentLPanel.removeAll();
                    firstButton[0] = null;
                    createRoleButtons(contentLPanel, updatedRoles, authoritiesBUS, ctqBUS, firstButton, roleBUS);
                    contentLPanel.revalidate();
                    contentLPanel.repaint();
                    JOptionPane.showMessageDialog(null, "Sửa tên quyền thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa tên quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            if (selectedButton == null) return;

            String btnText = selectedButton.getText();
            int roleId = Integer.parseInt(btnText.substring(btnText.indexOf("(") + 1, btnText.indexOf(")")));

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa quyền này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    roleBUS.updateStatusRole(0, roleId);
                    ArrayList<RoleDTO> updatedRoles = roleBUS.getAllRoles();
                    contentLPanel.removeAll();
                    firstButton[0] = null;
                    createRoleButtons(contentLPanel, updatedRoles, authoritiesBUS, ctqBUS, firstButton, roleBUS);
                    contentRPanel.removeAll();
                    contentRPanel.setBackground(Color.WHITE);
                    contentRPanel.add(new JLabel("Chọn một vai trò để hiển thị nội dung", SwingConstants.CENTER), BorderLayout.CENTER);
                    selectedButton = null;
                    contentLPanel.revalidate();
                    contentLPanel.repaint();
                    contentRPanel.revalidate();
                    contentRPanel.repaint();
                    JOptionPane.showMessageDialog(null, "Xóa quyền thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi xóa quyền: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return contentPanel;
    }

    // Reusable method to create role buttons
    private static void createRoleButtons(JPanel contentLPanel, ArrayList<RoleDTO> rolesList,
                                         AuthoritiesBUS authoritiesBUS, CTQBUS ctqBUS, JButton[] firstButton, RoleBUS roleBUS) {
        for (int i = 0; i < rolesList.size(); i++) {
            RoleDTO role = rolesList.get(i);
            JButton roleBtn = new JButton(role.getNameRole() + " (" + role.getIdRole() + ")");
            roleBtn.setPreferredSize(new Dimension(150, 40));
            roleBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            roleBtn.setBackground(DEFAULT_COLOR);
            roleBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            roleBtn.setBorder(null);
            roleBtn.setFocusPainted(false);
            roleBtn.setOpaque(true);

            if (i == 0) {
                firstButton[0] = roleBtn;
            }

            roleBtn.addActionListener(e -> {
                if (selectedButton != null && selectedButton != roleBtn) {
                    selectedButton.setBackground(DEFAULT_COLOR);
                }
                selectedButton = roleBtn;
                roleBtn.setBackground(SELECTED_COLOR);
                updateContentArea(role.getIdRole(), authoritiesBUS, ctqBUS);
            });

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
            contentLPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    // Update right content area
    private static void updateContentArea(int roleId, AuthoritiesBUS authoritiesBUS, CTQBUS ctqBUS) {
        contentRPanel.removeAll();
        contentRPanel.setBackground(SELECTED_COLOR);

        JPanel authoritiesPanel = new JPanel();
        authoritiesPanel.setLayout(new BoxLayout(authoritiesPanel, BoxLayout.Y_AXIS));
        authoritiesPanel.setBackground(SELECTED_COLOR);
        authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        int i = 1;
        for (String authority : AUTHORITIES) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
            rowPanel.setBackground(SELECTED_COLOR);
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JCheckBox checkBox = new JCheckBox();
            checkBox.setBackground(SELECTED_COLOR);
            checkBox.setPreferredSize(new Dimension(30, 30));
            boolean isChecked = authoritiesBUS.isChecked(roleId, i);
            checkBox.setSelected(isChecked);
            checkBox.setName(String.valueOf(i));
            JLabel label = new JLabel(authority);
            label.setFont(new Font("Tahoma", Font.PLAIN, 15));
            label.setPreferredSize(new Dimension(140, 30));
            rowPanel.add(checkBox);
            rowPanel.add(label);

            for (int j = 0; j < BUTTON[i - 1].length; j++) {
                JLabel labelItem = new JLabel(BUTTON[i - 1][j]);
                labelItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
                labelItem.setPreferredSize(new Dimension(70, 30));

                JCheckBox button = new JCheckBox();
                button.setBackground(SELECTED_COLOR);
                button.setPreferredSize(new Dimension(30, 30));
                button.setSelected(ctqBUS.isChecked(roleId, i, j + 1));
                button.setName(i + "_" + (j + 1));
                rowPanel.add(button);
                rowPanel.add(labelItem);
            }
            authoritiesPanel.add(rowPanel);
            authoritiesPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            i++;
        }

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