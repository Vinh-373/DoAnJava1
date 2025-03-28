package DAO;

import DTO.IMeiProductStockDTO;
import java.sql.*;
import java.util.ArrayList;

public class IMeiProductStockDAO {
    private Connection conn;

    public IMeiProductStockDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm IMei Product Stock vào cơ sở dữ liệu
    public boolean insertIMeiProductStock(IMeiProductStockDTO imeiProductStock) {
        String sql = "INSERT INTO IMei_Product_Stock (IMei_No, Product_ID, State) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imeiProductStock.getImeiNo());
            stmt.setString(2, imeiProductStock.getProductId());
            stmt.setString(3, imeiProductStock.getState());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu IMei Product Stock
    public ArrayList<IMeiProductStockDTO> getAllIMeiProductStock() {
        ArrayList<IMeiProductStockDTO> imeiProductStocks = new ArrayList<>();
        String sql = "SELECT * FROM IMei_Product_Stock";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                imeiProductStocks.add(new IMeiProductStockDTO(
                        rs.getString("IMei_No"),
                        rs.getString("Product_ID"),
                        rs.getString("State")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imeiProductStocks;
    }

    // Lấy thông tin IMei Product Stock theo IMei_No
    public IMeiProductStockDTO getIMeiProductStockByImeiNo(String imeiNo) {
        String sql = "SELECT * FROM IMei_Product_Stock WHERE IMei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imeiNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new IMeiProductStockDTO(
                            rs.getString("IMei_No"),
                            rs.getString("Product_ID"),
                            rs.getString("State")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật trạng thái của IMei Product Stock
    public boolean updateIMeiProductStock(IMeiProductStockDTO imeiProductStock) {
        String sql = "UPDATE IMei_Product_Stock SET Product_ID = ?, State = ? WHERE IMei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imeiProductStock.getProductId());
            stmt.setString(2, imeiProductStock.getState());
            stmt.setString(3, imeiProductStock.getImeiNo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa IMei Product Stock theo IMei_No
    public boolean deleteIMeiProductStock(String imeiNo) {
        String sql = "DELETE FROM IMei_Product_Stock WHERE IMei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imeiNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
