
package com.quanlybanlaptop.dto;


public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private int status;
    public CategoryDTO() {}

    // Constructor đầy đủ tham số
    public CategoryDTO(int categoryId, String categoryName, int status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }
    // Getters and Setters
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

}
