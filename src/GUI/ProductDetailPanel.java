/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import DTO.ProductDTO;
//
//public class ProductDetailPanel extends JPanel {
//    private JLabel lblTitle;
//    private JLabel lblImage;
//    private JButton btnUpload;
//    private JTextField txtProductId, txtProductName, txtCPU, txtRAM, txtGraphicsCard, txtBattery, txtWeight, txtPrice, txtQuantity, txtStatus, txtCategory,txtIMei_No;
//    private JButton btnSave, btnCancel;
//    private String imagePath;
//
//    public ProductDetailPanel(ProductDTO product) {
//        setLayout(new BorderLayout(10, 10));
//
//        // TOP - Tiêu đề
//        lblTitle = new JLabel((product == null) ? "Thêm Sản Phẩm" : "Chi Tiết Sản Phẩm", SwingConstants.CENTER);
//        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
//        add(lblTitle, BorderLayout.NORTH);
//
//        // CENTER - Chia làm 2 phần: trái là ảnh, phải là thông tin sản phẩm
//        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
//
//        // **BÊN TRÁI: HIỂN THỊ ẢNH SẢN PHẨM**
//        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
//        lblImage = new JLabel();
//        lblImage.setHorizontalAlignment(JLabel.CENTER);
//        lblImage.setPreferredSize(new Dimension(200, 200));
//
//        // Nếu có sản phẩm, hiển thị ảnh từ đường dẫn
//        if (product != null && product.getImage() != null) {
//            ImageIcon imageIcon = new ImageIcon(new ImageIcon(product.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
//            lblImage.setIcon(imageIcon);
//            imagePath = product.getImage();
//        } else {
//            lblImage.setIcon(new ImageIcon("images/no-image.png")); // Ảnh mặc định
//            imagePath = "images/no-image.png";
//        }
//
//        btnUpload = new JButton("Upload Ảnh");
//        btnUpload.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
//                int result = fileChooser.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile();
//                    imagePath = file.getAbsolutePath();
//                    lblImage.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
//                }
//            }
//        });
//
//        leftPanel.add(lblImage, BorderLayout.CENTER);
//        leftPanel.add(btnUpload, BorderLayout.SOUTH);
//
//        // **BÊN PHẢI: HIỂN THỊ CÁC THUỘC TÍNH SẢN PHẨM**
//        JPanel rightPanel = new JPanel(new GridLayout(10, 2, 5, 5));
//
//        rightPanel.add(new JLabel("Mã Sản Phẩm:"));
//        txtProductId = new JTextField();
//        txtProductId.setText(product != null ? product.getProductId() : "");
//        rightPanel.add(txtProductId);
//
//        rightPanel.add(new JLabel("Tên Sản Phẩm:"));
//        txtProductName = new JTextField();
//        txtProductName.setText(product != null ? product.getProductName() : "");
//        rightPanel.add(txtProductName);
//
//        rightPanel.add(new JLabel("CPU:"));
//        txtCPU = new JTextField();
//        txtCPU.setText(product != null ? product.getCpu() : "");
//        rightPanel.add(txtCPU);
//
//        rightPanel.add(new JLabel("RAM:"));
//        txtRAM = new JTextField();
//        txtRAM.setText(product != null ? product.getRam() : "");
//        rightPanel.add(txtRAM);
//
//        rightPanel.add(new JLabel("Card Đồ Họa:"));
//        txtGraphicsCard = new JTextField();
//        txtGraphicsCard.setText(product != null ? product.getGraphicsCard() : "");
//        rightPanel.add(txtGraphicsCard);
//
//        rightPanel.add(new JLabel("Pin:"));
//        txtBattery = new JTextField();
//        txtBattery.setText(product != null ? product.getBattery() : "");
//        rightPanel.add(txtBattery);
//
//        rightPanel.add(new JLabel("Trọng Lượng:"));
//        txtWeight = new JTextField();
//        txtWeight.setText(product != null ? product.getWeight() : "");
//        rightPanel.add(txtWeight);
//
//        rightPanel.add(new JLabel("Giá:"));
//        txtPrice = new JTextField();
//        txtPrice.setText(product != null ? product.getPrice().toString() : "");
//        rightPanel.add(txtPrice);
//
//        rightPanel.add(new JLabel("Số Lượng:"));
//        txtQuantity = new JTextField();
//        txtQuantity.setText(product != null ? String.valueOf(product.getQuantity()) : "");
//        rightPanel.add(txtQuantity);
//
//        rightPanel.add(new JLabel("Danh Mục:"));
//        txtCategory = new JTextField();
//        txtCategory.setText(product != null ? product.getCategoryId() : "");
//        rightPanel.add(txtCategory);
//
//        centerPanel.add(leftPanel);
//        centerPanel.add(rightPanel);
//        add(centerPanel, BorderLayout.CENTER);
//
//        // BOTTOM - Nút Lưu / Hủy
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
//
//        btnSave = new JButton(product == null ? "Lưu" : "Xong");
//            btnSave.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (product != null) { // Nếu đang ở chế độ xem chi tiết
//                closePanel(); // Đóng JPanel
//            } else {
//                saveProduct(); // Nếu đang thêm mới, thì lưu sản phẩm
//                closePanel(); // Sau khi lưu xong cũng đóng JPanel
//            }
//        }
//        });
//
//
//
//
//        btnCancel = new JButton("Hủy");
//        btnCancel.addActionListener(e -> JOptionPane.showMessageDialog(null, "Hủy thao tác"));
//
//        bottomPanel.add(btnSave);
//        bottomPanel.add(btnCancel);
//
//        add(bottomPanel, BorderLayout.SOUTH);
//    }
//    private void closePanel() {
//        SwingUtilities.getWindowAncestor(ProductDetailPanel.this).dispose(); // Đóng JPanel
//    }
//    private void saveProduct() {
//        // Lấy dữ liệu từ các JTextField
//        String productId = txtProductId.getText();
//        String productName = txtProductName.getText();
//        String cpu = txtCPU.getText();
//        String ram = txtRAM.getText();
//        String graphicsCard = txtGraphicsCard.getText();
//        String battery = txtBattery.getText();
//        String weight = txtWeight.getText();
//        String price = txtPrice.getText();
//        String quantity = txtQuantity.getText();
//        String categoryId = txtCategory.getText();
//
//        // Kiểm tra rỗng
//        if (productId.isEmpty() || productName.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Hiển thị thông tin đã nhập (có thể thay bằng lưu vào CSDL)
//        JOptionPane.showMessageDialog(null, "Lưu thành công sản phẩm:\nMã: " + productId + "\nTên: " + productName);
//    }
//}
//
