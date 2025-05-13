package com.quanlybanlaptop.gui.mainView;

import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.BillExport.ExportCtn;
import com.quanlybanlaptop.gui.account.AccountPanel;
import com.quanlybanlaptop.gui.authority.AuthorityPanel;
import com.quanlybanlaptop.gui.category_brand.CategoryBrandPanel;
import com.quanlybanlaptop.gui.product.ProductPanel;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.gui.customer.CustomerPanel;
import com.quanlybanlaptop.gui.insurance.InsurancePanel;
import com.quanlybanlaptop.gui.stock.StockPanel;
import com.quanlybanlaptop.gui.thongKe.ThongKePanel;
import com.quanlybanlaptop.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

public class MainContentPanel extends JPanel implements ContentChangeListener {
    private JPanel contentArea, headerPanel, windowControlPanel, topPanel;
    private ProductBUS productBUS;
    private CategoryBUS categoryBUS;
    private CompanyBUS companyBUS;
    private BillImportBUS billImportBUS;
    private SeriProductBUS seriProductBUS;
    private AdminDTO adminDTO;
    private BillExportBUS billExportBUS;
    private BillExDetailBUS billExDetailBUS;
    private CustomerBUS customerBUS;
    private InsuranceBUS insuranceBUS;
    private MainFrame parentFrame; // Thay JFrame thành MainFrame
    private InsuranceClaimBUS insuranceClaimBUS;
    private ThongKeBUS thongKeBUS;
    private ThongKePanel thongKePanel;

