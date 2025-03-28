package DAO;

import DTO.BillExportedDTO;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class BillExportedDAO {
    private Connection conn;

    // Constructor nhận Connection
    public BillExportedDAO(Connection conn) {
        this.conn = conn;
    }

    // 1️⃣ Thêm một hóa đơn xuất hàng
    public boolean insertBillExported(BillExportedDTO bill) {
        String sql = "INSERT INTO Bill_Exported (Invoice_No, Admin_ID, Total_Price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bill.getInvoiceNo());
            stmt.setString(2, bill.getAdminId());
            stmt.setBigDecimal(3, bill.getTotalPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2️⃣ Cập nhật tổng giá trị hóa đơn
    public boolean updateTotalPrice(String invoiceNo, String adminId, BigDecimal newTotalPrice) {
        String sql = "UPDATE Bill_Exported SET Total_Price = ? WHERE Invoice_No = ? AND Admin_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newTotalPrice);
            stmt.setString(2, invoiceNo);
            stmt.setString(3, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3️⃣ Xóa một hóa đơn xuất hàng
    public boolean deleteBillExported(String invoiceNo, String adminId) {
        String sql = "DELETE FROM Bill_Exported WHERE Invoice_No = ? AND Admin_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoiceNo);
            stmt.setString(2, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4️⃣ Lấy danh sách tất cả hóa đơn xuất hàng
    public ArrayList<BillExportedDTO> getAllBillExported() {
        ArrayList<BillExportedDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Bill_Exported";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BillExportedDTO bill = new BillExportedDTO(
                        rs.getString("Invoice_No"),
                        rs.getString("Admin_ID"),
                        rs.getBigDecimal("Total_Price")
                );
                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 5️⃣ Lấy thông tin hóa đơn xuất hàng theo Invoice_No và Admin_ID
    public BillExportedDTO getBillExportedById(String invoiceNo, String adminId) {
        String sql = "SELECT * FROM Bill_Exported WHERE Invoice_No = ? AND Admin_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoiceNo);
            stmt.setString(2, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BillExportedDTO(
                        rs.getString("Invoice_No"),
                        rs.getString("Admin_ID"),
                        rs.getBigDecimal("Total_Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}