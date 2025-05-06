package com.quanlybanlaptop.gui.insurance;

import com.quanlybanlaptop.bus.InsuranceBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.bus.InsuranceClaimBUS;
import javax.swing.*;
import java.awt.*;

public class InsurancePanel extends JPanel {
    private InsuranceTop topPanel;
    private InsuranceTable tablePanel;
    private InsuranceDetail detailPanel;

    public InsurancePanel(InsuranceBUS insuranceBUS, SeriProductBUS seriProductBUS, InsuranceClaimBUS insuranceClaimBUS) {
        setLayout(new BorderLayout());
        tablePanel = new InsuranceTable(insuranceBUS, insuranceClaimBUS); // truyền insuranceClaimBUS vào
        topPanel = new InsuranceTop(insuranceBUS, seriProductBUS, insuranceClaimBUS, tablePanel); // truyền tablePanel vào
        // detailPanel chỉ show khi cần
    
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    // Phương thức trả về panel này để add vào menu hoặc main frame
    public JPanel getPanel() {
        return this;
    }
}