    public MainContentPanel(
            InsuranceBUS insuranceBUS,
            AdminDTO adminDTO,
            ProductBUS productBUS,
            CategoryBUS categoryBUS,
            CompanyBUS companyBUS,
            BillImportBUS billImportBUS,
            SeriProductBUS seriProductBUS,
            BillExportBUS billExportBUS,
            BillExDetailBUS billExDetailBUS,
            CustomerBUS customerBUS,
            InsuranceClaimBUS insuranceClaimBUS,
            ThongKeBUS thongKeBUS,

            MainFrame parentFrame // Thay JFrame thành MainFrame
    ) {
        this.insuranceBUS = insuranceBUS;
        this.productBUS = productBUS;
        this.categoryBUS = categoryBUS;
        this.companyBUS = companyBUS;
        this.parentFrame = parentFrame;
        this.billImportBUS = billImportBUS;
        this.seriProductBUS = seriProductBUS;
        this.billExportBUS = billExportBUS;
        this.billExDetailBUS = billExDetailBUS;
        this.adminDTO = adminDTO;
        this.customerBUS = customerBUS;
        this.insuranceClaimBUS = insuranceClaimBUS;
        this.thongKeBUS = thongKeBUS;
        this.thongKePanel = new ThongKePanel(adminDTO,thongKeBUS);
        setLayout(new BorderLayout());
        setBackground(new Color(244, 241, 241));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        windowControlPanel = createWindowControlPanel();
        headerPanel = createHeaderPanel();
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(contentArea, BorderLayout.CENTER);
        updateContent("Trang chủ");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ProductPanel.adjustButtonPanelLayout();
                contentArea.revalidate();
                contentArea.repaint();
                MainContentPanel.this.revalidate();
                MainContentPanel.this.repaint();
            }
        });
    }

    private JPanel createWindowControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        controlPanel.setBackground(new Color(244, 241, 241));

        JButton minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(30, 30));
        minimizeButton.addActionListener(e -> parentFrame.setState(Frame.ICONIFIED));

        JButton fullscreenButton = new JButton("□");
        fullscreenButton.setPreferredSize(new Dimension(30, 30));
        fullscreenButton.addActionListener(e -> {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            if (gd.getFullScreenWindow() == parentFrame) {
                gd.setFullScreenWindow(null);
                parentFrame.dispose();
                parentFrame.setUndecorated(false);
                parentFrame.setSize(1400, 750);
                parentFrame.setLocationRelativeTo(null);
                parentFrame.setVisible(true);
                MainContentPanel.this.revalidate();
                MainContentPanel.this.repaint();
            } else {
                parentFrame.dispose();
                parentFrame.setUndecorated(true);
                gd.setFullScreenWindow(parentFrame);
                parentFrame.setVisible(true);
                MainContentPanel.this.revalidate();
                MainContentPanel.this.repaint();
            }
        });

        JButton closeButton = new JButton("×");
        closeButton.setPreferredSize(new Dimension(30, 30));
        closeButton.addActionListener(e -> parentFrame.dispose());

        for (JButton button : new JButton[]{minimizeButton, fullscreenButton, closeButton}) {
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFocusPainted(false);
            button.setBackground(new Color(220, 220, 220));
        }
        closeButton.setBackground(new Color(255, 102, 102));

        controlPanel.add(minimizeButton);
        controlPanel.add(fullscreenButton);
        controlPanel.add(closeButton);

        return controlPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN LAPTOP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 139, 139));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><center>Hãy hướng về phía mặt trời, nơi mà bóng tối luôn ở phía sau lưng bạn.</center></html>");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(descLabel);
        headerPanel.add(Box.createVerticalStrut(20));

        return headerPanel;
    }

    @Override
    public void onContentChange(String menuItem) throws SQLException {
        updateContent(menuItem);
    }

    private void updateContent(String menuItem) {
        contentArea.removeAll();

        if (menuItem.equals("Trang chủ")) {
            if (headerPanel.getParent() == null) {
                topPanel.add(headerPanel, BorderLayout.CENTER);
            }
        } else {
            topPanel.removeAll();
        }

        switch (menuItem) {
            case "Trang chủ":
                createHomeContent(contentArea);
                break;
            case "Sản phẩm":
                ProductPanel.createProductContent(adminDTO,contentArea, productBUS, categoryBUS, companyBUS, seriProductBUS);
                break;
            case "Loại, Hãng":
                CategoryBrandPanel.createCategoryBrandContent(adminDTO,contentArea, categoryBUS, companyBUS);
                break;
            case "Kho hàng":
                StockPanel.StockPanel(adminDTO, contentArea, productBUS, billImportBUS, seriProductBUS);
                break;
            case "Hóa đơn":
                ExportCtn.createExportPanel(contentArea, adminDTO, billExportBUS, billExDetailBUS, productBUS, seriProductBUS, customerBUS);
                break;
            case "Tài khoản":
                AccountPanel.createAccountPanel(adminDTO,contentArea);
                break;
            case "Khách hàng":
                CustomerPanel.createCustomerContent(adminDTO,contentArea, customerBUS);
                break;
            case "Bảo hành":
                contentArea.setLayout(new BorderLayout());
                InsurancePanel insurancePanel = new InsurancePanel(adminDTO,insuranceBUS, seriProductBUS, insuranceClaimBUS);
                contentArea.add(insurancePanel, BorderLayout.CENTER);
                break;
            case "Phân quyền":
                AuthorityPanel.createAuthorityPanel(contentArea);
                break;
            case "Thống kê":
                contentArea.setLayout(new BorderLayout());
                ThongKePanel thongKePanel = new ThongKePanel(adminDTO,thongKeBUS);
                contentArea.add(thongKePanel, BorderLayout.CENTER);
                break;
            case "Đăng xuất":
                logout();
                break;
            default:
                createDefaultContent(contentArea, menuItem);
                break;
        }

        topPanel.revalidate();
        topPanel.repaint();
        contentArea.revalidate();
        contentArea.repaint();
    }

    private void createHomeContent(JPanel contentArea) {
        contentArea.setLayout(new GridLayout(1, 3, 20, 20));
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentArea.add(new FeatureCard(
                "TÍNH CHÍNH XÁC",
                new ImageIcon(getClass().getResource("/img/accuracy_icon.png")),
                "Mã IMEI giúp kiểm soát chính xác và độ tin cậy cao."
        ));

        contentArea.add(new FeatureCard(
                "TÍNH BẢO MẬT",
                new ImageIcon(getClass().getResource("/img/security_icon.png")),
                "Dữ liệu giúp tăng tính bảo mật cho quản lý điện thoại."
        ));

        contentArea.add(new FeatureCard(
                "TÍNH HIỆU QUẢ",
                new ImageIcon(getClass().getResource("/img/efficiency_icon.png")),
                "Hỗ trợ quản lý điện thoại nhanh chóng và hiệu quả hơn."
        ));
    }

    private void createDefaultContent(JPanel contentArea, String menuItem) {
        contentArea.setLayout(new FlowLayout());
        JLabel label = new JLabel("Chức năng: " + menuItem + " đang phát triển!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        contentArea.add(label);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            parentFrame.dispose();
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
        }
    }
}