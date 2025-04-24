package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.BillExportDAO;
import com.quanlybanlaptop.dto.BillExportDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.sql.SQLException;

public class BillExportBUS {
    private BillExportDAO billExportDAO;

    public BillExportBUS(BillExportDAO billExportDAO) {
        this.billExportDAO = billExportDAO;
    }

    public ArrayList<BillExportDTO> getAllBillExports() throws SQLException {
        return billExportDAO.getAllActiveBillExports();
    }

    public BillExportDTO getBillExportById(int id) throws SQLException {
        return billExportDAO.getBillExportById(id);
    }
    public boolean addBillExport(BillExportDTO billExportDTO) throws SQLException {
        return billExportDAO.insertBillExport(billExportDTO);
    }
    public boolean deleteBillExport(int id) throws SQLException {
        return billExportDAO.deleteBillExport(id);
    }
    public ArrayList<BillExportDTO> filterBillExports(Date fromDate, Date toDate, String criteria, String searchText) {
        ArrayList<BillExportDTO> filteredList = new ArrayList<>();
        ArrayList<BillExportDTO> ds;

        try {
            ds = getAllBillExports(); // Lấy danh sách từ DAO
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            return filteredList; // Trả về danh sách rỗng nếu có lỗi
        }

        Timestamp fromTimestamp = null;
        Timestamp toTimestamp = null;
        if (fromDate != null && toDate != null) {
            fromTimestamp = new Timestamp(fromDate.getTime());
            toTimestamp = new Timestamp(toDate.getTime() + 86399999); // Thêm 1 ngày - 1ms
        }
        // Duyệt qua danh sách hóa đơn
        for (BillExportDTO bill : ds) {
            boolean matchesDate = true;
            boolean matchesText = true;

            // Lọc theo ngày
            if (fromTimestamp != null && toTimestamp != null) {
                Timestamp billDate = bill.getDateEx();
                if (billDate != null) {
                    matchesDate = billDate.compareTo(fromTimestamp) >= 0 && billDate.compareTo(toTimestamp) <= 0;
                } else {
                    matchesDate = false;
                }
            }

            // Lọc theo text
            if (searchText != null && !searchText.isEmpty()) {
                switch (criteria) {
                    case "Mã phiếu":
                        matchesText = String.valueOf(bill.getIdBillEx()).toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Nhân viên":
                        matchesText = bill.getNameAdmin() != null && bill.getNameAdmin().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Khách hàng":
                        matchesText = bill.getNameCustomer() != null && bill.getNameCustomer().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    default:
                        matchesText = false;
                        break;
                }
            }

            // Nếu hóa đơn thỏa mãn cả hai điều kiện, thêm vào danh sách lọc
            if (matchesDate && matchesText) {
                filteredList.add(bill);
            }
        }

        return filteredList;
    }
}

