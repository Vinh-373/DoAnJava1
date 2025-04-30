package com.quanlybanlaptop.gui.customer;

import com.quanlybanlaptop.bus.CustomerBUS;
import com.quanlybanlaptop.dto.CustomerDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CustomerTable {
    private static DefaultTableModel tableModel;
    private static RoundedTable customerTable;

    // Tạo bảng khách hàng trong JScrollPane
    public static JScrollPane createCustomerTable(CustomerBUS customerBUS) {
        String[] columnNames = {"Mã KH", "Tên", "Ngày sinh", "SĐT", "CCCD", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new RoundedTable(tableModel);
        customerTable.setColumnWidths(50, 150, 100, 100, 150, 100);
        customerTable.setFocusable(false);
        customerTable.setRequestFocusEnabled(false);
        customerTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(customerTable);
        // Chỉ hiển thị khách hàng hoạt động (status = 1)
        loadCustomerData(customerBUS, 1);
        return scrollPane;
    }

    // Phương thức tải dữ liệu khách hàng với lọc trạng thái
    public static void loadCustomerData(CustomerBUS customerBUS, Integer statusFilter) {
        tableModel.setRowCount(0);
        try {
            ArrayList<CustomerDTO> customers = customerBUS.getAllCustomers();
            if (customers != null) {
                for (CustomerDTO customer : customers) {
                    // Luôn chỉ hiển thị khách hàng có status = 1
                    if ((statusFilter == null && customer.getStatus() == 1) || (statusFilter != null && customer.getStatus() == statusFilter)) {
                        String status_temp = (customer.getStatus() == 1) ? "Hoạt động" : "Đã xóa";
                        Object[] rowData = {
                                customer.getId(),
                                customer.getName(),
                                customer.getBirthdate(),
                                customer.getPhone(),
                                customer.getCitizenId(),
                                status_temp
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ...existing code...

    /**
     * Tìm kiếm khách hàng theo trường được chọn và từ khóa.
     * @param customerBUS CustomerBUS để lấy dữ liệu
     * @param searchType 0: Tên, 1: SĐT, 2: CCCD
     * @param keyword Từ khóa tìm kiếm
     */
    public static void searchCustomerData(CustomerBUS customerBUS, int searchType, String keyword, Integer statusFilter) {
        tableModel.setRowCount(0);
        try {
            ArrayList<CustomerDTO> customers = customerBUS.getAllCustomers();
            if (customers != null) {
                for (CustomerDTO customer : customers) {
                    boolean match = false;
                    // Lọc theo trạng thái
                    if (statusFilter == null || customer.getStatus() == statusFilter) {
                        switch (searchType) {
                            case 0: // Tên
                                match = customer.getName() != null && customer.getName().toLowerCase().contains(keyword.toLowerCase());
                                break;
                            case 1: // SĐT
                                match = customer.getPhone() != null && customer.getPhone().contains(keyword);
                                break;
                            case 2: // CCCD
                                match = customer.getCitizenId() != null && customer.getCitizenId().contains(keyword);
                                break;
                        }
                    }
                    if (match) {
                        String status_temp = (customer.getStatus() == 1) ? "Hoạt động" : "Đã xóa";
                        Object[] rowData = {
                                customer.getId(),
                                customer.getName(),
                                customer.getBirthdate(),
                                customer.getPhone(),
                                customer.getCitizenId(),
                                status_temp
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

// ...existing code...

    // Lấy chỉ số hàng được chọn trong bảng
    public static int getSelectedRow() {
        return customerTable.getSelectedRow();
    }

    // Lấy table model để truy cập dữ liệu trong bảng
    public static DefaultTableModel getTableModel() {
        return tableModel;
    }
}