package GUI.product;

import BLL.ProductBLL;
import javax.swing.*;
import java.awt.*;

public class ProductPanel {
    private static JPanel buttonControlPanel;

    public static void createProductContent(JPanel contentArea, ProductBLL productBLL) {
        contentArea.setLayout(new BorderLayout());
        contentArea.setBackground(new Color(239, 237, 237));
        buttonControlPanel = ButtonPanel.createButtonPanel(productBLL);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentArea.add(buttonControlPanel, BorderLayout.NORTH);

        JPanel contentPrd = new JPanel(new BorderLayout());
        contentPrd.setBackground(Color.WHITE);
        JPanel spacer = new JPanel();
        spacer.setBackground(new Color(239, 237, 237));
        spacer.setPreferredSize(new Dimension(0, 25));
        contentPrd.add(spacer, BorderLayout.NORTH);

        JScrollPane scrollPane = ProductTable.createProductTable(productBLL);
        contentPrd.add(scrollPane, BorderLayout.CENTER);
        contentArea.add(contentPrd, BorderLayout.CENTER);
    }

    public static void adjustButtonPanelLayout() {
        if (buttonControlPanel != null) {
            buttonControlPanel.revalidate();
            buttonControlPanel.repaint();
        }
    }
}