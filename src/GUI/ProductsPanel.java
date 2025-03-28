package GUI;
//
//import javax.swing.*;  // Thư viện giao diện Swing
//import java.awt.*;  // Xử lý đồ họa (Image, Component)
//import java.math.*;
//import java.util.ArrayList; // Sử dụng ArrayList để lưu danh sách sản phẩm
//import DAO.*; // Import lớp DAO để truy xuất CSDL
//import DTO.*; // Import lớp DTO chứa thông tin sản phẩm
//import java.sql.*;
//
//public class ProductsPanel extends BasePanel {
//    private ProductDAO productDAO;
//
//    public ProductsPanel() {
//        super();
//        initDAO();
//        initData();
//    }
//
//    // Khởi tạo DAO với kết nối từ CSDL
//    private void initDAO() {
//        Connection conn = ConnectSQLServer.getConnection();
//        if (conn != null) {
//            productDAO = new ProductDAO(conn);
//        } else {
//            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//  private void initData() {
//    String[] columnNames = {
//        "Mã SP","Tên SP", "CPU", "RAM", "Card đồ họa", "Giá", "Số lượng",
//    };
//
//    tableModel.setColumnIdentifiers(columnNames);
//
//
//
////    // Áp dụng Renderer cho cột hình ảnh
////    dataTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
//
//      if (productDAO != null) {
//          try {
//              ArrayList<ProductDTO> products = productDAO.getAllProducts();
//              for (ProductDTO product : products) {
//                  addProduct(product);
//              }
//          } catch (SQLException e) { // Bắt lỗi truy vấn SQL
//              e.printStackTrace();
//              JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm từ CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//          }
//      }
//
//  }
//
//
//
//   // Thêm một sản phẩm vào bảng
//private void addProduct(ProductDTO product) {
////    ImageIcon image = null;
////    String imagePath = product.getImage(); // Đường dẫn ảnh từ CSDL
////
////    if (imagePath != null && !imagePath.isEmpty()) {
////        ImageIcon originalIcon = new ImageIcon(imagePath);
////        if (originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0) {
////            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
////            image = new ImageIcon(scaledImage);
////        }
////    }
//
//    Object[] row = {
//        product.getProductId() ,// Mã sản phẩm đặt cuối cùng
//        product.getProductName(),
//        product.getCpu(),
//        product.getRam(),
//        product.getGraphicsCard(),
//        product.getPrice(),
//        product.getQuantity(),
//
//    };
//
//    tableModel.addRow(row);
//}
//
//
//
//
//    @Override
//    protected void addAction() {
//    // Tạo cửa sổ mới để nhập sản phẩm
//    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Thêm Sản Phẩm", true);
//    ProductDetailPanel productDetailPanel = new ProductDetailPanel(null); // null vì đang thêm mới
//    dialog.add(productDetailPanel);
//
//    dialog.setSize(600, 500);
//    dialog.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
//    dialog.setVisible(true);
//}
//
//
//    @Override
//    protected void editAction() {
//        JOptionPane.showMessageDialog(this, "Chỉnh sửa sản phẩm");
//    }
//
//    @Override
//    protected void deleteAction() {
//        int selectedRow = dataTable.getSelectedRow();
//        if (selectedRow >= 0) {
//            tableModel.removeRow(selectedRow);
//        } else {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!");
//        }
//    }
//
//    @Override
//    protected void refreshAction() {
//        tableModel.setRowCount(0); // Xóa tất cả dữ liệu cũ
//        initData(); // Load lại dữ liệu mới từ CSDL
//    }
//
//    @Override
//    protected void detailAction() {
//        int selectedRow = dataTable.getSelectedRow();
//        if (selectedRow >= 0) {
//            String productId = tableModel.getValueAt(selectedRow, 0).toString();
//
//            // Truy vấn sản phẩm từ CSDL theo productId
//            ProductDTO product = productDAO.getProductById(productId);
//
//            if (product != null) {
//                // Mở giao diện chi tiết sản phẩm
//                JFrame frame = new JFrame("Chi Tiết Sản Phẩm");
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.setSize(600, 500);
//                frame.setLocationRelativeTo(null);
//                frame.add(new ProductDetailPanel(product));
//                frame.setVisible(true);
//            } else {
//                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//        }
//    }
//
//
//
//    @Override
//    protected void importExcel() {
//        JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ Excel");
//    }
//
//    @Override
//    protected void exportExcel() {
//        JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra Excel");
//    }
//}
