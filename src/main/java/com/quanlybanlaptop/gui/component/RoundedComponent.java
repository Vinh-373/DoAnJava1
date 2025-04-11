package com.quanlybanlaptop.gui.component;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedComponent extends JPanel {
    private final int cornerRadius;

    public RoundedComponent(int radius) {
        this.cornerRadius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        g2.dispose();
        super.paintComponent(g);
    }





    public static JTextField createRoundedTextField(int radius) {
        return new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // ✅ Vẽ nền lùi vào 1px để không che viền
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, radius, radius));

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // ✅ Vẽ viền lùi vào 1px để không bị cắt
                g2.setColor(getForeground());
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);

                g2.dispose();
            }

            @Override
            public Insets getInsets() {
                return new Insets(5, 10, 5, 10); // ✅ Để nội dung không sát viền
            }
        };
    }


    // ✅ STATIC: Tạo JComboBox bo tròn
    public static JComboBox<String> createRoundedComboBox(String[] items, int radius) {
        JComboBox<String> comboBox = new JComboBox<>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // ✅ Vẽ nền lùi vào 1px
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, radius, radius));

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // ✅ Vẽ viền lùi vào 1px
                g2.setColor(getForeground());
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);

                g2.dispose();
            }
        };

        comboBox.setOpaque(false);
        comboBox.setBorder(new EmptyBorder(5, 10, 5, 10)); // ✅ Tạo khoảng cách nội dung
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);

        return comboBox;
    }

}
