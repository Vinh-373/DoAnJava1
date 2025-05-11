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
    public boolean insert(int idRole) {
        String sql = "INSERT INTO AUTHORITIES_DETAIL (id_role, id_authorities, id_detail, name, status) VALUES " +
                "(?, 1, 1, N'Thêm', 0), " +
                "(?, 1, 2, N'Sửa', 0), " +
                "(?, 1, 3, N'Xóa', 0), " +
                "(?, 1, 4, N'Chi tiết', 0), " +
                "(?, 1, 5, N'DS Seri', 0), " +
                "(?, 1, 6, N'Nhập Ex', 0), " +
                "(?, 1, 7, N'Xuất Ex', 0), " +
                "(?, 2, 1, N'Thêm', 0), " +
                "(?, 2, 2, N'Sửa', 0), " +
                "(?, 2, 3, N'Xóa', 0), " +
                "(?, 3, 1, N'Thêm', 0), " +
                "(?, 3, 2, N'Sửa', 0), " +
                "(?, 3, 3, N'Xóa', 0), " +
                "(?, 3, 4, N'Xuất Ex', 0), " +
                "(?, 4, 1, N'Thêm', 0), " +
                "(?, 4, 2, N'Sửa', 0), " +
                "(?, 4, 3, N'Xóa', 0), " +
                "(?, 5, 1, N'Thêm', 0), " +
                "(?, 5, 2, N'Xóa', 0), " +
                "(?, 5, 3, N'Xuất PDF', 0), " +
                "(?, 6, 1, N'Nhập', 0), " +
                "(?, 6, 2, N'Xuất', 0), " +
                "(?, 7, 1, N'Thêm', 0), " +
                "(?, 7, 2, N'Xóa', 0), " +
                "(?, 7, 3, N'ThêmYC', 0), " +
                "(?, 7, 4, N'Xuất Ex', 0), " +
                "(?, 8, 1, N'TQuan', 0), " +
                "(?, 8, 2, N'KHàng', 0), " +
                "(?, 8, 3, N'Tồn', 0), " +
                "(?, 8, 4, N'DThu', 0), " +
                "(?, 8, 5, N'SPhẩm', 0)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set idRole for each row
           for (int i = 1; i <= 31; i++) {
            pstmt.setInt(i, idRole);
        }
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
