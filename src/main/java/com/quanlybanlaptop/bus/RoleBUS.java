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
    public RoleDTO getRoleById(int idRole) throws SQLException {
        return roleDAO.getRoleById(idRole);
    }
    public int addRole(RoleDTO roleDTO) throws SQLException {
        return roleDAO.insert(roleDTO);
    }
    public boolean updateNameRole(String name, int id) throws SQLException {
        return roleDAO.updateNameRole(name, id);
    }
    public boolean updateStatusRole(int status, int id) throws SQLException {
        return roleDAO.updateStatus(status, id);
    }
}
