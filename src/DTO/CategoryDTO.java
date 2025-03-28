/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Lê Quang Hoàng
 */
public class CategoryDTO {
    private String categoryId;
    private String categoryName;
    private String supId;
    public CategoryDTO() {}

    // Constructor đầy đủ tham số
    public CategoryDTO(String categoryId, String categoryName, String supId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.supId = supId;
    }
    // Getters and Setters
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getSupId() { return supId; }
    public void setSupId(String supId) { this.supId = supId; }
}
