package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.InsuranceClaimDAO;
import com.quanlybanlaptop.dto.InsuranceClaimDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsuranceClaimBUS {
    private InsuranceClaimDAO insuranceClaimDAO;

    public InsuranceClaimBUS(InsuranceClaimDAO insuranceClaimDAO) {
        this.insuranceClaimDAO = insuranceClaimDAO;
    }
    public boolean addInsuranceClaim(int idInsurance, int idAdmin, int idProduct, String numSeri, String description, String status) {
        return insuranceClaimDAO.addInsuranceClaim(idInsurance, idAdmin, idProduct, numSeri, description, status);
    }
    public ArrayList<InsuranceClaimDTO> getAllInsuranceClaimsWithNames() {
        return insuranceClaimDAO.getAllInsuranceClaimsWithNames();
    }
    public boolean updateStatusToProcessed(int idClaim) {
        return insuranceClaimDAO.updateStatusToProcessed(idClaim);
    }
    public List<InsuranceClaimDTO> searchInsuranceClaim(String keyword, String searchType) throws SQLException {
        return insuranceClaimDAO.searchInsuranceClaim(keyword, searchType);
    }
}