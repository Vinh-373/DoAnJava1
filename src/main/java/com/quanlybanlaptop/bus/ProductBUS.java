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




    // Thêm sản phẩm mới
    public boolean addProduct(ProductDTO product) throws SQLException {

        return productDAO.addProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(ProductDTO product) throws SQLException {
        return productDAO.editProduct(product);
    }

    // Xóa sản phẩm
    public boolean setStatusProduct(int idProduct,int status) throws SQLException {
        if (idProduct == -1) {
            return false;
        }
        return productDAO.deleteProduct(idProduct,status);
    }

    // Tìm kiếm sản phẩm theo tên
    public ArrayList<ProductDTO> searchProductsByName(String name, int status) throws SQLException {
        // Logic nghiệp vụ: Kiểm tra dữ liệu đầu vào
        if (name == null) {
            throw new IllegalArgumentException("Tên tìm kiếm không được null!");
        }

        return productDAO.searchProducts(name,status);
    }

    // Lấy thông tin chi tiết của một sản phẩm theo ID
    public ProductDTO getProductById(int idProduct) throws SQLException {
        return productDAO.getProductById(idProduct);
    }

}