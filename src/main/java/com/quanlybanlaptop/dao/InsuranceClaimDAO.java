package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.quanlybanlaptop.dto.InsuranceClaimDTO;

public class InsuranceClaimDAO {
    private Connection conn;

    public InsuranceClaimDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Thêm yêu cầu bảo hành mới vào bảng INSURANCE_CLAIM
     */
    public boolean addInsuranceClaim(int idInsurance, int idAdmin, int idProduct, String numSeri, String description, String status) {
        String sql = "INSERT INTO INSURANCE_CLAIM (id_insurance, id_admin, id_product, num_seri, description, date_processed, status) " +
                     "VALUES (?, ?, ?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInsurance);
            stmt.setInt(2, idAdmin);
            stmt.setInt(3, idProduct);
            stmt.setString(4, numSeri);
            stmt.setString(5, description);
            stmt.setString(6, status);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<InsuranceClaimDTO> getAllInsuranceClaimsWithNames() {
        ArrayList<InsuranceClaimDTO> list = new ArrayList<>();
        String sql = "SELECT ic.id_claim, ic.id_insurance, a.name AS admin_name, " +
                     "p.name AS product_name, c.name AS customer_name, " +
                     "ic.num_seri, ic.description, ic.date_processed, ic.status " +
                     "FROM INSURANCE_CLAIM ic " +
                     "JOIN ADMIN a ON ic.id_admin = a.id_admin " +
                     "JOIN PRODUCT p ON ic.id_product = p.id_product " +
                     "JOIN INSURANCE i ON ic.id_insurance = i.id_insurance " +
                     "JOIN CUSTOMER c ON i.id_customer = c.id_customer";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                InsuranceClaimDTO dto = new InsuranceClaimDTO();
                dto.setIdClaim(rs.getInt("id_claim"));
                dto.setIdInsurance(rs.getInt("id_insurance"));
                dto.setAdminName(rs.getString("admin_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setNumSeri(rs.getString("num_seri"));
                dto.setDescription(rs.getString("description"));
                dto.setDateProcessed(rs.getTimestamp("date_processed"));
                dto.setStatus(rs.getString("status"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatusToProcessed(int idClaim) {
        String sql = "UPDATE INSURANCE_CLAIM SET status = N'Đã xử lý' WHERE id_claim = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idClaim);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<InsuranceClaimDTO> searchInsuranceClaim(String keyword, String searchType) throws SQLException {
        List<InsuranceClaimDTO> list = new ArrayList<>();
        String sql = "SELECT ic.id_claim, ic.id_insurance, a.name AS admin_name, " +
                     "p.name AS product_name, c.name AS customer_name, " +
                     "ic.num_seri, ic.description, ic.date_processed, ic.status " +
                     "FROM INSURANCE_CLAIM ic " +
                     "JOIN ADMIN a ON ic.id_admin = a.id_admin " +
                     "JOIN PRODUCT p ON ic.id_product = p.id_product " +
                     "JOIN INSURANCE i ON ic.id_insurance = i.id_insurance " +
                     "JOIN CUSTOMER c ON i.id_customer = c.id_customer ";

        // Xây dựng điều kiện tìm kiếm
        if ("Số Seri".equalsIgnoreCase(searchType)) {
            sql += "WHERE ic.num_seri LIKE ?";
        } else if ("Khách hàng".equalsIgnoreCase(searchType)) {
            sql += "WHERE c.name LIKE ?";
        } else if ("Sản phẩm".equalsIgnoreCase(searchType)) {
            sql += "WHERE p.name LIKE ?";
        } else {
            sql += "WHERE 1=1";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!"Tất cả".equalsIgnoreCase(searchType)) {
                stmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InsuranceClaimDTO dto = new InsuranceClaimDTO();
                dto.setIdClaim(rs.getInt("id_claim"));
                dto.setIdInsurance(rs.getInt("id_insurance"));
                dto.setAdminName(rs.getString("admin_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setNumSeri(rs.getString("num_seri"));
                dto.setDescription(rs.getString("description"));
                dto.setDateProcessed(rs.getTimestamp("date_processed"));
                dto.setStatus(rs.getString("status"));
                list.add(dto);
            }
        }
        return list;
    }
}