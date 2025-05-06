package com.quanlybanlaptop.dto;

import java.util.Date;

public class InsuranceClaimDTO {
    private int idClaim;
    private int idInsurance;
    private int idAdmin;
    private int idProduct;
    private int idCustomer;
    private String adminName;
    private String productName;
    private String customerName;
    private String numSeri;
    private String description;
    private Date dateProcessed;
    private String status;

    public InsuranceClaimDTO() {}

    public InsuranceClaimDTO(int idClaim, int idInsurance, int idAdmin, int idProduct, int idCustomer,
                             String adminName, String productName, String customerName,
                             String numSeri, String description, Date dateProcessed, String status) {
        this.idClaim = idClaim;
        this.idInsurance = idInsurance;
        this.idAdmin = idAdmin;
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
        this.adminName = adminName;
        this.productName = productName;
        this.customerName = customerName;
        this.numSeri = numSeri;
        this.description = description;
        this.dateProcessed = dateProcessed;
        this.status = status;
    }

    public int getIdClaim() { return idClaim; }
    public void setIdClaim(int idClaim) { this.idClaim = idClaim; }

    public int getIdInsurance() { return idInsurance; }
    public void setIdInsurance(int idInsurance) { this.idInsurance = idInsurance; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public int getIdProduct() { return idProduct; }
    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }

    public int getIdCustomer() { return idCustomer; }
    public void setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getNumSeri() { return numSeri; }
    public void setNumSeri(String numSeri) { this.numSeri = numSeri; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateProcessed() { return dateProcessed; }
    public void setDateProcessed(Date dateProcessed) { this.dateProcessed = dateProcessed; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Thêm phương thức tiện ích nếu cần
    @Override
    public String toString() {
        return "InsuranceClaimDTO{" +
                "idClaim=" + idClaim +
                ", idInsurance=" + idInsurance +
                ", idAdmin=" + idAdmin +
                ", idProduct=" + idProduct +
                ", idCustomer=" + idCustomer +
                ", adminName='" + adminName + '\'' +
                ", productName='" + productName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", numSeri='" + numSeri + '\'' +
                ", description='" + description + '\'' +
                ", dateProcessed=" + dateProcessed +
                ", status='" + status + '\'' +
                '}';
    }
}