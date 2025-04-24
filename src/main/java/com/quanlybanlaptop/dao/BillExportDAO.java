package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.BillExportDTO;

import java.sql.*;
import java.util.ArrayList;

public class BillExportDAO {
    private Connection conn;

    public BillExportDAO(Connection conn) {
        this.conn = conn;
    }

    private BillExportDTO createBillExportDTO(ResultSet rs) throws SQLException {
        return new BillExportDTO(
                rs.getInt("id_bill_ex"),
                rs.getInt("id_admin"),
                rs.getInt("id_customer"),
                rs.getInt("total_product"),
                rs.getBigDecimal("total_price"),
                rs.getTimestamp("date_ex"),
                rs.getInt("status"),
                rs.getString("admin_name"),
                rs.getString("customer_name")
        );
    }

    // Thêm đơn xuất hàng
    public boolean insertBillExport(BillExportDTO dto) {
        String sql = "INSERT INTO BILL_EXPORT (id_admin, id_customer, total_product, total_price, status, date_ex) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dto.getIdAdmin());
            if (dto.getIdCustomer() != null) {
                stmt.setInt(2, dto.getIdCustomer());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setInt(3, dto.getTotalProduct());
            stmt.setBigDecimal(4, dto.getTotalPrice());
            stmt.setTimestamp(6, dto.getDateEx());
            stmt.setInt(5, dto.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả đơn xuất hàng có status = 1
    public ArrayList<BillExportDTO> getAllActiveBillExports() {
        ArrayList<BillExportDTO> list = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS admin_name, c.name AS customer_name\n" +
                "FROM BILL_EXPORT b JOIN ADMIN a ON a.id_admin = b.id_admin\n" +
                "JOIN CUSTOMER c ON c.id_customer = b.id_customer\n" +
                "WHERE b.status = 1\n" +
                "ORDER BY b.date_ex DESC;";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BillExportDTO dto = createBillExportDTO(rs);
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy đơn xuất hàng theo ID
    public BillExportDTO getBillExportById(int id) {
        String sql = "SELECT b.*, a.name AS admin_name, c.name AS customer_name" +
                " FROM BILL_EXPORT b JOIN ADMIN a ON a.id_admin = b.id_admin JOIN CUSTOMER c ON b.id_customer = c.id_customer" +
                " WHERE id_bill_ex = ? AND b.status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createBillExportDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    // Cập nhật đơn xuất hàng
//    public boolean updateBillExport(BillExportDTO dto) {
//        String sql = "UPDATE BILL_EXPORT SET id_admin = ?, id_customer = ?, total_product = ?, total_price = ?, status = ? WHERE id_bill_ex = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, dto.getIdAdmin());
//            if (dto.getIdCustomer() != null) {
//                stmt.setInt(2, dto.getIdCustomer());
//            } else {
//                stmt.setNull(2, Types.INTEGER);
//            }
//            stmt.setInt(3, dto.getTotalProduct());
//            stmt.setBigDecimal(4, dto.getTotalPrice());
//            stmt.setInt(5, dto.getStatus());
//            stmt.setInt(6, dto.getIdBillEx());
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Xóa mềm (set status = 0)
    public boolean deleteBillExport(int id) {
        String sql = "UPDATE BILL_EXPORT SET status = 0 WHERE id_bill_ex = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
