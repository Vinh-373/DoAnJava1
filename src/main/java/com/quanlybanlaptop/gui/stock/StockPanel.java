package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.BillImportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.BillImportDTO;

import javax.swing.*;
import java.awt.*;

public class StockPanel {
    public static JPanel buttonPanel;
    public static JPanel stockPanel, IPpanel;
    public static void StockPanel(AdminDTO adminDTO, JPanel contentArea, ProductBUS productBUS, BillImportBUS billImportBUS, SeriProductBUS seriProductBUS) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel = TopStock.createTopStock(adminDTO,productBUS,seriProductBUS,billImportBUS);
        stockPanel = StockTableL.createStockTable(productBUS);
        IPpanel = StockTableR.createStockPanel(billImportBUS);
        contentArea.add(buttonPanel, BorderLayout.NORTH);
        contentArea.add(stockPanel, BorderLayout.WEST);
        contentArea.add(IPpanel, BorderLayout.CENTER);
    }
}
