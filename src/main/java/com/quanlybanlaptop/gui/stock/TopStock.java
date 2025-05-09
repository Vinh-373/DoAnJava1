package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.AuthoritiesBUS;
import com.quanlybanlaptop.bus.BillImportBUS;
import com.quanlybanlaptop.bus.CTQBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.RoleBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dao.AuthoritiesDAO;
import com.quanlybanlaptop.dao.CTQDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.BillExportDTO;
import com.quanlybanlaptop.dto.BillImportDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.BillExport.ExportTable;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.util.ImageLoader;
import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class TopStock {
    private static JComboBox<String> comboBox,comboBox2,comboBox1;
    private static JDateChooser dateFromChooser, dateToChooser;
    private static JTextField txtFrom;
    public static JPanel createTopStock(AdminDTO adminDTO, ProductBUS productBUS, SeriProductBUS seriProductBUS, BillImportBUS billImportBUS) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       Connection cnn = DatabaseConnection.getConnection();
     
        CTQDAO ctqDAO = new CTQDAO(cnn);
        CTQBUS ctqBUS = new CTQBUS(ctqDAO);
        RoundedButton btnImport = new RoundedButton("Nhập hàng", ImageLoader.loadResourceImage("/img/import_prd_control.png"));
        btnImport.setImageSize(32, 32);
        btnImport.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 6, 1));
//        btnImport.setPreferredSize(new Dimension(37, 55));
        RoundedButton btnExport = new RoundedButton("Xuất hàng", ImageLoader.loadResourceImage("/img/export_prd_control.png"));
        btnExport.setImageSize(32, 32);
        btnExport.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 6, 2));
        
