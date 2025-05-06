package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;
import com.quanlybanlaptop.bus.ThongKeBUS;

public class TKPanelContent extends JPanel {
    private thongKeMenu menuPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public TKPanelContent(ThongKeBUS thongKeBUS) {
        setLayout(new BorderLayout());
        menuPanel = new thongKeMenu();
        add(menuPanel, BorderLayout.NORTH); // Menu nằm góc trái trên cùng

        // CardLayout cho các panel thống kê
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        TongQuan tongQuan = new TongQuan(thongKeBUS);
        DoanhThu doanhThu = new DoanhThu(thongKeBUS);
        KhachHang khachHang = new KhachHang(thongKeBUS);
        SanPham sanPham = new SanPham(thongKeBUS);
        TonKho tonKho = new TonKho(thongKeBUS); // Giả sử bạn có một lớp TonKho
        

        // Có thể thêm các panel khác nếu cần

        contentPanel.add(tongQuan, "tongquan");
        contentPanel.add(doanhThu, "doanhthu");
        contentPanel.add(khachHang, "khachhang");
        contentPanel.add(sanPham, "sanpham");
        contentPanel.add(tonKho, "tonkho");
        add(contentPanel, BorderLayout.CENTER);

        // Sự kiện cho menuPanel (giả sử có các JButton btnTongQuan, btnDoanhThu)
        menuPanel.getBtnTongQuan().addActionListener(e -> cardLayout.show(contentPanel, "tongquan"));
        menuPanel.getBtnDoanhThu().addActionListener(e -> cardLayout.show(contentPanel, "doanhthu"));
        menuPanel.getBtnKhachHang().addActionListener(e -> cardLayout.show(contentPanel, "khachhang"));
        menuPanel.getBtnSanPham().addActionListener(e -> cardLayout.show(contentPanel, "sanpham"));
        menuPanel.getBtnTonKho().addActionListener(e -> cardLayout.show(contentPanel, "tonkho"));
        // Mặc định hiển thị tổng quan
        cardLayout.show(contentPanel, "tongquan");
    }

    public thongKeMenu getMenuPanel() {
        return menuPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}