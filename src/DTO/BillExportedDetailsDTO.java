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
public class BillExportedDetailsDTO {
    private String invoiceNo;
    private String adminId;
    private String orderNo;
    private String customerId;
    private String productId;
    private String imeiNo;
    private BigDecimal unitPrice;
    private int quantity;
    private String discountNo;
    private BigDecimal totalPriceBefore;
    private BigDecimal totalPriceAfter;
    private LocalDate dateExported;
    private LocalTime timeExported;
    // Constructor không tham số
    public BillExportedDetailsDTO() {}

    // Constructor đầy đủ tham số
    public BillExportedDetailsDTO(String invoiceNo, String adminId, String orderNo, String customerId,
                                  String productId, String imeiNo, BigDecimal unitPrice, int quantity, 
                                  String discountNo, BigDecimal totalPriceBefore, BigDecimal totalPriceAfter, 
                                  LocalDate dateExported, LocalTime timeExported) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.productId = productId;
        this.imeiNo = imeiNo;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discountNo = discountNo;
        this.totalPriceBefore = totalPriceBefore;
        this.totalPriceAfter = totalPriceAfter;
        this.dateExported = dateExported;
        this.timeExported = timeExported;
    }


    // Getters and Setters
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getImeiNo() { return imeiNo; }
    public void setImeiNo(String imeiNo) { this.imeiNo = imeiNo; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getDiscountNo() { return discountNo; }
    public void setDiscountNo(String discountNo) { this.discountNo = discountNo; }
    public BigDecimal getTotalPriceBefore() { return totalPriceBefore; }
    public void setTotalPriceBefore(BigDecimal totalPriceBefore) { this.totalPriceBefore = totalPriceBefore; }
    public BigDecimal getTotalPriceAfter() { return totalPriceAfter; }
    public void setTotalPriceAfter(BigDecimal totalPriceAfter) { this.totalPriceAfter = totalPriceAfter; }
    public LocalDate getDateExported() { return dateExported; }
    public void setDateExported(LocalDate dateExported) { this.dateExported = dateExported; }
    public LocalTime getTimeExported() { return timeExported; }
    public void setTimeExported(LocalTime timeExported) { this.timeExported = timeExported; }
}
