/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
import java.math.*;
/**
 *
 * @author Lê Quang Hoàng
 */
public class BillExportedDTO {
    private String invoiceNo;
    private String adminId;
    private BigDecimal totalPrice;
    public BillExportedDTO() {}

    // Constructor đầy đủ tham số
    public BillExportedDTO(String invoiceNo, String adminId, BigDecimal totalPrice) {
        this.invoiceNo = invoiceNo;
        this.adminId = adminId;
        this.totalPrice = totalPrice;
    }
    // Getters and Setters
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }  
}
