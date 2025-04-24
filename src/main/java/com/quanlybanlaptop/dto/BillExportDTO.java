package com.quanlybanlaptop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BillExportDTO {
    private int idBillEx;
    private int idAdmin;
    private String nameAdmin, nameCustomer;
    private Integer idCustomer; // nullable
    private int totalProduct;
    private BigDecimal totalPrice;
    private Timestamp dateEx;
    private int status;

    public BillExportDTO() {}

    public BillExportDTO(int idBillEx, int idAdmin, Integer idCustomer, int totalProduct, BigDecimal totalPrice, Timestamp dateEx, int status, String nameAdmin, String nameCustomer) {
        this.idBillEx = idBillEx;
        this.idAdmin = idAdmin;
        this.idCustomer = idCustomer;
        this.totalProduct = totalProduct;
        this.totalPrice = totalPrice;
        this.dateEx = dateEx;
        this.status = status;
        this.nameAdmin = nameAdmin;
        this.nameCustomer = nameCustomer;
    }

    public int getIdBillEx() {
        return idBillEx;
    }

    public void setIdBillEx(int idBillEx) {
        this.idBillEx = idBillEx;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String getNameAdmin() {
        return nameAdmin;
    }
    public void setNameAdmin(String nameAdmin) {
        this.nameAdmin = nameAdmin;
    }
    public String getNameCustomer() {
        return nameCustomer;
    }
    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }
    public Timestamp getDateEx() {
        return dateEx;
    }
    public void setDateEx(Timestamp dateEx) {
        this.dateEx = dateEx;
    }


}

