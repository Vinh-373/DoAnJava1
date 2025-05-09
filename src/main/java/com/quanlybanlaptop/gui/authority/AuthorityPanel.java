package com.quanlybanlaptop.gui.authority;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.*;

import com.quanlybanlaptop.bus.RoleBUS;;
public class AuthorityPanel {
    public static void createAuthorityPanel(JPanel contentArea) {
        // Create the main panel
        contentArea.setLayout(new BorderLayout());
        
        // Create the top panel with buttons
     
        
        // Create the content panel
        JPanel contentPanel = ContentPanel.createContentPanel();
        
        // Add the top panel and content panel to the main panel

        contentArea.add(contentPanel, BorderLayout.CENTER);
        
    
    }
}
