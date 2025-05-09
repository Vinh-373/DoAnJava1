package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dto.ProductDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class AdminDAO {
    private Connection conn;
    public AdminDAO(Connection conn) {
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Connection không được null");
        }
        this.conn = conn;
    }
    private AdminDTO createAdminDTO (ResultSet rs) throws SQLException {
        return new AdminDTO(
                rs.getInt("id_admin"),           // idProduct
                rs.getInt("id_role"),           // idRole
                rs.getString("name"),         // name
                rs.getString("gender"),
                rs.getString("email"),
                rs.getString("contact"),
                rs.getString("password"),
                rs.getInt("status")
        );
    }
    public boolean checkLogin(String email, String password) {
        String query = "SELECT * FROM ADMIN WHERE email = ? AND password = ? AND status = 1";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có dòng dữ liệu nghĩa là thông tin đăng nhập đúng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<AdminDTO> getAllAdmin() throws SQLException {
        ArrayList<AdminDTO> adminDTOS = new ArrayList<>();
        String sql = "SELECT a.id_admin,a.id_role ,a.name, a.gender, a.email, a.contact, a.password, a.status FROM ADMIN a WHERE a.status = 1 OR a.status = 0";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AdminDTO adminDTO = createAdminDTO(rs); // Sử dụng hàm createProductDTO
                adminDTOS.add(adminDTO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi lấy danh sách taikhoan: " + ex.getMessage(), ex);
        }
        return adminDTOS;
    }
    public AdminDTO getByEmailPass(String email, String password) {
        String query = "SELECT * FROM ADMIN WHERE email = ? AND password = ? AND status = 1";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createAdminDTO(rs); // Trả về đối tượng AdminDTO nếu tìm thấy
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Không tìm thấy hoặc có lỗi
    }
    public boolean insertAdmin(AdminDTO adminDTO) {
        String sql = "INSERT INTO ADMIN (id_role, name, gender, email, contact, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adminDTO.getIdRole());
            ps.setString(2, adminDTO.getName());
            ps.setString(3, adminDTO.getGender());
            ps.setString(4, adminDTO.getEmail());
            ps.setString(5, adminDTO.getContact());
            ps.setString(6, adminDTO.getPassword());
            ps.setInt(7, adminDTO.getStatus());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateAdmin(AdminDTO adminDTO) {
        String sql = "UPDATE ADMIN SET id_role = ?, name = ?, gender = ?, email = ?, contact = ?, password = ?, status = ? WHERE id_admin = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adminDTO.getIdRole());
            ps.setString(2, adminDTO.getName());
            ps.setString(3, adminDTO.getGender());
            ps.setString(4, adminDTO.getEmail());
            ps.setString(5, adminDTO.getContact());
            ps.setString(6, adminDTO.getPassword());
            ps.setInt(7, adminDTO.getStatus());
            ps.setInt(8, adminDTO.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean offAcc(int id,int status) {
        String sql = "UPDATE ADMIN SET status = ? WHERE id_admin = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public ArrayList<AdminDTO> searchAdmin(String keyword, String field, int status) throws SQLException {
        ArrayList<AdminDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ADMIN WHERE " + field + " LIKE ? AND status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setInt(2, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AdminDTO admin = new AdminDTO(
                        rs.getInt("id_admin"),
                        rs.getInt("id_role"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("password"),
                        rs.getInt("status")
                );
                list.add(admin);
            }
        }

        return list;
    }

}
