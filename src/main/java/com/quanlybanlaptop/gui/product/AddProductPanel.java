package com.quanlybanlaptop.gui.product;


import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.dto.CategoryDTO;
import com.quanlybanlaptop.dto.CompanyDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class AddProductPanel {//Panel thêm sp
    public static JTextField txtTenSanPham, txtChipXuLy, txtDungLuongPin, txtTrongLuong, txtCardDoHoa, txtGia, txtScreen;
    public static JComboBox<ComboItem> cmbLoai, cmbHang, cmbHeDieuHanh, cmbRam, cmbBoNhoTrong;
    public static RoundedButton btnSave, btnEdit, btnCancel;
    public static String fileName ;


    public static void showAddProduct(ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS, String chucNang) throws SQLException {
        JDialog addDialog = new JDialog();
        addDialog.setTitle(chucNang.equals("Thêm") ? "Thêm sản phẩm" : "Sửa sản phẩm");
        addDialog.setModal(true);
        addDialog.setSize(880, 500);
        addDialog.setLayout(new BorderLayout());
        addDialog.setLocationRelativeTo(null);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(56, 136, 214));
        JLabel headerLabel = new JLabel(chucNang.equals("Thêm") ? "THÊM SẢN PHẨM" : "SỬA SẢN PHẨM");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        addDialog.add(headerPanel, BorderLayout.NORTH);

        JPanel imgPanel = new JPanel();
        imgPanel.setPreferredSize(new Dimension(450, 0));
        imgPanel.setLayout(new BorderLayout());
        JLabel imgLabel = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imgLabel.setPreferredSize(new Dimension(445, 300));
        RoundedButton btnChooseImage = new RoundedButton("Chọn ảnh");
        btnChooseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("D:/Doanjava/QuanLyBanLaptop/src/main/resources/img"));
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(450, 300, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
                imgLabel.setText("");
                fileName = selectedFile.getName();
            }
        });
        imgPanel.add(imgLabel, BorderLayout.CENTER);
        imgPanel.add(btnChooseImage, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        txtTenSanPham = createLabelInput("Tên sản phẩm", formPanel);
        ArrayList<CategoryDTO> categories = categoryBUS.getAllCategory();
        List<ComboItem> categoryItems = new ArrayList<>();
        for (CategoryDTO ctg : categories) {
            categoryItems.add(new ComboItem(ctg.getCategoryId(), ctg.getCategoryName()));
        }
        cmbLoai = createLabelCombobox("Loại", categoryItems, formPanel);
        txtChipXuLy = createLabelInput("Chip xử lý", formPanel);
        txtDungLuongPin = createLabelInput("Dung lượng pin", formPanel);
        txtTrongLuong = createLabelInput("Trọng lượng", formPanel);
        ArrayList<CompanyDTO> companies = CompanyBUS.getAllCompany();
        List<ComboItem> companyItem = new ArrayList<>();
        for (CompanyDTO cpn : companies) {
            companyItem.add(new ComboItem(cpn.getCompanyId(), cpn.getCompanyName()));
        }
        cmbHang = createLabelCombobox("Hãng", companyItem, formPanel);
        List<ComboItem> HDHItem = new ArrayList<>();
        Collections.addAll(HDHItem,
                new ComboItem("Windows"),
                new ComboItem("MacOS"),
                new ComboItem("ChromeOS"),
                new ComboItem("Linux"),
                new ComboItem("Android"),
                new ComboItem("iOS")
        );
        cmbHeDieuHanh = createLabelCombobox("Hệ điều hành", HDHItem, formPanel);
        List<ComboItem> RamItem = new ArrayList<>();
        Collections.addAll(RamItem,
                new ComboItem("4GB"),
                new ComboItem("8GB"),
                new ComboItem("16GB"),
                new ComboItem("32GB"),
                new ComboItem("64GB"),
                new ComboItem("128GB")
        );
        cmbRam = createLabelCombobox("RAM", RamItem, formPanel);
        List<ComboItem> RomItem = new ArrayList<>();
        Collections.addAll(RomItem,
                new ComboItem("64GB"),
                new ComboItem("128GB"),
                new ComboItem("256GB"),
                new ComboItem("512GB"),
                new ComboItem("1TB")
        );
        cmbBoNhoTrong = createLabelCombobox("Bộ nhớ trong", RomItem, formPanel);
        txtCardDoHoa = createLabelInput("Card đồ họa", formPanel);
        txtScreen = createLabelInput("Màn hình", formPanel);
        txtGia = createLabelInput("Giá", formPanel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        contentPanel.add(imgPanel, BorderLayout.WEST);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        addDialog.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnSave = null; // Đặt lại giá trị ban đầu để tránh lỗi
        btnEdit = null;

        if (chucNang.equals("Thêm")) {
            btnSave = new RoundedButton("Thêm sản phẩm");
            btnSave.setPreferredSize(new Dimension(140, 30));
            btnSave.setBackground(new Color(153, 194, 237));
            buttonPanel.add(btnSave);
        } else {
            btnEdit = new RoundedButton("Sửa sản phẩm");
            btnEdit.setPreferredSize(new Dimension(140, 30));
            btnEdit.setBackground(new Color(239, 233, 161));
            buttonPanel.add(btnEdit);
        }

        btnCancel = new RoundedButton("Hủy bỏ");
        btnCancel.setBackground(Color.RED);
        btnCancel.setPreferredSize(new Dimension(60, 30));
        buttonPanel.add(btnCancel);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> addDialog.dispose());

        // Chỉ thêm ActionListener cho nút tồn tại
        if (btnSave != null) {
            btnSave.addActionListener(e -> {
                try {
                    int idProduct = -1;
                    String name = txtTenSanPham.getText();
                    String cpu = txtChipXuLy.getText();
                    String ram = cmbRam.getSelectedItem().toString();
                    String rom = cmbBoNhoTrong.getSelectedItem().toString();
                    String graphicsCard = txtCardDoHoa.getText();
                    String battery = txtDungLuongPin.getText();
                    String weight = txtTrongLuong.getText();
                    BigDecimal price = new BigDecimal(txtGia.getText().trim());
                    int quantity = 0;
                    int status = 1;
                    int quantityStock = 0;
                    String nameCompany = cmbHang.getSelectedItem().toString();
                    String operatingSystem = cmbHeDieuHanh.getSelectedItem().toString();
                    String nameCategory = cmbLoai.getSelectedItem().toString();
                    int idCategory = ((ComboItem) cmbLoai.getSelectedItem()).getId();
                    int idCompany = ((ComboItem) cmbHang.getSelectedItem()).getId();
                    String sizeScreen = txtScreen.getText();
                    String image = fileName.trim();
                    ProductDTO productInput = new ProductDTO(
                            idProduct, name, cpu, ram, rom, graphicsCard, battery, weight,
                            price, quantity, quantityStock, idCategory, idCompany, image, sizeScreen, operatingSystem, status, nameCategory, nameCompany
                    );
                    boolean success = productBUS.addProduct(productInput);
                    if (success) {
                        ProductTable.loadProductData(productBUS,1,null);
                        JOptionPane.showMessageDialog(null, "Đã thêm sản phẩm thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại!");
                    }
                    addDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addDialog, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (btnEdit != null) {
            btnEdit.addActionListener(e -> {
                int selectedRow = ProductTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int idProduct = (int) ProductTable.getTableModel().getValueAt(selectedRow, 0);
                    try {
                        ProductDTO productDTO = productBUS.getProductById(idProduct);
                        if (productDTO != null) {
                            productDTO.setName(txtTenSanPham.getText());
                            productDTO.setCpu(txtChipXuLy.getText());
                            productDTO.setRam(cmbRam.getSelectedItem().toString());
                            productDTO.setRom(cmbBoNhoTrong.getSelectedItem().toString());
                            productDTO.setBattery(txtDungLuongPin.getText());
                            productDTO.setWeight(txtTrongLuong.getText());
                            productDTO.setGraphicsCard(txtCardDoHoa.getText());
                            productDTO.setOperatingSystem(cmbHeDieuHanh.getSelectedItem().toString());
                            productDTO.setSizeScreen(txtScreen.getText());
                            productDTO.setPrice(new BigDecimal(txtGia.getText().trim()));
                            productDTO.setIdCategory(((ComboItem) cmbLoai.getSelectedItem()).getId());
                            productDTO.setNameCategory(((ComboItem) cmbLoai.getSelectedItem()).toString());
                            productDTO.setIdCompany(((ComboItem) cmbHang.getSelectedItem()).getId());
                            productDTO.setNameCompany(((ComboItem) cmbHang.getSelectedItem()).toString());
                            productDTO.setImage(fileName.trim());

                            boolean updated = productBUS.updateProduct(productDTO);
                            if (updated) {

                                ProductTable.loadProductData(productBUS,1,null);
                                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                                addDialog.dispose(); // <- đây là đoạn đóng dialog
                            } else {
                                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        }
        if(chucNang.equals("Sửa")) {
            showInfo(productBUS, addDialog);
        }
        addDialog.setVisible(true);
    }

    public static void showInfo(ProductBUS productBUS, JDialog addDialog) {
        int selectedRow = ProductTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }

        int idProduct = Integer.parseInt(ProductTable.getTableModel().getValueAt(selectedRow, 0).toString());
        System.out.println("ID sản phẩm cần sửa: " + idProduct);

        try {
            ProductDTO productDTO = productBUS.getProductById(idProduct);
            System.out.println(productDTO.toString());
            txtTenSanPham.setText(productDTO.getName());
            txtChipXuLy.setText(productDTO.getCpu());
            txtCardDoHoa.setText(productDTO.getGraphicsCard());
            txtDungLuongPin.setText(productDTO.getBattery());
            txtTrongLuong.setText(productDTO.getWeight());
            txtGia.setText(productDTO.getPrice().toString());
            txtScreen.setText(productDTO.getSizeScreen());
            selectComboByName(cmbRam,productDTO.getRam());
            selectComboByName(cmbBoNhoTrong,productDTO.getRom());
            selectComboByName(cmbLoai,productDTO.getNameCategory());
            selectComboByName(cmbHeDieuHanh,productDTO.getOperatingSystem());
            selectComboByName(cmbHang,productDTO.getNameCompany());

            // Gán ảnh cũ
            fileName = productDTO.getImage().replace("/img/", "");
            ImageIcon icon = new ImageIcon("src/main/resources/img/" + fileName);
            Image scaled = icon.getImage().getScaledInstance(450, 300, Image.SCALE_SMOOTH);
            JLabel imgLabel = (JLabel)((JPanel)((JPanel)addDialog.getContentPane().getComponent(1)).getComponent(0)).getComponent(0);
            imgLabel.setIcon(new ImageIcon(scaled));
            imgLabel.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void selectComboByName(JComboBox<ComboItem> comboBox, String name) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            ComboItem item = comboBox.getItemAt(i);
            if (item.toString().equals(name)) {
                comboBox.setSelectedItem(item);
                break;
            }
        }
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

    private static JComboBox<ComboItem> createLabelCombobox(String labelTxt, List<ComboItem> items, JPanel panel) {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelTxt);
        JComboBox<ComboItem> comboBox = new JComboBox<>();

        for (ComboItem item : items) {
            comboBox.addItem(item);
        }

        comboBox.setPreferredSize(new Dimension(0, 40));
        inputPanel.add(label, BorderLayout.NORTH);
        inputPanel.add(comboBox, BorderLayout.CENTER);
        panel.add(inputPanel);

        return comboBox;
    }

}
