package com.quanlybanlaptop.gui.insurance;

import com.quanlybanlaptop.bus.InsuranceBUS;
import com.quanlybanlaptop.bus.InsuranceClaimBUS;
import com.quanlybanlaptop.dto.InsuranceDTO;
import com.quanlybanlaptop.gui.component.RoundedButton;
import com.quanlybanlaptop.dto.InsuranceClaimDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InsuranceTable extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnBaoHanh;
    private JButton btnYeuCauBaoHanh;
    private InsuranceBUS insuranceBUS;
    private InsuranceClaimBUS insuranceClaimBUS;
    private JButton btnSuaTrangThai;

    public InsuranceTable(InsuranceBUS insuranceBUS, InsuranceClaimBUS insuranceClaimBUS) {

        this.insuranceBUS = insuranceBUS;
        this.insuranceClaimBUS = insuranceClaimBUS;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnBaoHanh = new JButton("Bảo hành");
        btnYeuCauBaoHanh = new JButton("Yêu cầu bảo hành");

        btnBaoHanh.addActionListener(e -> loadBaoHanhTable());
        btnYeuCauBaoHanh.addActionListener(e -> loadYeuCauBaoHanhTable());
        topPanel.add(btnBaoHanh);
        topPanel.add(btnYeuCauBaoHanh);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Mặc định hiển thị bảng bảo hành
        loadBaoHanhTable();
    }

    public JTable getTable() {
        return table;
    }

    private void suaTrangThaiYeuCau() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một yêu cầu để sửa trạng thái!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kiểm tra đang ở bảng yêu cầu bảo hành (dựa vào số cột hoặc tên cột)
        if (table.getColumnCount() == 8 && "Trạng thái".equals(table.getColumnName(7))) {
            String trangThai = (String) table.getValueAt(selectedRow, 7);
            if (!"Đã xử lý".equalsIgnoreCase(trangThai)) {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Xác nhận đã hoàn thành sửa chữa cho khách hàng?",
                    "Xác nhận",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (confirm == JOptionPane.OK_OPTION) {
                    int idClaim = (int) table.getValueAt(selectedRow, 0);
                    // Gọi BUS để cập nhật trạng thái thành "Đã xử lý" trên DB
                    boolean ok = insuranceClaimBUS.updateStatusToProcessed(idClaim);
                    if (ok) {
                        // Sau khi cập nhật DB, load lại bảng từ DB để đảm bảo dữ liệu mới nhất
                        loadYeuCauBaoHanhTable();
                        JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Yêu cầu này đã được xử lý!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void loadBaoHanhTable() {
        String[] columnNames = {"ID", "Admin", "Khách hàng", "Sản phẩm", "Số Seri", "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú"};
        tableModel.setDataVector(null, columnNames);
        try {
            List<InsuranceDTO> list = insuranceBUS.getAllInsuranceTable();
            for (InsuranceDTO dto : list) {
                Object[] row = {
                    dto.getIdInsurance(),
                    dto.getAdminName(),
                    dto.getCustomerName(),
                    dto.getProductName(),
                    dto.getNumSeri(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getDescription()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu bảo hành!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hiển thị bảng yêu cầu bảo hành với danh sách truyền vào (dùng cho tìm kiếm)
    public void reloadYeuCauBaoHanhData(List<InsuranceClaimDTO> list) {
        String[] columnNames = {"ID YC", "Khách hàng", "Admin", "Sản phẩm", "Số Seri", "Mô tả", "Ngày xử lý", "Trạng thái"};
        tableModel.setDataVector(null, columnNames);
        for (InsuranceClaimDTO dto : list) {
            Object[] row = {
                dto.getIdClaim(),
                dto.getCustomerName(),
                dto.getAdminName(),
                dto.getProductName(),
                dto.getNumSeri(),
                dto.getDescription(),
                dto.getDateProcessed(),
                dto.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    // Hiển thị bảng yêu cầu bảo hành mặc định
    public void loadYeuCauBaoHanhTable() {
        String[] columnNames = {"ID YC", "Khách hàng", "Admin", "Sản phẩm", "Số Seri", "Mô tả", "Ngày xử lý", "Trạng thái"};
        tableModel.setDataVector(null, columnNames);
        try {
            List<InsuranceClaimDTO> list = insuranceClaimBUS.getAllInsuranceClaimsWithNames();
            for (InsuranceClaimDTO dto : list) {
                Object[] row = {
                    dto.getIdClaim(),
                    dto.getCustomerName(),
                    dto.getAdminName(),
                    dto.getProductName(),
                    dto.getNumSeri(),
                    dto.getDescription(),
                    dto.getDateProcessed(),
                    dto.getStatus()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu yêu cầu bảo hành!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm reloadData cho bảng bảo hành (nếu cần tìm kiếm)
    public void reloadBaoHanhData(List<InsuranceDTO> list) {
        String[] columnNames = {"ID", "Admin", "Khách hàng", "Sản phẩm", "Số Seri", "Ngày bắt đầu", "Ngày kết thúc", "Ghi chú"};
        tableModel.setDataVector(null, columnNames);
        for (InsuranceDTO dto : list) {
            Object[] row = {
                dto.getIdInsurance(),
                dto.getAdminName(),
                dto.getCustomerName(),
                dto.getProductName(),
                dto.getNumSeri(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getDescription()
            };
            tableModel.addRow(row);
        }
    }
}