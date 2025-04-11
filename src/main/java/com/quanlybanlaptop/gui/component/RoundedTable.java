package com.quanlybanlaptop.gui.component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;

public class RoundedTable extends JTable {

    public RoundedTable(DefaultTableModel tableModel) {
        super(tableModel); // Sử dụng DefaultTableModel được truyền vào

        // Thiết lập giao diện bảng
        this.setFillsViewportHeight(true);
        this.setShowGrid(true);
        this.setGridColor(new Color(210, 210, 210));
        this.setRowHeight(35);
        this.setSelectionBackground(new Color(174, 214, 241));
        this.setSelectionForeground(Color.BLACK);
        this.setIntercellSpacing(new Dimension(10, 5));

        // Thiết lập header
        JTableHeader header = this.getTableHeader();
        header.setBackground(new Color(174, 205, 239));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
    }

    /**
     * Phương thức chỉnh độ rộng của cột
     * @param widths Mảng chứa độ rộng của từng cột
     */
    public void setColumnWidths(int... widths) {
        SwingUtilities.invokeLater(() -> {
            if (widths.length != this.getColumnCount()) {
                throw new IllegalArgumentException("Số lượng độ rộng cột (" + widths.length + ") không khớp với số cột của bảng (" + this.getColumnCount() + ")!");
            }

            for (int i = 0; i < widths.length; i++) {
                TableColumn column = this.getColumnModel().getColumn(i);
                column.setPreferredWidth(widths[i]);
                column.setMinWidth(5);
            }
        });
        this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
}