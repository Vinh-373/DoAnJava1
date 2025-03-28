/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Lê Quang Hoàng
 */
public class IMeiProductDTO {
    private String imeiNo;
    private String productId;
    private String state;
    public IMeiProductDTO() {}

    // Constructor đầy đủ tham số
    public IMeiProductDTO(String imeiNo, String productId, String state) {
        this.imeiNo = imeiNo;
        this.productId = productId;
        this.state = state;
    }
    // Getters and Setters
    public String getImeiNo() { return imeiNo; }
    public void setImeiNo(String imeiNo) { this.imeiNo = imeiNo; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
