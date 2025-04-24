package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.BillImportDTO;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BillImportDAO {
    private Connection conn;

    public BillImportDAO(Connection conn) {
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Connection không được null");
        }
        this.conn = conn;
    }

    // Hàm tạo DTO từ ResultSet
    private BillImportDTO createBillImportDTO(ResultSet rs) throws SQLException {
        return new BillImportDTO(
                rs.getInt("id_bill_im"),
                rs.getInt("id_admin"),
                rs.getInt("id_product"),
                rs.getBigDecimal("unit_price"),
                rs.getBigDecimal("total_price"),
                rs.getInt("quantity"),
                rs.getTimestamp("date_import"),
                rs.getString("name")
        );
    }

    // Lấy danh sách tất cả bill import, mới nhất lên đầu
    public ArrayList<BillImportDTO> getAllBillImports() throws SQLException {
        ArrayList<BillImportDTO> list = new ArrayList<>();

        String sql = "SELECT b.id_bill_im, b.id_admin, b.id_product, b.unit_price, b.total_price, b.quantity, b.date_import, a.name\n" +
                "FROM BILL_IMPORT b \n" +
                "JOIN ADMIN a ON b.id_admin = a.id_admin\n" +
                "ORDER BY b.date_import DESC;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(createBillImportDTO(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi lấy danh sách phiếu nhập: " + ex.getMessage(), ex);
        }

        return list;
    }

    public boolean insertBillImport(BillImportDTO billImportDTO) throws SQLException {
        String sql = "INSERT INTO BILL_IMPORT (id_admin, id_product, unit_price, total_price, quantity, date_import) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billImportDTO.getIdAdmin());
            stmt.setInt(2, billImportDTO.getIdProduct()); // Lưu ý: getStatus() trả về id_product theo DTO của bạn
            stmt.setBigDecimal(3, billImportDTO.getUnitPrice());
            stmt.setBigDecimal(4, billImportDTO.getTotalPrice());
            stmt.setInt(5, billImportDTO.getQuantity()); // quantity
            stmt.setTimestamp(6, billImportDTO.getImportDate());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi thêm phiếu nhập: " + ex.getMessage(), ex);
        }
    }

    // Bạn có thể thêm các hàm như: getById, add, delete... nếu cần
}

