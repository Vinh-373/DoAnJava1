package com.quanlybanlaptop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BillImportDTO {
    private int idBillIm;
    private int idAdmin;
    private int idProduct;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;
    private int quantity;
    private Timestamp importDate;
    private String nameAdmin;

    public BillImportDTO() {
    }

    public BillImportDTO(int idBillIm, int idAdmin, int idProduct, BigDecimal unitPrice, BigDecimal totalPrice, int quantity, Timestamp importDate, String nameAdmin) {
        this.idBillIm = idBillIm;
        this.idAdmin = idAdmin;
        this.idProduct = idProduct;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.importDate = importDate;
        this.nameAdmin = nameAdmin;
    }

    public int getIdBillIm() {
        return idBillIm;
    }

    public void setIdBillIm(int idBillIm) {
        this.idBillIm = idBillIm;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getImportDate() {
        return importDate;
    }

    public void setImportDate(Timestamp importDate) {
        this.importDate = importDate;
    }

    public String getNameAdmin() {
        return nameAdmin;
    }

    public void setNameAdmin(String nameAdmin) {
        this.nameAdmin = nameAdmin;
    }

    @Override
    public String toString() {
        return "BillImportDTO{" +
                "idBillIm=" + idBillIm +
                ", idAdmin=" + idAdmin +
                ", idProduct=" + idProduct +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", quantity=" + quantity +
                ", importDate=" + importDate +
                ", nameAdmin='" + nameAdmin + '\'' +
                '}';
    }
}
