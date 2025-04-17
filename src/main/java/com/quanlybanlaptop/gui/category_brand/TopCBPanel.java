package com.quanlybanlaptop.gui.category_brand;

import com.quanlybanlaptop.bus.CategoryBUS;
import com.quanlybanlaptop.bus.CompanyBUS;
import com.quanlybanlaptop.dto.CategoryDTO;
import com.quanlybanlaptop.dto.CompanyDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TopCBPanel {
    private static JTextField idCategoryField;
    private static JTextField nameCategoryField;
    private static JTextField idCompanyField;
    private static JTextField nameCompanyField, addressCompanyField, contactCompanyField;

    public static JPanel createIpCategorydPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // Tạo JPanel con cho dòng ID
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        idPanel.setBackground(Color.WHITE);
        JLabel idCategoryLabel = new JLabel("Mã loại:");
        idCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idCategoryLabel.setPreferredSize(new Dimension(60, 35));
        idCategoryField = RoundedComponent.createRoundedTextField(10);
        idCategoryField.setPreferredSize(new Dimension(170, 35));
        idCategoryField.setEditable(false); // Cho phép chỉnh sửa
        idPanel.add(idCategoryLabel);
        idPanel.add(idCategoryField); // Sửa lỗi từ idCategory5

        // Tạo JPanel con cho dòng Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Tên loại:");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setPreferredSize(new Dimension(60, 35));
        nameCategoryField = RoundedComponent.createRoundedTextField(10);
        nameCategoryField.setPreferredSize(new Dimension(170, 35));
        nameCategoryField.setEditable(true); // Cho phép chỉnh sửa
        namePanel.add(nameLabel);
        namePanel.add(nameCategoryField);

        // Thêm các panel con vào panel chính
        formPanel.add(idPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(namePanel);

        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return formPanel;
    }

    public static JPanel createIpBrandPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2,2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // Tạo JPanel con cho dòng ID
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        idPanel.setBackground(Color.WHITE);
        JLabel idCategoryLabel = new JLabel("Mã hãng:");
        idCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idCategoryLabel.setPreferredSize(new Dimension(60, 35));
        idCompanyField = RoundedComponent.createRoundedTextField(10);
        idCompanyField.setPreferredSize(new Dimension(170, 35));
        idCompanyField.setEditable(false); // Cho phép chỉnh sửa
        idPanel.add(idCategoryLabel);
        idPanel.add(idCompanyField);
        idPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Tạo JPanel con cho dòng Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Tên hãng:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setPreferredSize(new Dimension(60, 35));
        nameCompanyField = RoundedComponent.createRoundedTextField(10);
        nameCompanyField.setPreferredSize(new Dimension(170, 35));
        nameCompanyField.setEditable(true); // Cho phép chỉnh sửa
        namePanel.add(nameLabel);
        namePanel.add(nameCompanyField);

        // Tạo JPanel con cho dòng Name
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        addressPanel.setBackground(Color.WHITE);
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addressLabel.setPreferredSize(new Dimension(60, 35));
        addressCompanyField = RoundedComponent.createRoundedTextField(10);
        addressCompanyField.setPreferredSize(new Dimension(170, 35));
        addressCompanyField.setEditable(true); // Cho phép chỉnh sửa
        addressPanel.add(addressLabel);
        addressPanel.add(addressCompanyField);
        addressPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));



        // Tạo JPanel con cho dòng Name
        JPanel contactPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        contactPanel.setBackground(Color.WHITE);
        JLabel contactLabel = new JLabel("Liên hệ:");
        contactLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contactLabel.setPreferredSize(new Dimension(60, 35));
        contactCompanyField = RoundedComponent.createRoundedTextField(10);
        contactCompanyField.setPreferredSize(new Dimension(170, 35));
        contactCompanyField.setEditable(true); // Cho phép chỉnh sửa
        contactPanel.add(contactLabel);
        contactPanel.add(contactCompanyField);

        // Thêm các panel con vào panel chính
        formPanel.add(idPanel);
        formPanel.add(namePanel);
        formPanel.add(addressPanel);
        formPanel.add(contactPanel);
