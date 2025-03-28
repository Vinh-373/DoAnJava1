package DAO;

import DTO.DiscountDTO;
import java.sql.*;
import java.util.ArrayList;

public class DiscountDAO {
    private Connection conn;

    public DiscountDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm chương trình giảm giá mới vào cơ sở dữ liệu
    public boolean insertDiscount(DiscountDTO discount) {
        String sql = "INSERT INTO Discount (Discount_No, Discount_Name, Discount_Value, Date_Started, Date_Ended) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discount.getDiscountNo());
            stmt.setString(2, discount.getDiscountName());
            stmt.setString(3, discount.getDiscountValue());
            stmt.setDate(4, Date.valueOf(discount.getDateStarted()));
            stmt.setDate(5, Date.valueOf(discount.getDateEnded()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả chương trình giảm giá từ cơ sở dữ liệu
    public ArrayList<DiscountDTO> getAllDiscounts() {
        ArrayList<DiscountDTO> discounts = new ArrayList<>();
        String sql = "SELECT * FROM Discount";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                discounts.add(new DiscountDTO(
                        rs.getString("Discount_No"),
                        rs.getString("Discount_Name"),
                        rs.getString("Discount_Value"),
                        rs.getDate("Date_Started").toLocalDate(),
                        rs.getDate("Date_Ended").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    // Lấy thông tin chương trình giảm giá theo Discount_No
    public DiscountDTO getDiscountByNo(String discountNo) {
        String sql = "SELECT * FROM Discount WHERE Discount_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discountNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DiscountDTO(
                            rs.getString("Discount_No"),
                            rs.getString("Discount_Name"),
                            rs.getString("Discount_Value"),
                            rs.getDate("Date_Started").toLocalDate(),
                            rs.getDate("Date_Ended").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin chương trình giảm giá
    public boolean updateDiscount(DiscountDTO discount) {
        String sql = "UPDATE Discount SET Discount_Name = ?, Discount_Value = ?, Date_Started = ?, Date_Ended = ? WHERE Discount_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discount.getDiscountName());
            stmt.setString(2, discount.getDiscountValue());
            stmt.setDate(3, Date.valueOf(discount.getDateStarted()));
            stmt.setDate(4, Date.valueOf(discount.getDateEnded()));
            stmt.setString(5, discount.getDiscountNo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa chương trình giảm giá theo Discount_No
    public boolean deleteDiscount(String discountNo) {
        String sql = "DELETE FROM Discount WHERE Discount_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, discountNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
