package com.quanlybanlaptop.gui.product;

import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.dto.SeriProductDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeriListDialog {
    public static void showDialog(ProductBUS productBUS, SeriProductBUS seriProductBUS, String status) throws SQLException {
        int selectedRow = ProductTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm trước khi xem danh sách seri.");
            return;
        }

        int idProduct = (int) ProductTable.getTableModel().getValueAt(selectedRow, 0);
        ProductDTO productDTO = productBUS.getProductById(idProduct);

        // Tạo dialog
        JDialog seriListDialog = new JDialog();
        seriListDialog.setTitle("Danh sách Seri");
        seriListDialog.setModal(true);
        seriListDialog.setSize(400, 400);
        seriListDialog.setLayout(new BorderLayout());
        seriListDialog.setLocationRelativeTo(null);
        seriListDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel header = new JLabel("Mã sản phẩm: " + productDTO.getIdProduct());
        header.setFont(new Font("Tahoma", Font.PLAIN, 16));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Số Seri", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        RoundedTable seriTable = new RoundedTable(tableModel);
        seriTable.setFocusable(false);
        seriTable.setRequestFocusEnabled(false);
        seriTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(seriTable);

        // Lấy danh sách seri từ DTO hoặc từ BUS (tuỳ bạn setup model)
        ArrayList<SeriProductDTO> seriList = new ArrayList<>();
        if(status.equals("Hoạt động")){
            seriList = seriProductBUS.getListSeriById(idProduct,1); // nếu không có, bạn có thể dùng BUS để lấy

        }else{
            seriList = seriProductBUS.getListSeriById(idProduct,-1); // nếu không có, bạn có thể dùng BUS để lấy

        }
        if (seriList != null && !seriList.isEmpty()) {
            for (SeriProductDTO seri : seriList) {
                String trangThai = seri.getStatus() == 1 ? "Sẵn sàng bán" : "Lỗi";
                tableModel.addRow(new Object[]{seri.getNumSeri(), trangThai});
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không có số seri nào cho sản phẩm này.");
            return;
        }

        RoundedButton closeButton = new RoundedButton("Đóng");
        closeButton.setPreferredSize(new Dimension(100, 30));
        closeButton.addActionListener(e -> seriListDialog.dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(closeButton);

        seriListDialog.add(header, BorderLayout.NORTH);
        seriListDialog.add(scrollPane, BorderLayout.CENTER);
        seriListDialog.add(footer, BorderLayout.SOUTH);
        seriListDialog.setVisible(true);
    }
}
