package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.SeriProductDTO;

import java.sql.*;
import java.util.ArrayList;

public class SeriProductDAO {
    private Connection conn;

    public SeriProductDAO(Connection conn) {
        this.conn = conn;
    }

    private SeriProductDTO createDTO(ResultSet rs) throws SQLException {
        return new SeriProductDTO(
                rs.getString("num_seri"),
                rs.getInt("id_product"),
                rs.getInt("status")
        );
    }

    public boolean insertSeriProduct(SeriProductDTO dto) {
        String sql = "INSERT INTO SERI_PRODUCT (num_seri, id_product, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getNumSeri());
            stmt.setInt(2, dto.getIdProduct());
            stmt.setInt(3, dto.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<SeriProductDTO> getAllSeriProduct() {
        ArrayList<SeriProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SERI_PRODUCT WHERE status = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(createDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public  ArrayList<SeriProductDTO> getListSeriById(int idProduct, int status) {
        ArrayList<SeriProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SERI_PRODUCT WHERE status = ? AND id_product = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setInt(2, idProduct);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(createDTO(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public SeriProductDTO getByNumSeri(String numSeri) {
        String sql = "SELECT * FROM SERI_PRODUCT WHERE num_seri = ? AND status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numSeri);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return createDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public SeriProductDTO getByNumSeri2(String numSeri) {
        String sql = "SELECT * FROM SERI_PRODUCT WHERE num_seri = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numSeri);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return createDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateSeriProduct(ArrayList<String> seriProduct, int status) {
        String sql = "UPDATE SERI_PRODUCT SET status = ? WHERE num_seri = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String seri : seriProduct) {
                stmt.setInt(1, status);         // Sử dụng tham số status truyền vào
                stmt.setString(2, seri);
                stmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteSeriProduct(String numSeri) {
        String sql = "UPDATE SERI_PRODUCT SET status = 0 WHERE num_seri = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numSeri);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isSeriExists(String numSeri) {
        String sql = "SELECT 1 FROM SERI_PRODUCT WHERE num_seri = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numSeri);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Nếu có kết quả thì Seri đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Lỗi xảy ra → coi như không tồn tại (hoặc bạn có thể xử lý riêng)
        }
    }
    public boolean updateStatusTo3BySeri(String numSeri) {
        String sql = "UPDATE SERI_PRODUCT SET status = 3 WHERE num_seri = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numSeri);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
