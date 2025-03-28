package DAO;

import DTO.InsuranceDetailsDTO;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class InsuranceDetailsDAO {
    private Connection conn;

    public InsuranceDetailsDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm Insurance Details vào cơ sở dữ liệu
    public boolean insertInsuranceDetails(InsuranceDetailsDTO insuranceDetails) {
        String sql = "INSERT INTO Insurance_Details (Insurance_No, Admin_ID, Customer_ID, Product_ID, Imei_No, Description, Date_Insurance, Time_Insurance) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceDetails.getInsuranceNo());
            stmt.setString(2, insuranceDetails.getAdminId());
            stmt.setString(3, insuranceDetails.getCustomerId());
            stmt.setString(4, insuranceDetails.getProductId());
            stmt.setString(5, insuranceDetails.getImeiNo());
            stmt.setString(6, insuranceDetails.getDescription());
            stmt.setDate(7, Date.valueOf(insuranceDetails.getDateInsurance()));
            stmt.setTime(8, Time.valueOf(insuranceDetails.getTimeInsurance()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu Insurance Details
    public ArrayList<InsuranceDetailsDTO> getAllInsuranceDetails() {
        ArrayList<InsuranceDetailsDTO> insuranceDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM Insurance_Details";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                insuranceDetailsList.add(new InsuranceDetailsDTO(
                        rs.getString("Insurance_No"),
                        rs.getString("Admin_ID"),
                        rs.getString("Customer_ID"),
                        rs.getString("Product_ID"),
                        rs.getString("IMei_No"),
                        rs.getString("Description"),
                        rs.getDate("Date_Insurance").toLocalDate(),
                        rs.getTime("Time_Insurance").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insuranceDetailsList;
    }

    // Lấy thông tin Insurance Details theo Insurance_No
    public InsuranceDetailsDTO getInsuranceDetailsByInsuranceNo(String insuranceNo) {
        String sql = "SELECT * FROM Insurance_Details WHERE Insurance_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new InsuranceDetailsDTO(
                            rs.getString("Insurance_No"),
                            rs.getString("Admin_ID"),
                            rs.getString("Customer_ID"),
                            rs.getString("Product_ID"),
                            rs.getString("IMei_No"),
                            rs.getString("Description"),
                            rs.getDate("Date_Insurance").toLocalDate(),
                            rs.getTime("Time_Insurance").toLocalTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin Insurance Details
    public boolean updateInsuranceDetails(InsuranceDetailsDTO insuranceDetails) {
        String sql = "UPDATE Insurance_Details SET Product_ID = ?, Imei_No = ?, Description = ?, Date_Insurance = ?, Time_Insurance = ? "
                + "WHERE Insurance_No = ? AND Admin_ID = ? AND Customer_ID = ? AND Imei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceDetails.getProductId());
            stmt.setString(2, insuranceDetails.getImeiNo());
            stmt.setString(3, insuranceDetails.getDescription());
            stmt.setDate(4, Date.valueOf(insuranceDetails.getDateInsurance()));
            stmt.setTime(5, Time.valueOf(insuranceDetails.getTimeInsurance()));
            stmt.setString(6, insuranceDetails.getInsuranceNo());
            stmt.setString(7, insuranceDetails.getAdminId());
            stmt.setString(8, insuranceDetails.getCustomerId());
            stmt.setString(9, insuranceDetails.getImeiNo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Insurance Details theo Insurance_No
    public boolean deleteInsuranceDetails(String insuranceNo, String adminId, String customerId, String imeiNo) {
        String sql = "DELETE FROM Insurance_Details WHERE Insurance_No = ? AND Admin_ID = ? AND Customer_ID = ? AND Imei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insuranceNo);
            stmt.setString(2, adminId);
            stmt.setString(3, customerId);
            stmt.setString(4, imeiNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
