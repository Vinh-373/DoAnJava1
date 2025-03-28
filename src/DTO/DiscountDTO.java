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
public class DiscountDTO {
    private String discountNo;
    private String discountName;
    private String discountValue;
    private LocalDate dateStarted;
    private LocalDate dateEnded;
    public DiscountDTO() {}

    // Constructor đầy đủ tham số
    public DiscountDTO(String discountNo, String discountName, String discountValue, LocalDate dateStarted, LocalDate dateEnded) {
        this.discountNo = discountNo;
        this.discountName = discountName;
        this.discountValue = discountValue;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
    }

    // Getters and Setters
    public String getDiscountNo() { return discountNo; }
    public void setDiscountNo(String discountNo) { this.discountNo = discountNo; }
    public String getDiscountName() { return discountName; }
    public void setDiscountName(String discountName) { this.discountName = discountName; }
    public String getDiscountValue() { return discountValue; }
    public void setDiscountValue(String discountValue) { this.discountValue = discountValue; }
    public LocalDate getDateStarted() { return dateStarted; }
    public void setDateStarted(LocalDate dateStarted) { this.dateStarted = dateStarted; }
    public LocalDate getDateEnded() { return dateEnded; }
    public void setDateEnded(LocalDate dateEnded) { this.dateEnded = dateEnded; }
}
