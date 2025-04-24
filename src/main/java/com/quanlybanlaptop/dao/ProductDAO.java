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
                rs.getString("ram"),                  // ram
                rs.getString("rom"),                  // rom
                rs.getString("graphics_card"),        // graphicsCard
                rs.getString("battery"),              // battery
                rs.getString("weight"),               // weight
                rs.getBigDecimal("price"),                // price
                rs.getInt("quantity"),                // quantityStore
                rs.getInt("quantity_stock"),
                rs.getInt("id_category"),          // category
                rs.getInt("id_company"),          // company
                rs.getString("img"),                // image
                rs.getString("size_screen"),          // sizeScreen
                rs.getString("operating_system"),     // operatingSystem
                rs.getInt("status"),               // status
                rs.getString("name_category"),              // supName
                rs.getString("name_company")              // supName
        );
    }

    // Lấy danh sách tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        String sql = "SELECT p.id_product, p.name, p.cpu, p.ram, p.rom, p.graphics_card, p.battery,\n" +
                "       p.weight, p.price, p.quantity, p.quantity_stock, p.id_category, p.id_company, p.img, p.size_screen,\n" +
                "       p.operating_system, p.status, c.name_category, s.name_company\n" +
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
    public ProductDTO getProductById(int idProduct) throws SQLException {
        String sql ="SELECT p.id_product, p.name, p.cpu, p.ram, p.rom, p.graphics_card, p.battery,\n" +
                "       p.weight, p.price, p.quantity, p.quantity_stock, p.id_category, p.id_company, p.img, p.size_screen,\n" +
                "       p.operating_system, p.status, c.name_category, s.name_company\n" +
                "FROM PRODUCT p JOIN CATEGORY c ON p.id_category = c.id_category JOIN COMPANY s ON p.id_company = s.id_company WHERE p.id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduct);
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

    // Thêm sản phẩm mới
    public boolean addProduct(ProductDTO product) throws SQLException {
        String sqlProduct = "INSERT INTO PRODUCT (name, cpu, ram, rom, " +
                "graphics_card, battery, weight, price, quantity, quantity_stock, id_category, id_company, img, size_screen, operating_system, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct)) {
                stmtProduct.setString(1, product.getName());
                stmtProduct.setString(2, product.getCpu());
                stmtProduct.setString(3, product.getRam());
                stmtProduct.setString(4, product.getRom());
                stmtProduct.setString(5, product.getGraphicsCard());
                stmtProduct.setString(6, product.getBattery());
                stmtProduct.setString(7, product.getWeight());
                stmtProduct.setBigDecimal(8, product.getPrice());
                stmtProduct.setInt(9, product.getQuantity());
                stmtProduct.setInt(10, product.getQuantityStock());
                stmtProduct.setInt(11, product.getIdCategory());
                stmtProduct.setInt(12, product.getIdCompany());
                stmtProduct.setString(13, product.getImage());
                stmtProduct.setString(14, product.getSizeScreen());
                stmtProduct.setString(15, product.getOperatingSystem());
                stmtProduct.setInt(16, product.getStatus());

                stmtProduct.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new SQLException("Lỗi khi thêm sản phẩm: " + ex.getMessage(), ex);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean editProduct(ProductDTO product) throws SQLException {
        String sql = "UPDATE PRODUCT SET name = ?, cpu = ?, ram = ?, rom = ?, graphics_card = ?, battery = ?," +
                " weight = ?, price = ?, id_category = ?, id_company = ?, img = ?, " +
                "size_screen = ?, operating_system = ? WHERE id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCpu());
            stmt.setString(3, product.getRam());
            stmt.setString(4, product.getRom());
            stmt.setString(5, product.getGraphicsCard());
            stmt.setString(6, product.getBattery());
            stmt.setString(7, product.getWeight());
            stmt.setBigDecimal(8, product.getPrice());
            stmt.setInt(9, product.getIdCategory());
            stmt.setInt(10, product.getIdCompany());
            stmt.setString(11, product.getImage());
            stmt.setString(12, product.getSizeScreen());
            stmt.setString(13, product.getOperatingSystem());
            stmt.setInt(14, product.getIdProduct());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi cập nhật sản phẩm: " + ex.getMessage(), ex);
        }
    }
    public boolean updateQuantityStock(int idProduct, int quantity) {
        String sql = "UPDATE PRODUCT SET quantity_stock = quantity_stock + ? WHERE id_product = ? AND status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, idProduct);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateQuantity(int idProduct, int quantity) {
        String sql = "UPDATE PRODUCT SET quantity = quantity + ? WHERE id_product = ? AND status = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, idProduct);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteProduct(int idProduct,int status) throws SQLException {
        String sql = "UPDATE PRODUCT SET status = ? WHERE id_product = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(2, idProduct);
            stmt.setInt(1, status);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất 1 bản ghi được cập nhật
        } catch (SQLException ex) {
            throw new SQLException("Lỗi khi xóa sản phẩm: " + ex.getMessage(), ex);
        }
    }
    public ArrayList<ProductDTO> searchProducts(String keyword, String status) throws SQLException {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        String sql = "SELECT p.id_product, p.name, p.cpu, p.ram, p.rom, p.graphics_card, p.battery,\n" +
                "       p.weight, p.price, p.quantity, p.quantity_stock, p.id_category, p.id_company, p.img, p.size_screen,\n" +
                "       p.operating_system, p.status, c.name_category, s.name_company\n" +
                "FROM PRODUCT p \n" +
                "JOIN CATEGORY c ON p.id_category = c.id_category \n" +
                "JOIN COMPANY s ON p.id_company = s.id_company \n" +
                "WHERE (p.name LIKE ? OR p.price LIKE ? OR c.name_category LIKE ? OR s.name_company LIKE ?) AND p.status = ?";
        int tt ;
        if(status.equals("Ngừng HĐ")) {
             tt = 0;
        }else{
             tt = 1;
        }
        if(status.equals("Cần nhập")){
            sql+=" AND p.quantity < 4";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);
            stmt.setInt(5, tt);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDTO product = createProductDTO(rs);
                    productList.add(product);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Lỗi khi tìm kiếm sản phẩm: " + ex.getMessage(), ex);
        }

        return productList;
    }

}