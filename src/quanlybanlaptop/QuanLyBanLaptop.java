package quanlybanlaptop;

import DAO.ConnectSQLServer;
import DAO.ProductDAO;
import BLL.ProductBLL;
import GUI.MainFrame;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class QuanLyBanLaptop {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = ConnectSQLServer.getConnection();
                if (conn == null) {
                    throw new Exception("Không thể kết nối đến cơ sở dữ liệu!");
                }

                ProductDAO productDAO = new ProductDAO(conn);
                ProductBLL productBLL = new ProductBLL(productDAO);
                MainFrame mainFrame = new MainFrame(productBLL);
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}