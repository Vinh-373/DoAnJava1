package com.quanlybanlaptop;

import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.dao.CategoryDAO;
import com.quanlybanlaptop.dao.CompanyDAO;
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
                CategoryDAO categoryDAO = new CategoryDAO(conn);
                CategoryBUS categoryBUS = new CategoryBUS(categoryDAO);
                CompanyDAO companyDAO = new CompanyDAO(conn);
                CompanyBUS companyBUS = new CompanyBUS(companyDAO);
                MainFrame mainFrame = new MainFrame(productBUS,categoryBUS,companyBUS);
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}