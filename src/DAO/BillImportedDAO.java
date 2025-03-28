package DAO;

import DTO.BillImportedDTO;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BillImportedDAO {
    private Connection conn;

    public BillImportedDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một hóa đơn nhập hàng mới
    public boolean insertBillImported(BillImportedDTO bill) {
        String sql = "INSERT INTO Bill_Imported (Invoice_No, Admin_ID, Total_Price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bill.getInvoiceNo());
            stmt.setString(2, bill.getAdminId());
            stmt.setBigDecimal(3, bill.getTotalPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tất cả hóa đơn nhập hàng
    public ArrayList<BillImportedDTO> getAllBillImported() {
        ArrayList<BillImportedDTO> billList = new ArrayList<>();
        String sql = "SELECT * FROM Bill_Imported";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BillImportedDTO bill = new BillImportedDTO(
                        rs.getString("Invoice_No"),
                        rs.getString("Admin_ID"),
                        rs.getBigDecimal("Total_Price")
                );
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    // Lấy một hóa đơn nhập hàng theo Invoice_No
    public BillImportedDTO getBillImportedById(String invoiceNo) {
        String sql = "SELECT * FROM Bill_Imported WHERE Invoice_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoiceNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new BillImportedDTO(
                            rs.getString("Invoice_No"),
                            rs.getString("Admin_ID"),
                            rs.getBigDecimal("Total_Price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin hóa đơn nhập hàng
    public boolean updateBillImported(BillImportedDTO bill) {
        String sql = "UPDATE Bill_Imported SET Total_Price = ? WHERE Invoice_No = ? AND Admin_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, bill.getTotalPrice());
            stmt.setString(2, bill.getInvoiceNo());
            stmt.setString(3, bill.getAdminId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa một hóa đơn nhập hàng
    public boolean deleteBillImported(String invoiceNo, String adminId) {
        String sql = "DELETE FROM Bill_Imported WHERE Invoice_No = ? AND Admin_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoiceNo);
            stmt.setString(2, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
