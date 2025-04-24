package com.quanlybanlaptop.dto;

public class SeriProductDTO {
    private String numSeri;
    private int idProduct;
    private int status;

    public SeriProductDTO(String numSeri, int idProduct, int status) {
        this.numSeri = numSeri;
        this.idProduct = idProduct;
        this.status = status;
    }

    public String getNumSeri() {
        return numSeri;
    }

    public void setNumSeri(String numSeri) {
        this.numSeri = numSeri;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
