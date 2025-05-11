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
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.text.DecimalFormat;
import com.quanlybanlaptop.bus.ThongKeBUS;
public class KhachHang extends JPanel {
      private JTable table;
    private JTextField txtSearchName;
    private JDateChooser dateChooserStart;
    private JDateChooser dateChooserEnd;
    private ThongKeBUS thongKeBUS;

    public KhachHang(ThongKeBUS thongKeBUS) {
        this.thongKeBUS = thongKeBUS;
        setLayout(new BorderLayout());

        // Top panel: Tìm kiếm và chọn ngày
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Tên khách hàng:"));
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

        
        JButton btnXuatExcel = new JButton("Xuất Excel");
        
        filterPanel.add(btnXuatExcel);

        add(filterPanel, BorderLayout.NORTH);

        // Bảng thống kê khách hàng
        String[] col = {"ID khách hàng", "Tên khách hàng", "Tổng đơn đã mua", "Tổng tiền đã mua"};
        Object[][] data = new Object[0][4]; // Khởi tạo rỗng, sẽ cập nhật sau
        table = new JTable(data, col);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Thống kê khách hàng"));
        add(scrollPane, BorderLayout.CENTER);

        // Thêm DocumentListener cho txtSearchName để tìm kiếm thời gian thực
        txtSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTableWithCurrentFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTableWithCurrentFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTableWithCurrentFilters();
            }
        });

        // Thêm DocumentListener cho JDateChooser để cập nhật khi ngày thay đổi
        if (dateChooserStart.getDateEditor().getUiComponent() instanceof JTextComponent) {
            JTextComponent startDateField = (JTextComponent) dateChooserStart.getDateEditor().getUiComponent();
            startDateField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }
            });
        }

        if (dateChooserEnd.getDateEditor().getUiComponent() instanceof JTextComponent) {
            JTextComponent endDateField = (JTextComponent) dateChooserEnd.getDateEditor().getUiComponent();
            endDateField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateTableWithCurrentFilters();
                }
            });
        }

        
        // Xử lý sự kiện nút Xuất Excel
        btnXuatExcel.addActionListener(e -> {
            String customerName = txtSearchName.getText().trim();
            LocalDate startDate = dateChooserStart.getDate() != null ?
                dateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            LocalDate endDate = dateChooserEnd.getDate() != null ?
                dateChooserEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            exportToExcel(customerName, startDate, endDate);
        });

        // Tải dữ liệu ban đầu (không lọc)
        updateTableWithCurrentFilters();
    }

    // Cập nhật bảng với bộ lọc hiện tại
    private void updateTableWithCurrentFilters() {
        String customerName = txtSearchName.getText().trim();
        LocalDate startDate = dateChooserStart.getDate() != null ?
            dateChooserStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate endDate = dateChooserEnd.getDate() != null ?
            dateChooserEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        updateTable(customerName, startDate, endDate);
    }

    // Cập nhật bảng với dữ liệu từ ThongKeBUS
    private void updateTable(String customerName, LocalDate startDate, LocalDate endDate) {
        try {
            List<Object[]> statsList = thongKeBUS.getCustomerStatistics(customerName, startDate, endDate);
            DecimalFormat dt = new DecimalFormat("#,###.00");
            // Tạo dữ liệu cho bảng
            Object[][] data = new Object[statsList.size()][4];
            for (int i = 0; i < statsList.size(); i++) {
                Object[] stats = statsList.get(i);
                data[i][0] = stats[0]; // ID khách hàng
                data[i][1] = stats[1]; // Tên khách hàng
                data[i][2] = stats[2]; // Tổng đơn
                data[i][3] = dt.format((Number) stats[3]); // Tổng tiền
            }

            // Cập nhật bảng
            table.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID khách hàng", "Tên khách hàng", "Tổng đơn đã mua", "Tổng tiền đã mua"}));
            table.setRowHeight(28);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xuất dữ liệu ra Excel
    private void exportToExcel(String customerName, LocalDate startDate, LocalDate endDate) {
        try {
            List<Object[]> statsList = thongKeBUS.getCustomerStatistics(customerName, startDate, endDate);

            // Chuẩn bị dữ liệu cho Excel
            List<String> headers = Arrays.asList("ID khách hàng", "Tên khách hàng", "Tổng đơn đã mua", "Tổng tiền đã mua");
            List<List<Object>> data = new ArrayList<>();
            for (Object[] stats : statsList) {
                List<Object> row = new ArrayList<>();
                row.add(stats[0]); // ID khách hàng
                row.add(stats[1]); // Tên khách hàng
                row.add(stats[2]); // Tổng đơn
                row.add(String.format("%.2f", (Double) stats[3])); // Tổng tiền
                data.add(row);
            }

            // Mở JFileChooser để chọn đường dẫn
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            String fileName = "ThongKeKhachHang_" + (startDate != null ? startDate.toString() : "NoStart") + "_" +
                             (endDate != null ? endDate.toString() : "NoEnd") + ".xlsx";
            fileChooser.setSelectedFile(new java.io.File(fileName));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                ExcelExporter.exportToExcel(headers, data, "Thống kê khách hàng", filePath);
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

    // Xu