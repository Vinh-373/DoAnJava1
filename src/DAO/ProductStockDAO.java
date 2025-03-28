package DAO;

import DTO.ProductStockDTO;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class ProductStockDAO {
    private Connection conn;

    public ProductStockDAO(Connection conn) {
        this.conn = conn;
    }

    // Thêm một bản ghi Product_Stock vào cơ sở dữ liệu
    public boolean insertProductStock(ProductStockDTO product) {
        String sql = "INSERT INTO Product_Stock (Product_ID, Product_Name, CPU, Ram, Graphics_Card, Battery, "
                + "Weight, Price, Quantity, Status, Spoiled_Quantity, Category_ID, Image) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getCpu());
            stmt.setString(4, product.getRam());
            stmt.setString(5, product.getGraphicsCard());
            stmt.setString(6, product.getBattery());
            stmt.setString(7, product.getWeight());
            stmt.setBigDecimal(8, product.getPrice());
            stmt.setInt(9, product.getQuantity());
            stmt.setString(10, product.getStatus());
            stmt.setInt(11, product.getSpoiledQuantity());
            stmt.setString(12, product.getCategoryId());
            stmt.setString(13, product.getImage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả dữ liệu Product_Stock
    public ArrayList<ProductStockDTO> getAllProductStocks() {
        ArrayList<ProductStockDTO> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product_Stock";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productList.add(new ProductStockDTO(
                        rs.getString("Product_ID"),
                        rs.getString("Product_Name"),
                        rs.getString("CPU"),
                        rs.getString("Ram"),
                        rs.getString("Graphics_Card"),
                        rs.getString("Battery"),
                        rs.getString("Weight"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getInt("Spoiled_Quantity"),
                        rs.getString("Category_ID"),
                        rs.getString("Image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Lấy thông tin Product_Stock theo Product_ID
    public ProductStockDTO getProductStockById(String productId) {
        String sql = "SELECT * FROM Product_Stock WHERE Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductStockDTO(
                            rs.getString("Product_ID"),
                            rs.getString("Product_Name"),
                            rs.getString("CPU"),
                            rs.getString("Ram"),
                            rs.getString("Graphics_Card"),
                            rs.getString("Battery"),
                            rs.getString("Weight"),
                            rs.getBigDecimal("Price"),
                            rs.getInt("Quantity"),
                            rs.getString("Status"),
                            rs.getInt("Spoiled_Quantity"),
                            rs.getString("Category_ID"),
                            rs.getString("Image")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin Product_Stock
    public boolean updateProductStock(ProductStockDTO product) {
        String sql = "UPDATE Product_Stock SET Product_Name = ?, CPU = ?, Ram = ?, Graphics_Card = ?, "
                + "Battery = ?, Weight = ?, Price = ?, Quantity = ?, Status = ?, Spoiled_Quantity = ?, "
                + "Category_ID = ?, Image = ? WHERE Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCpu());
            stmt.setString(3, product.getRam());
            stmt.setString(4, product.getGraphicsCard());
            stmt.setString(5, product.getBattery());
            stmt.setString(6, product.getWeight());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setInt(8, product.getQuantity());
            stmt.setString(9, product.getStatus());
            stmt.setInt(10, product.getSpoiledQuantity());
            stmt.setString(11, product.getCategoryId());
            stmt.setString(12, product.getImage());
            stmt.setString(13, product.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Product_Stock theo Product_ID
    public boolean deleteProductStock(String productId) {
        String sql = "DELETE FROM Product_Stock WHERE Product_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
