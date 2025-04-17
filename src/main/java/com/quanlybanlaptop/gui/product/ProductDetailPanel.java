package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductDetailPanel {//panel bảng chi tiết sản phẩm

    public static void showProductDetailDialog(ProductDTO product) {
        // Tạo dialog
        JDialog detailDialog = new JDialog();
        detailDialog.setTitle("Chi tiết sản phẩm - " + product.getIdProduct());
        detailDialog.setModal(true);
        detailDialog.setSize(850, 520);
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
        mainPanel.setBackground(new Color(176, 213, 250));

        // Panel chứa ảnh và bảng
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Hiển thị hình ảnh sản phẩm
        JLabel imageLabel = new JLabel("Không có hình ảnh", SwingConstants.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(197, 221, 239));
        imageLabel.setPreferredSize(new Dimension(445, 300));

        // Sử dụng đường dẫn giả định cho hình ảnh
        String sampleImagePath = product.getImage();
        ImageIcon icon = ImageLoader.loadResourceImage(sampleImagePath);
        assert icon != null;
        Image img = icon.getImage().getScaledInstance(450, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));

        contentPanel.add(imageLabel, BorderLayout.WEST);

        // Dữ liệu bảng
        String[][] data = {
                {"Mã sản phẩm", String.valueOf(product.getIdProduct())},
                {"Tên sản phẩm", product.getName() != null ? product.getName() : "N/A"},
                {"Danh mục", product.getNameCategory() != null ? product.getNameCategory() : "N/A"},
                {"Hệ đều hành", product.getOperatingSystem() != null ? product.getOperatingSystem() : "N/A"},
                {"Chip xử lý", product.getCpu() != null ? product.getCpu() : "N/A"},
                {"Bộ nhớ RAM", product.getRam() != null ? product.getRam() : "N/A"},
                {"Bộ nhớ trong", product.getRom() != null ? product.getRom() : "N/A"},
                {"Card đồ họa", product.getGraphicsCard() != null ? product.getGraphicsCard() : "N/A"},
                {"Màn hình", product.getSizeScreen() != null ? product.getSizeScreen() : "N/A"},
                {"Dung lượng Pin", product.getBattery() != null ? product.getBattery() : "N/A"},
                {"Trọng lượng", product.getWeight() != null ? product.getWeight() : "N/A"},
                {"Giá bán", String.format("%.3f VNĐ", product.getPrice())},
                {"Số lượng ", String.valueOf(product.getQuantity())},
                {"Tên Hãng", product.getNameCompany() != null ? product.getNameCompany() : "N/A"},
                {"Trạng thái", product.getNameStatus(product.getStatus()) != null ? product.getNameStatus(product.getStatus()) : "N/A"},
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
        buttonPanel.setBackground(new Color(176, 213, 250));
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