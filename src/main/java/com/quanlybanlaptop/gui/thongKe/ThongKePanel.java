package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;
import com.quanlybanlaptop.bus.ThongKeBUS;
import com.quanlybanlaptop.dto.AdminDTO;
public class ThongKePanel extends JPanel {
    public ThongKePanel(AdminDTO adminDTO,ThongKeBUS thongKeBUS) {
        setLayout(new BorderLayout());
        add(new TKPanelContent(adminDTO,thongKeBUS), BorderLayout.CENTER);
    }
}