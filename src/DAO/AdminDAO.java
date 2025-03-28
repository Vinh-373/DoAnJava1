package DAO;

import DTO.AdminDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminDAO {
    private Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean InsertAdmin(AdminDTO admin){
        String sql = "INSERT INTO Admin (Admin_ID,Admin_Name,Gender,Email,Contact,Password) VALUES (?,?,?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,admin.getAdminID());
            stmt.setString(2,admin.getAdminName());
            stmt.setString(3,admin.getGender());
            stmt.setString(4,admin.getEmail());
            stmt.setString(5,admin.getContact());
            stmt.setString(6,admin.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteAdmin(String adminID){
        String sql = "DELETE FROM Admin WHERE Admin_ID=?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,adminID);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateAdmin(AdminDTO admin){
        String sql = "UPDATE Admin SET Admin_Name = ?, Gender = ?, Email = ?, Contact = ?, Password = ? WHERE Admin_ID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,admin.getAdminName());
            stmt.setString(2,admin.getGender());
            stmt.setString(3,admin.getEmail());
            stmt.setString(4,admin.getContact());
            stmt.setString(5,admin.getPassword());
            stmt.setString(6,admin.getAdminID());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public AdminDTO getAdminByID(String adminID){
        AdminDTO admin = null;
        String sql = "SELECT * FROM Admin WHERE Admin_ID = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,adminID);
            ResultSet rs = stmt.executeQuery(adminID);
            if(rs.next()){
                admin = new AdminDTO(
                    rs.getString("Admin_ID"),
                    rs.getString("Admin_Name"),
                    rs.getString("Gender"),
                    rs.getString("Email"),
                    rs.getString("Contact"),
                    rs.getString("Password")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }
    public ArrayList<AdminDTO> getAllAdmin(){
        ArrayList<AdminDTO> adminList = new ArrayList<>();
        String sql = "SELECT * FROM Admin";
        try(PreparedStatement stmt = conn.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                AdminDTO admin = new AdminDTO(
                    rs.getString("Admin_ID"),
                    rs.getString("Admin_Name"),
                    rs.getString("Gender"),
                    rs.getString("Email"),
                    rs.getString("Contact"),
                    rs.getString("Password")
                );
                adminList.add(admin);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return adminList;
    }

}
