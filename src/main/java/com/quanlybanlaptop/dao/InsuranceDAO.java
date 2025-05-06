package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.InsuranceDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceDAO {
    private Connection conn;

    public InsuranceDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm bảo hành mới
    public boolean insertInsurance(InsuranceDTO insurance) {
        String sql = "INSERT INTO INSURANCE (id_admin, id_customer, id_product, num_seri, start_date, end_date, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, insurance.getIdAdmin());
            ps.setInt(2, insurance.getIdCustomer());
            ps.setInt(3, insurance.getIdProduct());
            ps.setString(4, insurance.getNumSeri());
            ps.setTimestamp(5, new Timestamp(insurance.getStartDate().getTime()));
            ps.setTimestamp(6, new Timestamp(insurance.getEndDate().getTime()));
            ps.setString(7, insurance.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả bảo hành
    public List<InsuranceDTO> getAllInsurance() {
        List<InsuranceDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM INSURANCE";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                InsuranceDTO dto = new InsuranceDTO();
                dto.setIdInsurance(rs.getInt("id_insurance"));
                dto.setIdAdmin(rs.getInt("id_admin"));
                dto.setIdCustomer(rs.getInt("id_customer"));
                dto.setIdProduct(rs.getInt("id_product"));
                dto.setNumSeri(rs.getString("num_seri"));
                dto.setStartDate(rs.getTimestamp("start_date"));
                dto.setEndDate(rs.getTimestamp("end_date"));
                dto.setDescription(rs.getString("description"));
                list.add(dto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Kiểm tra đã có bảo hành cho seri này chưa
    public boolean existsBySeri(String numSeri) {
        String sql = "SELECT 1 FROM INSURANCE WHERE num_seri = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numSeri);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public List<Object[]> getSoldSeriInfo() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT c.name AS customer_name, be.date_ex, p.name AS product_name, sp.num_seri " +
                     "FROM SERI_PRODUCT sp " +
                     "JOIN BILL_EXPORT_DETAIL bed ON sp.num_seri = bed.num_seri " +
                     "JOIN BILL_EXPORT be ON bed.id_bill_ex = be.id_bill_ex " +
                     "JOIN CUSTOMER c ON be.id_customer = c.id_customer " +
                     "JOIN PRODUCT p ON sp.id_product = p.id_product " +
                     "WHERE sp.status = 2";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("customer_name");
                row[1] = rs.getTimestamp("date_ex");
                row[2] = rs.getString("product_name");
                row[3] = rs.getString("num_seri");
                result.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public List<Object[]> getSoldSeriInfo3() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT c.name AS customer_name, be.date_ex, p.name AS product_name, sp.num_seri " +
                     "FROM SERI_PRODUCT sp " +
                     "JOIN BILL_EXPORT_DETAIL bed ON sp.num_seri = bed.num_seri " +
                     "JOIN BILL_EXPORT be ON bed.id_bill_ex = be.id_bill_ex " +
                     "JOIN CUSTOMER c ON be.id_customer = c.id_customer " +
                     "JOIN PRODUCT p ON sp.id_product = p.id_product " +
                     "WHERE sp.status = 3";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("customer_name");
                row[1] = rs.getTimestamp("date_ex");
                row[2] = rs.getString("product_name");
                row[3] = rs.getString("num_seri");
                result.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public List<Object[]> getInsuredSeriInfo() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT c.name, p.name, s.num_seri, i.start_date, i.end_date, i.id_insurance, i.id_admin, i.id_product " +
                     "FROM INSURANCE i " +
                     "JOIN CUSTOMER c ON i.id_customer = c.id_customer " +
                     "JOIN PRODUCT p ON i.id_product = p.id_product " +
                     "JOIN SERI_PRODUCT s ON i.num_seri = s.num_seri " +
                     "WHERE s.status = 3";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[8];
                for (int i = 0; i < 8; i++) row[i] = rs.getObject(i + 1);
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
        public ArrayList<InsuranceDTO> getAllInsuranceTable() throws SQLException {
        ArrayList<InsuranceDTO> list = new ArrayList<>();
        String sql = "SELECT i.id_insurance, a.name AS admin_name, c.name AS customer_name, p.name AS product_name, " +
                     "i.num_seri, i.start_date, i.end_date, i.description " +
                     "FROM INSURANCE i " +
                     "JOIN ADMIN a ON i.id_admin = a.id_admin " +
                     "JOIN CUSTOMER c ON i.id_customer = c.id_customer " +
                     "JOIN PRODUCT p ON i.id_product = p.id_product";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                InsuranceDTO dto = new InsuranceDTO();
                dto.setIdInsurance(rs.getInt("id_insurance"));
                dto.setAdminName(rs.getString("admin_name"));      // Thêm dòng này
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setNumSeri(rs.getString("num_seri"));
                dto.setStartDate(rs.getTimestamp("start_date"));
                dto.setEndDate(rs.getTimestamp("end_date"));
                dto.setDescription(rs.getString("description"));
                list.add(dto);
            }
        }
        return list;
    }
    public List<InsuranceDTO> searchInsurance(String keyword, String searchType) throws SQLException {
        List<InsuranceDTO> list = new ArrayList<>();
        String sql = "SELECT i.id_insurance, a.name AS admin_name, c.name AS customer_name, p.name AS product_name, " +
                     "i.num_seri, i.start_date, i.end_date, i.description " +
                     "FROM INSURANCE i " +
                     "JOIN ADMIN a ON i.id_admin = a.id_admin " +
                     "JOIN CUSTOMER c ON i.id_customer = c.id_customer " +
                     "JOIN PRODUCT p ON i.id_product = p.id_product WHERE ";
        if ("Số Seri".equals(searchType)) {
            sql += "i.num_seri LIKE ?";
        } else if ("Khách hàng".equals(searchType)) {
            sql += "c.name LIKE ?";
        } else if ("Sản phẩm".equals(searchType)) {
            sql += "p.name LIKE ?";
        } else {
            sql += "1=1";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InsuranceDTO dto = new InsuranceDTO();
                dto.setIdInsurance(rs.getInt("id_insurance"));
                dto.setAdminName(rs.getString("admin_name"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setProductName(rs.getString("product_name"));
                dto.setNumSeri(rs.getString("num_seri"));
                dto.setStartDate(rs.getTimestamp("start_date"));
                dto.setEndDate(rs.getTimestamp("end_date"));
                dto.setDescription(rs.getString("description"));
                list.add(dto);
            }
        }
        return list;
    }
    
}