/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
import java.math.*;
import java.time.*;
/**
 *
 * @author Lê Quang Hoàng
 */
public class OrdersDetailsDTO {
    private String orderNo;
    private String customerId;
    private String productId;
    private BigDecimal price;
    private int quantity;
    private LocalDate dateOrder;
    private LocalTime timeOrder;
    private String status;
    // Constructor không tham số
    public OrdersDetailsDTO() {}

    // Constructor đầy đủ tham số
    public OrdersDetailsDTO(String orderNo, String customerId, String productId, 
                            BigDecimal price, int quantity, LocalDate dateOrder, 
                            LocalTime timeOrder, String status) {
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
        this.status = status;
    }
    // Getters and Setters
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getDateOrder() { return dateOrder; }
    public void setDateOrder(LocalDate dateOrder) { this.dateOrder = dateOrder; }
    public LocalTime getTimeOrder() { return timeOrder; }
    public void setTimeOrder(LocalTime timeOrder) { this.timeOrder = timeOrder; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
