package com.quanlybanlaptop.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.*;

public class DatabaseConnection {
    private static final String SERVER = "LAPTOP-HDQ605AN\\SQLEXPRESS";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String DATABASE = "QLLT";
    private static final int PORT = 1433;
    private static Connection con = null;

    // Lấy kết nối
    public static Connection getConnection() {
    SQLServerDataSource ds = new SQLServerDataSource();
    ds.setUser(USER);
    ds.setPassword(PASSWORD);
    ds.setDatabaseName(DATABASE);
    ds.setServerName(SERVER);
    ds.setPortNumber(PORT);
    ds.setTrustServerCertificate(true);
    try {
        return ds.getConnection();
    } catch (SQLException ex) {
        System.err.println("❌ Lỗi kết nối: " + ex.getMessage());
        return null;
    }
}

    // Đóng kết nối
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("🔌 Đã đóng kết nối.");
                con = null;
            } catch (SQLException ex) {
                System.err.println("⚠️ Lỗi khi đóng kết nối: " + ex.getMessage());
            }
        }
    }

    // Truy vấn SELECT trả về ResultSet
    public static ResultSet runQuery(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi truy vấn runQuery: " + e.getMessage());
            return null;
        }
    }

    // Truy vấn INSERT, UPDATE, DELETE trả về số dòng ảnh hưởng
    public static int runUpdate(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi truy vấn runUpdate: " + e.getMessage());
            return 0;
        }
    }

    // Truy vấn sử dụng PreparedStatement (có tham số truyền vào)
    public static int prepareUpdate(String sql, Object... params) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi truy vấn prepareUpdate: " + e.getMessage());
            return 0;
        }
    }

    // Hàm kiểm tra kết nối
    public static void main(String[] args) {
        System.out.println("🔍 Đang kiểm tra kết nối đến SQL Server...");
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Kết nối thành công đến cơ sở dữ liệu: " + DATABASE);
            closeConnection();
        } else {
            System.out.println("❌ Kết nối thất bại. Vui lòng kiểm tra thông tin kết nối.");
        }
    }
}
