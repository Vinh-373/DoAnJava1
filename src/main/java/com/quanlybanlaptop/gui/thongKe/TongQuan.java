package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;
import com.quanlybanlaptop.bus.ThongKeBUS;
import java.util.List;

public class TongQuan extends JPanel {
    private JLabel lblTongSanPham;
    private JLabel lblTongKhachHang;
    private JLabel lblTongNhanVien;
    private JTable tableDoanhThu;

    public TongQuan(ThongKeBUS thongKeBUS) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel tổng quan 3 ô
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        int tongSanPham = 0;
        int tongKhachHang = 0;
        int tongNhanVien = 0;
        try {
            tongSanPham = thongKeBUS.getTongSanPham();
            tongKhachHang = thongKeBUS.getTongKhachHang();
            tongNhanVien = thongKeBUS.getTongNhanVien();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        lblTongSanPham = createInfoBox("Tổng Sản Phẩm", tongSanPham);
        lblTongKhachHang = createInfoBox("Tổng Khách Hàng", tongKhachHang);
        lblTongNhanVien = createInfoBox("Tổng Nhân Viên", tongNhanVien);

        topPanel.add(lblTongSanPham);
        topPanel.add(lblTongKhachHang);
        topPanel.add(lblTongNhanVien);

        add(topPanel, BorderLayout.NORTH);

        // Bảng doanh thu 7 ngày gần nhất
        String[] columnNames = {"Ngày", "Vốn", "Doanh thu"};
        Object[][] data = new Object[7][3];

        try {
            // Lấy dữ liệu 7 ngày gần nhất từ BUS (giả sử trả về List<Object[]>)
            List<Object[]> doanhThu7Ngay = thongKeBUS.getDoanhThu7NgayGanNhat();
            for (int i = 0; i < doanhThu7Ngay.size() && i < 7; i++) {
                data[i] = doanhThu7Ngay.get(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tableDoanhThu = new JTable(data, columnNames);
        tableDoanhThu.setRowHeight(28);
        tableDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableDoanhThu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableDoanhThu.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(tableDoanhThu);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Doanh thu 7 ngày gần nhất"));

        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createInfoBox(String title, int value) {
        JLabel panel = new JLabel("<html><div style='text-align:center;'>"
                + "<div style='font-size:16px;font-weight:bold;'>" + title + "</div>"
                + "<div style='font-size:28px;color:#007bff;margin-top:10px;'>" + value + "</div>"
                + "</div></html>", SwingConstants.CENTER);
        panel.setOpaque(true);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        panel.setPreferredSize(new Dimension(180, 100));
        return panel;
    }

    // Các phương thức cập nhật số liệu nếu cần
    public void setTongSanPham(int value) {
        lblTongSanPham.setText(createInfoHtml("Tổng Sản Phẩm", value));
    }
    public void setTongKhachHang(int value) {
        lblTongKhachHang.setText(createInfoHtml("Tổng Khách Hàng", value));
    }
    public void setTongNhanVien(int value) {
        lblTongNhanVien.setText(createInfoHtml("Tổng Nhân Viên", value));
    }
    private String createInfoHtml(String title, int value) {
        return "<html><div style='text-align:center;'>"
                + "<div style='font-size:16px;font-weight:bold;'>" + title + "</div>"
                + "<div style='font-size:28px;color:#007bff;margin-top:10px;'>" + value + "</div>"
                + "</div></html>";
    }
}