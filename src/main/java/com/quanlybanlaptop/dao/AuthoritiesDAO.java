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
}
