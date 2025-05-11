package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement; 
import com.quanlybanlaptop.dto.RoleDTO;


public class RoleDAO {
    private Connection conn;

    public RoleDAO(Connection conn) {
        this.conn = conn;
    }

    private RoleDTO createDTO(ResultSet rs) throws SQLException {
        return new RoleDTO(
                rs.getInt("id_role"),
                rs.getString("name_role"),
                rs.getInt("status")
        );
    }
     public ArrayList<RoleDTO> getAllRoles() throws SQLException {
        ArrayList<RoleDTO> roleDTOS = new ArrayList<>();
        String sql = "SELECT * FROM ROLE WHERE status = 1";
        try (var stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RoleDTO roleDTO = createDTO(rs);
                roleDTOS.add(roleDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching roles: " + e.getMessage(), e);
        }
        return roleDTOS;
     }
    public RoleDTO getRoleById(int idRole) throws SQLException {
        String sql = "SELECT * FROM ROLE WHERE id_role = ?";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRole);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public int insert(RoleDTO role) throws SQLException {
        String sql = "INSERT INTO ROLE (name_role, status) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getNameRole());
            stmt.setInt(2, role.getStatus());
            stmt.executeUpdate();

            // Retrieve the auto-generated idRole
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated idRole
                }
            }
            throw new SQLException("Failed to retrieve auto-generated idRole");
        }
    }
    public boolean updateNameRole(String name,int id) throws SQLException {
        String sql = "UPDATE ROLE SET name_role = ? WHERE id_role = ?";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateStatus(int status, int id) throws SQLException {
        String sql = "UPDATE ROLE SET status = ? WHERE id_role = ?";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
