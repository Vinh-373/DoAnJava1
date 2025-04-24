package com.quanlybanlaptop.gui.BillExport;

import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.SeriProductDTO;
import com.quanlybanlaptop.gui.component.RoundedTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DSSeriDialog {
    public static  boolean createDSSeriDialog(int quantity,int idProduct, SeriProductBUS seriProductBUS, ArrayList<String> selectedSeris) {
        JDialog addDialog = new JDialog();
        addDialog.setTitle("Danh sách seri");
        addDialog.setModal(true);
        addDialog.setSize(200, 400);
        addDialog.setLayout(new BorderLayout());
        addDialog.setLocationRelativeTo(null);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Label header = new Label("Hãy chọn "+ quantity+"số seri");
        ArrayList<SeriProductDTO> data = seriProductBUS.getListSeriById(idProduct,1);
        // Biến để theo dõi trạng thái
        final boolean[] result = {false}; // Mặc định là false
        if(data.size()<quantity){
            JOptionPane.showMessageDialog(null, "Số lượng tại cửa hàng không đủ (còn lại: "+ data.size()+")");
            result[0] = false;
            return result[0];
        }
        String[] columnNames = {"Số Seri", "Chọn"};
        Object[][] tableData = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            tableData[i][0] = data.get(i).getNumSeri();
            if(selectedSeris.contains(data.get(i).getNumSeri())){
                tableData[i][1] = true; // chưa chọn

            }else {
                tableData[i][1] = false; // chưa chọn
            }
        }

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        JTable seriTable = new JTable(model);
        seriTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(seriTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 200));
        tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Nút OK & Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOk.setPreferredSize(new Dimension(80, 30));
        btnCancel.setPreferredSize(new Dimension(80, 30));
        btnOk.addActionListener(e -> {
            ArrayList<String> selectedSeri = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean isChecked = (Boolean) model.getValueAt(i, 1);
                if (Boolean.TRUE.equals(isChecked)) {
                    String seri = (String) model.getValueAt(i, 0);
                    // Kiểm tra xem số seri đã có trong selectedSeris chưa
                    if (!selectedSeri.contains(seri)) {
                        selectedSeri.add(seri);
                    }
                }
            }
            if (selectedSeri.isEmpty() ) {
                JOptionPane.showMessageDialog(addDialog, "Vui lòng chọn ít nhất một số seri.");
                return;
            }
            if (quantity != selectedSeri.size()) {
                JOptionPane.showMessageDialog(addDialog,
                        "Số lượng nhập (" + quantity + ") không khớp với số seri đã chọn (" + selectedSeri.size() + ").");
                return;
            }
            for (int i = 0; i < selectedSeri.size(); i++) {
                String seri = selectedSeri.get(i); // Lấy trực tiếp chuỗi từ selectedSeri
                if (!selectedSeris.contains(seri)) { // Kiểm tra trùng lặp
                    selectedSeris.add(seri);
                }
            }
            addDialog.dispose();
            result[0] = true;
            System.out.println(selectedSeri);
            System.out.println(selectedSeris);
        });
        btnCancel.addActionListener(e -> {
            addDialog.dispose();
            result[0] = false;
        });
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        addDialog.add(header, BorderLayout.NORTH);
        addDialog.add(tableScrollPane, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
        return result[0];
    }
}
