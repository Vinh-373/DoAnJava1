package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.AdminDAO;
import com.quanlybanlaptop.dto.AdminDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBUS {
    private AdminDAO adminDAO ;
    public AdminBUS(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public  boolean checkLogin (String email, String password) {
        return adminDAO.checkLogin(email, password);
    }
    public AdminDTO getAdminByEmail(String email,String password) {
        return adminDAO.getByEmailPass(email,password);
    }
    public ArrayList<AdminDTO> getAllAdminDTOS() throws SQLException {
        return adminDAO.getAllAdmin();
    }
    public ArrayList<AdminDTO> getActiveAdmin(int status) throws SQLException {
        ArrayList<AdminDTO> list = new ArrayList<>();
        ArrayList<AdminDTO> listt = new ArrayList<>();
        for(AdminDTO ad : adminDAO.getAllAdmin()){
            if(ad.getStatus()==1){
                list.add(ad);
            }else{
                listt.add(ad);
            }
        }
        if(status==1){
            return list;
        }
        return listt;
    }
    public boolean addAdmin(AdminDTO adminDTO) throws SQLException {
        return adminDAO.insertAdmin(adminDTO);
    }
    public boolean updateAdmin(AdminDTO adminDTO) throws SQLException {
        return adminDAO.updateAdmin(adminDTO);
    }
    public boolean offAcc(int id,int status) throws SQLException {
        return adminDAO.offAcc(id,status);
    }
    public ArrayList<AdminDTO> searchAdmin(String keyword, String field, int status) throws SQLException {
        return adminDAO.searchAdmin(keyword, field, status);
    }
}