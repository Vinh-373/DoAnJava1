package DAO;

import DTO.OrdersDetailsDTO;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class OrdersDetailsDAO {
    private Connection conn;

    public OrdersDetailsDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một bản ghi OrdersDetails vào cơ sở dữ liệu
    public boolean insertOrderDetails(OrdersDetailsDTO orderDetails) {
        String sql = "INSERT INTO Orders_Details (Order_No, Customer_ID, Product_ID, Price, Quantity, Date_Order, Time_Order, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderDetails.getOrderNo());
            stmt.setString(2, orderDetails.getCustomerId());
            stmt.setString(3, orderDetails.getProductId());
            stmt.setBigDecimal(4, orderDetails.getPrice());
            stmt.setInt(5, orderDetails.getQuantity());
            stmt.setDate(6, orderDetails.getDateOrder() != null ? Date.valueOf(orderDetails.getDateOrder()) : null);
            stmt.setTime(7, orderDetails.getTimeOrder() != null ? Time.valueOf(orderDetails.getTimeOrder()) : null);
            stmt.setString(8, orderDetails.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu OrdersDetails
    public ArrayList<OrdersDetailsDTO> getAllOrdersDetails() {
        ArrayList<OrdersDetailsDTO> ordersDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM Orders_Details";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ordersDetailsList.add(new OrdersDetailsDTO(
                        rs.getString("Order_No"),
                        rs.getString("Customer_ID"),
                        rs.getString("Product_ID"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Quantity"),
                        rs.getDate("Date_Order") != null ? rs.getDate("Date_Order").toLocalDate() : null,
                        rs.getTime("Time_Order") != null ? rs.getTime("Time_Order").toLocalTime() : null,
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersDetailsList;
    }

    // Lấy thông tin OrdersDetails theo Order_No, Customer_ID và Product_ID
    public OrdersDetailsDTO getOrderDetailsById(String orderNo, String customerId, String productId) {
        String sql = "SELECT * FROM Orders_Details WHERE Order_No = ? AND Customer_ID = ? AND Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, customerId);
            stmt.setString(3, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdersDetailsDTO(
                            rs.getString("Order_No"),
                            rs.getString("Customer_ID"),
                            rs.getString("Product_ID"),
                            rs.getBigDecimal("Price"),
                            rs.getInt("Quantity"),
                            rs.getDate("Date_Order") != null ? rs.getDate("Date_Order").toLocalDate() : null,
                            rs.getTime("Time_Order") != null ? rs.getTime("Time_Order").toLocalTime() : null,
                            rs.getString("Status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin OrdersDetails
    public boolean updateOrderDetails(OrdersDetailsDTO orderDetails) {
        String sql = "UPDATE Orders_Details SET Price = ?, Quantity = ?, Date_Order = ?, Time_Order = ?, Status = ? "
                + "WHERE Order_No = ? AND Customer_ID = ? AND Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, orderDetails.getPrice());
            stmt.setInt(2, orderDetails.getQuantity());
            stmt.setDate(3, orderDetails.getDateOrder() != null ? Date.valueOf(orderDetails.getDateOrder()) : null);
            stmt.setTime(4, orderDetails.getTimeOrder() != null ? Time.valueOf(orderDetails.getTimeOrder()) : null);
            stmt.setString(5, orderDetails.getStatus());
            stmt.setString(6, orderDetails.getOrderNo());
            stmt.setString(7, orderDetails.getCustomerId());
            stmt.setString(8, orderDetails.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa OrdersDetails theo Order_No, Customer_ID và Product_ID
    public boolean deleteOrderDetails(String orderNo, String customerId, String productId) {
        String sql = "DELETE FROM Orders_Details WHERE Order_No = ? AND Customer_ID = ? AND Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, customerId);
            stmt.setString(3, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
