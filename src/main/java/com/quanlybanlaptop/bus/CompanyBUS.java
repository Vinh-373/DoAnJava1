package com.quanlybanlaptop.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import com.quanlybanlaptop.dao.CompanyDAO;
import com.quanlybanlaptop.dto.CompanyDTO;

public class CompanyBUS {

    private static CompanyDAO companyDAO;
    public CompanyBUS(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }
    public static ArrayList<CompanyDTO> getAllCompany() throws SQLException {
        return companyDAO.getAllCompanies();
    }
    public CompanyDTO getCompanyById(int companyId) throws SQLException {
        if(companyDAO.getCompanyById(companyId) != null) {
            return companyDAO.getCompanyById(companyId);
        }
        return null;
    }
    public boolean addCompany(CompanyDTO company) throws SQLException {
        if(company == null) {
            return false;
        }
        return companyDAO.insertCompany(company);
    }
    public boolean updateCompany(CompanyDTO company) throws SQLException {
        if(company == null) {
            return false;
        }
        return companyDAO.updateCompany(company);
    }
    public boolean deleteCompany(int companyId) throws SQLException {
        if(companyId < 0){
            return false;
        }
        return companyDAO.deleteCompany(companyId);
    }
}