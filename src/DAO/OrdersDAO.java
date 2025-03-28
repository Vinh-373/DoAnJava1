package DAO;

import DTO.OrdersDTO;
import java.sql.*;
import java.util.ArrayList;
import java.time.*;

public class OrdersDAO {
    private Connection conn;

    public OrdersDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một bản ghi Orders vào cơ sở dữ liệu
    public boolean insertOrder(OrdersDTO order) {
        String sql = "INSERT INTO Orders (Order_No, Customer_ID, Date_Order, Time_Order) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getOrderNo());
            stmt.setString(2, order.getCustomerId());
            stmt.setDate(3, order.getDateOrder() != null ? Date.valueOf(order.getDateOrder()) : null);
            stmt.setTime(4, order.getTimeOrder() != null ? Time.valueOf(order.getTimeOrder()) : null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu Orders
    public ArrayList<OrdersDTO> getAllOrders() {
        ArrayList<OrdersDTO> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ordersList.add(new OrdersDTO(
                        rs.getString("Order_No"),
                        rs.getString("Customer_ID"),
                        rs.getDate("Date_Order") != null ? rs.getDate("Date_Order").toLocalDate() : null,
                        rs.getTime("Time_Order") != null ? rs.getTime("Time_Order").toLocalTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    // Lấy thông tin Orders theo Order_No và Customer_ID
    public OrdersDTO getOrderById(String orderNo, String customerId) {
        String sql = "SELECT * FROM Orders WHERE Order_No = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdersDTO(
                            rs.getString("Order_No"),
                            rs.getString("Customer_ID"),
                            rs.getDate("Date_Order") != null ? rs.getDate("Date_Order").toLocalDate() : null,
                            rs.getTime("Time_Order") != null ? rs.getTime("Time_Order").toLocalTime() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin Orders
    public boolean updateOrder(OrdersDTO order) {
        String sql = "UPDATE Orders SET Date_Order = ?, Time_Order = ? WHERE Order_No = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, order.getDateOrder() != null ? Date.valueOf(order.getDateOrder()) : null);
            stmt.setTime(2, order.getTimeOrder() != null ? Time.valueOf(order.getTimeOrder()) : null);
            stmt.setString(3, order.getOrderNo());
            stmt.setString(4, order.getCustomerId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Orders theo Order_No và Customer_ID
    public boolean deleteOrder(String orderNo, String customerId) {
        String sql = "DELETE FROM Orders WHERE Order_No = ? AND Customer_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
