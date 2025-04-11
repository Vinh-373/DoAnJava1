package com.quanlybanlaptop.util;

import javax.swing.*;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadResourceImage(String path) {
        URL imageURL = ImageLoader.class.getResource(path);
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        } else {
            System.err.println("Không tìm thấy ảnh: " + path);
            return null;
        }
    }
}