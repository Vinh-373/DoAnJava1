package DAO;

import DTO.ProductDTO;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductDAO {
    private Connection conn;

    // Constructor khởi tạo kết nối
    public ProductDAO(Connection conn) {
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.conn = conn;
    }



    // Lấy danh sách tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        String sql = "SELECT p.id_product, p.category, p.operating_system, p.origin, p.name, " +
                "p.cpu, p.ram, p.rom, p.graphics_card, p.battery, p.weight, p.image, " +
                "ps.price_store, ps.status_store, ps.quantity_store, ps.broken_quantity_store " +
                "FROM PRODUCT_IN_STOCK p " +
                "JOIN PRODUCT_IN_STORE ps ON p.id_product = ps.id_product";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProductDTO product = new ProductDTO(
                        rs.getString("id_product"),
                        rs.getString("name"),
                        rs.getString("cpu"),
                        rs.getString("ram"),
                        rs.getString("rom"), // Thêm rom
                        rs.getString("graphics_card"),
                        rs.getString("battery"),
                        rs.getString("weight"),
                        rs.getBigDecimal("price_store"),
                        rs.getInt("quantity_store"),
                        rs.getBoolean("status_store"),
                        rs.getInt("broken_quantity_store"), // Sửa thành broken_quantity_store
                        rs.getString("category"),
                        rs.getString("image"),
                        rs.getString("operating_system"),
                        rs.getString("origin")
                );
                productList.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    //Xóa sản phẩm
    public boolean deleteProduct(String idProduct) throws SQLException {
        String sql = "UPDATE PRODUCT_IN_STORE pis " +
                "JOIN PRODUCT_IN_STOCK p ON pis.id_product = p.id_product " +
                "SET pis.status_store = 0, " +
                "p.quantity = p.quantity + pis.quantity_store, " +
                "pis.quantity_store = 0 " +
                "WHERE pis.id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduct);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row is affected
        }
    }
    public ProductDTO getProductById(String idProduct) throws SQLException {
        String sql = "SELECT p.id_product, p.category, p.operating_system, p.origin, p.name, " +
                "p.cpu, p.ram, p.rom, p.graphics_card, p.battery, p.weight, p.image, " +
                "ps.price_store, ps.status_store, ps.quantity_store, ps.broken_quantity_store " +
                "FROM PRODUCT_IN_STOCK p " +
                "JOIN PRODUCT_IN_STORE ps ON p.id_product = ps.id_product " +
                "WHERE p.id_product = ?"; // Thêm điều kiện WHERE
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idProduct);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductDTO(
                        rs.getString("id_product"),           // idProduct
                        rs.getString("name"),                // name
                        rs.getString("origin"),              // company (giả sử origin là company)
                        rs.getString("cpu"),                 // cpu
                        rs.getString("ram"),                 // ram
                        rs.getString("rom"),                 // storage
                        rs.getString("battery"),             // screen (giả sử battery là screen)
                        rs.getString("graphics_card"),       // card
                        rs.getBigDecimal("price_store"),     // price
                        rs.getInt("quantity_store"),         // quantity
                        rs.getBoolean("status_store"),       // status
                        rs.getInt("broken_quantity_store"),  // quantityStore
                        rs.getString("image"),               // image
                        rs.getString("operating_system"),    // description (giả sử operating_system là description)
                        rs.getString("category"),            // category
                        rs.getString("origin")               // brand
                );
            }
            return null;
        }
    }



}
