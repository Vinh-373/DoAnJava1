/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
import java.time.*;
/**
 *
 * @author Lê Quang Hoàng
 */
public class InsuranceDetailsDTO {
    private String insuranceNo;
    private String adminId;
    private String customerId;
    private String productId;
    private String imeiNo;
    private String description;
    private LocalDate dateInsurance;
    private LocalTime timeInsurance;
    // Constructor không tham số
    public InsuranceDetailsDTO() {}

    // Constructor đầy đủ tham số
    public InsuranceDetailsDTO(String insuranceNo, String adminId, String customerId, String productId, 
                               String imeiNo, String description, LocalDate dateInsurance, LocalTime timeInsurance) {
        this.insuranceNo = insuranceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.productId = productId;
        this.imeiNo = imeiNo;
        this.description = description;
        this.dateInsurance = dateInsurance;
        this.timeInsurance = timeInsurance;
    }


    // Getters and Setters
    public String getInsuranceNo() { return insuranceNo; }
    public void setInsuranceNo(String insuranceNo) { this.insuranceNo = insuranceNo; }
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getImeiNo() { return imeiNo; }
    public void setImeiNo(String imeiNo) { this.imeiNo = imeiNo; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDateInsurance() { return dateInsurance; }
    public void setDateInsurance(LocalDate dateInsurance) { this.dateInsurance = dateInsurance; }
    public LocalTime getTimeInsurance() { return timeInsurance; }
    public void setTimeInsurance(LocalTime timeInsurance) { this.timeInsurance = timeInsurance; }
}
