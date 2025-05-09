package com.quanlybanlaptop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    // public RoleDTO getRoleById(int idRole) throws SQLException {
    //     String sql = "SELECT * FROM ROLE WHERE id_role = ?";
    //     try (var stmt = conn.prepareStatement(sql)) {
    //         stmt.setInt(1, idRole);
    //         try (ResultSet rs = stmt.executeQuery()) {
    //             if (rs.next()) {
    //                 return createDTO(rs);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    // public RoleDTO getRoleByName(String nameRole) throws SQLException {
    //     String sql = "SELECT * FROM ROLE WHERE name_role = ?";
    //     try (var stmt = conn.prepareStatement(sql)) {
    //         stmt.setString(1, nameRole);
    //         try (ResultSet rs = stmt.executeQuery()) {
    //             if (rs.next()) {
    //                 return createDTO(rs);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    // public boolean insertRole(RoleDTO dto) throws SQLException {
    //     String sql = "INSERT INTO ROLE (name_role, status) VALUES (?, ?)";
    //     try (var stmt = conn.prepareStatement(sql)) {
    //         stmt.setString(1, dto.getNameRole());
    //         stmt.setInt(2, dto.getStatus());
    //         return stmt.executeUpdate() > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
    // public boolean updateRole(RoleDTO dto) throws SQLException {
    //     String sql = "UPDATE ROLE SET name_role = ?, status = ? WHERE id_role = ?";
    //     try (var stmt = conn.prepareStatement(sql)) {
    //         stmt.setString(1, dto.getNameRole());
    //         stmt.setInt(2, dto.getStatus());
    //         stmt.setInt(3, dto.getIdRole());
    //         return stmt.executeUpdate() > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
}
