package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.CustomerDTO;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CustomerDAO {
   

    // Lấy tất cả khách hàng
    public ArrayList<CustomerDTO> getAllCustomers() {
    ArrayList<CustomerDTO> customers = new ArrayList<>();
    String query = "SELECT * FROM Customer";

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            CustomerDTO customer = new CustomerDTO();
            customer.setId(rs.getInt("id_customer"));
            customer.setName(rs.getString("name"));
            customer.setBirthdate(rs.getDate("birthdate"));
            customer.setGender(rs.getString("gender"));
            customer.setPhone(rs.getString("contact"));
            customer.setCitizenId(rs.getString("citizen_id"));
            customer.setStatus(rs.getInt("status"));

            customers.add(customer);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return customers;
}


    // Thêm khách hàng
   public boolean addCustomer(CustomerDTO customer) {
    String query = "INSERT INTO Customer (name, birthdate, gender, contact, citizen_id, status) VALUES (?, ?, ?, ?, ?, ?)";
    try (
        java.sql.Connection conn = DatabaseConnection.getConnection();
        java.sql.PreparedStatement stmt = conn.prepareStatement(query)
    ) {
        stmt.setString(1, customer.getName());
        stmt.setDate(2, customer.getBirthdate());
        stmt.setString(3, customer.getGender());
        stmt.setString(4, customer.getPhone());
        stmt.setString(5, customer.getCitizenId());
        stmt.setInt(6, customer.getStatus());
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}
    // Lấy khách hàng theo ID
    public CustomerDTO getCustomerById(int id) {
        String query = "SELECT * FROM Customer WHERE id_customer = ?";
        try {
            PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setId(rs.getInt("id_customer"));
                customer.setName(rs.getString("name"));
                customer.setBirthdate(rs.getDate("birthdate"));
                customer.setGender(rs.getString("gender"));
                customer.setPhone(rs.getString("contact"));
                customer.setCitizenId(rs.getString("citizen_id"));
                customer.setStatus(rs.getInt("status"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Xóa mềm khách hàng (chuyển status = 0)
    public boolean deleteCustomer(int id) {
        String query = "UPDATE Customer SET status = 0 WHERE id = ?";
        return DatabaseConnection.prepareUpdate(query, id) > 0;
    }
    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(CustomerDTO customer) {
        String query = "UPDATE Customer SET name = ?, birthdate = ?, gender = ?, contact = ?, citizen_id = ?, status = ? WHERE id_customer = ?";
        return DatabaseConnection.prepareUpdate(query, customer.getName(), customer.getBirthdate(), customer.getGender(),
                customer.getPhone(), customer.getCitizenId(), customer.getStatus(), customer.getId()) > 0;  // Gọi phương thức prepareUpdate từ DatabaseConnection
    }






}