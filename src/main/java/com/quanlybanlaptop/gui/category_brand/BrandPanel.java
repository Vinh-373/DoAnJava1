package com.quanlybanlaptop.gui.category_brand;

import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.CategoryDTO;
import com.quanlybanlaptop.dto.CompanyDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandPanel {
    private static JPanel buttonControlPanel, brandPanel;
    private static DefaultTableModel tableModel;
    private static RoundedTable brandTable;
    public static JPanel createBrandPanel(AdminDTO adminDTO,JPanel contentArea, CompanyBUS companyBUS) {
        buttonControlPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"MÃ HÃNG", "TÊN HÃNG", "ĐỊA CHỈ", "SỐ ĐIỆN THOẠI"};
        tableModel = new DefaultTableModel(columnNames, 0);
        brandTable = new RoundedTable(tableModel);
        brandTable.setFocusable(false); // Không cho focus
        brandTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        brandTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(brandTable);
        JPanel contentCtgr = new JPanel(new BorderLayout());
        loadBrandData(companyBUS); // Tải dữ liệu ban đầu
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentCtgr.add(spacer, BorderLayout.NORTH);
        contentCtgr.add(scrollPane, BorderLayout.CENTER);
        brandPanel = new JPanel(new BorderLayout());
//        int parentWidth = contentArea.getWidth();
//        int panelWidth = (int) (parentWidth * 0.6);
//        brandPanel.setPreferredSize(new Dimension(panelWidth, 0));
        brandPanel.setBackground(new Color(168, 196, 230));
//        brandPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonControlPanel.add(TopCBPanel.createButtonPanel(adminDTO,"Brand", companyBUS, brandTable, () -> loadBrandData(companyBUS)), BorderLayout.NORTH);
        buttonControlPanel.add(TopCBPanel.createIpBrandPanel(), BorderLayout.CENTER);
        brandPanel.add(buttonControlPanel, BorderLayout.NORTH);
        brandPanel.add(contentCtgr, BorderLayout.CENTER);
        // Thêm ListSelectionListener cho bảng
        brandTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Đảm bảo chỉ xử lý khi chọn xong
                    int selectedRow = brandTable.getSelectedRow();
                    if (selectedRow != -1) { // Có hàng được chọn
                        String id = brandTable.getValueAt(selectedRow, 0).toString();
                        String name = brandTable.getValueAt(selectedRow, 1).toString();
                        String address = brandTable.getValueAt(selectedRow, 2).toString();
                        String phone = brandTable.getValueAt(selectedRow, 3).toString();
                        TopCBPanel.getIdCompanyField().setText(id);
                        TopCBPanel.getNameCompanyField().setText(name);
                        TopCBPanel.getAddressCompanyField().setText(address);
                        TopCBPanel.getContactCompanyField().setText(phone);
                    }
                }
            }
        });
        return brandPanel;
    }
    public static void loadBrandData(CompanyBUS companyBUS) {
        tableModel.setRowCount(0);
        try {
            ArrayList<CompanyDTO> companies = companyBUS.getAllCompany();
            if(companies != null) {
                for (CompanyDTO company : companies) {
                    tableModel.addRow(new Object[]{
                            company.getCompanyId(),
                            company.getCompanyName(),
                            company.getCompanyAddress(),
                            company.getCompanyContact()
                    });
                }
            }else{
                JOptionPane.showMessageDialog(null, "Không có dữ liệu danh mục!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
