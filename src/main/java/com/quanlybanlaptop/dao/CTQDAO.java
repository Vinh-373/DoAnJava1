package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class CTQDAO {
    private Connection conn;

    public CTQDAO(Connection conn) {
        this.conn = conn;
    }
    public boolean isChecked(int idRole, int idAuthority,int idCTQ) {
        String sql = "SELECT status FROM AUTHORITIES_DETAIL WHERE id_role = ? AND id_authorities = ? AND id_detail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idRole);
            pstmt.setInt(2, idAuthority);
            pstmt.setInt(3, idCTQ);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateIsChecked(int idRole, int idAuthority, int idCTQ, boolean isChecked) {
        String sql = "UPDATE AUTHORITIES_DETAIL SET status = ? WHERE id_role = ? AND id_authorities = ? AND id_detail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isChecked);
            pstmt.setInt(2, idRole);
            pstmt.setInt(3, idAuthority);
            pstmt.setInt(4, idCTQ);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
