package com.quanlybanlaptop.bus;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.RoleDTO;
import java.sql.SQLException;
import java.util.ArrayList;
public class RoleBUS {
    private RoleDAO roleDAO;
    public RoleBUS(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public ArrayList<RoleDTO> getAllRoles() throws SQLException {
        return roleDAO.getAllRoles();
    }
}
