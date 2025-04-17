package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO(Connection conn) {
        this.conn = conn;
    }
    // Hàm tạo CategoryDTO từ ResultSet
    private CategoryDTO createCategoryDTO(ResultSet rs) throws SQLException {
        return new CategoryDTO(
                rs.getInt("id_category"),
                rs.getString("name_category"),
                rs.getInt("status")
        );
    }

    // Thêm danh mục mới
    public boolean insertCategory(CategoryDTO category) {
        String sql = "INSERT INTO CATEGORY (name_category,status) VALUES (?, 1)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả danh mục
    public ArrayList<CategoryDTO> getAllCategories() {
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORY c WHERE status = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CategoryDTO categoryDTO = createCategoryDTO(rs);
                list.add(categoryDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh mục theo ID
    public CategoryDTO getCategoryById(int categoryId) {
        String sql = "SELECT * FROM CATEGORY WHERE id_category = ? AND status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createCategoryDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật danh mục
    public boolean updateCategory(CategoryDTO category) {
        String sql = "UPDATE CATEGORY SET name_category = ? WHERE id_category = ? AND status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setInt(2, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) {
        String sql = "UPDATE CATEGORY SET status = 0 WHERE id_category = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
