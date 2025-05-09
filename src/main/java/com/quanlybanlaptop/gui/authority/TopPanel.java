package com.quanlybanlaptop.gui.authority;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.util.ImageLoader;

public class TopPanel {
     public static JPanel createButtonPanel() {
        JPanel buttonControlPanel = new JPanel(new FlowLayout());
        buttonControlPanel.setBackground(Color.WHITE);
        buttonControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        RoundedButton btnDelete = new RoundedButton("Xóa",  ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnSave = new RoundedButton("Lưu", ImageLoader.loadResourceImage("/img/save_control.png"));
        btnSave.setImageSize(32, 32);

        buttonControlPanel.add(btnAdd);
        buttonControlPanel.add(btnDelete);
        buttonControlPanel.add(btnSave);
        return buttonControlPanel;
    }
}
