package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
public class ThongKeDAO {
    private Connection conn;

    public ThongKeDAO(Connection conn) {
        this.conn = conn;
    }

    public int getTongSanPham() throws SQLException {
        String sql = "SELECT COUNT(*) FROM PRODUCT";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTongKhachHang() throws SQLException {
        String sql = "SELECT COUNT(*) FROM CUSTOMER";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getTongNhanVien() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ADMIN";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Object[]> getDoanhThu7NgayGanNhat() throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String sql = 
            "SELECT d.Ngay, " +
            "   ISNULL(SUM(bi.total_price), 0) AS Von, " +
            "   ISNULL(SUM(be.total_price), 0) AS DoanhThu " +
            "FROM (" +
            "   SELECT CONVERT(VARCHAR(10), DATEADD(DAY, -number, CAST(GETDATE() AS DATE)), 120) AS Ngay " +
            "   FROM master.dbo.spt_values " +
            "   WHERE type = 'P' AND number BETWEEN 0 AND 6 " +
            ") d " +
            "LEFT JOIN BILL_EXPORT be ON CONVERT(VARCHAR(10), be.date_ex, 120) = d.Ngay " +
            "LEFT JOIN BILL_IMPORT bi ON CONVERT(VARCHAR(10), bi.date_import, 120) = d.Ngay " +
            "GROUP BY d.Ngay " +
            "ORDER BY d.Ngay DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("Ngay");
                row[1] = rs.getBigDecimal("Von");
                row[2] = rs.getBigDecimal("DoanhThu");
                result.add(row);
            }
        }
        return result;
    }
    // Lấy doanh thu theo ngày trong tháng
    public List<Object[]> getDailyRevenue(int year, int month) throws SQLException {
        List<Object[]> statsList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        String sql = "SELECT DAY(date_ex) AS day, SUM(total_price) AS revenue " +
                     "FROM BILL_EXPORT " +
                     "WHERE YEAR(date_ex) = ? AND MONTH(date_ex) = ? " +
                     "GROUP BY DAY(date_ex)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();

            // Khởi tạo danh sách với tất cả các ngày trong tháng, doanh thu = 0
            for (int day = 1; day <= daysInMonth; day++) {
                statsList.add(new Object[]{day, 0.0, 0.0, 0.0});
            }

            // Cập nhật doanh thu cho các ngày có dữ liệu
            while (rs.next()) {
                int day = rs.getInt("day");
                double revenue = rs.getDouble("revenue");
                statsList.get(day - 1)[1] = revenue;
            }
        }
        return statsList;
    }

    // Tính vốn theo ngày trong tháng
    public void calculateDailyCapital(List<Object[]> statsList, int year, int month) throws SQLException {
        String sql = "WITH ProductCount AS (" +
                    "    SELECT DAY(be.date_ex) AS day, bed.id_product, COUNT(bed.num_seri) AS quantity " +
                    "    FROM BILL_EXPORT be " +
                    "    JOIN BILL_EXPORT_DETAIL bed ON be.id_bill_ex = bed.id_bill_ex " +
                    "    WHERE YEAR(be.date_ex) = ? AND MONTH(be.date_ex) = ? " +
                    "    GROUP BY DAY(be.date_ex), bed.id_product" +
                    ") " +
                    "SELECT pc.day, SUM(pc.quantity * bi.unit_price) AS capital " +
                    "FROM ProductCount pc " +
                    "JOIN BILL_IMPORT bi ON pc.id_product = bi.id_product " +
                    "GROUP BY pc.day";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();

            // Cập nhật vốn cho các ngày có dữ liệu
            while (rs.next()) {
                int day = rs.getInt("day");
                double capital = rs.getDouble("capital");
                statsList.get(day - 1)[2] = capital;
            }
        }
    }
    // Lấy doanh thu theo tháng trong năm
    public List<Object[]> getMonthlyRevenue(int year) throws SQLException {
        List<Object[]> statsList = new ArrayList<>();

        String sql = "SELECT MONTH(date_ex) AS month, SUM(total_price) AS revenue " +
                     "FROM BILL_EXPORT " +
                     "WHERE YEAR(date_ex) = ? " +
                     "GROUP BY MONTH(date_ex)";

        try (PreparedStatement validity = conn.prepareStatement(sql)) {
            validity.setInt(1, year);
            ResultSet rs = validity.executeQuery();

            // Khởi tạo danh sách với 12 tháng, doanh thu = 0
            for (int month = 1; month <= 12; month++) {
                statsList.add(new Object[]{month, 0.0, 0.0, 0.0});
            }

            // Cập nhật doanh thu cho các tháng có dữ liệu
            while (rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                statsList.get(month - 1)[1] = revenue;
            }
        }
        return statsList;
    }

    // Tính vốn theo tháng trong năm
    public void calculateMonthlyCapital(List<Object[]> statsList, int year) throws SQLException {
        String sql = "WITH ProductCount AS (" +
                    "    SELECT MONTH(be.date_ex) AS month, bed.id_product, COUNT(bed.num_seri) AS quantity " +
                    "    FROM BILL_EXPORT be " +
                    "    JOIN BILL_EXPORT_DETAIL bed ON be.id_bill_ex = bed.id_bill_ex " +
                    "    WHERE YEAR(be.date_ex) = ? " +
                    "    GROUP BY MONTH(be.date_ex), bed.id_product" +
                    ") " +
                    "SELECT pc.month, SUM(pc.quantity * bi.unit_price) AS capital " +
                    "FROM ProductCount pc " +
                    "JOIN BILL_IMPORT bi ON pc.id_product = bi.id_product " +
                    "GROUP BY pc.month";

        try (PreparedStatement validity = conn.prepareStatement(sql)) {
            validity.setInt(1, year);
            ResultSet rs = validity.executeQuery();

            // Cập nhật vốn cho các tháng có dữ liệu
            while (rs.next()) {
                int month = rs.getInt("month");
                double capital = rs.getDouble("capital");
                statsList.get(month - 1)[2] = capital;
            }
        }
    }
    // Lấy doanh thu theo năm
    public List<Object[]> getYearlyRevenue(int startYear) throws SQLException {
        List<Object[]> statsList = new ArrayList<>();

        String sql = "SELECT YEAR(date_ex) AS year, SUM(total_price) AS revenue " +
                     "FROM BILL_EXPORT " +
                     "WHERE YEAR(date_ex) BETWEEN ? AND ? " +
                     "GROUP BY YEAR(date_ex)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, startYear + 9);
            ResultSet rs = stmt.executeQuery();

            // Khởi tạo danh sách với 10 năm, doanh thu = 0
            for (int i = 0; i < 10; i++) {
                statsList.add(new Object[]{startYear + i, 0.0, 0.0, 0.0});
            }

            // Cập nhật doanh thu cho các năm có dữ liệu
            while (rs.next()) {
                int year = rs.getInt("year");
                double revenue = rs.getDouble("revenue");
                int index = year - startYear;
                if (index >= 0 && index < 10) {
                    statsList.get(index)[1] = revenue;
                }
            }
        }
        return statsList;
    }

    // Tính vốn theo năm
    public void calculateYearlyCapital(List<Object[]> statsList, int startYear) throws SQLException {
        String sql = "WITH ProductCount AS (" +
                    "    SELECT YEAR(be.date_ex) AS year, bed.id_product, COUNT(bed.num_seri) AS quantity " +
                    "    FROM BILL_EXPORT be " +
                    "    JOIN BILL_EXPORT_DETAIL bed ON be.id_bill_ex = bed.id_bill_ex " +
                    "    WHERE YEAR(be.date_ex) BETWEEN ? AND ? " +
                    "    GROUP BY YEAR(be.date_ex), bed.id_product" +
                    ") " +
                    "SELECT pc.year, SUM(pc.quantity * bi.unit_price) AS capital " +
                    "FROM ProductCount pc " +
                    "JOIN BILL_IMPORT bi ON pc.id_product = bi.id_product " +
                    "GROUP BY pc.year";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, startYear + 9);
            ResultSet rs = stmt.executeQuery();

            // Cập nhật vốn cho các năm có dữ liệu
            while (rs.next()) {
                int year = rs.getInt("year");
                double capital = rs.getDouble("capital");
                int index = year - startYear;
                if (index >= 0 && index < 10) {
                    statsList.get(index)[2] = capital;
                }
            }
        }
    }
    // Lấy thống kê khách hàng
    public List<Object[]> getCustomerStatistics(String customerName, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Object[]> statsList = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT c.id_customer, c.name, " +
            "COUNT(be.id_bill_ex) AS total_orders, " +
            "COALESCE(SUM(be.total_price), 0) AS total_spent " +
            "FROM CUSTOMER c " +
            "LEFT JOIN BILL_EXPORT be ON c.id_customer = be.id_customer " +
            "WHERE c.status = 1" // Chỉ lấy khách hàng đang hoạt động
        );

        // Thêm điều kiện tìm kiếm tên khách hàng
        if (customerName != null && !customerName.isEmpty()) {
            sql.append(" AND c.name LIKE ?");
        }

        // Thêm điều kiện lọc ngày
        if (startDate != null) {
            sql.append(" AND be.date_ex >= ?");
        }
        if (endDate != null) {
            sql.append(" AND be.date_ex <= ?");
        }

        sql.append(" GROUP BY c.id_customer, c.name");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Gán tham số cho tên khách hàng
            if (customerName != null && !customerName.isEmpty()) {
                stmt.setString(paramIndex++, "%" + customerName + "%");
            }

            // Gán tham số cho ngày
            if (startDate != null) {
                stmt.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
            }
            if (endDate != null) {
                stmt.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] stats = new Object[4];
                stats[0] = String.valueOf(rs.getInt("id_customer")); // ID khách hàng
                stats[1] = rs.getString("name"); // Tên khách hàng
                stats[2] = rs.getInt("total_orders"); // Tổng đơn
                stats[3] = rs.getDouble("total_spent"); // Tổng tiền
                statsList.add(stats);
            }
        }
        return statsList;
    }
   // Lấy thống kê sản phẩm theo tên và khoảng ngày (so sánh theo ngày, bỏ qua giờ)
