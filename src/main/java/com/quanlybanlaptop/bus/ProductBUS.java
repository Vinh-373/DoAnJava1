package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.ProductDAO;
import com.quanlybanlaptop.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBUS {
    private ProductDAO productDAO;

    public ProductBUS(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    // Lấy danh sách tất cả sản phẩm
    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }
    public ArrayList<ProductDTO> getActiveProducts() throws SQLException {
        ArrayList<ProductDTO> allProducts = getAllProducts();
        ArrayList<ProductDTO> activeProducts = new ArrayList<>();
        for (ProductDTO product : allProducts) {
            if (product.getStatus() == 1) {
                activeProducts.add(product);
            }
        }
        return activeProducts;
    }
    public ArrayList<ProductDTO> getInactiveProducts() throws SQLException {
        ArrayList<ProductDTO> allProducts = getAllProducts();
        ArrayList<ProductDTO> inactiveProducts = new ArrayList<>();
        for (ProductDTO product : allProducts) {
            if (product.getStatus()==0) {
                inactiveProducts.add(product);
            }
        }
        return inactiveProducts;
    }




//    // Thêm sản phẩm mới
//    public boolean addProduct(ProductDTO product) {
//        // Logic nghiệp vụ: Kiểm tra dữ liệu trước khi thêm
//        if (product.getIdProduct() == null || product.getIdProduct().trim().isEmpty()) {
//            throw new IllegalArgumentException("ID sản phẩm không được để trống!");
//        }
//        if (product.getName() == null || product.getName().trim().isEmpty()) {
//            throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
//        }
//        if (product.getPriceStore() == null || product.getPriceStore().compareTo(new java.math.BigDecimal(0)) <= 0) {
//            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0!");
//        }
//        if (product.getQuantityStore() < 0) {
//            throw new IllegalArgumentException("Số lượng tồn kho không được nhỏ hơn 0!");
//        }
//        if (product.getBrokenQuantityStore() < 0) {
//            throw new IllegalArgumentException("Số lượng hỏng không được nhỏ hơn 0!");
//        }
//
//        // Kiểm tra xem ID sản phẩm đã tồn tại chưa
//        ProductDTO existingProduct = getProductById(product.getIdProduct());
//        if (existingProduct != null) {
//            throw new IllegalArgumentException("ID sản phẩm đã tồn tại!");
//        }
//
//        return productDAO.addProduct(product);
//    }

    // Cập nhật thông tin sản phẩm
//    public boolean updateProduct(ProductDTO product) {
//        // Logic nghiệp vụ: Kiểm tra dữ liệu trước khi cập nhật
//        if (product.getIdProduct() == null || product.getIdProduct().trim().isEmpty()) {
//            throw new IllegalArgumentException("ID sản phẩm không được để trống!");
//        }
//        if (product.getName() == null || product.getName().trim().isEmpty()) {
//            throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
//        }
//        if (product.getPriceStore() == null || product.getPriceStore().compareTo(new java.math.BigDecimal(0)) <= 0) {
//            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0!");
//        }
//        if (product.getQuantityStore() < 0) {
//            throw new IllegalArgumentException("Số lượng tồn kho không được nhỏ hơn 0!");
//        }
//        if (product.getBrokenQuantityStore() < 0) {
//            throw new IllegalArgumentException("Số lượng hỏng không được nhỏ hơn 0!");
//        }
//
//        // Kiểm tra xem sản phẩm có tồn tại không
//        ProductDTO existingProduct = getProductById(product.getIdProduct());
//        if (existingProduct == null) {
//            throw new IllegalArgumentException("Sản phẩm không tồn tại!");
//        }
//
//        return productDAO.updateProduct(product);
//    }

    // Xóa sản phẩm
    public boolean deleteProduct(String idProduct) throws SQLException {
        if (idProduct == null || idProduct.trim().isEmpty()) {
            return false;
        }
        return productDAO.deleteProduct(idProduct);
    }

    // Tìm kiếm sản phẩm theo tên
//    public ArrayList<ProductDTO> searchProductsByName(String name) {
//        // Logic nghiệp vụ: Kiểm tra dữ liệu đầu vào
//        if (name == null) {
//            throw new IllegalArgumentException("Tên tìm kiếm không được null!");
//        }
//
//        return productDAO.searchProductsByName(name);
//    }

    // Lấy thông tin chi tiết của một sản phẩm theo ID
    public ProductDTO getProductById(String idProduct) throws SQLException {
        return productDAO.getProductById(idProduct);
    }

}