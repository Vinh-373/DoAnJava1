package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.ProductDTO;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductDAO {
    private Connection conn;

    // Constructor khởi tạo kết nối
    public ProductDAO(Connection conn) {
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
    }

    // Hàm tạo ProductDTO từ ResultSet
    private ProductDTO createProductDTO(ResultSet rs) throws SQLException {
        return new ProductDTO(
                rs.getInt("id_product"),           // idProduct
                rs.getString("name"),         // name
                rs.getString("cpu"),                  // cpu
                rs.getString("size_screen"),          // sizeScreen
                rs.getString("ram"),                  // ram
                rs.getString("graphics_card"),        // graphicsCard
                rs.getString("battery"),              // battery
                rs.getString("operating_system"),     // operatingSystem
                rs.getString("weight"),               // weight
                rs.getBigDecimal("price"),                // price
                rs.getInt("quantity"),                // quantityStore
                rs.getInt("status"),               // status
                rs.getInt("id_category"),          // category
                rs.getInt("id_company"),          // company
                rs.getString("img"),                // image
                rs.getString("rom"),                  // rom
                rs.getString("name_category"),              // supName
                rs.getString("name_company")              // supName
        );
    }

    // Lấy danh sách tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        String sql = "SELECT p.id_product, p.name, p.cpu, p.size_screen, p.ram, p.graphics_card, p.battery,\n" +
                "       p.operating_system, p.weight, p.price, p.quantity, p.status, p.id_category, p.id_company, p.img,\n" +
                "       p.rom, c.name_category, s.name_company\n" +
                "FROM PRODUCT p JOIN CATEGORY c ON p.id_category = c.id_category JOIN COMPANY s ON p.id_company = s.id_company";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductDTO product = createProductDTO(rs); // Sử dụng hàm createProductDTO
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi lấy danh sách sản phẩm: " + ex.getMessage(), ex);
        }
        return productList;
    }


//    // Lấy sản phẩm theo ID
    public ProductDTO getProductById(String idProduct) throws SQLException {
        String sql = "SELECT p.id_product, p.name, p.cpu, p.size_screen, p.ram, p.graphics_card, p.battery,\n" +
                "       p.operating_system, p.weight, p.price, p.quantity, p.status, p.id_category, p.id_company, p.img,\n" +
                "       p.rom, c.name_category, s.name_company\n" +
                "FROM PRODUCT p JOIN CATEGORY c ON p.id_category = c.id_category JOIN COMPANY s ON p.id_company = s.id_company WHERE p.id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduct);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createProductDTO(rs); // Sử dụng hàm createProductDTO
                }
                return null; // Không tìm thấy sản phẩm
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi lấy sản phẩm theo ID: " + ex.getMessage(), ex);
        }
    }
//
//    // Thêm sản phẩm mới
//    public boolean addProduct(ProductDTO product) throws SQLException {
//        String sqlProduct = "INSERT INTO Product (Product_ID, Product_Name, CPU, Size_Screen, Ram, Rom, " +
//                "Graphics_Card, Battery, Operating_System, Weight, Price, Status, Spoiled_Quantity, Category_ID, Image) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        String sqlProductStock = "INSERT INTO Product_Stock (Product_ID, Quantity_Stock) VALUES (?, ?)";
//
//        try {
//            // Bắt đầu transaction
//            conn.setAutoCommit(false);
//
//            // Thêm vào bảng Product
//            try (PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct)) {
//                stmtProduct.setString(1, product.getIdProduct());
//                stmtProduct.setString(2, product.getName());
//                stmtProduct.setString(3, product.getCpu());
//                stmtProduct.setString(4, product.getSizeScreen());
//                stmtProduct.setString(5, product.getRam());
//                stmtProduct.setString(6, product.getRom());
//                stmtProduct.setString(7, product.getGraphicsCard());
//                stmtProduct.setString(8, product.getBattery());
//                stmtProduct.setString(9, product.getOperatingSystem());
//                stmtProduct.setString(10, product.getWeight());
//                stmtProduct.setDouble(11, product.getPrice());
//                stmtProduct.setString(12, product.getStatus());
//                stmtProduct.setInt(13, product.getSpoiledQuantity());
//                stmtProduct.setString(14, product.getCategory());
//                stmtProduct.setString(15, product.getImage());
//                stmtProduct.executeUpdate();
//            }
//
//            // Thêm vào bảng Product_Stock
//            try (PreparedStatement stmtProductStock = conn.prepareStatement(sqlProductStock)) {
//                stmtProductStock.setString(1, product.getIdProduct());
//                stmtProductStock.setInt(2, product.getQuantityStore());
//                stmtProductStock.executeUpdate();
//            }
//
//            // Commit transaction
//            conn.commit();
//            return true;
//        } catch (SQLException ex) {
//            // Rollback transaction nếu có lỗi
//            try {
//                conn.rollback();
//            } catch (SQLException rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
//            throw new SQLException("Lỗi khi thêm sản phẩm: " + ex.getMessage(), ex);
//        } finally {
//            // Khôi phục chế độ auto-commit
//            conn.setAutoCommit(true);
//        }
//    }
//
    public boolean deleteProduct(String idProduct) throws SQLException {
        String sql = "UPDATE PRODUCT SET Status = 0 WHERE id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduct);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất 1 bản ghi được cập nhật
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi xóa sản phẩm: " + ex.getMessage(), ex);
        }
    }
}