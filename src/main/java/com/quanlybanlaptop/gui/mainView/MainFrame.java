package com.quanlybanlaptop.gui.mainView;

import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dto.AdminDTO;

import javax.swing.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    public MainFrame(AdminDTO adminDTO, ProductBUS productBUS, CategoryBUS categoryBUS, CompanyBUS companyBUS, BillImportBUS billImportBUS, SeriProductBUS seriProductBUS, BillExportBUS billExportBUS, BillExDetailBUS billExDetailBUS, CustomerBUS customerBUS) throws SQLException {
        setTitle("Hệ thống quản lý cửa hàng laptop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 750);

        SidebarPanel sidebar = new SidebarPanel(adminDTO);
        MainContentPanel mainContent = new MainContentPanel(
            adminDTO,
            productBUS,
            categoryBUS,
            companyBUS,
            billImportBUS,
            seriProductBUS,
            billExportBUS,
            billExDetailBUS,
            customerBUS, // truyền thêm customerBUS
            this
        );
        sidebar.setContentChangeListener(mainContent);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContent);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.2); // Giữ tỷ lệ sidebar nhỏ hơn
        add(splitPane);

        setLocationRelativeTo(null); // Căn giữa màn hình
        setVisible(true);
    }
}