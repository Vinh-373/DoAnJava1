package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.quanlybanlaptop.util.ExcelExporter;
import com.quanlybanlaptop.bus.ThongKeBUS;
import java.text.DecimalFormat;
public class ThongKeNgayPanel extends JPanel {
    private JTable table;
    private JComboBox<Integer> cbNam;
    private JComboBox<Integer> cbThang;
    private ThongKeBUS thongKeBUS;

    public ThongKeNgayPanel(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        setLayout(new BorderLayout());

        // Bộ lọc chọn năm, tháng
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbNam = new JComboBox<>();
        cbThang = new JComboBox<>();
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        for (int y = currentYear - 10; y <= currentYear + 1; y++) cbNam.addItem(y);
        for (int m = 1; m <= 12; m++) cbThang.addItem(m);
        cbNam.setSelectedItem(currentYear);
        cbThang.setSelectedItem(currentMonth);
        filterPanel.add(new JLabel("Năm:"));
        filterPanel.add(cbNam);
        filterPanel.add(new JLabel("Tháng:"));
        filterPanel.add(cbThang);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnXuatExcel = new JButton("Xuất Excel");
        filterPanel.add(btnTimKiem);
        filterPanel.add(btnXuatExcel);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng 31 ngày
        String[] col = {"Ngày", "Vốn", "Doanh thu", "Lợi nhuận"};
        Object[][] data = new Object[31][4];
        for (int i = 0; i < 31; i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = "";
            data[i][2] = "";
            data[i][3] = "";
        }
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê doanh thu theo ngày"));
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện nút Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            int year = (int) cbNam.getSelectedItem();
            int month = (int) cbThang.getSelectedItem();
            updateTable(year, month);
        });
        btnXuatExcel.addActionListener(e -> {
            int year = (int) cbNam.getSelectedItem();
            int month = (int) cbThang.getSelectedItem();
            exportToExcel(year, month);
        });

        // Tải dữ liệu ban đầu cho tháng hiện tại
        updateTable(currentYear, currentMonth);
    }
    // Xuất dữ liệu ra Excel
    private void exportToExcel(int year, int month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            List<Object[]> statsList = thongKeBUS.getDailyStatistics(year, month);
            DecimalFormat dt = new DecimalFormat("#,###.00");
            // Chuẩn bị dữ liệu cho Excel
            List<String> headers = Arrays.asList("Ngày", "Vốn", "Doanh thu", "Lợi nhuận");
            List<List<Object>> data = new ArrayList<>();
            for (int i = 0; i < daysInMonth && i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // Ngày
                row.add(dt.format((Number) stats[1])); // Vốn
                row.add(dt.format((Number) stats[2])); // Doanh thu
                row.add(dt.format((Number) stats[3])); // Lợi nhuận
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new java.io.File("ThongKeNgay_" + year + "_" + month + ".xlsx"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê ngày", filePath);
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

    // Phương thức để cập nhật bảng với dữ liệu từ ThongKeBUS
    private void updateTable(int year, int month) {
    try {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Object[]> statsList = thongKeBUS.getDailyStatistics(year, month);
        DecimalFormat dt = new DecimalFormat("#,###.00");
        for (int i = 0; i < 31; i++) {
            if (i < daysInMonth && i < statsList.size()) {
                Object[] stats = statsList.get(i);
                table.setValueAt(stats[0], i, 0); // Ngày (String)
                table.setValueAt(dt.format((Number) stats[1]), i, 1); // Vốn
                table.setValueAt(dt.format((Number) stats[2]), i, 2); // Doanh thu
                table.setValueAt(dt.format((Number) stats[3]), i, 3); // Lợi nhuận
            } else {
                table.setValueAt((i + 1) + "", i, 0); // Ngày
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
}