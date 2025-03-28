/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Lê Quang Hoàng
 */
public class SupplierDTO {
    private String supId;
    private String supName;
    private String address;
    private String contact;
    public SupplierDTO() {
    }
    public SupplierDTO(String supId, String supName, String address, String contact) {
        this.supId = supId;
        this.supName = supName;
        this.address = address;
        this.contact = contact;
    }

    // Getters and Setters
    public String getSupId() { return supId; }
    public void setSupId(String supId) { this.supId = supId; }
    public String getSupName() { return supName; }
    public void setSupName(String supName) { this.supName = supName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}