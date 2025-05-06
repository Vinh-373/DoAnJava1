package com.quanlybanlaptop.dto;

import java.util.Date;

public class InsuranceDTO {
    private int idInsurance;
    private int idAdmin;
    private String adminName;
    private int idCustomer;
    private int idProduct;
    private String numSeri;
    private Date startDate;
    private Date endDate;
    private String description;
    private String customerName;
    private String productName;

    public InsuranceDTO() {}

    public InsuranceDTO(int idInsurance, int idAdmin, int idCustomer, int idProduct, String numSeri, Date startDate, Date endDate, String description) {
        this.idInsurance = idInsurance;
        this.idAdmin = idAdmin;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.numSeri = numSeri;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
    public InsuranceDTO(int idInsurance, int idAdmin, int idCustomer, int idProduct, String numSeri, Date startDate, Date endDate, String description, String customerName, String productName) {
        this.idInsurance = idInsurance;
        this.idAdmin = idAdmin;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.numSeri = numSeri;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.customerName = customerName;
        this.productName = productName;
    }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
        // Getters and setters
    public int getIdInsurance() { return idInsurance; }
    public void setIdInsurance(int idInsurance) { this.idInsurance = idInsurance; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public int getIdCustomer() { return idCustomer; }
    public void setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }

    public int getIdProduct() { return idProduct; }
    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }

    public String getNumSeri() { return numSeri; }
    public void setNumSeri(String numSeri) { this.numSeri = numSeri; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
}