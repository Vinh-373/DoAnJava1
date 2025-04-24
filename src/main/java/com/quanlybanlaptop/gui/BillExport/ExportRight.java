package com.quanlybanlaptop.gui.BillExport;

import com.quanlybanlaptop.bus.BillExDetailBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.BillExDetailDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.gui.component.ComboItem;
import com.quanlybanlaptop.gui.component.RoundedComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExportRight {
    private static JTextField txtID, txtDate, txtNV, txtKH, ipQtity;
    private static DefaultTableModel model;
    private static JTable table;
    private static JLabel tongTien = new JLabel("Tong Tien:");
    private static JLabel lblID, lblDate, lblNV, lblKH, lblProduct, lblQuantity;
    private static JComboBox cmbProduct;
    private static JButton btnAddCTHD;
    private static ArrayList<String> selectedSeris = new ArrayList<>();

    public static JPanel createRightPanel(BillExDetailBUS billExDetailBUS, ProductBUS productBUS, SeriProductBUS seriProductBUS) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(500, 0));

        JPanel panelSpacer = new JPanel();
        panelSpacer.setBackground(new Color(239, 237, 237));
        panelSpacer.setPreferredSize(new Dimension(10, 0));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel headingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        headingPanel.setBackground(Color.WHITE);
        headingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        headingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel heading = new JLabel("HÓA ĐƠN BÁN HÀNG");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        headingPanel.add(heading);
        content.add(headingPanel);

        JPanel storeInfoPanel = new JPanel();
        storeInfoPanel.setLayout(new BoxLayout(storeInfoPanel, BoxLayout.Y_AXIS));
        storeInfoPanel.setBackground(Color.WHITE);
        storeInfoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel storeName = new JLabel("Cửa hàng Laptop Vinh Hoàng");
        storeName.setFont(new Font("Arial", Font.BOLD, 15));
        storeName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel storeAddress = new JLabel("Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM");
        storeAddress.setFont(new Font("Arial", Font.PLAIN, 14));
        storeAddress.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel storeContact = new JLabel("SĐT: 0123 456 789");
        storeContact.setFont(new Font("Arial", Font.PLAIN, 14));
        storeContact.setAlignmentX(Component.LEFT_ALIGNMENT);

        storeInfoPanel.add(Box.createVerticalStrut(5));
        storeInfoPanel.add(storeName);
        storeInfoPanel.add(storeAddress);
        storeInfoPanel.add(storeContact);
        storeInfoPanel.add(Box.createVerticalStrut(5));

        content.add(storeInfoPanel);

        JPanel invoiceHeader = new JPanel();
        invoiceHeader.setLayout(new BoxLayout(invoiceHeader, BoxLayout.Y_AXIS));
        invoiceHeader.setBackground(Color.WHITE);
        invoiceHeader.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        invoiceHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        txtID = RoundedComponent.createRoundedTextField(10);
        txtDate = RoundedComponent.createRoundedTextField(10);
        txtNV = RoundedComponent.createRoundedTextField(10);
        txtKH = RoundedComponent.createRoundedTextField(10);

        txtID.setFont(fieldFont);
        txtDate.setFont(fieldFont);
        txtNV.setFont(fieldFont);
        txtKH.setFont(fieldFont);

        txtID.setPreferredSize(new Dimension(50, 25));
        txtDate.setPreferredSize(new Dimension(170, 25));
        txtNV.setPreferredSize(new Dimension(170, 25));
        txtKH.setPreferredSize(new Dimension(170, 25));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.setBackground(Color.WHITE);
        lblID = new JLabel("Mã hóa đơn:");
        lblID.setFont(fieldFont);
        lblDate = new JLabel("Thời gian xuất:");
        lblDate.setFont(fieldFont);
        row1.add(lblID);
        row1.add(txtID);
        row1.add(lblDate);
        row1.add(txtDate);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.setBackground(Color.WHITE);
        lblNV = new JLabel("NV:");
        lblNV.setFont(fieldFont);
        lblKH = new JLabel("KH:");
        lblKH.setFont(fieldFont);
        isVisible(0);
        row2.add(lblNV);
        row2.add(txtNV);
        row2.add(lblKH);
        row2.add(txtKH);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row3.setBackground(Color.WHITE);
        lblProduct = new JLabel("SP:");
        lblProduct.setFont(fieldFont);
        ComboItem[] items = {};
        cmbProduct = new JComboBox<>();

        try {
            ArrayList<ProductDTO> listPrdDTO = productBUS.getAllProducts();
            for (ProductDTO product : listPrdDTO) {
                String name = product.getName() + " (" + product.getIdProduct() + ")";
                ComboItem item = new ComboItem(product.getIdProduct(), name);
                cmbProduct.addItem(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        lblQuantity = new JLabel("Sl:");
        lblQuantity.setFont(fieldFont);
        ipQtity = RoundedComponent.createRoundedTextField(10);
        ipQtity.setPreferredSize(new Dimension(40, 25));
        btnAddCTHD = new JButton("Thêm Sp");
        btnAddCTHD.addActionListener(e -> {
            try {
                ComboItem selectedItem = (ComboItem) cmbProduct.getSelectedItem();
                if (selectedItem == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm.");
                    return;
                }

                String quantityStr = ipQtity.getText().trim();
                if (quantityStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng.");
                    return;
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ. Vui lòng nhập số.");
                    return;
                }

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.");
                    return;
                }
                boolean chonSeri = DSSeriDialog.createDSSeriDialog(quantity, selectedItem.getId(), seriProductBUS, selectedSeris);
                if (!chonSeri) {
                    return;
                }

                int idProduct = selectedItem.getId();
                ProductDTO product = productBUS.getProductById(idProduct);
                if (product == null) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm không tồn tại.");
                    return;
                }
                BigDecimal unitPrice = product.getPrice();
                BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));
                DecimalFormat df = new DecimalFormat("#,###");

                String tenSanPham = product.getNameCategory() + " " +
                        product.getNameCompany() + " " + product.getName() + " " +
                        product.getCpu() + "/" + product.getRam() + "/" +
                        product.getRom() + "/" + product.getGraphicsCard() + "/" +
                        product.getSizeScreen();

                // Add row with 5 elements
                model.addRow(new Object[]{
                        product.getIdProduct(), // Column 0: Product ID
                        tenSanPham,            // Column 1: Product Name
                        quantity,              // Column 2: Quantity
                        df.format(unitPrice),  // Column 3: Unit Price
                        df.format(totalPrice)  // Column 4: Total Price
                });

                // Calculate total amount using column 4
                BigDecimal tong = BigDecimal.ZERO;
                for (int i = 0; i < model.getRowCount(); i++) {
                    String thanhTienStr = model.getValueAt(i, 4).toString().replace(".", "");
                    tong = tong.add(new BigDecimal(thanhTienStr));
                }
                tongTien.setText("Tổng tiền: " + df.format(tong) + " VND");
                tongTien.setVisible(true);

                ipQtity.setText("");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi truy xuất sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        row3.add(lblProduct);
        row3.add(cmbProduct);
        row3.add(lblQuantity);
        row3.add(ipQtity);
        row3.add(btnAddCTHD);
        invoiceHeader.add(row1);
        invoiceHeader.add(row2);
        invoiceHeader.add(row3);
        content.add(invoiceHeader);
        content.add(Box.createVerticalStrut(10));

        String[] columnNames = {"Mã SP", "Tên sản phẩm", "Slg", "Đơn giá", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        // Enable word wrapping for the "Tên sản phẩm" column (index 1)
        table.getColumnModel().getColumn(1).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JTextArea area = new JTextArea(value != null ? value.toString() : "");
            area.setFont(new Font("Arial", Font.PLAIN, 14));
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setOpaque(true);

            if (isSelected) {
                area.setBackground(table1.getSelectionBackground());
                area.setForeground(table1.getSelectionForeground());
            } else {
                area.setBackground(table1.getBackground());
                area.setForeground(table1.getForeground());
            }

            area.setSize(table1.getColumnModel().getColumn(column).getWidth(), Integer.MAX_VALUE);
            int preferredHeight = area.getPreferredSize().height;
            if (table1.getRowHeight(row) < preferredHeight) {
                table1.setRowHeight(row, preferredHeight);
            }

            return area;
        });

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(24);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);  // Mã SP
        columnModel.getColumn(1).setPreferredWidth(200); // Tên sản phẩm
        columnModel.getColumn(2).setPreferredWidth(30);  // Số lượng
        columnModel.getColumn(3).setPreferredWidth(96); // Đơn giá
        columnModel.getColumn(4).setPreferredWidth(100); // Thành tiền
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(480, 100));
        content.add(scrollPane);

        content.add(Box.createVerticalStrut(10));
        tongTien = new JLabel();
        tongTien.setFont(new Font("Arial", Font.BOLD, 16));
        tongTien.setAlignmentX(Component.RIGHT_ALIGNMENT);
        content.add(tongTien);

        content.add(Box.createVerticalStrut(10));
        JLabel thankYou = new JLabel("Cảm ơn quý khách đã mua sắm!");
        thankYou.setFont(new Font("Arial", Font.ITALIC, 14));
        thankYou.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(thankYou);

        panel.add(panelSpacer, BorderLayout.WEST);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    public static void loadBillExportDetails(BillExDetailBUS billExDetailBUS, ProductBUS productBUS) {
        try {
            model.setRowCount(0);
            String idText = txtID.getText().trim();
            if (idText.isEmpty()) return;
            int id = Integer.parseInt(idText);

            ArrayList<BillExDetailDTO> billExDetailDTOs = billExDetailBUS.getBillExportDetailsByBillExId(id);
            BigDecimal total = BigDecimal.ZERO;
            DecimalFormat df = new DecimalFormat("#,###");
            for (BillExDetailDTO dto : billExDetailDTOs) {
                ProductDTO product = productBUS.getProductById(dto.getIdProduct());
                if (product == null) continue; // Skip if product not found
                String tenSanPham = product.getNameCategory() + " " + product.getNameCompany() + " " +
                        product.getName() + " " + product.getCpu() + "/" +
                        product.getRam() + "/" + product.getRom() + "/" +
                        product.getGraphicsCard() + "/" + product.getSizeScreen();
                int soLuong = dto.getQuantity();
                String donGia = df.format(dto.getUnitPrice());
                String thanhTien = df.format(dto.getUnitPrice().multiply(new BigDecimal(soLuong)));
                total = total.add(dto.getUnitPrice().multiply(new BigDecimal(soLuong)));

                // Add row with 5 elements
                model.addRow(new Object[]{
                        dto.getIdProduct(), // Column 0: Product ID
                        tenSanPham,        // Column 1: Product Name
                        soLuong,           // Column 2: Quantity
                        donGia,            // Column 3: Unit Price
                        thanhTien          // Column 4: Total Price
                });
            }

            tongTien.setText("Tổng tiền: " + df.format(total) + " VND");
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static JTextField getTxtDate() {
        return txtDate;
    }

    public static JTextField getTxtID() {
        return txtID;
    }

    public static JTextField getTxtKH() {
        return txtKH;
    }

    public static JTextField getTxtNV() {
        return txtNV;
    }

    public static DefaultTableModel getTable() {
        return model;
    }

    public static JLabel getTongTien() {
        return tongTien;
    }

    public static void isVisible(int status) {
        if (status == 0) {
            txtDate.setVisible(false);
            txtID.setVisible(false);
            txtNV.setVisible(false);
            lblID.setVisible(false);
            lblNV.setVisible(false);
            lblDate.setVisible(false);
        } else {
            txtDate.setVisible(true);
            txtID.setVisible(true);
            txtNV.setVisible(true);
            lblID.setVisible(true);
            lblNV.setVisible(true);
            lblDate.setVisible(true);
        }
    }
    public static ArrayList<String> getSelectedSeris(){
        return selectedSeris;
    }
}