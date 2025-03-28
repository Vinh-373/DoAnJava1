package DAO;
import java.sql.Connection;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class ConnectSQLServer {
    private static final String SERVER = "LAPTOP-HDQ605AN\\SQLEXPRESS";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    private static final String DATABASE = "quanlibanlaptop";
    private static final int PORT = 1433;

    // Phương thức lấy kết nối
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

    // Phương thức đóng kết nối
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔌 Đã đóng kết nối.");
            } catch (SQLException ex) {
                System.err.println("⚠️ Lỗi khi đóng kết nối: " + ex.getMessage());
            }
        }
    }
    // Hàm main để kiểm tra kết nối
//    public static void main(String[] args) {
//        System.out.println("🔍 Đang kiểm tra kết nối đến SQL Server...");
//
//        Connection conn = getConnection();
//        if (conn != null) {
//            System.out.println("✅ Kết nối thành công đến cơ sở dữ liệu: " + DATABASE);
//            closeConnection(conn);
//        } else {
//            System.out.println("❌ Kết nối thất bại. Vui lòng kiểm tra thông tin kết nối.");
//        }
//    }
}
