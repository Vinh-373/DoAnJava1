package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.CompanyDTO;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDAO {


    private Connection conn;

    public CompanyDAO(Connection conn) {
        this.conn = conn;
    }
    // Hàm tạo CategoryDTO từ ResultSet
    private CompanyDTO createCompanyDTO(ResultSet rs) throws SQLException {
        return new CompanyDTO(
                rs.getInt("id_company"),
                rs.getString("name_company"),
                rs.getString("address"),
                rs.getString("contact"),
                rs.getInt("status")
        );
    }

    // Thêm danh mục mới
    public boolean insertCompany(CompanyDTO companyDTO) {
        String sql = "INSERT INTO COMPANY ( name_company, address,contact,status) VALUES (?, ?, ?, 1)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, companyDTO.getCompanyName());
            stmt.setString(2, companyDTO.getCompanyAddress());
            stmt.setString(3, companyDTO.getCompanyContact());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả danh mục
    public ArrayList<CompanyDTO> getAllCompanies() {
        ArrayList<CompanyDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM COMPANY c WHERE status = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CompanyDTO categoryDTO = createCompanyDTO(rs);
                list.add(categoryDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh mục theo ID
    public CompanyDTO getCompanyById(int companyId) {
        String sql = "SELECT * FROM COMPANY WHERE id_company = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createCompanyDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật danh mục
    public boolean updateCompany(CompanyDTO companyDTO) {
        String sql = "UPDATE COMPANY SET name_company = ?, address = ?, contact = ? WHERE id_company = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, companyDTO.getCompanyName());
            stmt.setString(2, companyDTO.getCompanyAddress());
            stmt.setString(3, companyDTO.getCompanyContact());
            stmt.setInt(4, companyDTO.getCompanyId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục
    public boolean deleteCompany(int companyId) {
        String sql = "UPDATE COMPANY SET status = 0 WHERE id_company = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
