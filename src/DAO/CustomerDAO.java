package DAO;

import DTO.CustomerDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm khách hàng mới vào cơ sở dữ liệu
    public boolean insertCustomer(CustomerDTO customer) {
        String sql = "INSERT INTO Customer (Customer_ID, Full_Name, Gender, Date_Of_Birth, Email, Contact, Address, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getFullName());
            stmt.setString(3, customer.getGender());
            stmt.setDate(4, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getContact());
            stmt.setString(7, customer.getAddress());
            stmt.setString(8, customer.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả khách hàng từ cơ sở dữ liệu
    public ArrayList<CustomerDTO> getAllCustomers() {
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new CustomerDTO(
                        rs.getString("Customer_ID"),
                        rs.getString("Full_Name"),
                        rs.getString("Gender"),
                        rs.getDate("Date_Of_Birth").toLocalDate(),
                        rs.getString("Email"),
                        rs.getString("Contact"),
                        rs.getString("Address"),
                        rs.getString("Password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Lấy thông tin khách hàng theo Customer_ID
    public CustomerDTO getCustomerById(String customerId) {
        String sql = "SELECT * FROM Customer WHERE Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CustomerDTO(
                            rs.getString("Customer_ID"),
                            rs.getString("Full_Name"),
                            rs.getString("Gender"),
                            rs.getDate("Date_Of_Birth").toLocalDate(),
                            rs.getString("Email"),
                            rs.getString("Contact"),
                            rs.getString("Address"),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(CustomerDTO customer) {
        String sql = "UPDATE Customer SET Full_Name = ?, Gender = ?, Date_Of_Birth = ?, Email = ?, Contact = ?, Address = ?, Password = ? WHERE Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getGender());
            stmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getContact());
            stmt.setString(6, customer.getAddress());
            stmt.setString(7, customer.getPassword());
            stmt.setString(8, customer.getCustomerID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng theo Customer_ID
    public boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM Customer WHERE Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra sự tồn tại của khách hàng theo email
    public boolean checkCustomerByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE Email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
