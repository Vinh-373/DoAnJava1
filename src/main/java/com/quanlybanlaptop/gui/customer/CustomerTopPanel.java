package com.quanlybanlaptop.gui.customer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.quanlybanlaptop.bus.CustomerBUS;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.util.ExcelExporter;
import com.quanlybanlaptop.util.ImageLoader;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.quanlybanlaptop.dto.CustomerDTO;

public class CustomerTopPanel extends JPanel {
    private RoundedButton btnAdd;
    private RoundedButton btnDelete;
    private RoundedButton btnEdit;
    private RoundedButton btnExportExcel;
    private RoundedButton btnExportPDF;
    private RoundedButton btnRefresh;
    private JTextField searchField;
    private CustomerBUS customerBUS;
    private JComboBox<String> searchTypeComboBox;

    public CustomerTopPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        customerBUS = new CustomerBUS();

        btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        btnAdd.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                AddCustomer addFrame = new AddCustomer();
                addFrame.setVisible(true);
            });
            CustomerTable.loadCustomerData(customerBUS, null);
        });

        btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        btnEdit.addActionListener(e -> {
            int selectedRow = CustomerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) CustomerTable.getTableModel().getValueAt(selectedRow, 0);
                CustomerDTO customer = customerBUS.getCustomerById(customerId);
                if (customer != null) {
                    EditCustomer editFrame = new EditCustomer(customer);
                    editFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin khách hàng.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa.");
            }
        });

        btnDelete = new RoundedButton("Xóa", ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        btnDelete.addActionListener(e -> {
            int selectedRow = CustomerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) CustomerTable.getTableModel().getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn khóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = customerBUS.deleteCustomer(customerId);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Khóa khách hàng thành công!");
                        CustomerTable.loadCustomerData(customerBUS, null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Khóa khách hàng thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần khóa.");
            }
            CustomerTable.loadCustomerData(customerBUS, null);
        });

        btnExportExcel = new RoundedButton("Xuất Excel", ImageLoader.loadResourceImage("/img/xuatExcel.png"));
        btnExportExcel.setImageSize(32, 32);
        btnExportExcel.addActionListener(e -> exportExcelAction());

        btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);
        btnRefresh.addActionListener(e -> {
            CustomerTable.loadCustomerData(customerBUS, null);
        });

        // ComboBox chọn trường tìm kiếm
        String[] searchTypes = {"Tên", "Số điện thoại", "Căn cước công dân"};
        searchTypeComboBox = RoundedComponent.createRoundedComboBox(searchTypes, 10);
        searchTypeComboBox.setPreferredSize(new Dimension(150, 35));

        // Trường nhập tìm kiếm
        searchField = RoundedComponent.createRoundedTextField(10);
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setToolTipText("Nhập từ khóa tìm kiếm...");
         searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });

        add(btnAdd);
        add(btnEdit);
        add(btnDelete);
        add(btnExportExcel);
    
        add(btnRefresh);

        add(searchTypeComboBox);
        add(searchField);
    }

    // Thực hiện tìm kiếm theo trường (không lọc trạng thái)
    private void performSearch() {
        String keyword = searchField.getText().trim();
        int searchType = searchTypeComboBox.getSelectedIndex(); // 0: Tên, 1: SĐT, 2: CCCD
        CustomerTable.searchCustomerData(customerBUS, searchType, keyword, null);
    }

    // Hàm xuất Excel
    private void exportExcelAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) filePath += ".xlsx";

            // Lấy dữ liệu từ bảng
            javax.swing.table.TableModel model = CustomerTable.getTableModel();
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < model.getColumnCount(); i++) {
                headers.add(model.getColumnName(i));
            }
            List<List<Object>> data = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                List<Object> row = new ArrayList<>();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row.add(model.getValueAt(i, j));
                }
                data.add(row);
            }

            try {
                ExcelExporter.exportToExcel(headers, data, "KhachHang", filePath);
                JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
            CustomerTable.loadCustomerData(customerBUS, null);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
            }
        }
    }

    public RoundedButton getBtnAdd() { return btnAdd; }
    public RoundedButton getBtnDelete() { return btnDelete; }
    public RoundedButton getBtnEdit() { return btnEdit; }
    public RoundedButton getBtnExportExcel() { return btnExportExcel; }
    public RoundedButton getBtnExportPDF() { return btnExportPDF; }
    public RoundedButton getBtnRefresh() { return btnRefresh; }
    public JTextField getSearchField() { return searchField; }
    public JComboBox<String> getSearchTypeComboBox() { return searchTypeComboBox; }
}