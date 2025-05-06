package com.quanlybanlaptop.gui.thongKe;

import javax.swing.*;
import java.awt.*;
import com.quanlybanlaptop.bus.ThongKeBUS;
public class ThongKePanel extends JPanel {
    public ThongKePanel(ThongKeBUS thongKeBUS) {
        setLayout(new BorderLayout());
        add(new TKPanelContent(thongKeBUS), BorderLayout.CENTER);
    }
}