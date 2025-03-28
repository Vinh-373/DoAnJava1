package GUI.product;

import DTO.ProductDTO;
import GUI.component.*;
import BLL.ProductBLL;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ButtonPanel {
    private static RoundedButton btnDelete;

    public static JPanel createButtonPanel(ProductBLL productBLL) {
        JPanel buttonControlPanel = new JPanel(new GridBagLayout());
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        RoundedButton btnAdd = new RoundedButton("Thêm", new ImageIcon("src/images/add_control.png"));
        RoundedButton btnEdit = new RoundedButton("Sửa", new ImageIcon("src/images/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        btnDelete = new RoundedButton("Xóa", new ImageIcon("src/images/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnSeeDetail = new RoundedButton("Chi tiết", new ImageIcon("src/images/detail_control.png"));
        btnSeeDetail.setImageSize(32, 32);
        RoundedButton btnSeeImeis = new RoundedButton("Ds Seri", new ImageIcon("src/images/serinumber.png"));
        btnSeeImeis.setImageSize(32, 32);
        RoundedButton btnImportEx = new RoundedButton("Xuất PDF", new ImageIcon("src/images/pdf.png"));
        btnImportEx.setImageSize(32, 32);
        RoundedButton btnExportEx = new RoundedButton("Xuất Excel", new ImageIcon("src/images/export_control.png"));
        btnExportEx.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", new ImageIcon("src/images/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);
        btnRefresh.addActionListener(e -> ProductTable.loadProductData(productBLL));

        String[] companyList = {"Tất cả", "Lenovo", "Asus", "Dell"};
        JComboBox<String> companycb = RoundedComponent.createRoundedComboBox(companyList, 10);
        companycb.setPreferredSize(new Dimension(100, 35));

        JTextField tfName = RoundedComponent.createRoundedTextField(10);
        tfName.setPreferredSize(new Dimension(200, 35));

        // Sự kiện cho nút Xóa
        btnDelete.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            System.out.println("Selected row: " + selectedRow); // Debug để kiểm tra
            if (selectedRow >= 0) {
                String idProduct = (String) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct); // Debug để kiểm tra
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc muốn xóa sản phẩm ID " + idProduct + " khỏi cửa hàng?",
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                System.out.println("Confirm result: " + confirm); // Debug để kiểm tra
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = productBLL.deleteProduct(idProduct);
                        if (success) {
                            ProductTable.loadProductData(productBLL);
                            JOptionPane.showMessageDialog(null, "Đã xóa sản phẩm khỏi cửa hàng thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa sản phẩm thất bại: Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        //Sự kiện nút xem chi tiết
        btnSeeDetail.addActionListener(e -> {
            int selectedRow = ProductTable.getSelectedRow();
            if (selectedRow >= 0) {
                String idProduct = (String) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                System.out.println("ID Product: " + idProduct);
                try {
                    ProductDTO product = productBLL.getProductById(idProduct);
                    if (product != null) {
                        ProductDetailPanel.showProductDetailDialog(product);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) { // Dòng 82: Lỗi ở đây
                    JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xem chi tiết!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; buttonControlPanel.add(btnAdd, gbc);
        gbc.gridx = 1; buttonControlPanel.add(btnEdit, gbc);
        gbc.gridx = 2; buttonControlPanel.add(btnDelete, gbc);
        gbc.gridx = 3; buttonControlPanel.add(btnSeeDetail, gbc);
        gbc.gridx = 4; buttonControlPanel.add(btnSeeImeis, gbc);
        gbc.gridx = 5; buttonControlPanel.add(btnImportEx, gbc);
        gbc.gridx = 6; buttonControlPanel.add(btnExportEx, gbc);
        gbc.gridx = 7; buttonControlPanel.add(btnRefresh, gbc);
        gbc.gridx = 8; gbc.gridwidth = 2; buttonControlPanel.add(companycb, gbc);
        gbc.gridx = 10; gbc.gridwidth = 4; buttonControlPanel.add(tfName, gbc);

        return buttonControlPanel;
    }
}