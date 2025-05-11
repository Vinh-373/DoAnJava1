package com.quanlybanlaptop.gui.thongKe;

import com.quanlybanlaptop.util.ExcelExporter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.IOException;
import java.text.DecimalFormat;
import com.quanlybanlaptop.bus.ThongKeBUS;

public class TonKho extends JPanel {
    private JTable table;
    private JTextField txtSearchName;
    private JComboBox<String> sortComboBox;
    private ThongKeBUS thongKeBUS;
    private Timer searchTimer;
    private String sortOrder = "NONE"; // Trạng thái sắp xếp: NONE, ASC, DESC
    private String sortField = "Số lượng tồn kho"; // Trường sắp xếp mặc định

    public TonKho(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        setLayout(new BorderLayout());

        // Khởi tạo Timer cho debounce
        searchTimer = new Timer(300, e -> updateTableWithCurrentFilters());
        searchTimer.setRepeats(false);

        // Top panel: Tìm kiếm, combo box, và nút
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Tên sản phẩm:"));
        txtSearchName = new JTextField(15);
        filterPanel.add(txtSearchName);

        filterPanel.add(new JLabel("Sắp xếp theo:"));
        String[] sortOptions = {"Số lượng tồn kho", "Hàng tồn cửa hàng"};
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedItem("Số lượng tồn kho");
        sortComboBox.setPreferredSize(new Dimension(150, 25));
        filterPanel.add(sortComboBox);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        JButton btnLamMoi = new JButton("Làm mới");
        JButton btnSortAsc = new JButton("Sắp xếp tăng");
        JButton btnSortDesc = new JButton("Sắp xếp giảm");
        filterPanel.add(btnXuatExcel);
        filterPanel.add(btnLamMoi);
        filterPanel.add(btnSortAsc);
        filterPanel.add(btnSortDesc);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng thống kê tồn kho
        String[] col = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn kho", "Hàng tồn cửa hàng"};
        Object[][] data = new Object[0][4];
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê tồn kho"));
        add(scrollPane, BorderLayout.CENTER);

        // Thêm DocumentListener cho txtSearchName để tìm kiếm thời gian thực
        txtSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTimer.restart();
            }
        });

        // Thêm ActionListener cho sortComboBox để cập nhật khi thay đổi lựa chọn
        sortComboBox.addActionListener(e -> {
            sortField = (String) sortComboBox.getSelectedItem();
            updateTableWithCurrentFilters();
        });


        // Xử lý sự kiện nút Xuất Excel
        btnXuatExcel.addActionListener(e -> {
            String productName = txtSearchName.getText().trim();
            exportToExcel(productName);
        });

        // Xử lý sự kiện nút Làm mới
        btnLamMoi.addActionListener(e -> {
            txtSearchName.setText("");
            sortComboBox.setSelectedItem("Số lượng tồn kho");
            sortOrder = "NONE"; // Reset sắp xếp
            sortField = "Số lượng tồn kho";
            updateTableWithCurrentFilters();
        });

        // Xử lý sự kiện nút Sắp xếp tăng
        btnSortAsc.addActionListener(e -> {
            sortOrder = "ASC";
            updateTableWithCurrentFilters();
        });

        // Xử lý sự kiện nút Sắp xếp giảm
        btnSortDesc.addActionListener(e -> {
            sortOrder = "DESC";
            updateTableWithCurrentFilters();
        });

        // Tải dữ liệu ban đầu (không lọc)
        updateTableWithCurrentFilters();
    }

    // Cập nhật bảng với bộ lọc hiện tại
    private void updateTableWithCurrentFilters() {
        String productName = txtSearchName.getText().trim();
        System.out.println("productName: " + productName + ", sortField: " + sortField + ", sortOrder: " + sortOrder);
        updateTable(productName);
    }

    // Cập nhật bảng với dữ liệu từ ThongKeBUS
    private void updateTable(String productName) {
        try {
            List<Object[]> statsList = thongKeBUS.getInventoryStatistics(productName);
            System.out.println("Số lượng bản ghi trả về: " + statsList.size());
            System.out.println("Dữ liệu trả về: " + statsList);

            // Sắp xếp danh sách dựa trên sortField và sortOrder
            if (!sortOrder.equals("NONE")) {
                Comparator<Object[]> comparator = null;
                switch (sortField) {
                    case "Số lượng tồn kho":
                        comparator = Comparator.comparingInt(o -> (Integer) ((Object[]) o)[2]);
                        break;
                    case "Hàng tồn cửa hàng":
                        comparator = Comparator.comparingInt(o -> (Integer) ((Object[]) o)[3]);
                        break;
                }
                if (comparator != null) {
                    if (sortOrder.equals("DESC")) {
                        comparator = comparator.reversed();
                    }
                    Collections.sort(statsList, comparator);
                }
            }

            // Tạo dữ liệu cho bảng (chỉ lấy 4 cột đầu, bỏ cột revenue)
            Object[][] data = new Object[statsList.size()][4];
            for (int i = 0; i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                if (!(stats instanceof Object[])) {
                    System.err.println("Lỗi: Phần tử tại vị trí " + i + " không phải Object[]: " + stats);
                    continue;
                }
                data[i][0] = stats[0]; // Mã sản phẩm
                data[i][1] = stats[1]; // Tên sản phẩm
                data[i][2] = stats[2]; // Số lượng tồn kho
                data[i][3] = stats[3]; // Hàng tồn cửa hàng
            }

            // Cập nhật bảng
            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn kho", "Hàng tồn cửa hàng"}));
            table.setRowHeight(28);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xuất dữ liệu ra Excel
    private void exportToExcel(String productName) {
        try {
            List<Object[]> statsList = thongKeBUS.getInventoryStatistics(productName);
            System.out.println("Dữ liệu xuất Excel: " + statsList);

            // Sắp xếp danh sách nếu cần khi xuất Excel
            if (!sortOrder.equals("NONE")) {
                Comparator<Object[]> comparator = null;
                switch (sortField) {
                    case "Số lượng tồn kho":
                        comparator = Comparator.comparingInt(o -> (Integer) ((Object[]) o)[2]);
                        break;
                    case "Hàng tồn cửa hàng":
                        comparator = Comparator.comparingInt(o -> (Integer) ((Object[]) o)[3]);
                        break;
                }
                if (comparator != null) {
                    if (sortOrder.equals("DESC")) {
                        comparator = comparator.reversed();
                    }
                    Collections.sort(statsList, comparator);
                }
            }

            // Chuẩn bị dữ liệu cho Excel (chỉ lấy 4 cột đầu, bỏ cột revenue)
            List<String> headers = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn kho", "Hàng tồn cửa hàng");
            List<List<Object>> data = new ArrayList<>();
            for (Object[] stats : statsList) {
                if (!(stats instanceof Object[])) {
                    System.err.println("Lỗi: Phần tử không phải Object[]: " + stats);
                    continue;
                }
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // Mã sản phẩm
                row.add(stats[1]); // Tên sản phẩm
                row.add(stats[2]); // Số lượng tồn kho
                row.add(stats[3]); // Hàng tồn cửa hàng
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            String fileName = "ThongKeTonKho_" + System.currentTimeMillis() + ".xlsx";
            fileChooser.setSelectedFile(new java.io.File(fileName));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê tồn kho", filePath);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}