package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.SeriProductDAO;
import com.quanlybanlaptop.dto.SeriProductDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class SeriProductBUS {
    private SeriProductDAO dao;

    public SeriProductBUS(SeriProductDAO seriProductDAO) {
        this.dao = seriProductDAO;
    }

    public boolean themDanhSachSeri(ArrayList<SeriProductDTO> danhSachSeri) {
        for (SeriProductDTO dto : danhSachSeri) {
            if (dao.isSeriExists(dto.getNumSeri())) {
                return false; // phát hiện seri trùng trước khi insert
            }
        }

        for (SeriProductDTO dto : danhSachSeri) {
            if (!dao.insertSeriProduct(dto)) {
                return false;
            }
        }

        return true;
    }
    public ArrayList<SeriProductDTO> getListSeriById(int idProduct, int status) {
        return dao.getListSeriById(idProduct,status);
    }
    public ArrayList<SeriProductDTO> getAllSeriProduct() {
        return dao.getAllSeriProduct();
    }

    public SeriProductDTO getByNumSeri(String seri) {
        return dao.getByNumSeri(seri);
    }
    public SeriProductDTO getByNumSeri2(String seri) {
        return dao.getByNumSeri2(seri);
    }
    

    public boolean updateSeriProduct(ArrayList<String> listSeri, int status) {
        return dao.updateSeriProduct(listSeri, status);
    }

    public boolean deleteSeriProduct(String seri) {
        return dao.deleteSeriProduct(seri);
    }
    public boolean updateStatusTo3BySeri(String numSeri) {
        return dao.updateStatusTo3BySeri(numSeri);
    }
}
