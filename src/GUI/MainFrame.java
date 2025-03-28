package GUI;

import BLL.ProductBLL;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(ProductBLL productBLL) {
        setTitle("Hệ thống quản lý cửa hàng laptop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 750);

        SidebarPanel sidebar = new SidebarPanel();
        MainContentPanel mainContent = new MainContentPanel(productBLL);
        sidebar.setContentChangeListener(mainContent);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContent);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.2); // Giữ tỷ lệ sidebar nhỏ hơn
        add(splitPane);

        setLocationRelativeTo(null); // Căn giữa màn hình
        setVisible(true);
    }
}