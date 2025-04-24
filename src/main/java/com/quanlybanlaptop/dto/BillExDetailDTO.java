package com.quanlybanlaptop.dto;

import java.math.BigDecimal;

public class BillExDetailDTO {
    private int idBillEx;
    private int idProduct;
    private int quantity;
    private BigDecimal unitPrice;
    private String seri;

    // Constructor
    public BillExDetailDTO(int idBillEx, int idProduct, int quantity, BigDecimal unitPrice, String seri) {
        this.idBillEx = idBillEx;
        this.idProduct = idProduct;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.seri = seri;
    }

    // Getters and Setters
    public int getIdBillEx() {
        return idBillEx;
    }

    public void setIdBillEx(int idBillEx) {
        this.idBillEx = idBillEx;
    }
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String getSeri() {
        return seri;
    }
    public void setSeri(String seri) {
        this.seri = seri;
    }

}