//        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return formPanel;
    }

    public static JPanel createButtonPanel(String context, Object bus, JTable table, Runnable refreshCallback) {
        JPanel buttonControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        RoundedButton btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        RoundedButton btnDelete = new RoundedButton("Xóa", ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);

        btnAdd.addActionListener(e -> {
            if (context.equals("Category")) {
                handleAddCategory((CategoryBUS) bus);
            } else if (context.equals("Brand")) {
                handleAddBrand((CompanyBUS) bus);
            }
            refreshCallback.run();
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            System.out.println(selectedRow);
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (context.equals("Category")) {
                handleEditCategory((CategoryBUS) bus);
            } else if (context.equals("Brand")) {
                handleEditBrand((CompanyBUS) bus);
            }
            refreshCallback.run();
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (context.equals("Category")) {
                handleDeleteCategory((CategoryBUS) bus);
            } else if (context.equals("Brand")) {
                handleDeleteBrand((CompanyBUS) bus);
            }
            refreshCallback.run();
        });

        btnRefresh.addActionListener(e -> {
            if (context.equals("Category")) {
                idCategoryField.setText("");
                nameCategoryField.setText("");
            } else if (context.equals("Brand")) {
                idCompanyField.setText("");
                nameCompanyField.setText("");
                addressCompanyField.setText("");
                contactCompanyField.setText("");
            }
            table.clearSelection();
            refreshCallback.run();
        });

        buttonControlPanel.add(btnAdd);
        buttonControlPanel.add(btnEdit);
        buttonControlPanel.add(btnDelete);
        buttonControlPanel.add(btnRefresh);
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return buttonControlPanel;
    }

    private static void handleAddCategory(CategoryBUS categoryBUS) {
        String name = nameCategoryField.getText().trim();
        if (!idCategoryField.getText().trim().isEmpty() || nameCategoryField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ô Mã Loại phải để trống, ô tên không đưuọc để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        try {
            CategoryDTO category = new CategoryDTO();
            category.setCategoryName(name);
            boolean success = categoryBUS.addCategory(category);
            if (success) {
                JOptionPane.showMessageDialog(null, "Thêm danh mục thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                idCategoryField.setText(""); // Xóa trường sau khi thêm
                nameCategoryField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm danh mục thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void handleEditCategory(CategoryBUS categoryBUS) {
        if (idCategoryField.getText().trim().isEmpty() || nameCategoryField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ mã loại và tên loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idCategoryField.getText().trim());
        String name = nameCategoryField.getText().trim();

        try {
            CategoryDTO category = new CategoryDTO();
            category.setCategoryId(id);
            category.setCategoryName(name);
            boolean success = categoryBUS.updateCategory(category);
            if (success) {
                JOptionPane.showMessageDialog(null, "Sửa danh mục thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                idCategoryField.setText("");
                nameCategoryField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Sửa danh mục thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void handleDeleteCategory(CategoryBUS categoryBUS) {
        int id = Integer.parseInt(idCategoryField.getText().trim());
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa danh mục '" + id + "'?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = categoryBUS.deleteCategory(id);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Xóa danh mục thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    idCategoryField.setText(""); // Xóa trường sau khi xóa
                    nameCategoryField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa danh mục thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void handleAddBrand(CompanyBUS companyBUS) {
        if (!idCompanyField.getText().trim().isEmpty() || nameCompanyField.getText().trim().isEmpty() || addressCompanyField.getText().trim().isEmpty() || contactCompanyField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ và để trống mã hãng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = nameCompanyField.getText().trim();
        String address = addressCompanyField.getText().trim();
        String contact = contactCompanyField.getText().trim();


        try {
            CompanyDTO company = new CompanyDTO();
            company.setCompanyName(name);
            company.setCompanyAddress(address);
            company.setCompanyContact(contact);
            boolean success = companyBUS.addCompany(company);
            if (success) {
                JOptionPane.showMessageDialog(null, "Thêm hãng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                idCompanyField.setText(""); // Xóa trường sau khi thêm
                nameCompanyField.setText("");
                addressCompanyField.setText("");
                contactCompanyField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm hãng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void handleEditBrand(CompanyBUS companyBUS ) {
        int id = Integer.parseInt(idCompanyField.getText().trim());
        String name = nameCompanyField.getText().trim();
        String address = addressCompanyField.getText().trim();
        String contact = contactCompanyField.getText().trim();
        if (id < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ mã hãng và tên hãng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            CompanyDTO company = new CompanyDTO();
            company.setCompanyId(id);
            company.setCompanyName(name);
            company.setCompanyAddress(address);
            company.setCompanyContact(contact);
            boolean success = companyBUS.updateCompany(company);
            if (success) {
                JOptionPane.showMessageDialog(null, "Sửa hãng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Sửa hãng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void handleDeleteBrand(CompanyBUS companyBUS ) {
        int id = Integer.parseInt(idCompanyField.getText().trim()) ;
        System.out.println(id);
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa hãng '" + id + "'?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = companyBUS.deleteCompany(id);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Xóa hãng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    idCompanyField.setText(""); // Xóa trường sau khi xóa
                    nameCompanyField.setText("");
                    addressCompanyField.setText("");
                    contactCompanyField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa hãng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static JTextField getIdCategoryField() {
        return idCategoryField;
    }

    public static JTextField getNameCategoryField() {
        return nameCategoryField;
    }

    public static JTextField getIdCompanyField() {
        return idCompanyField;
    }

    public static JTextField getNameCompanyField() {
        return nameCompanyField;
    }
    public static JTextField getAddressCompanyField() {
        return addressCompanyField;
    }
    public static JTextField getContactCompanyField() {
        return contactCompanyField;
    }
}