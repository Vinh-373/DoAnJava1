package com.quanlybanlaptop.gui.mainView;


import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.gui.BillExport.ExportCtn;
import com.quanlybanlaptop.gui.account.AccountPanel;
import com.quanlybanlaptop.gui.category_brand.CategoryBrandPanel;
import com.quanlybanlaptop.gui.product.ProductPanel;
import com.quanlybanlaptop.gui.component.*;
import com.quanlybanlaptop.gui.customer.CustomerPanel;
import com.quanlybanlaptop.gui.stock.StockPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.sql.SQLException;

public class MainContentPanel extends JPanel implements ContentChangeListener {
    private JPanel contentArea, headerPanel,windowControlPanel,topPanel;
    private ProductBUS productBUS;
    private CategoryBUS categoryBUS;
    private CompanyBUS companyBUS;
    private BillImportBUS billImportBUS;
    private SeriProductBUS seriProductBUS;
    private AdminDTO adminDTO;
    private BillExportBUS billExportBUS;
    private BillExDetailBUS billExDetailBUS;
    private CustomerBUS customerBUS; 

    private JFrame parentFrame;

    public MainContentPanel(AdminDTO adminDTO, ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS, BillImportBUS billImportBUS, SeriProductBUS seriProductBUS, BillExportBUS billExportBUS, BillExDetailBUS billExDetailBUS, CustomerBUS customerBUS, JFrame parentFrame) {
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
        setLayout(new BorderLayout());
        setBackground(new Color(244, 241, 241));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        windowControlPanel = createWindowControlPanel();
        headerPanel = createHeaderPanel();
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        topPanel = new JPanel(new BorderLayout());
//        topPanel.add(windowControlPanel, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(contentArea, BorderLayout.CENTER);
        updateContent("Trang chủ");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ProductPanel.adjustButtonPanelLayout(); // Điều chỉnh ProductPanel nếu cần
                contentArea.revalidate();               // Cập nhật contentArea
                contentArea.repaint();
                MainContentPanel.this.revalidate();     // Cập nhật toàn bộ MainContentPanel
                MainContentPanel.this.repaint();
            }
        });
    }
    private JPanel createWindowControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        controlPanel.setBackground(new Color(244, 241, 241));

        // Nút thu nhỏ
        JButton minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(30, 30));
        minimizeButton.addActionListener(e -> {
            parentFrame.setState(Frame.ICONIFIED);
        });

        // Nút fullscreen/restore
        JButton fullscreenButton = new JButton("□");
        fullscreenButton.setPreferredSize(new Dimension(30, 30));
        fullscreenButton.addActionListener(e -> {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            if (gd.getFullScreenWindow() == parentFrame) {
                // Thoát fullscreen
                gd.setFullScreenWindow(null);
                parentFrame.dispose();
                parentFrame.setUndecorated(false); // Khôi phục thanh tiêu đề
                parentFrame.setSize(1800, 950); // Đặt kích thước mặc định
                parentFrame.setLocationRelativeTo(null); // Căn giữa màn hình
                parentFrame.setVisible(true);

                // Cập nhật lại toàn bộ giao diện
                MainContentPanel.this.revalidate(); // Cập nhật bố cục của MainContentPanel
                MainContentPanel.this.repaint();    // Vẽ lại giao diện
            } else {
                // Vào fullscreen
                parentFrame.dispose();
                parentFrame.setUndecorated(true); // Loại bỏ thanh tiêu đề
                gd.setFullScreenWindow(parentFrame);
                parentFrame.setVisible(true);

                // Cập nhật lại toàn bộ giao diện
                MainContentPanel.this.revalidate();
                MainContentPanel.this.repaint();
            }
        });

        // Nút đóng
        JButton closeButton = new JButton("×");
        closeButton.setPreferredSize(new Dimension(30, 30));
        closeButton.addActionListener(e -> {
            parentFrame.dispose();
        });

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
    
        // Xử lý headerPanel
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
                ProductPanel.createProductContent(contentArea, productBUS, categoryBUS, companyBUS, seriProductBUS);
                break;
            case "Loại, Hãng":
                CategoryBrandPanel.createCategoryBrandContent(contentArea, categoryBUS, companyBUS);
                break;
            case "Kho hàng":
                StockPanel.StockPanel(adminDTO, contentArea, productBUS, billImportBUS, seriProductBUS);
                break;
            case "Hóa đơn":
                ExportCtn.createExportPanel(contentArea, adminDTO, billExportBUS, billExDetailBUS, productBUS, seriProductBUS);
                break;
            case "Tài khoản":
                AccountPanel.createAccountPanel(contentArea);
                break;
//            case "Bảo hành":
//                InsurancePanel.createPanel(contentArea);
//                break;
            case "Khách hàng":
                CustomerPanel.createCustomerContent(contentArea, customerBUS); // Gọi đúng phương thức và truyền customerBUS
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
}