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
public class InsuranceDTO {
    private String insuranceNo;
    private String adminId;
    private String customerId;
    private LocalDate dateInsurance;
    // Constructor không tham số
    public InsuranceDTO() {}

    // Constructor đầy đủ tham số
    public InsuranceDTO(String insuranceNo, String adminId, String customerId, LocalDate dateInsurance) {
        this.insuranceNo = insuranceNo;
        this.adminId = adminId;
        this.customerId = customerId;
        this.dateInsurance = dateInsurance;
    }
    // Getters and Setters
    public String getInsuranceNo() { return insuranceNo; }
    public void setInsuranceNo(String insuranceNo) { this.insuranceNo = insuranceNo; }
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public LocalDate getDateInsurance() { return dateInsurance; }
    public void setDateInsurance(LocalDate dateInsurance) { this.dateInsurance = dateInsurance; }
}
