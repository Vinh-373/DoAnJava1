package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.InsuranceDAO;
import com.quanlybanlaptop.dao.SeriProductDAO;
import com.quanlybanlaptop.dto.InsuranceDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsuranceBUS {
    private InsuranceDAO insuranceDAO;

    public InsuranceBUS(InsuranceDAO insuranceDAO) {
        this.insuranceDAO = insuranceDAO;
    }

    // Thêm bảo hành mới
    public boolean addInsurance(InsuranceDTO insurance) {
        return insuranceDAO.insertInsurance(insurance);
    }

    // Lấy tất cả bảo hành
    public List<InsuranceDTO> getAllInsurance() {
        return insuranceDAO.getAllInsurance();
    }

    // Kiểm tra đã có bảo hành cho seri này chưa
    public boolean existsBySeri(String numSeri) {
        return insuranceDAO.existsBySeri(numSeri);
    }
    public List<Object[]> getSoldSeriInfo() {
        return insuranceDAO.getSoldSeriInfo();
    }
    public List<Object[]> getInsuredSeriInfo() {
        return insuranceDAO.getInsuredSeriInfo();
    }
    public ArrayList<InsuranceDTO> getAllInsuranceTable() throws SQLException {
        return insuranceDAO.getAllInsuranceTable();
    }
    public List<InsuranceDTO> searchInsurance(String keyword, String searchType) throws SQLException {
        return insuranceDAO.searchInsurance(keyword, searchType);
    }
    
}