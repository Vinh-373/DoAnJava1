package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;

public class thongKeMenu extends JPanel {
    private JButton btnTongQuan;
    private JButton btnKhachHang;
    private JButton btnTonKho;
    private JButton btnDoanhThu;
    private JButton btnSanPham;

    public thongKeMenu() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBackground(Color.WHITE);

        btnTongQuan = createMenuButton("Tổng Quan");
        btnKhachHang = createMenuButton("Khách Hàng");
        btnTonKho = createMenuButton("Tồn Kho");
        btnDoanhThu = createMenuButton("Doanh Thu");
        btnSanPham = createMenuButton("Sản Phẩm");

        add(btnTongQuan);
        add(btnKhachHang);
        add(btnTonKho);
        add(btnDoanhThu);
        add(btnSanPham);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(5, 10, 5, 10));
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return btn;
    }

    // Getter cho các nút nếu cần gắn sự kiện chuyển panel
    public JButton getBtnTongQuan() { return btnTongQuan; }
    public JButton getBtnKhachHang() { return btnKhachHang; }
    public JButton getBtnTonKho() { return btnTonKho; }
    public JButton getBtnDoanhThu() { return btnDoanhThu; }
    public JButton getBtnSanPham() { return btnSanPham; }
}