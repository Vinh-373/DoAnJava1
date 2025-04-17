package com.quanlybanlaptop.dto;

public class CompanyDTO {
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private int status;
    public CompanyDTO() {}
    // Constructor đầy đủ tham số
    public CompanyDTO(int companyId, String companyName, String companyAddress, String companyContact, int status) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
        this.status = status;
    }
    // Getters and Setters
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int categoryId) { this.companyId = categoryId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
    public String getCompanyContact() { return companyContact; }
    public void setCompanyContact(String companyContact) { this.companyContact = companyContact; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}

