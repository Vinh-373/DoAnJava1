package DAO;

import DTO.BillExportedDetailsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class BillExportedDetailsDAO {
    private Connection conn;

    // Constructor nhận Connection
    public BillExportedDetailsDAO(Connection conn) {
        this.conn = conn;
    }

    // 1️⃣ Thêm một hóa đơn xuất hàng
    public boolean insertBillExportedDetails(BillExportedDetailsDTO bed) {
        String sql = "INSERT INTO Bill_Exported_Details (Invoice_No, Admin_ID, Order_No, Customer_ID, " +
                "Product_ID, IMei_No, Unit_Price, Quantity, Discount_No, Total_Price_Before, " +
                "Total_Price_After, Date_Exported, Time_Exported) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bed.getInvoiceNo());
            stmt.setString(2, bed.getAdminId());
            stmt.setString(3, bed.getOrderNo());
            stmt.setString(4, bed.getCustomerId());
            stmt.setString(5, bed.getProductId());
            stmt.setString(6, bed.getImeiNo());
            stmt.setBigDecimal(7, bed.getUnitPrice());
            stmt.setInt(8, bed.getQuantity());
            stmt.setString(9, bed.getDiscountNo());
            stmt.setBigDecimal(10, bed.getTotalPriceBefore());
            stmt.setBigDecimal(11, bed.getTotalPriceAfter());
            stmt.setDate(12, Date.valueOf(bed.getDateExported())); // Chuyển từ LocalDate sang SQL Date
            stmt.setTime(13, Time.valueOf(bed.getTimeExported())); // Chuyển từ LocalTime sang SQL Time
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2️⃣ Lấy danh sách tất cả hóa đơn xuất hàng
    public List<BillExportedDetailsDTO> getAllBillExportedDetails() {
        List<BillExportedDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Bill_Exported_Details";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BillExportedDetailsDTO bed = new BillExportedDetailsDTO(
                        rs.getString("Invoice_No"),
                        rs.getString("Admin_ID"),
                        rs.getString("Order_No"),
                        rs.getString("Customer_ID"),
                        rs.getString("Product_ID"),
                        rs.getString("IMei_No"),
                        rs.getBigDecimal("Unit_Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Discount_No"),
                        rs.getBigDecimal("Total_Price_Before"),
                        rs.getBigDecimal("Total_Price_After"),
                        rs.getDate("Date_Exported").toLocalDate(),
                        rs.getTime("Time_Exported").toLocalTime()
                );
                list.add(bed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3️⃣ Lấy thông tin hóa đơn xuất hàng theo Invoice_No
    public BillExportedDetailsDTO getBillExportedDetailsByInvoiceNo(String invoiceNo) {
        String sql = "SELECT * FROM Bill_Exported_Details WHERE Invoice_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoiceNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BillExportedDetailsDTO(
                        rs.getString("Invoice_No"),
                        rs.getString("Admin_ID"),
                        rs.getString("Order_No"),
                        rs.getString("Customer_ID"),
                        rs.getString("Product_ID"),
                        rs.getString("IMei_No"),
                        rs.getBigDecimal("Unit_Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Discount_No"),
                        rs.getBigDecimal("Total_Price_Before"),
                        rs.getBigDecimal("Total_Price_After"),
                        rs.getDate("Date_Exported").toLocalDate(),
                        rs.getTime("Time_Exported").toLocalTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