//        btnExport.setPreferredSize(new Dimension(37, 55));
        JLabel fromLb = new JLabel("Từ:");
        JLabel ToLb = new JLabel("Đến:");
         dateFromChooser = new JDateChooser();
        dateFromChooser.setPreferredSize(new Dimension(100, 30));
         dateToChooser = new JDateChooser();
        dateToChooser.setPreferredSize(new Dimension(100, 30));

         txtFrom = RoundedComponent.createRoundedTextField(10);
        txtFrom.setPreferredSize(new Dimension(200, 30));
        JButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        String[] op = {"Hoạt động","Cần nhập"};
        comboBox = RoundedComponent.createRoundedComboBox(op,10);
        comboBox.addActionListener(e->{
            String select = (String) comboBox.getSelectedItem();
            ArrayList<ProductDTO> productss = null;

            try {
                productss = productBUS.getOutOfStock(2);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(select.equals("Hoạt động")){
                StockTableL.loadStockTable(productBUS);
            }else{
                StockTableL.loadStockTable1(productss);
            }

        });
        String[] op1 = {"DS nhập kho", "DS xuất kho"};
         comboBox1 = RoundedComponent.createRoundedComboBox(op1,10);
        String[] op2 = {"Mã Sp", "Nhân viên"};
        comboBox2 = RoundedComponent.createRoundedComboBox(op2,10);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.gridy = 0; // Tất cả trên cùng một hàng
        gbc.fill = GridBagConstraints.HORIZONTAL; // Kéo giãn ngang

// Nhập
        gbc.gridx = 0;
        gbc.weightx = 0; // Trọng số ngang bằng nhau cho các nút
        gbc.gridwidth = 1; // Chiếm 1 cột
        buttonPanel.add(btnImport, gbc);

// Xuất
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        buttonPanel.add(btnExport, gbc);

//

// Nút Refresh (vị trí thứ 4)
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        buttonPanel.add(btnRefresh, gbc); // Đã sửa từ btnReresh thành btnRefresh

// ComboBox đầu tiên (dịch sang phải)
        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        buttonPanel.add(comboBox, gbc);

// Từ ngày (Label) (dịch sang phải)
        gbc.gridx = 4;
        gbc.weightx = 0; // Label không cần kéo giãn
        gbc.gridwidth = 1;
        buttonPanel.add(fromLb, gbc);

// JDateChooser "Từ ngày" (dịch sang phải)
        gbc.gridx = 5;
        gbc.weightx = 1; // Kéo giãn vừa phải
        gbc.gridwidth = 2;
        buttonPanel.add(dateFromChooser, gbc);

// Đến ngày (Label) (dịch sang phải)
        gbc.gridx = 7;
        gbc.weightx = 0; // Label không cần kéo giãn
        gbc.gridwidth = 1;
        buttonPanel.add(ToLb, gbc);

// JDateChooser "Đến ngày" (dịch sang phải)
        gbc.gridx = 8;
        gbc.weightx = 1; // Kéo giãn vừa phải
        gbc.gridwidth = 2;
        buttonPanel.add(dateToChooser, gbc);

// ComboBox thứ hai (dịch sang phải)
        gbc.gridx = 10;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        buttonPanel.add(comboBox1, gbc);

        gbc.gridx = 11;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        buttonPanel.add(comboBox2, gbc);

// Ô tìm kiếm (txtFrom) - Gấp đôi kích thước (dịch sang phải)
        gbc.gridx = 12;
        gbc.weightx = 1; // Trọng số gấp đôi để kéo giãn lớn hơn
        gbc.gridwidth = 2; // Chiếm 2 cột
        buttonPanel.add(txtFrom, gbc);

        comboBox1.addActionListener(e -> {
            String Selected = (String)comboBox1.getSelectedItem();
            if(Selected.equals("DS nhập kho")){
                StockTableR.loadBillImExTable(billImportBUS,0);
                dateFromChooser.setDate(null);
                dateToChooser.setDate(null);
                txtFrom.setText("");
            }else{
                StockTableR.loadBillImExTable(billImportBUS,1);
                dateFromChooser.setDate(null);
                dateToChooser.setDate(null);
                txtFrom.setText("");
            }
        });

        btnImport.addActionListener(e -> {
            comboBox1.setSelectedItem("DS nhập kho");
            int selectedRow = StockTableL.getSelectedRow();
            if (selectedRow >= 0) {
//                int[] idProduct = (int) StockTableL.getTableModel().getValueAt(selectedRow, 0);
//                try {
//                    ArrayList<ProductDTO> product = productBUS.getProductById(idProduct);
//                    if (product != null) {
                try {
                    ImportStock.showImportStock(adminDTO,seriProductBUS,productBUS,billImportBUS);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    }
//                } catch (SQLException ex) { // Dòng 82: Lỗi ở đây
//                    JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để nhập kho!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExport.addActionListener(e -> {
            comboBox1.setSelectedItem("DS xuất kho");
//            try {
            try {
                ExportStock.showExportStock(adminDTO,productBUS,seriProductBUS,billImportBUS);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
        });

        btnRefresh.addActionListener(e -> {
            comboBox1.setSelectedItem("DS phiếu nhập");
            StockTableR.loadBillImExTable(billImportBUS,0);
            comboBox.setSelectedItem("Hoạt động");
            comboBox1.setSelectedItem("DS nhập kho");
            StockTableL.loadStockTable(productBUS);
            dateFromChooser.setDate(null);
            dateToChooser.setDate(null);

            txtFrom.setText("");
        });
        // Sự kiện cho dateFromChooser
        dateFromChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    applyCombinedFilter(billImportBUS);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Sự kiện cho dateToChooser
        dateToChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    applyCombinedFilter(billImportBUS);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Sự kiện cho txtSearch
        txtFrom.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    applyCombinedFilter(billImportBUS);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    applyCombinedFilter(billImportBUS);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    applyCombinedFilter(billImportBUS);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return buttonPanel;
    }
    public static JComboBox<String> getComboBox() {
        return comboBox;
    }
    public static void applyCombinedFilter(BillImportBUS billImportBUS) throws SQLException {
        Date fromDate = dateFromChooser.getDate();
        Date toDate = dateToChooser.getDate();
        String searchCriteria = (String) comboBox2.getSelectedItem();
        String searchText = txtFrom.getText().trim();
        String Loaids = comboBox1.getSelectedItem().toString();


        ArrayList<BillImportDTO> filteredList = billImportBUS.filterBillExports(fromDate, toDate, searchCriteria, searchText,Loaids);

        // Cập nhật bảng với danh sách đã lọc
        StockTableR.loadBillExData1(filteredList);

        // Thông báo nếu không tìm thấy kết quả
        if (filteredList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
