package GUI.product;

import DTO.ProductDTO;
import GUI.component.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class ProductDetailPanel {

    public static void showProductDetailDialog(ProductDTO product) {
        // Tạo dialog
        JDialog detailDialog = new JDialog();
        detailDialog.setTitle("Chi tiết sản phẩm - " + product.getIdProduct());
        detailDialog.setModal(true);
        detailDialog.setSize(700, 550);
        detailDialog.setLayout(new BorderLayout());
        detailDialog.setLocationRelativeTo(null);
        detailDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Tạo panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        headerPanel.setBackground(new Color(56, 136, 214));
        headerPanel.setPreferredSize(new Dimension(0, 50));

        JLabel titleLabel = new JLabel("BẢNG CHI TIẾT SẢN PHẨM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel);

        // Tạo panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Panel chứa ảnh và bảng
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Hiển thị hình ảnh sản phẩm
        JLabel imageLabel = new JLabel("Không có hình ảnh", SwingConstants.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(56, 136, 214));
        imageLabel.setPreferredSize(new Dimension(300, 300));

        // Sử dụng đường dẫn giả định cho hình ảnh
        String sampleImagePath = "src/images/pdf.png";
        ImageIcon icon = new ImageIcon(sampleImagePath);
        Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));

        contentPanel.add(imageLabel, BorderLayout.WEST);

        // Dữ liệu bảng
        String[][] data = {
                {"Mã sản phẩm", product.getIdProduct() != null ? product.getIdProduct() : "N/A"},
                {"Tên sản phẩm", product.getName() != null ? product.getName() : "N/A"},
                {"Hãng", product.getOrigin() != null ? product.getOrigin() : "N/A"},
                {"Danh mục", product.getCategory() != null ? product.getCategory() : "N/A"},
                {"Xuất xứ", product.getOrigin() != null ? product.getOrigin() : "N/A"},
                {"CPU", product.getCpu() != null ? product.getCpu() : "N/A"},
                {"RAM", product.getRam() != null ? product.getRam() : "N/A"},
                {"Bộ nhớ trong", product.getRom() != null ? product.getRom() : "N/A"},
                {"Card đồ họa", product.getGraphicsCard() != null ? product.getGraphicsCard() : "N/A"},
                {"Pin", product.getBattery() != null ? product.getBattery() : "N/A"},
                {"Trọng lượng", product.getWeight() != null ? product.getWeight() : "N/A"},
                {"Giá", product.getPriceStore() != null ? product.getPriceStore().toString() + " VNĐ" : "Không có giá"},
                {"Số lượng trong kho", String.valueOf(product.getQuantityStore())},
                {"Số lượng hỏng", String.valueOf(product.getBrokenQuantityStore())}

        };

        // Tạo model và bảng
        String[] columnNames = {"Thuộc tính", "Giá trị"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        RoundedTable detailTable = new RoundedTable(tableModel);
        detailTable.setColumnWidths(50, 0);
        detailTable.setFocusable(false); // Không cho focus
        detailTable.setRequestFocusEnabled(false); // Không nhận focus từ bàn phím
        detailTable.setRowSelectionAllowed(false); // Ngăn chọn dòng
        detailTable.setCellSelectionEnabled(false); // Ngăn chọn ô
        detailTable.setDefaultEditor(Object.class, null); // Tắt focus trên từng ô

        JScrollPane scrollPane = new JScrollPane(detailTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Thêm nút đóng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        RoundedButton closeButton = new RoundedButton("Đóng");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.addActionListener(e -> detailDialog.dispose());
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm các panel vào dialog
        detailDialog.add(headerPanel, BorderLayout.NORTH);
        detailDialog.add(mainPanel, BorderLayout.CENTER);
        detailDialog.setVisible(true);
    }
}