public List<Object[]> getProductStatistics(String productName, LocalDate startDate, LocalDate endDate) throws SQLException {
    List<Object[]> statsList = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT p.id_product, p.name, " +
        "COUNT(bed.num_seri) AS quantity_sold, " +
        "COALESCE(SUM(bed.unit_price), 0) AS revenue " +
        "FROM PRODUCT p " +
        "LEFT JOIN BILL_EXPORT_DETAIL bed ON p.id_product = bed.id_product " +
        "LEFT JOIN BILL_EXPORT be ON bed.id_bill_ex = be.id_bill_ex " +
        "WHERE p.status = 1"
    );

    // Thêm điều kiện tên sản phẩm nếu có
    if (productName != null && !productName.trim().isEmpty()) {
        sql.append(" AND p.name LIKE ?");
    }

    // So sánh ngày, bỏ qua giờ/phút/giây
    if (startDate != null) {
        sql.append(" AND CONVERT(DATE, be.date_ex) >= ?");
    }
    if (endDate != null) {
        sql.append(" AND CONVERT(DATE, be.date_ex) <= ?");
    }

    sql.append(" GROUP BY p.id_product, p.name");

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
        int paramIndex = 1;

        if (productName != null && !productName.trim().isEmpty()) {
            stmt.setString(paramIndex++, "%" + productName.trim() + "%");
        }
        if (startDate != null) {
            stmt.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
        }
        if (endDate != null) {
            stmt.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
        }

        System.out.println("SQL Query: " + sql);
        System.out.println("Parameters: productName=" + productName + ", startDate=" + startDate + ", endDate=" + endDate);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Object[] stats = new Object[4];
            stats[0] = String.valueOf(rs.getInt("id_product")); // Mã sản phẩm
            stats[1] = rs.getString("name"); // Tên sản phẩm
            stats[2] = rs.getInt("quantity_sold"); // Số lượng bán
            stats[3] = rs.getDouble("revenue"); // Doanh thu
            statsList.add(stats);
        }

        System.out.println("Số lượng bản ghi trả về từ DB: " + statsList.size());
    }
    return statsList;
}
// Lấy thống kê tồn kho
public List<Object[]> getInventoryStatistics(String productName) throws SQLException {
    List<Object[]> statsList = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT p.id_product, p.name, p.quantity_stock, p.quantity " +
        "FROM PRODUCT p " +
        "WHERE p.status = 1"
    );

    if (productName != null && !productName.trim().isEmpty()) {
        sql.append(" AND p.name LIKE ?");
    }

    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
        if (productName != null && !productName.trim().isEmpty()) {
            stmt.setString(1, "%" + productName.trim() + "%");
        }

        System.out.println("SQL Query: " + sql.toString());
        System.out.println("Parameters: productName=" + productName);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Object[] stats = new Object[4];
            stats[0] = String.valueOf(rs.getInt("id_product")); // Mã sản phẩm
            stats[1] = rs.getString("name"); // Tên sản phẩm
            stats[2] = rs.getInt("quantity_stock"); // Số lượng tồn kho
            stats[3] = rs.getInt("quantity"); // Hàng tồn cửa hàng
            statsList.add(stats);
        }

        System.out.println("Số lượng bản ghi trả về từ DB: " + statsList.size());
    }
    return statsList;
}
}
