package DAO;

import DTO.InsuranceDTO;
import java.sql.*;
import java.util.ArrayList;

public class InsuranceDAO {
    private Connection conn;

    public InsuranceDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một bản ghi Insurance vào cơ sở dữ liệu
    public boolean insertInsurance(InsuranceDTO insurance) {
        String sql = "INSERT INTO Insurance (Insurance_No, Admin_ID, Customer_ID, Date_Insurance) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insurance.getInsuranceNo());
            stmt.setString(2, insurance.getAdminId());
            stmt.setString(3, insurance.getCustomerId());
            stmt.setDate(4, insurance.getDateInsurance() != null ? Date.valueOf(insurance.getDateInsurance()) : null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu Insurance
    public ArrayList<InsuranceDTO> getAllInsurance() {
        ArrayList<InsuranceDTO> insuranceList = new ArrayList<>();
        String sql = "SELECT * FROM Insurance";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                insuranceList.add(new InsuranceDTO(
                        rs.getString("Insurance_No"),
                        rs.getString("Admin_ID"),
                        rs.getString("Customer_ID"),
                        rs.getDate("Date_Insurance") != null ? rs.getDate("Date_Insurance").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insuranceList;
    }

    // Lấy thông tin Insurance theo Insurance_No, Admin_ID và Customer_ID
    public InsuranceDTO getInsuranceById(String insuranceNo, String adminId, String customerId) {
        String sql = "SELECT * FROM Insurance WHERE Insurance_No = ? AND Admin_ID = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceNo);
            stmt.setString(2, adminId);
            stmt.setString(3, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new InsuranceDTO(
                            rs.getString("Insurance_No"),
                            rs.getString("Admin_ID"),
                            rs.getString("Customer_ID"),
                            rs.getDate("Date_Insurance") != null ? rs.getDate("Date_Insurance").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin Insurance
    public boolean updateInsurance(InsuranceDTO insurance) {
        String sql = "UPDATE Insurance SET Date_Insurance = ? WHERE Insurance_No = ? AND Admin_ID = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, insurance.getDateInsurance() != null ? Date.valueOf(insurance.getDateInsurance()) : null);
            stmt.setString(2, insurance.getInsuranceNo());
            stmt.setString(3, insurance.getAdminId());
            stmt.setString(4, insurance.getCustomerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Insurance theo Insurance_No, Admin_ID và Customer_ID
    public boolean deleteInsurance(String insuranceNo, String adminId, String customerId) {
        String sql = "DELETE FROM Insurance WHERE Insurance_No = ? AND Admin_ID = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceNo);
            stmt.setString(2, adminId);
            stmt.setString(3, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
