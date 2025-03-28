package DAO;

import DTO.CategoryDTO;
import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm danh mục mới
    public boolean insertCategory(CategoryDTO category) {
        String sql = "INSERT INTO Category (Category_ID, Category_Name, Sup_ID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryId());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getSupId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả danh mục
    public ArrayList<CategoryDTO> getAllCategories() {
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CategoryDTO(
                        rs.getString("Category_ID"),
                        rs.getString("Category_Name"),
                        rs.getString("Sup_ID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh mục theo ID
    public CategoryDTO getCategoryById(String categoryId) {
        String sql = "SELECT * FROM Category WHERE Category_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CategoryDTO(
                            rs.getString("Category_ID"),
                            rs.getString("Category_Name"),
                            rs.getString("Sup_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật danh mục
    public boolean updateCategory(CategoryDTO category) {
        String sql = "UPDATE Category SET Category_Name = ?, Sup_ID = ? WHERE Category_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getSupId());
            stmt.setString(3, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục
    public boolean deleteCategory(String categoryId) {
        String sql = "DELETE FROM Category WHERE Category_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
