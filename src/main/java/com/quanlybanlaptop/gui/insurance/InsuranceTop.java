package com.quanlybanlaptop.gui.insurance;

import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.gui.component.RoundedComponent;
import com.quanlybanlaptop.util.ExcelExporter;
import com.quanlybanlaptop.util.ImageLoader;
import com.quanlybanlaptop.bus.AuthoritiesBUS;
import com.quanlybanlaptop.bus.CTQBUS;
import com.quanlybanlaptop.bus.InsuranceBUS;
import com.quanlybanlaptop.bus.SeriProductBUS;
import com.quanlybanlaptop.dao.AuthoritiesDAO;
import com.quanlybanlaptop.dao.CTQDAO;
import com.quanlybanlaptop.dao.DatabaseConnection;
import com.quanlybanlaptop.dao.RoleDAO;
import com.quanlybanlaptop.dto.AdminDTO;
import com.quanlybanlaptop.dto.InsuranceClaimDTO;
import com.quanlybanlaptop.dto.InsuranceDTO;
import com.quanlybanlaptop.bus.InsuranceClaimBUS;
import com.quanlybanlaptop.bus.RoleBUS;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InsuranceTop extends JPanel {
    private RoundedButton btnAddInsurance;
    private RoundedButton btnAddClaim;
    private RoundedButton btnEdit;
    private RoundedButton btnDelete;
    private RoundedButton btnRefresh;
    private RoundedButton btnExportExcel;
    private RoundedButton btnExportPDF;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private InsuranceBUS insuranceBUS;
    private SeriProductBUS seriProductBUS;
    private InsuranceClaimBUS insuranceClaimBUS;
    private InsuranceTable insuranceTable;

    public InsuranceTop(AdminDTO adminDTO,InsuranceBUS insuranceBUS, SeriProductBUS seriProductBUS, InsuranceClaimBUS insuranceClaimBUS, InsuranceTable insuranceTable) {
        this.insuranceBUS = insuranceBUS;
        this.seriProductBUS = seriProductBUS;
        this.insuranceClaimBUS = insuranceClaimBUS;
        this.insuranceTable = insuranceTable;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
          Connection cnn = DatabaseConnection.getConnection();
        
        CTQDAO ctqDAO = new CTQDAO(cnn);
        CTQBUS ctqBUS = new CTQBUS(ctqDAO);
        btnAddInsurance = new RoundedButton("Thêm bảo hành", ImageLoader.loadResourceImage("/img/add_control.png"));
        btnAddInsurance.setImageSize(32, 32);
        btnAddInsurance.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 3, 1));
        btnAddInsurance.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            InsuranceAdd dialog = new InsuranceAdd(parentFrame, insuranceBUS, seriProductBUS);
            dialog.setVisible(true);
        });

        btnAddClaim = new RoundedButton("Thêm yêu cầu", ImageLoader.loadResourceImage("/img/addDo.png"));
        btnAddClaim.setImageSize(32, 32);
        btnAddClaim.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 3, 3));
        btnAddClaim.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            InsuranceRequestAdd dialog = new InsuranceRequestAdd(parentFrame, insuranceBUS, insuranceClaimBUS);
            dialog.setVisible(true);
        });

        btnEdit = new RoundedButton("Sửa", ImageLoader.loadResourceImage("/img/edit_control.png"));
        btnEdit.setImageSize(32, 32);
        btnEdit.setEnabled(ctqBUS.isChecked(adminDTO.getIdRole(), 3, 2));
        btnEdit.addActionListener(e -> {
            int selectedRow = insuranceTable.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa trạng thái!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Chỉ cho sửa khi đang ở bảng yêu cầu bảo hành
            if (insuranceTable.getTable().getColumnCount() == 8 &&
                "Trạng thái".equals(insuranceTable.getTable().getColumnName(7))) {
                String trangThai = (String) insuranceTable.getTable().getValueAt(selectedRow, 7);
                if (!"Đã xử lý".equalsIgnoreCase(trangThai)) {
                    int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Xác nhận đã hoàn thành sửa chữa cho khách hàng?",
                        "Xác nhận",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (confirm == JOptionPane.OK_OPTION) {
                        int idClaim = (int) insuranceTable.getTable().getValueAt(selectedRow, 0);
                        boolean ok = insuranceClaimBUS.updateStatusToProcessed(idClaim);
                        if (ok) {
                            insuranceTable.loadYeuCauBaoHanhTable();
                            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Yêu cầu này đã được xử lý!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnDelete = new RoundedButton("Xóa", ImageLoader.loadResourceImage("/img/delete_control.png"));
        btnDelete.setImageSize(32, 32);

        btnRefresh = new RoundedButton("Làm mới", ImageLoader.loadResourceImage("/img/refresh_control.png"));
        btnRefresh.setImageSize(32, 32);
        btnRefresh.addActionListener(e -> {
            try {
                searchField.setText("");
                searchTypeComboBox.setSelectedIndex(0);
                JTable table = insuranceTable.getTable();
                // Làm mới đúng bảng đang hiển thị
                if (table.getColumnCount() == 8 && "Trạng thái".equals(table.getColumnName(7))) {
                    insuranceTable.loadYeuCauBaoHanhTable();
                } else {
                    List<InsuranceDTO> list = insuranceBUS.getAllInsuranceTable();
                    insuranceTable.reloadBaoHanhData(list);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Làm mới dữ liệu thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExportExcel = new RoundedButton("Xuất Excel", ImageLoader.loadResourceImage("/img/xuatExcel.png"));
        btnExportExcel.setImageSize(32, 32);
        btnExportExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) filePath += ".xlsx";

                JTable table = insuranceTable.getTable();
                List<String> headers = new ArrayList<>();
                List<List<Object>> data = new ArrayList<>();
                try {
                    if (table.getColumnCount() == 8 && "Trạng thái".equals(table.getColumnName(7))) {
                        // Xuất Excel cho bảng yêu cầu bảo hành
                        headers = Arrays.asList("ID YC", "Khách hàng", "Admin", "Sản phẩm", "Số Seri", "Mô tả", "Ngày xử lý", "Trạng thái");
                        for (InsuranceClaimDTO dto : insuranceClaimBUS.getAllInsuranceClaimsWithNames()) {
                            data.add(Arrays.asList(
                                dto.getIdClaim(),
                                dto.getCustomerName(),
                                dto.getAdminName(),
                                dto.getProductName(),
                                dto.getNumSeri(),
                                dto.getDescription(),
                                dto.getDateProcessed(),
                                dto.getStatus()
                            ));
                        }
                        ExcelExporter.exportToExcel(headers, data, "Yêu cầu bảo hành", filePath);
                    } else {
                        // Xuất Excel cho bảng bảo hành
                        headers = Arrays.asList("ID", "Admin", "Khách hàng", "Sản phẩm", "Số Seri", "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú");
                        for (InsuranceDTO dto : insuranceBUS.getAllInsuranceTable()) {
                            data.add(Arrays.asList(
                                dto.getIdInsurance(),
                                dto.getAdminName(),
                                dto.getCustomerName(),
                                dto.getProductName(),
                                dto.getNumSeri(),
                                dto.getStartDate(),
                                dto.getEndDate(),
                                dto.getDescription()
                            ));
                        }
                        ExcelExporter.exportToExcel(headers, data, "Bảo hành", filePath);
                    }
                    JOptionPane.showMessageDialog(this, "Xuất Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExportPDF = new RoundedButton("Xuất PDF", ImageLoader.loadResourceImage("/img/pdf.png"));
        btnExportPDF.setImageSize(32, 32);

        String[] searchTypes = {"Số Seri", "Khách hàng", "Sản phẩm"};
        searchTypeComboBox = RoundedComponent.createRoundedComboBox(searchTypes, 10);
        searchTypeComboBox.setPreferredSize(new Dimension(150, 35));

        searchField = RoundedComponent.createRoundedTextField(10);
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setToolTipText("Nhập từ khóa tìm kiếm...");

        // Sự kiện tìm kiếm realtime khi gõ phím
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                String keyword = searchField.getText().trim();
                String searchType = (String) searchTypeComboBox.getSelectedItem();
                try {
                    JTable table = insuranceTable.getTable();
                    // Nếu đang ở bảng yêu cầu bảo hành (có cột "Trạng thái" ở vị trí 7)
                    if (table.getColumnCount() == 8 && "Trạng thái".equals(table.getColumnName(7))) {
                        List<InsuranceClaimDTO> result = insuranceClaimBUS.searchInsuranceClaim(keyword, searchType);
                        insuranceTable.reloadYeuCauBaoHanhData(result != null ? result : new ArrayList<>());
                    } else {
                        List<InsuranceDTO> result = insuranceBUS.searchInsurance(keyword, searchType);
                        insuranceTable.reloadBaoHanhData(result != null ? result : new ArrayList<>());
                    }
                } catch (Exception ex) {
                    // Có thể log hoặc thông báo lỗi nếu cần
                }
            }
        });

        add(btnAddInsurance);
        add(btnAddClaim);
        add(btnEdit);
        add(btnRefresh);
        add(btnExportExcel);
        add(searchTypeComboBox);
        add(searchField);
    }

    public RoundedButton getBtnAddInsurance() { return btnAddInsurance; }
    public RoundedButton getBtnAddClaim() { return btnAddClaim; }
    public RoundedButton getBtnEdit() { return btnEdit; }
    public RoundedButton getBtnDelete() { return btnDelete; }
    public RoundedButton getBtnRefresh() { return btnRefresh; }
    public RoundedButton getBtnExportExcel() { return btnExportExcel; }
    public RoundedButton getBtnExportPDF() { return btnExportPDF; }
    public JTextField getSearchField() { return searchField; }
    public JComboBox<String> getSearchTypeComboBox() { return searchTypeComboBox; }
}