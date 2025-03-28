package UTIL;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {
    public static ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(path);
        } catch (Exception e) {
            System.err.println("Không thể tải hình ảnh: " + path);
            return new ImageIcon(); // Trả về biểu tượng rỗng nếu lỗi
        }
    }
}