package com.quanlybanlaptop;

import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dao.*;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.mainView.MainFrame;
import com.quanlybanlaptop.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Login extends JFrame {

    public Login() {
        setTitle("Đăng nhập vào hệ thống");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPanel = new JPanel(null) {
            Image bg = ImageLoader.loadResourceImage("/img/login.jpg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JLabel header = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setForeground(Color.BLACK);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(900, 50));

        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
        input.setBackground(Color.WHITE);
        input.setPreferredSize(new Dimension(250, 200));

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsername.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtUsername = new JTextField("nguyenmai@gmail.com");
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPasswordField txtPassword = new JPasswordField("password123");
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JCheckBox chckbxLogin = new JCheckBox("Hiển thị mật khẩu.");
        chckbxLogin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chckbxLogin.setBorder(null);
        chckbxLogin.setBackground(Color.WHITE);
        chckbxLogin.addActionListener(e -> {
            txtPassword.setEchoChar(chckbxLogin.isSelected() ? (char) 0 : '•');
        });

        RoundedButton btnLogin = new RoundedButton("ĐĂNG NHẬP");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogin.setBackground(new Color(100, 178, 239));

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
                return;
            }

            try {
                Connection conn = DatabaseConnection.getConnection();
                AdminDAO adminDAO = new AdminDAO(conn);
                AdminBUS adminBUS = new AdminBUS(adminDAO);

                AdminDTO admin = adminBUS.getAdminByEmail(username, password);
                if (admin != null) {
                    // Đăng nhập thành công, mở MainFrame và truyền AdminDTO
                    this.dispose(); // Đóng form Login hiện tại
                    SwingUtilities.invokeLater(() -> {
                        try {
                            ProductDAO productDAO = new ProductDAO(conn);
                            ProductBUS productBUS = new ProductBUS(productDAO);
                            CategoryDAO categoryDAO = new CategoryDAO(conn);
                            CategoryBUS categoryBUS = new CategoryBUS(categoryDAO);
                            CompanyDAO companyDAO = new CompanyDAO(conn);
                            CompanyBUS companyBUS = new CompanyBUS(companyDAO);
                            BillImportDAO billImportDAO = new BillImportDAO(conn);
                            BillImportBUS billImportBUS = new BillImportBUS(billImportDAO);
                            SeriProductDAO seriProductDAO = new SeriProductDAO(conn);
                            SeriProductBUS seriProductBUS = new SeriProductBUS(seriProductDAO);
                            BillExportDAO billExportDAO = new BillExportDAO(conn);
                            BillExportBUS billExportBUS = new BillExportBUS(billExportDAO);
                            BillExDetailDAO billExDetailDAO = new BillExDetailDAO(conn);
                            BillExDetailBUS billExDetailBUS = new BillExDetailBUS(billExDetailDAO);
                            CustomerDAO customerDAO = new CustomerDAO();
                            CustomerBUS customerBUS = new CustomerBUS();
                            InsuranceDAO insuranceDAO = new InsuranceDAO(conn);
                            InsuranceBUS insuranceBUS = new InsuranceBUS(insuranceDAO);
                            InsuranceClaimDAO insuranceClaimDAO = new InsuranceClaimDAO(conn);
                            InsuranceClaimBUS insuranceClaimBUS = new InsuranceClaimBUS(insuranceClaimDAO);
                            ThongKeDAO thongKeDAO = new ThongKeDAO(conn);
                            ThongKeBUS thongKeBUS = new ThongKeBUS(thongKeDAO);
                            // Truyền insuranceBUS và các BUS khác vào MainFrame
                            MainFrame mainFrame = new MainFrame(
                                insuranceBUS,
                                admin,
                                productBUS,
                                categoryBUS,
                                companyBUS,
                                billImportBUS,
                                seriProductBUS,
                                billExportBUS,
                                billExDetailBUS,
                                customerBUS,
                                
                                insuranceClaimBUS// Truyền insuranceClaimBUS vào MainFrame
                                , thongKeBUS // Truyền thongKeBUS vào MainFrame
                            );
                            mainFrame.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi đăng nhập: " + ex.getMessage());
            }
        });

        input.add(lblUsername);
        input.add(txtUsername);
        input.add(Box.createVerticalStrut(15));
        input.add(lblPassword);
        input.add(txtPassword);
        input.add(Box.createVerticalStrut(15));
        input.add(chckbxLogin);
        input.add(Box.createVerticalStrut(15));
        input.add(btnLogin);

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(300, 300));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginPanel.add(header, BorderLayout.NORTH);
        loginPanel.add(input, BorderLayout.CENTER);

        loginPanel.setBounds((getWidth() - 300) / 2, (getHeight() - 300) / 2, 300, 300);
        contentPanel.add(loginPanel);

        this.setContentPane(contentPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}