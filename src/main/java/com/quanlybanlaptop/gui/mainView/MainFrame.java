package com.quanlybanlaptop.gui.mainView;

import com.quanlybanlaptop.bus.*;
import com.quanlybanlaptop.dto.AdminDTO;
import javax.swing.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    public MainFrame(
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
            ThongKeBUS thongKeBUS
   
    ) throws SQLException {
        setTitle("Hệ thống quản lý cửa hàng laptop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 750);

        SidebarPanel sidebar = new SidebarPanel(adminDTO);

        // Sửa: Đảm bảo MainContentPanel có constructor đúng với các tham số này
        MainContentPanel mainContent = new MainContentPanel(
                insuranceBUS,
                adminDTO,
                productBUS,
                categoryBUS,
                companyBUS,
                billImportBUS,
                seriProductBUS,
                billExportBUS,
                billExDetailBUS,
                customerBUS,
                insuranceClaimBUS,
                thongKeBUS,
    
                this // truyền MainFrame vào cuối cùng
        );
        sidebar.setContentChangeListener(mainContent);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainContent);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.2);
        add(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}