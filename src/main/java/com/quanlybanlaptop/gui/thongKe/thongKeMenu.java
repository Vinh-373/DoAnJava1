package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;

import com.quanlybanlaptop.bus.AuthoritiesBUS;
import com.quanlybanlaptop.bus.CTQBUS;
import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.dao.AuthoritiesDAO;
import com.quanlybanlaptop.dao.CTQDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.AdminDTO;

import java.awt.*;
import java.sql.Connection;

public class thongKeMenu extends JPanel {
    private JButton btnTongQuan;
    private JButton btnKhachHang;
    private JButton btnTonKho;
    private JButton btnDoanhThu;
    private JButton btnSanPham;

    public thongKeMenu(AdminDTO adminDTO) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBackground(Color.WHITE);
          Connection cnn = DatabaseConnection.getConnection();
      
        CTQDAO ctqDAO = new CTQDAO(cnn);
        CTQBUS ctqBUS = new CTQBUS(ctqDAO);
        btnTongQuan = createMenuButton("Tổng Quan");
        btnTongQuan.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 8, 1));
        btnKhachHang = createMenuButton("Khách Hàng");
        btnKhachHang.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 8, 2));
        btnTonKho = createMenuButton("Tồn Kho");
        btnTonKho.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 8, 3));
        btnDoanhThu = createMenuButton("Doanh Thu");
        btnDoanhThu.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 8, 4));
        btnSanPham = createMenuButton("Sản Phẩm");
        btnSanPham.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 8, 5));

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