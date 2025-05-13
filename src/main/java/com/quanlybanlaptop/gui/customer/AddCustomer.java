package com.quanlybanlaptop.gui.customer;
import com.quanlybanlaptop.bus.CustomerBUS;
import com.quanlybanlaptop.dto.CustomerDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import javax.swing.text.DateFormatter;

public class AddCustomer extends JFrame {
    private JTextField nameField;
    private JFormattedTextField birthdateField;
    private JTextField phoneField;
    private JTextField citizenIdField;
    private JComboBox<String> genderComboBox;
    private JButton saveButton;

    public AddCustomer() {
        setTitle("Thêm Khách Hàng");
        setSize(400, 300);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Tên:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Ngày sinh
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Ngày sinh (yyyy-MM-dd):"), gbc);

        gbc.gridx = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(dateFormat);
        birthdateField = new JFormattedTextField(dateFormatter);
        birthdateField.setColumns(20);
        add(birthdateField, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Số điện thoại:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

        // CCCD
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("CCCD:"), gbc);

        gbc.gridx = 1;
        citizenIdField = new JTextField(20);
        add(citizenIdField, gbc);

        // Giới tính
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Giới tính:"), gbc);

        gbc.gridx = 1;
        genderComboBox = new JComboBox<>(new String[] { "Nam", "Nữ" });
        add(genderComboBox, gbc);

        // Nút Lưu
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Lưu");
        add(saveButton, gbc);

        // Xử lý sự kiện nút Lưu
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String birthdateStr = birthdateField.getText().trim();
                String phone = phoneField.getText().trim();
                String citizenId = citizenIdField.getText().trim();
                String gender = (String) genderComboBox.getSelectedItem();
                int status = 1;

                // Kiểm tra dữ liệu đầu vào
                if (name.isEmpty() || birthdateStr.isEmpty() || phone.isEmpty() || citizenId.isEmpty()) {
                    JOptionPane.showMessageDialog(AddCustomer.this, "Vui lòng điền đầy đủ thông tin.");
                    return;
                }

                // Chuyển đổi chuỗi ngày sinh sang Date
                Date birthdate;
                try {
                    birthdate = dateFormat.parse(birthdateStr);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(AddCustomer.this, "Định dạng ngày sinh không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
                    return;
                }

                // Kiểm tra số điện thoại (tùy chọn, có thể bỏ qua nếu không cần)
                if (!phone.matches("\\d{10,11}")) {
                    JOptionPane.showMessageDialog(AddCustomer.this, "Số điện thoại không hợp lệ.");
                    return;
                }

                // Kiểm tra CCCD (tùy chọn, có thể bỏ qua nếu không cần)
                if (!citizenId.matches("\\d{9,12}")) {
                    JOptionPane.showMessageDialog(AddCustomer.this, "CCCD không hợp lệ.");
                    return;
                }

                // Tạo đối tượng CustomerDTO
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setBirthdate(new java.sql.Date(birthdate.getTime()));
                customer.setGender(gender);
                customer.setPhone(phone);
                customer.setCitizenId(citizenId);
                customer.setStatus(status);

                // Gọi BUS để thêm khách hàng
                CustomerBUS customerBUS = new CustomerBUS();
                if(customerBUS.getByCCCD(customer.getCitizenId()) != null){
                     JOptionPane.showMessageDialog(AddCustomer.this, "CCCD đã tồn tại.");
                    return;
                }
                boolean success = customerBUS.addCustomer(customer);

                if (success) {
                    JOptionPane.showMessageDialog(AddCustomer.this, "Thêm khách hàng thành công!");
                    dispose();
                    CustomerTable.loadCustomerData(customerBUS,null);// Đóng cửa sổ sau khi thêm thành công
                } else {
                    JOptionPane.showMessageDialog(AddCustomer.this, "Thêm khách hàng thất bại.");
                }
            }
        });
    }
}