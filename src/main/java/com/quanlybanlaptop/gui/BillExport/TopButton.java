package com.quanlybanlaptop.gui.BillExport;

import com.itextpdf.text.Paragraph;
import com.quanlybanlaptop.bus.BillExDetailBUS;
import com.quanlybanlaptop.bus.BillExportBUS;
import com.quanlybanlaptop.bus.ProductBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.BillExDetailDTO;
import com.quanlybanlaptop.dto.BillExportDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.util.ImageLoader;
import com.toedter.calendar.JDateChooser;
import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class TopButton {
    private static ArrayList<BillExportDTO> billExports; // Lưu trữ dữ liệu gốc
    private static JDateChooser dateFromChooser, dateToChooser;
    private static JComboBox<String> cmb;
    private static JTextField txtSearch;

    public static JPanel createTopPanel(AdminDTO adminDTO, BillExportBUS billExportBUS, BillExDetailBUS billExDetailBUS, SeriProductBUS seriProductBUS, ProductBUS productBUS) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Khởi tạo các nút
        RoundedButton btnAdd = new RoundedButton("Thêm", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAdd.setImageSize(32, 32);
        RoundedButton btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        RoundedButton btnDelete = new RoundedButton("Xóa", ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);
        RoundedButton btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);
        RoundedButton btnExPFD = new RoundedButton("Xuất PFD", ImageLoader.loadResourceImage("/img/pdf.png"));
        btnExPFD.setImageSize(32, 32);

        // Khởi tạo các thành phần tìm kiếm
        String[] op = {"Mã phiếu", "Nhân viên", "Khách hàng"};
        cmb = RoundedComponent.createRoundedComboBox(op, 10);
        JLabel fromLb = new JLabel("Từ:");
        JLabel toLb = new JLabel("Đến:");
        dateFromChooser = new JDateChooser();
        dateFromChooser.setPreferredSize(new Dimension(100, 30));
        dateToChooser = new JDateChooser();
        dateToChooser.setPreferredSize(new Dimension(100, 30));
        txtSearch = RoundedComponent.createRoundedTextField(10);
        txtSearch.setPreferredSize(new Dimension(200, 30));

        // Lấy dữ liệu gốc từ billExportBUS
        try {
            billExports = billExportBUS.getAllBillExports();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            billExports = new ArrayList<>();
        }

        // Sự kiện cho btnRefresh
        btnRefresh.addActionListener(e -> {
            try {
                billExports = billExportBUS.getAllBillExports();
                ExportTable.loadBillExData(billExportBUS);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi làm mới dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            ExportRight.getTxtNV().setText("");
            ExportRight.getTxtKH().setText("");
            ExportRight.getTxtID().setText("");
            ExportRight.getTxtDate().setText("");
            ExportRight.getTable().setRowCount(0);
            ExportRight.getTongTien().setText("");
            ExportRight.isVisible(0);
            txtSearch.setText("");
            dateFromChooser.setDate(null);
            dateToChooser.setDate(null);
        });

        // Sự kiện cho btnAdd
        btnAdd.addActionListener(e -> {
            boolean visib = ExportRight.getTxtNV().isVisible();
            if (visib) {
                JOptionPane.showMessageDialog(null, "Bấm nút Làm mới trước khi thêm");
                return;
            }

            if (ExportRight.getTable().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Chi tiết hóa đơn không được để trống");
                return;
            }

            DefaultTableModel tableModel = ExportRight.getTable();
            int totalPrd = 0;
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Object value = tableModel.getValueAt(row, 2);
                if (value == null || value.toString().trim().isEmpty()) {
                    continue;
                }
                String stringValue = value.toString().replaceAll("[^0-9]", "");
                int number = Integer.parseInt(stringValue);
                totalPrd += number;
            }

            BigDecimal totalPrice = new BigDecimal(ExportRight.getTongTien().getText().replaceAll("[^0-9]", ""));
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            BillExportDTO billExportDTO = new BillExportDTO(0, adminDTO.getId(), Integer.parseInt(ExportRight.getTxtKH().getText()), totalPrd, totalPrice, currentTimestamp, 1, adminDTO.getName(), "Hoàng Minh Tú");
            int idBill = 0;
            try {
                billExportBUS.addBillExport(billExportDTO);
                billExports = billExportBUS.getAllBillExports();
                idBill = billExports.get(0).getIdBillEx();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            ArrayList<String> DsSeri = ExportRight.getSelectedSeris();
            int index = 0;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object value = tableModel.getValueAt(i, 2);
                Object idProduct = tableModel.getValueAt(i, 0);
                Object unitPrice = tableModel.getValueAt(i, 3);
                if (value == null || value.toString().trim().isEmpty() || idProduct == null || idProduct.toString().trim().isEmpty()
                        || unitPrice == null || unitPrice.toString().trim().isEmpty()) {
                    continue;
                }
                String stringValue = value.toString().replaceAll("[^0-9]", "");
                String stringIdProduct = idProduct.toString().replaceAll("[^0-9]", "");
                String stringUnitPrice = unitPrice.toString().replaceAll("[^0-9]", "");
                int number = Integer.parseInt(stringValue);
                int idProduc = Integer.parseInt(stringIdProduct);
                try {
                    productBUS.updateqtityStore(idProduc, number);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng tồn kho: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                BigDecimal price = new BigDecimal(stringUnitPrice);
                for (int j = 0; j < number; j++) {
                    BillExDetailDTO billExDetailDTO = new BillExDetailDTO(idBill, idProduc, number, price, DsSeri.get(index));
                    try {
                        billExDetailBUS.addBillExportDetail(billExDetailDTO);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                        return;
                    }
                    index++;
                }
            }
            seriProductBUS.updateSeriProduct(DsSeri, 2);
            JOptionPane.showMessageDialog(null, "Thêm hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            ExportTable.loadBillExData(billExportBUS);
            ExportRight.getTxtNV().setText("");
            ExportRight.getTxtKH().setText("");
            ExportRight.getTxtID().setText("");
            ExportRight.getTxtDate().setText("");
            ExportRight.getTable().setRowCount(0);
            ExportRight.getTongTien().setText("");
            ExportRight.isVisible(0);
        });

        // Sự kiện cho btnDelete
        btnDelete.addActionListener(e -> {
            boolean visib = ExportRight.getTxtNV().isVisible();
            if (!visib) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một hóa đơn");
                return;
            }
            int idbill = Integer.parseInt(ExportRight.getTxtID().getText());
            try {
                billExportBUS.deleteBillExport(idbill);
                billExports = billExportBUS.getAllBillExports();
                JOptionPane.showMessageDialog(null, "Xóa bill mã " + idbill + " thành công");
                ExportTable.loadBillExData(billExportBUS);
                ExportRight.getTxtNV().setText("");
                ExportRight.getTxtKH().setText("");
                ExportRight.getTxtID().setText("");
                ExportRight.getTxtDate().setText("");
                ExportRight.getTable().setRowCount(0);
                ExportRight.getTongTien().setText("");
                ExportRight.isVisible(0);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Sự kiện cho dateFromChooser
        dateFromChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                applyCombinedFilter(billExportBUS);
            }
        });

        // Sự kiện cho dateToChooser
        dateToChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                applyCombinedFilter(billExportBUS);
            }
        });

        // Sự kiện cho txtSearch
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyCombinedFilter(billExportBUS);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyCombinedFilter(billExportBUS);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyCombinedFilter(billExportBUS);
            }
        });
        btnExPFD.addActionListener(e->{
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);

            try {
                String path = "D:/Doanjava/QuanLyBanLaptop/src/main/resources/exportData/hoadon_"+ExportRight.getTxtID().getText()+".pdf";
                PdfWriter.getInstance(document, new FileOutputStream(path));

                document.open();

                // Đường dẫn đến font Roboto trong thư mục Windows
                BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                com.itextpdf.text.Font robotoFont = new com.itextpdf.text.Font(baseFont, 12, com.itextpdf.text.Font.NORMAL); // Sử dụng font Roboto

                // Tiêu đề
                Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG",
                        new com.itextpdf.text.Font(baseFont, 16, com.itextpdf.text.Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\nCửa hàng Laptop Vinh Hoàng", robotoFont));
                document.add(new Paragraph("Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM", robotoFont));
                document.add(new Paragraph("SĐT: 0123 456 789\n", robotoFont));

                // Mã hóa đơn và thời gian
                document.add(new Paragraph("Mã hóa đơn: " + ExportRight.getTxtID().getText()+"     Thời gian xuất: "+ ExportRight.getTxtDate().getText(), robotoFont));
                document.add(new Paragraph("NV: " + ExportRight.getTxtNV().getText()+"     KH: "+ExportRight.getTxtKH().getText()+"\n\n", robotoFont));

                // Bảng sản phẩm
                PdfPTable table = new PdfPTable(new float[]{1, 5, 1, 2, 2});
                table.setWidthPercentage(100);

                table.addCell(new PdfPCell(new Phrase("Mã SP", robotoFont)));
                table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", robotoFont)));
                table.addCell(new PdfPCell(new Phrase("SLg", robotoFont)));
                table.addCell(new PdfPCell(new Phrase("Đơn giá", robotoFont)));
                table.addCell(new PdfPCell(new Phrase("Thành tiền", robotoFont)));
                DefaultTableModel model = ExportRight.getTable();

                // Giả sử model là một DefaultTableModel hoặc bất kỳ TableModel nào có dữ liệu
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        // Lấy giá trị từ model tại vị trí row, col
                        Object cellValue = model.getValueAt(row, col);

                        // Thêm giá trị vào ô của bảng PDF
                        table.addCell(cellValue != null ? cellValue.toString() : "");
                    }
                }

                document.add(table);

                document.add(new Paragraph("\n" +ExportRight.getTongTien().getText(),
                        new com.itextpdf.text.Font(baseFont, 12, com.itextpdf.text.Font.BOLD)));

                document.add(new Paragraph("\nCảm ơn quý khách đã mua sắm!\n",
                        new com.itextpdf.text.Font(baseFont, 10, com.itextpdf.text.Font.ITALIC)));

            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            } finally {
                document.close();
            }
            JOptionPane.showMessageDialog(null, "✅ File hóa đơn đã được tạo thành công");
        });
        // Sắp xếp các thành phần trên panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridwidth = 1;

        gbc.gridx = 0; panel.add(btnAdd, gbc);
        gbc.gridx = 1; panel.add(btnEdit, gbc);
        gbc.gridx = 2; panel.add(btnDelete, gbc);
        gbc.gridx = 3; panel.add(btnRefresh, gbc);
        gbc.gridx = 4; panel.add(btnExPFD, gbc);
        gbc.gridx = 5; gbc.weightx = 0; panel.add(fromLb, gbc);
        gbc.gridx = 6; gbc.weightx = 0; panel.add(dateFromChooser, gbc);
        gbc.gridx = 7; gbc.weightx = 0; panel.add(toLb, gbc);
        gbc.gridx = 8; gbc.weightx = 0; panel.add(dateToChooser, gbc);
        gbc.gridx = 9; gbc.weightx = 0; panel.add(cmb, gbc);
        gbc.gridx = 10; gbc.gridwidth = 1; gbc.weightx = 1.0; panel.add(txtSearch, gbc);

        return panel;
    }

    // Hàm gọi BUS để lọc dữ liệu
    private static void applyCombinedFilter(BillExportBUS billExportBUS) {
        Date fromDate = dateFromChooser.getDate();
        Date toDate = dateToChooser.getDate();
        String searchCriteria = (String) cmb.getSelectedItem();
        String searchText = txtSearch.getText().trim();

        ArrayList<BillExportDTO> filteredList = billExportBUS.filterBillExports(fromDate, toDate, searchCriteria, searchText);

        // Cập nhật bảng với danh sách đã lọc
        ExportTable.loadBillExData1(filteredList);

        // Thông báo nếu không tìm thấy kết quả
        if (filteredList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}