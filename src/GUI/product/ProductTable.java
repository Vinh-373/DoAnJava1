package GUI.product;

import GUI.component.RoundedButton;
import GUI.component.RoundedTable;
import BLL.ProductBLL;
import DTO.ProductDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ProductTable {
    private static DefaultTableModel tableModel;
    private static RoundedTable productTable;

    public static JScrollPane createProductTable(ProductBLL productBLL) {
        String[] columnNames = {"ID", "NAME", "STORE QTY", "CATEGORY", "ORIGIN", "SELLING PRICE"};
        tableModel = new DefaultTableModel(columnNames, 0);

        productTable = new RoundedTable(tableModel);
        productTable.setColumnWidths(10, 180, 70, 50, 70, 100);
        productTable.setFocusable(false); // Không cho focus
        productTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        productTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô
        JScrollPane scrollPane = new JScrollPane(productTable);

        loadProductData(productBLL); // Tải dữ liệu ban đầu
        return scrollPane;
    }

    public static void loadProductData(ProductBLL productBLL) {
        tableModel.setRowCount(0);
        try {
            ArrayList<ProductDTO> products = productBLL.getAllProducts();
            if (products != null) {
                for (ProductDTO product : products) {
                    Object[] rowData = {
                            product.getIdProduct(),
                            product.getName(),
                            product.getQuantityStore(),
                            product.getCategory(),
                            product.getOrigin(),
                            product.getPriceStore()
                    };
                    if(product.getStatusStore() == true){
                        tableModel.addRow(rowData);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy chỉ số hàng được chọn
    public static int getSelectedRow() {
        return productTable.getSelectedRow();
    }

    // Lấy table model để truy cập dữ liệu
    public static DefaultTableModel getTableModel() {
        return tableModel;
    }
}