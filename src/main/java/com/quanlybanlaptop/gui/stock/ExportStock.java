package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.BillImportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.BillImportDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.dto.SeriProductDTO;
import com.quanlybanlaptop.gui.component.RoundedComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ExportStock {
    private static JTextField txtquantity;
    private static ProductDTO productDTO;

    public static void showExportStock(AdminDTO adminDTO,ProductBUS productBUS, SeriProductBUS seriProductBUS, BillImportBUS billImportBUS) throws SQLException {
        int selectedRow = StockTableL.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm trước khi xuất kho.");
            return;
        }
        int idProduct = (int) StockTableL.getTableModel().getValueAt(selectedRow, 0);
        productDTO = productBUS.getProductById(idProduct);

        JDialog exportDialog = new JDialog();
        exportDialog.setModal(true);
        exportDialog.setSize(400, 500);
        exportDialog.setLayout(new BorderLayout());
        exportDialog.setLocationRelativeTo(null);
        exportDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        exportDialog.setTitle("Xuất kho");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel header = new JLabel("XUẤT KHO");
        header.setFont(new Font("Tahoma", Font.BOLD, 20));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel nhập số lượng và mã SP
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        quantityPanel.setBackground(Color.white);
        quantityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblQuantity = new JLabel("Số lượng:");
        lblQuantity.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtquantity = RoundedComponent.createRoundedTextField(10);
        txtquantity.setPreferredSize(new Dimension(70, 30));
        JButton btnChonTuDong = new JButton("Tự động chọn");
        JLabel malb = new JLabel("Mã SP: " + productDTO.getIdProduct());

        quantityPanel.add(lblQuantity);
        quantityPanel.add(txtquantity);
        quantityPanel.add(btnChonTuDong);
        quantityPanel.add(malb);
        quantityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel tìm kiếm (tạm thời chưa xử lý tìm kiếm)

        // Dữ liệu bảng
        ArrayList<SeriProductDTO> data = seriProductBUS.getListSeriById(idProduct,0);
        String[] columnNames = {"Số Seri", "Chọn"};
        Object[][] tableData = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            tableData[i][0] = data.get(i).getNumSeri();
            tableData[i][1] = false; // chưa chọn
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
        tableScrollPane.setPreferredSize(new Dimension(360, 200));
        tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nút OK & Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOk.setPreferredSize(new Dimension(80, 30));
        btnCancel.setPreferredSize(new Dimension(80, 30));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnOk);

        // Xử lý nút Cancel
        btnCancel.addActionListener(e -> exportDialog.dispose());

        // Xử lý nút OK
        btnOk.addActionListener(e -> {
            ArrayList<String> selectedSeris = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                Boolean isChecked = (Boolean) model.getValueAt(i, 1);
                if (Boolean.TRUE.equals(isChecked)) {
                    selectedSeris.add((String) model.getValueAt(i, 0));
                }
            }

            if (selectedSeris.isEmpty()) {
                JOptionPane.showMessageDialog(exportDialog, "Vui lòng chọn ít nhất một số seri.");
                return;
            }

            String soLuongStr = txtquantity.getText().trim();
            if (soLuongStr.isEmpty() || !soLuongStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(exportDialog, "Số lượng không hợp lệ.");
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);

            if (soLuong != selectedSeris.size()) {
                JOptionPane.showMessageDialog(exportDialog,
                        "Số lượng nhập (" + soLuong + ") không khớp với số seri đã chọn (" + selectedSeris.size() + ").");
                return;
            }

            try {
                seriProductBUS.updateSeriProduct(selectedSeris, 1);
                productBUS.updateqtityStock(productDTO.getIdProduct(), -1 * selectedSeris.size());
                productBUS.updateqtityStore(productDTO.getIdProduct(), selectedSeris.size());
                BigDecimal unitPrice = productDTO.getPrice();
                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(soLuong));
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                BillImportDTO billImportDTO = new BillImportDTO(
                        0,
                        adminDTO.getId(),
                        productDTO.getIdProduct(),
                        unitPrice,
                        totalPrice,
                        soLuong,
                        currentTimestamp,
                        adminDTO.getName()
                );

                billImportBUS.insertBillImport(billImportDTO);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

                StockTableL.loadStockTable(productBUS);
                StockTableR.loadBillImExTable(billImportBUS,1);


            JOptionPane.showMessageDialog(exportDialog, "Xuất kho thành công.");

            exportDialog.dispose();
        });


        // Xử lý nút Tự động chọn
        btnChonTuDong.addActionListener(e -> {
            try {
                int soLuong = Integer.parseInt(txtquantity.getText().trim());
                int soLuongCoSan = model.getRowCount();

                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(exportDialog, "Số lượng phải lớn hơn 0.");
                    return;
                }

                if (soLuong > soLuongCoSan) {
                    JOptionPane.showMessageDialog(exportDialog,
                            "Số lượng vượt quá số lượng seri hiện có (" + soLuongCoSan + ").");
                    return;
                }

                int daChon = 0;
                for (int i = 0; i < soLuongCoSan; i++) {
                    if (daChon < soLuong) {
                        model.setValueAt(true, i, 1);
                        daChon++;
                    } else {
                        model.setValueAt(false, i, 1);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(exportDialog, "Vui lòng nhập số lượng hợp lệ.");
            }
        });


        // Thêm thành phần vào dialog
        panel.add(header);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quantityPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tableScrollPane);

        exportDialog.add(panel, BorderLayout.CENTER);
        exportDialog.add(buttonPanel, BorderLayout.SOUTH);
        exportDialog.setVisible(true);
    }
}
