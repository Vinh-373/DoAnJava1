package DAO;

import DTO.SupplierDTO;
import java.sql.*;
import java.util.ArrayList;

public class SupplierDAO {
    private Connection conn;

    public SupplierDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một bản ghi Supplier vào cơ sở dữ liệu
    public boolean insertSupplier(SupplierDTO supplier) {
        String sql = "INSERT INTO Supplier (Sup_ID, Sup_Name, Address, Contact) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupId());
            stmt.setString(2, supplier.getSupName());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getContact());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu Supplier
    public ArrayList<SupplierDTO> getAllSuppliers() {
        ArrayList<SupplierDTO> supplierList = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                supplierList.add(new SupplierDTO(
                        rs.getString("Sup_ID"),
                        rs.getString("Sup_Name"),
                        rs.getString("Address"),
                        rs.getString("Contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    // Lấy thông tin Supplier theo Sup_ID
    public SupplierDTO getSupplierById(String supId) {
        String sql = "SELECT * FROM Supplier WHERE Sup_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SupplierDTO(
                            rs.getString("Sup_ID"),
                            rs.getString("Sup_Name"),
                            rs.getString("Address"),
                            rs.getString("Contact")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin Supplier
    public boolean updateSupplier(SupplierDTO supplier) {
        String sql = "UPDATE Supplier SET Sup_Name = ?, Address = ?, Contact = ? WHERE Sup_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupName());
            stmt.setString(2, supplier.getAddress());
            stmt.setString(3, supplier.getContact());
            stmt.setString(4, supplier.getSupId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Supplier theo Sup_ID
    public boolean deleteSupplier(String supId) {
        String sql = "DELETE FROM Supplier WHERE Sup_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
