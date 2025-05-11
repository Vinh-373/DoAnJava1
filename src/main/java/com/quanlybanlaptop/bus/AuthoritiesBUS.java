package com.quanlybanlaptop.bus;
import com.quanlybanlaptop.dao.AuthoritiesDAO;

public class AuthoritiesBUS {
    private AuthoritiesDAO authoritiesDAO;
    public AuthoritiesBUS(AuthoritiesDAO authoritiesDAO) {
        this.authoritiesDAO = authoritiesDAO;
    }
    public boolean isChecked(int idRole, int idAuthority) {
        return authoritiesDAO.isChecked(idRole, idAuthority);
    }
    public boolean updateIsChecked(int idRole, int idAuthority, boolean isChecked) {
        return authoritiesDAO.updateIsChecked(idRole, idAuthority, isChecked);
    }
    public boolean insert(int idRole) {
        return authoritiesDAO.insert(idRole);
    }
}
