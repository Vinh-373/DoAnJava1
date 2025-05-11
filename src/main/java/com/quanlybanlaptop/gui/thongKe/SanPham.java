package com.quanlybanlaptop.gui.thongKe;

import com.quanlybanlaptop.util.ExcelExporter;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.quanlybanlaptop.bus.ThongKeBUS;
import java.text.DecimalFormat;

public class SanPham extends JPanel {
    private JTable table;
    private JTextField txtSearchName;
    private JDateChooser dateChooserStart;
    private JDateChooser dateChooserEnd;
    private ThongKeBUS thongKeBUS;
    private Timer searchTimer;

    public SanPham(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        setLayout(new BorderLayout());

        // Khởi tạo Timer cho debounce
        searchTimer = new Timer(300, e -> updateTableWithCurrentFilters());
        searchTimer.setRepeats(false);

        // Top panel: Tìm kiếm, chọn ngày, và nút
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Tên sản phẩm:"));
        txtSearchName = new JTextField(15);
        filterPanel.add(txtSearchName);

        filterPanel.add(new JLabel("Từ ngày:"));
        dateChooserStart = new JDateChooser();
        dateChooserStart.setDateFormatString("dd/MM/yyyy");
        dateChooserStart.setPreferredSize(new Dimension(120, 25));
        filterPanel.add(dateChooserStart);

        filterPanel.add(new JLabel("Đến ngày:"));
        dateChooserEnd = new JDateChooser();
        dateChooserEnd.setDateFormatString("dd/MM/yyyy");
        dateChooserEnd.setPreferredSize(new Dimension(120, 25));
        filterPanel.add(dateChooserEnd);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnXuatExcel = new JButton("Xuất Excel");
        JButton btnLamMoi = new JButton("Làm mới");
        filterPanel.add(btnTimKiem);
        filterPanel.add(btnXuatExcel);
        filterPanel.add(btnLamMoi);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng thống kê sản phẩm
        String[] col = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng bán", "Doanh thu"};
        Object[][] data = new Object[0][4];
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê sản phẩm"));
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

        // Thêm DocumentListener cho JDateChooser để cập nhật khi ngày thay đổi
        if (dateChooserStart.getDateEditor().getUiComponent() instanceof JTextComponent) {
            JTextComponent startDateField = (JTextComponent) dateChooserStart.getDateEditor().getUiComponent();
            startDateField.getDocument().addDocumentListener(new DocumentListener() {
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
        }

        if (dateChooserEnd.getDateEditor().getUiComponent() instanceof JTextComponent) {
            JTextComponent endDateField = (JTextComponent) dateChooserEnd.getDateEditor().getUiComponent();
            endDateField.getDocument().addDocumentListener(new DocumentListener() {
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
        }

        // Xử lý sự kiện nút Tìm kiếm
        btnTimKiem.addActionListener(e -> updateTableWithCurrentFilters());

        // Xử lý sự kiện nút Xuất Excel
        btnXuatExcel.addActionListener(e -> {
            String productName = txtSearchName.getText().trim();
            LocalDate startDate = dateChooserStart.getDate() != null ?
                dateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate endDate = dateChooserEnd.getDate() != null ?
                dateChooserEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            exportToExcel(productName, startDate, endDate);
        });

        // Xử lý sự kiện nút Làm mới
        btnLamMoi.addActionListener(e -> {
            txtSearchName.setText("");
            dateChooserStart.setDate(null);
            dateChooserEnd.setDate(null);
            updateTableWithCurrentFilters();
        });

        // Tải dữ liệu ban đầu (không lọc)
        updateTableWithCurrentFilters();
    }

    // Cập nhật bảng với bộ lọc hiện tại
    private void updateTableWithCurrentFilters() {
        String productName = txtSearchName.getText().trim();
        LocalDate startDate = dateChooserStart.getDate() != null ?
            dateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate endDate = dateChooserEnd.getDate() != null ?
            dateChooserEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        System.out.println("productName: " + productName + ", startDate: " + startDate + ", endDate: " + endDate);
        updateTable(productName, startDate, endDate);
    }

    // Cập nhật bảng với dữ liệu từ ThongKeBUS
    private void updateTable(String productName, LocalDate startDate, LocalDate endDate) {
        try {
            List<Object[]> statsList = thongKeBUS.getProductStatistics(productName, startDate, endDate);
            System.out.println("Số lượng bản ghi trả về: " + statsList.size());
            DecimalFormat dt = new DecimalFormat("#,###.00");
            // Tạo dữ liệu cho bảng
            Object[][] data = new Object[statsList.size()][4];
            for (int i = 0; i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                data[i][0] = stats[0]; // Mã sản phẩm
                data[i][1] = stats[1]; // Tên sản phẩm
                data[i][2] = stats[2]; // Số lượng bán
                data[i][3] = dt.format((Number) stats[3]); // Doanh thu
            }

            // Cập nhật bảng
            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"Mã sản phẩm", "Tên sản phẩm", "Số lượng bán", "Doanh thu"}));
            table.setRowHeight(28);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Raw startDate from JDateChooser: " + dateChooserStart.getDate());
        System.out.println("Raw endDate from JDateChooser: " + dateChooserEnd.getDate());
    }

    // Xuất dữ liệu ra Excel
    private void exportToExcel(String productName, LocalDate startDate, LocalDate endDate) {
        try {
            List<Object[]> statsList = thongKeBUS.getProductStatistics(productName, startDate, endDate);

            // Chuẩn bị dữ liệu cho Excel
            List<String> headers = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Số lượng bán", "Doanh thu");
            List<List<Object>> data = new ArrayList<>();
            for (Object[] stats : statsList) {
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // Mã sản phẩm
                row.add(stats[1]); // Tên sản phẩm
                row.add(stats[2]); // Số lượng bán
                row.add(String.format("%.2f", (Double) stats[3])); // Doanh thu
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            String fileName = "ThongKeSanPham_" + (startDate != null ? startDate.toString() : "NoStart") + "_" +
                             (endDate != null ? endDate.toString() : "NoEnd") + ".xlsx";
            fileChooser.setSelectedFile(new java.io.File(fileName));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê sản phẩm", filePath);
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