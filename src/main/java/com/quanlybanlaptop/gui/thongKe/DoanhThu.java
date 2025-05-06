package com.quanlybanlaptop.gui.thongKe;
import javax.swing.*;
import java.awt.*;
import com.quanlybanlaptop.bus.ThongKeBUS;
public class DoanhThu extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DoanhThu(ThongKeBUS thongKeBUS) {
        setLayout(new BorderLayout());

        // Menu nút
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNgay = new JButton("Thống kê ngày");
        JButton btnThang = new JButton("Thống kê tháng");
        JButton btnNam = new JButton("Thống kê năm");
        menuPanel.add(btnNgay);
        menuPanel.add(btnThang);
        menuPanel.add(btnNam);
        add(menuPanel, BorderLayout.NORTH);

        // CardLayout cho các panel thống kê
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(new ThongKeNgayPanel(thongKeBUS), "ngay");
        cardPanel.add(new ThongKeThangPanel(thongKeBUS), "thang");
        cardPanel.add(new ThongKeNamPanel(thongKeBUS), "nam");
        add(cardPanel, BorderLayout.CENTER);
        cardPanel.add(new ThongKeThangPanel(thongKeBUS), "thang");
        cardPanel.add(new ThongKeNamPanel(thongKeBUS), "nam");
        add(cardPanel, BorderLayout.CENTER);

        // Sự kiện chuyển panel
        btnNgay.addActionListener(e -> cardLayout.show(cardPanel, "ngay"));
        btnThang.addActionListener(e -> cardLayout.show(cardPanel, "thang"));
        btnNam.addActionListener(e -> cardLayout.show(cardPanel, "nam"));

        // Mặc định hiển thị thống kê ngày
        cardLayout.show(cardPanel, "ngay");
    }
}