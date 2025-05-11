package com.quanlybanlaptop.gui.thongKe;

import com.quanlybanlaptop.util.ExcelExporter;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.quanlybanlaptop.bus.ThongKeBUS;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
public class ThongKeThangPanel extends JPanel {
    private JTable table;
    private JComboBox<Integer> cbNam;
    private ThongKeBUS thongKeBUS;

    public ThongKeThangPanel(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        setLayout(new BorderLayout());

        // Bộ lọc chọn năm
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbNam = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 10; y <= currentYear + 1; y++) cbNam.addItem(y);
        cbNam.setSelectedItem(currentYear);
        filterPanel.add(new JLabel("Năm:"));
        filterPanel.add(cbNam);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnXuatExcel = new JButton("Xuất Excel");
        filterPanel.add(btnTimKiem);
        filterPanel.add(btnXuatExcel);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng 12 tháng
        String[] col = {"Tháng", "Vốn", "Doanh thu", "Lợi nhuận"};
        Object[][] data = new Object[12][4];
        for (int i = 0; i < 12; i++) {
            data[i][0] = "Tháng " + (i + 1);
            data[i][1] = "";
            data[i][2] = "";
            data[i][3] = "";
        }
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê doanh thu theo tháng"));
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện nút Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            int year = (int) cbNam.getSelectedItem();
            updateTable(year);
        });

        // Xử lý sự kiện nút Xuất Excel
        btnXuatExcel.addActionListener(e -> {
            int year = (int) cbNam.getSelectedItem();
            exportToExcel(year);
        });

        // Tải dữ liệu ban đầu cho năm hiện tại
        updateTable(currentYear);
    }

    // Cập nhật bảng với dữ liệu từ ThongKeBUS
    private void updateTable(int year) {
        try {
            List<Object[]> statsList = thongKeBUS.getMonthlyStatistics(year);
            DecimalFormat dt = new DecimalFormat("#,###.00");   
            for (int i = 0; i < 12; i++) {
                if (i < statsList.size()) {
                    Object[] stats = statsList.get(i);
                    table.setValueAt(stats[0], i, 0); // Tháng (String)
                    table.setValueAt(dt.format((Number) stats[1]), i, 1); // Vốn
                    table.setValueAt(dt.format((Number) stats[2]), i, 2); // Doanh thu
                    table.setValueAt(dt.format((Number) stats[3]), i, 3); // Lợi nhuận
                } else {
                    table.setValueAt("Tháng " + (i + 1), i, 0); // Tháng
                    table.setValueAt(dt.format(0), i, 1); // Vốn
                    table.setValueAt(dt.format(0), i, 2); // Doanh thu
                    table.setValueAt(dt.format(0), i, 3); // Lợi nhuận
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xuất dữ liệu ra Excel
    private void exportToExcel(int year) {
        try {
            List<Object[]> statsList = thongKeBUS.getMonthlyStatistics(year);
            DecimalFormat dt = new DecimalFormat("#,###.00");
            // Chuẩn bị dữ liệu cho Excel
            List<String> headers = Arrays.asList("Tháng", "Vốn", "Doanh thu", "Lợi nhuận");
            List<List<Object>> data = new ArrayList<>();
            for (int i = 0; i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // Tháng
                row.add(dt.format((Number) stats[1])); // Vốn
                row.add(dt.format((Number) stats[2])); // Doanh thu
                row.add(dt.format((Number) stats[3])); // Lợi nhuận
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new java.io.File("ThongKeThang_" + year + ".xlsx"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê tháng", filePath);
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}