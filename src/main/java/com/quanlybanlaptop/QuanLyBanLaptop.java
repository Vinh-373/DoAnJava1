package com.quanlybanlaptop;

import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.ProductDAO;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.gui.mainView.MainFrame;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class QuanLyBanLaptop {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                if (conn == null) {
                    throw new Exception("Không thể kết nối đến cơ sở dữ liệu!");
                }

                ProductDAO productDAO = new ProductDAO(conn);
                ProductBUS productBUS = new ProductBUS(productDAO);
                MainFrame mainFrame = new MainFrame(productBUS);
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}