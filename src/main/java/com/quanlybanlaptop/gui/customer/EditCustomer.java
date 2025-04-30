package com.quanlybanlaptop.gui.customer;

import com.quanlybanlaptop.bus.CustomerBUS;
import com.quanlybanlaptop.dto.CustomerDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class EditCustomer extends JFrame {
    private JTextField nameField;
    private JFormattedTextField birthdateField;
    private JTextField phoneField;
    private JTextField citizenIdField;
    private JComboBox<String> genderComboBox;
    private JButton saveButton;

    public EditCustomer(CustomerDTO customer) {
        setTitle("Sửa Thông Tin Khách Hàng");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(customer.getName(), 20);
        add(nameField, gbc);

        // Ngày sinh
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Ngày sinh (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        birthdateField = new JFormattedTextField(dateFormat);
        if (customer.getBirthdate() != null) {
            birthdateField.setText(dateFormat.format(customer.getBirthdate()));
        } else {
            birthdateField.setText(""); // hoặc để trống nếu chưa có ngày sinh
        }
        birthdateField.setColumns(20);
        add(birthdateField, gbc);

        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(customer.getPhone(), 20);
        add(phoneField, gbc);

        // CCCD
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("CCCD:"), gbc);
        gbc.gridx = 1;
        citizenIdField = new JTextField(customer.getCitizenId(), 20);
        add(citizenIdField, gbc);

        // Giới tính
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        genderComboBox = new JComboBox<>(new String[] { "Nam", "Nữ" });
        genderComboBox.setSelectedItem(customer.getGender());
        add(genderComboBox, gbc);

        // Nút Lưu
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Lưu");
        add(saveButton, gbc);

        // Sự kiện lưu
        saveButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String birthdateStr = birthdateField.getText().trim();
            String phone = phoneField.getText().trim();
            String citizenId = citizenIdField.getText().trim();
            String gender = (String) genderComboBox.getSelectedItem();

            // Kiểm tra dữ liệu đầu vào
            if (name.isEmpty() || birthdateStr.isEmpty() || phone.isEmpty() || citizenId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
                return;
            }

            // Kiểm tra số điện thoại
            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.");
                return;
            }

            // Kiểm tra CCCD
            if (!citizenId.matches("\\d{9,12}")) {
                JOptionPane.showMessageDialog(this, "CCCD không hợp lệ.");
                return;
            }

            // Chuyển đổi ngày sinh
            java.util.Date birthdate;
            try {
                birthdate = dateFormat.parse(birthdateStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ.");
                return;
            }

            // Cập nhật thông tin khách hàng
            customer.setName(name);
            customer.setBirthdate(new java.sql.Date(birthdate.getTime()));
            customer.setGender(gender);
            customer.setPhone(phone);
            customer.setCitizenId(citizenId);


            // Gọi BUS để cập nhật
            CustomerBUS customerBUS = new CustomerBUS();
            boolean success = customerBUS.updateCustomer(customer);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại.");
            }
        });
    }
}