package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.gui.component.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddProductPanel {//Panel thêm sp
    private static JTextField txtTenSanPham, txtChipXuLy, txtDungLuongPin, txtTrongLuong, txtCardDoHoa, txtGia;
    private static JComboBox<String> cmbXuatXu, cmbHang, cmbHeDieuHanh, cmbRam, cmbBoNhoTrong;

    public static void showAddProduct() {
        JDialog addDialog = new JDialog();
        addDialog.setTitle("Thêm sản phẩm");
        addDialog.setModal(true);
        addDialog.setSize(800, 500);
        addDialog.setLayout(new BorderLayout());
        addDialog.setLocationRelativeTo(null);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(56, 136, 214));
        JLabel headerLabel = new JLabel("THÊM SẢN PHẨM");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        addDialog.add(headerPanel, BorderLayout.NORTH);

        JPanel imgPanel = new JPanel();
        imgPanel.setPreferredSize(new Dimension(350, 0));
        imgPanel.setLayout(new BorderLayout());
        JLabel imgLabel = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imgLabel.setPreferredSize(new Dimension(200, 200));
        RoundedButton btnChooseImage = new RoundedButton("Chọn ảnh");
        btnChooseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
                imgLabel.setText("");
            }
        });
        imgPanel.add(imgLabel, BorderLayout.CENTER);
        imgPanel.add(btnChooseImage, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        txtTenSanPham = createLabelInput("Tên sản phẩm", formPanel);
        cmbXuatXu = createLabelCombobox("Xuất xứ", new String[]{"Trung Quốc", "Việt Nam", "Nhật Bản", "Mỹ", "Đài Loan", "Hàn Quốc"}, formPanel);
        txtChipXuLy = createLabelInput("Chip xử lý", formPanel);
        txtDungLuongPin = createLabelInput("Dung lượng pin", formPanel);
        txtTrongLuong = createLabelInput("Trọng lượng", formPanel);
        cmbHang = createLabelCombobox("Hãng", new String[]{"Apple", "Dell", "HP", "Lenovo", "Huawei", "Xiaomi", "Asus", "Acer", "Samsung", "LG", "Sony"}, formPanel);
        cmbHeDieuHanh = createLabelCombobox("Hệ điều hành", new String[]{"Windows", "macOS", "Linux", "Chrome OS"}, formPanel);
        cmbRam = createLabelCombobox("RAM", new String[]{"4GB", "8GB", "16GB", "32GB", "64GB"}, formPanel);
        cmbBoNhoTrong = createLabelCombobox("Bộ nhớ trong", new String[]{"64GB", "128GB", "256GB", "512GB", "1TB"}, formPanel);
        txtCardDoHoa = createLabelInput("Card đồ họa", formPanel);
        txtGia = createLabelInput("Giá", formPanel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(imgPanel, BorderLayout.WEST);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        addDialog.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        RoundedButton btnSave = new RoundedButton("Thêm sản phẩm");
        RoundedButton btnCancel = new RoundedButton("Hủy bỏ");
        btnSave.setBackground(Color.BLUE);
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(Color.RED);
        btnCancel.setForeground(Color.WHITE);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> addDialog.dispose());

//        btnSave.addActionListener(e -> {
//            try {
//                // ID sản phẩm tự tăng -> Không cần nhập
//                String idProduct = null;
//                String name = txtTenSanPham.getText();
//                String cpu = txtChipXuLy.getText();
//                String ram = cmbRam.getSelectedItem().toString();
//                String rom = cmbBoNhoTrong.getSelectedItem().toString();
//                String graphicsCard = txtCardDoHoa.getText();
//                String battery = txtDungLuongPin.getText();
//                String weight = txtTrongLuong.getText();
//                float price = Float.parseFloat(txtGia.getText().trim());
//                int quantity = 0; // Mặc định số lượng ban đầu là 0
//                boolean status = true; // Giả sử mặc định sản phẩm còn hàng
//                int brokenQuantity = 0; // Mặc định số lượng lỗi là 0
//                String category = cmbHang.getSelectedItem().toString();
//                String image = imgLabel.getText(); // Nếu có đường dẫn ảnh thì thay thế
//                String operatingSystem = cmbHeDieuHanh.getSelectedItem().toString();
//                String origin = cmbXuatXu.getSelectedItem().toString();
//
//                // Tạo ProductDTO từ dữ liệu form
//                ProductDTO productInput = new ProductDTO(
//                        idProduct, name, cpu, ram, rom, graphicsCard, battery, weight,
//                        price, quantity, status, brokenQuantity, category, image, operatingSystem, origin
//                );
//                ProductDAO productDAO = new ProductDAO();
//                addDialog.dispose();
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(addDialog, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        });
        addDialog.setVisible(true);
    }

    private static JTextField createLabelInput(String labelTxt, JPanel panel) {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelTxt);
        JTextField textField = RoundedComponent.createRoundedTextField(10);
        textField.setPreferredSize(new Dimension(0, 40));
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);
        panel.add(inputPanel);
        return textField;
    }

    private static JComboBox<String> createLabelCombobox(String labelTxt, String[] items, JPanel panel) {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelTxt);
        JComboBox<String> comboBox = RoundedComponent.createRoundedComboBox(items, 5);
        comboBox.setPreferredSize(new Dimension(0, 40));
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(comboBox, BorderLayout.CENTER);
        panel.add(inputPanel);
        return comboBox;
    }
}
