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
public class ThongKeNamPanel extends JPanel {
    private JTable table;
    private JComboBox<Integer> cbNam;
    private ThongKeBUS thongKeBUS;

    public ThongKeNamPanel(ThongKeBUS thongKeBUS) {
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

        // Bảng 10 năm
        String[] col = {"Năm", "Vốn", "Doanh thu", "Lợi nhuận"};
        Object[][] data = new Object[10][4];
        for (int i = 0; i < 10; i++) {
            data[i][0] = (currentYear - 9 + i) + "";
            data[i][1] = "";
            data[i][2] = "";
            data[i][3] = "";
        }
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê doanh thu theo năm"));
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
            int startYear = year - 9; // Hiển thị 10 năm từ startYear
            List<Object[]> statsList = thongKeBUS.getYearlyStatistics(startYear);

            for (int i = 0; i < 10; i++) {
                if (i < statsList.size()) {
                    Object[] stats = statsList.get(i);
                    table.setValueAt(stats[0], i, 0); // Năm (String)
                    table.setValueAt(String.format("%.2f", (Double) stats[1]), i, 1); // Vốn
                    table.setValueAt(String.format("%.2f", (Double) stats[2]), i, 2); // Doanh thu
                    table.setValueAt(String.format("%.2f", (Double) stats[3]), i, 3); // Lợi nhuận
                } else {
                    table.setValueAt((startYear + i) + "", i, 0); // Năm
                    table.setValueAt("", i, 1); // Vốn
                    table.setValueAt("", i, 2); // Doanh thu
                    table.setValueAt("", i, 3); // Lợi nhuận
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
            int startYear = year - 9;
            List<Object[]> statsList = thongKeBUS.getYearlyStatistics(startYear);

            // Chuẩn bị dữ liệu cho Excel
            List<String> headers = Arrays.asList("Năm", "Vốn", "Doanh thu", "Lợi nhuận");
            List<List<Object>> data = new ArrayList<>();
            for (int i = 0; i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // Năm
                row.add(String.format("%.2f", (Double) stats[1])); // Vốn
                row.add(String.format("%.2f", (Double) stats[2])); // Doanh thu
                row.add(String.format("%.2f", (Double) stats[3])); // Lợi nhuận
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new java.io.File("ThongKeNam_" + year + ".xlsx"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê năm", filePath);
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