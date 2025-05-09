package com.quanlybanlaptop.gui.category_brand;

import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.CategoryDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryPanel {
    private static JPanel buttonControlPanel, categoryPanel;
    private static DefaultTableModel tableModel;
    private static RoundedTable categoryTable;

    public static JPanel createCategoryPanel(AdminDTO adminDTO,JPanel contentArea, CategoryBUS categoryBUS) {
        buttonControlPanel = new JPanel(new BorderLayout());
        buttonControlPanel.add(TopCBPanel.createIpCategorydPanel(), BorderLayout.CENTER);
        // Sử dụng createButtonPanel với tham số
        String[] columnNames = {"MÃ LOẠI", "TÊN LOẠI"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new RoundedTable(tableModel);
        categoryTable.setFocusable(false);
        categoryTable.setRequestFocusEnabled(false);
        categoryTable.setDefaultEditor(Object.class, null);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        JPanel contentCtgr = new JPanel(new BorderLayout());
        loadCategoryData(categoryBUS);
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentCtgr.add(spacer, BorderLayout.NORTH);
        contentCtgr.add(scrollPane, BorderLayout.CENTER);
        categoryPanel = new JPanel(new BorderLayout());
        int parentWidth = contentArea.getWidth();
        int panelWidth = (int) (parentWidth * 0.4);
        categoryPanel.setPreferredSize(new Dimension(panelWidth, 0));
        categoryPanel.setBackground(new Color(168, 196, 230));
        buttonControlPanel.add(TopCBPanel.createButtonPanel(adminDTO,"Category", categoryBUS, categoryTable, () -> loadCategoryData(categoryBUS)), BorderLayout.SOUTH);
        categoryPanel.add(buttonControlPanel, BorderLayout.NORTH);
        categoryPanel.add(contentCtgr, BorderLayout.CENTER);

        categoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = categoryTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String id = categoryTable.getValueAt(selectedRow, 0).toString();
                        String name = categoryTable.getValueAt(selectedRow, 1).toString();
                        TopCBPanel.getIdCategoryField().setText(id);
                        TopCBPanel.getNameCategoryField().setText(name);
                    }
                }
            }
        });

        return categoryPanel;
    }

    public static void loadCategoryData(CategoryBUS categoryBUS) {
        tableModel.setRowCount(0);
        try {
            ArrayList<CategoryDTO> categories = categoryBUS.getAllCategory();
            if (categories != null) {
                for (CategoryDTO category : categories) {
                    tableModel.addRow(new Object[]{
                            category.getCategoryId(),
                            category.getCategoryName()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu danh mục!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}