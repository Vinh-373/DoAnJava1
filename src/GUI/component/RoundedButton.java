package GUI.component;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int radius = 20; // Mặc định bo tròn 20px
    private ImageIcon icon;
    private int imgWidth = 32; // Kích thước ảnh mặc định
    private int imgHeight = 32;

    // Constructor đơn giản chỉ có text
    public RoundedButton(String text) {
        super(text);
        setDefaults();
    }

    // Constructor có cả text và icon
    public RoundedButton(String text, ImageIcon icon) {
        super(text);
        this.icon = icon;
        setDefaults();
        resizeImage(); // Cập nhật kích thước ảnh
    }

    private void setDefaults() {
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        // 🔴 Đặt vị trí icon trên, text dưới
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }

    // ✅ Phương thức thiết lập ảnh
    public void setImage(ImageIcon icon) {
        this.icon = icon;
        resizeImage(); // Cập nhật kích thước ảnh
    }

    // ✅ Phương thức cập nhật kích thước ảnh
    public void setImageSize(int width, int height) {
        this.imgWidth = width;
        this.imgHeight = height;
        resizeImage(); // Cập nhật lại ảnh với kích thước mới
    }

    private void resizeImage() {
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        }
    }

    // ✅ Phương thức cập nhật font chữ
    public void setCustomFont(Font font) {
        setFont(font);
    }

    // ✅ Phương thức cập nhật độ bo tròn
    public void setCornerRadius(int radius) {
        this.radius = radius;
        repaint(); // Cập nhật giao diện
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền bo tròn
        g2.setColor(getBackground());
        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ border bo tròn
        g2.setColor(getForeground());
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);

        g2.dispose();
    }
}
