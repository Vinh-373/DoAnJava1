package com.quanlybanlaptop.gui.BillExport;

import com.quanlybanlaptop.bus.BillExDetailBUS;
import com.quanlybanlaptop.bus.BillExportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.AdminDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ExportCtn {

    public static void createExportPanel(JPanel contentArea,AdminDTO adminDTO ,BillExportBUS billExportBUS, BillExDetailBUS billExDetailBUS, ProductBUS productBUS, SeriProductBUS seriProductBUS) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));
        JPanel buttonPanel = TopButton.createTopPanel(adminDTO,billExportBUS,billExDetailBUS,seriProductBUS,productBUS);
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentPanel.add(spacer, BorderLayout.NORTH);
        contentPanel.add(ExportTable.createTable(billExportBUS,productBUS,billExDetailBUS), BorderLayout.CENTER);
        contentPanel.add(ExportRight.createRightPanel(billExDetailBUS,productBUS,seriProductBUS), BorderLayout.EAST);
        contentArea.add(buttonPanel, BorderLayout.NORTH);
        contentArea.add(contentPanel, BorderLayout.CENTER);
    }
}
