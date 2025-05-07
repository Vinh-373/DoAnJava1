package com.quanlybanlaptop.gui.stock;

import com.quanlybanlaptop.bus.BillImportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dao.BillImportDAO;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.BillImportDTO;
import com.quanlybanlaptop.dto.ProductDTO;
import com.quanlybanlaptop.dto.SeriProductDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.gui.product.ProductTable;

import org.apache.poi.hpsf.Decimal;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImportStock {
    private static JTextField txtQuantity, txtGiaNhap;
    private static JTextArea txtSeriArea;
    private static ProductDTO productDTO;

    public static void showImportStock(AdminDTO adminDTO, SeriProductBUS seriProductBUS, ProductBUS productBUS, BillImportBUS billImportBUS) throws SQLException {
        int selectedRow = StockTableL.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm trước khi xuất kho.");
            return;
        }
        int idProduct = (int) StockTableL.getTableModel().getValueAt(selectedRow, 0);
        productDTO = productBUS.getProductById(idProduct);
        JDialog importDialog = new JDialog();
        importDialog.setModal(true);
        importDialog.setSize(350, 450);
        importDialog.setLayout(new BorderLayout());
        importDialog.setLocationRelativeTo(null);
        importDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        importDialog.setTitle("Nhập kho");

        // Panel chính
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel header = new JLabel("NHẬP KHO");
        header.setFont(new Font("Tahoma", Font.BOLD, 20));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel số lượng và mã sản phẩm
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        quantityPanel.setBackground(Color.white);
        quantityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblQuantity = new JLabel("Số lượng:");
        lblQuantity.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtQuantity = RoundedComponent.createRoundedTextField(10);
        txtQuantity.setPreferredSize(new Dimension(70, 30));
        JLabel lblMaSP = new JLabel("Mã SP: " + productDTO.getIdProduct());
        quantityPanel.add(lblQuantity);
        quantityPanel.add(txtQuantity);
        quantityPanel.add(lblMaSP);
        quantityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel nhập Excel (chưa xử lý logic)
        JPanel excelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        RoundedButton btnImportExcel = new RoundedButton("Nhập Excel");
        btnImportExcel.setPreferredSize(new Dimension(120, 30));
        JLabel gianhap = new JLabel("Đơn giá:");
        txtGiaNhap = RoundedComponent.createRoundedTextField(5);
        txtGiaNhap.setPreferredSize(new Dimension(100, 30));
        excelPanel.add(btnImportExcel);
        excelPanel.add(gianhap);
        excelPanel.add(txtGiaNhap);
        excelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        excelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label và text area nhập seri
        JLabel lblSeri = new JLabel("Danh sách Seri:");
        lblSeri.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSeri.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtSeriArea = new JTextArea();
        txtSeriArea.setLineWrap(true);
        txtSeriArea.setWrapStyleWord(true);
        txtSeriArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtSeriArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel nút OK - Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        RoundedButton btnOk = new RoundedButton("OK");
        RoundedButton btnCancel = new RoundedButton("Cancel");
        btnOk.setPreferredSize(new Dimension(80, 30));
        btnCancel.setPreferredSize(new Dimension(80, 30));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnOk);

        // Xử lý nút Cancel
        btnCancel.addActionListener(e -> importDialog.dispose());

        // Xử lý nút OK
        btnOk.addActionListener(e -> {
            String soLuongStr = txtQuantity.getText().trim();
            BigDecimal giaNhap = new BigDecimal(txtGiaNhap.getText().trim());
            String[] danhSachSeri = txtSeriArea.getText().trim().split("\\n");

            // Kiểm tra dữ liệu
            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(importDialog, "Vui lòng nhập số lượng.");
                return;
            }
            if(giaNhap.compareTo(BigDecimal.ZERO) <= 0 || giaNhap == null) {
                JOptionPane.showMessageDialog(importDialog, "Giá nhập không hợp lệ.");
                return;

            }
            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(importDialog, "Số lượng phải là số nguyên.");
                return;
            }

            if (soLuong != danhSachSeri.length) {
                JOptionPane.showMessageDialog(importDialog, "Số lượng và số dòng Seri không khớp.");
                return;
            }

            // Tạo danh sách DTO Seri
            ArrayList<SeriProductDTO> listSeri = new ArrayList<>();
            for (String seri : danhSachSeri) {
                String seriTrimmed = seri.trim();
                if (!seriTrimmed.isEmpty()) {
                    listSeri.add(new SeriProductDTO(seriTrimmed, productDTO.getIdProduct(), 0));
                }
            }

            // Gọi BUS để xử lý lưu và cập nhật
            boolean success = seriProductBUS.themDanhSachSeri(listSeri);
            if (success) {
                boolean capNhat = false;
                boolean themBill = false;
                try {
                    ProductDTO productDTO = productBUS.getProductById(idProduct);
                    BigDecimal totalPrice = giaNhap.multiply(BigDecimal.valueOf(soLuong));
                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                    BillImportDTO billImportDTO = new BillImportDTO(
                            0,
                            adminDTO.getId(),
                            productDTO.getIdProduct(),
                            giaNhap,
                            totalPrice,
                            soLuong * -1,
                            currentTimestamp,
                            adminDTO.getName()
                    );

                    themBill = billImportBUS.insertBillImport(billImportDTO);
                    capNhat = productBUS.updateqtityStock(productDTO.getIdProduct(), listSeri.size());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (capNhat && themBill) {
                    StockTableL.loadStockTable(productBUS);
                    StockTableR.loadBillImExTable(billImportBUS,0);
                    JOptionPane.showMessageDialog(importDialog, "Nhập kho thành công!");
                    importDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(importDialog, "Nhập Seri thành công nhưng cập nhật số lượng thất bại.");
                }
            } else {
                JOptionPane.showMessageDialog(importDialog, "Lỗi seri trùng.");
            }
        });
        btnImportExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("D:/Doanjava/QuanLyBanLaptop/src/main/resources/importData"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(file);
                     XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    // Sử dụng DataFormatter để chuyển tất cả giá trị ô thành chuỗi
                    DataFormatter formatter = new DataFormatter();
                    int count = 0; // Đếm số dòng đã nhập vào txtSeriArea

                    // Xóa nội dung hiện tại của txtSeriArea (nếu cần)
                    txtSeriArea.setText(""); // Xóa nội dung cũ trong txtSeriArea

                    for (Row row : sheet) {
                        if (row == null || row.getRowNum() == 0) continue; // Bỏ qua header và dòng null

                        // Đọc giá trị seri từ cột 0
                        String seri = formatter.formatCellValue(row.getCell(0));
                        if (seri.isEmpty()) {
                            continue; // Bỏ qua nếu seri rỗng
                        }

                        // Thêm seri vào txtSeriArea, mỗi seri trên một dòng
                        txtSeriArea.append(seri + "\n");

                        count++; // Tăng biến đếm
                    }

                    // Gán số dòng đã nhập vào txtQuantity
                    txtQuantity.setText(String.valueOf(count));

                    // Kiểm tra xem có dữ liệu được thêm vào txtSeriArea không
                    if (count > 0) {
                        JOptionPane.showMessageDialog(null, "Đã nhập " + count + " dòng từ Excel vào txtSeriArea!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Không có dữ liệu để nhập từ file Excel.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi nhập Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        // Thêm các thành phần vào panel
        panel.add(header);
        panel.add(Box.createVerticalStrut(10));
        panel.add(excelPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quantityPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblSeri);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scrollPane);

        // Hiển thị dialog
        importDialog.getContentPane().add(panel, BorderLayout.CENTER);
        importDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        importDialog.setVisible(true);
    }
}