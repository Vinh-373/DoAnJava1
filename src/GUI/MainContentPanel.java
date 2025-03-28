package GUI;

import GUI.product.ProductPanel;
import GUI.component.*;
import BLL.ProductBLL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainContentPanel extends JPanel implements ContentChangeListener {
    private JPanel contentArea, headerPanel;
    private ProductBLL productBLL;

    public MainContentPanel(ProductBLL productBLL) {
        this.productBLL = productBLL;
        setLayout(new BorderLayout());
        setBackground(new Color(244, 241, 241));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerPanel = createHeaderPanel();
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        add(headerPanel, BorderLayout.NORTH);
        add(contentArea, BorderLayout.CENTER);
        updateContent("Trang chủ");

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ProductPanel.adjustButtonPanelLayout();
            }
        });
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
    public void onContentChange(String menuItem) {
        updateContent(menuItem);
    }

    private void updateContent(String menuItem) {
        contentArea.removeAll();
        contentArea.setLayout(null);
        if (menuItem.equals("Trang chủ")) {
            if (headerPanel.getParent() == null) {
                add(headerPanel, BorderLayout.NORTH);
            }
        } else {
            remove(headerPanel);
        }

        switch (menuItem) {
            case "Trang chủ":
                createHomeContent(contentArea);
                break;
            case "Sản phẩm":
                ProductPanel.createProductContent(contentArea, productBLL);
                break;
            default:
                createDefaultContent(contentArea, menuItem);
                break;
        }

        contentArea.revalidate();
        contentArea.repaint();
    }

    private void createHomeContent(JPanel contentArea) {
        contentArea.setLayout(new GridLayout(1, 3, 20, 20));
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentArea.add(new FeatureCard("TÍNH CHÍNH XÁC", new ImageIcon(getClass().getResource("/images/accuracy_icon.png")),
                "Mã IMEI giúp kiểm soát chính xác và độ tin cậy cao."));
        contentArea.add(new FeatureCard("TÍNH BẢO MẬT", new ImageIcon(getClass().getResource("/images/security_icon.png")),
                "Dữ liệu giúp tăng tính bảo mật cho quản lý điện thoại."));
        contentArea.add(new FeatureCard("TÍNH HIỆU QUẢ", new ImageIcon(getClass().getResource("/images/efficiency_icon.png")),
                "Hỗ trợ quản lý điện thoại nhanh chóng và hiệu quả hơn."));
    }

    private void createDefaultContent(JPanel contentArea, String menuItem) {
        contentArea.setLayout(new FlowLayout());
        JLabel label = new JLabel("Chức năng: " + menuItem + " đang phát triển!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        contentArea.add(label);
    }
}