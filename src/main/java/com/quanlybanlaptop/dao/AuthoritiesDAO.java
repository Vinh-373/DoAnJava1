package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AuthoritiesDAO {
    private Connection connection;

    public AuthoritiesDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean isChecked(int idRole, int idAuthority) {
        String sql = "SELECT status FROM AUTHORITIES WHERE id_role = ? AND id_authorities = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRole);
            stmt.setInt(2, idAuthority);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateIsChecked(int idRole, int idAuthority, boolean isChecked) {
        String sql = "UPDATE AUTHORITIES SET status = ? WHERE id_role = ? AND id_authorities = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isChecked);
            stmt.setInt(2, idRole);
            stmt.setInt(3, idAuthority);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean insert(int idRole) {
    String sql = "INSERT INTO AUTHORITIES (id_role, id_authorities, name_authorities, status) VALUES " +
                "(?, 1, N'Sản phẩm', 0), " +
                "(?, 2, N'Loại hãng', 0), " +
                "(?, 3, N'Khách hàng', 0), " +
                "(?, 4, N'Tài khoản', 0), " +
                "(?, 5, N'Hóa đơn', 0), " +
                "(?, 6, N'Kho hàng', 0), " +
                "(?, 7, N'Bảo hành', 0), " +
                "(?, 8, N'Thống kê', 0), " +
                "(?, 9, N'Phân quyền', 0)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        // Set idRole for each row
        for (int i = 1; i <= 9; i++) {
            stmt.setInt(i, idRole);
        }
        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
