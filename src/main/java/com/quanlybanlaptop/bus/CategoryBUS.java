package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.CategoryDAO;
import com.quanlybanlaptop.dto.CategoryDTO;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBUS {
    private CategoryDAO categoryDAO;
    public CategoryBUS(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    public ArrayList<CategoryDTO> getAllCategory() throws SQLException {
        return categoryDAO.getAllCategories();
    }
    public CategoryDTO getCategoryById(int categoryId) throws SQLException {
        return categoryDAO.getCategoryById(categoryId);
    }
    public boolean addCategory(CategoryDTO category) throws SQLException {
       if(category == null) {
           return false;
       }
       return categoryDAO.insertCategory(category);
    }
    public boolean updateCategory(CategoryDTO category) throws SQLException {
        if(category == null) {
            return false;
        }
        return categoryDAO.updateCategory(category);
    }
    public boolean deleteCategory(int categoryId) throws SQLException {
        if(categoryId <= 0) {
            return false;
        }
        return categoryDAO.deleteCategory(categoryId);
    }

}
