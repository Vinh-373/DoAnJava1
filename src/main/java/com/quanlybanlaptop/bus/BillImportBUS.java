package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.BillImportDAO;
import com.quanlybanlaptop.dto.BillExportDTO;
import com.quanlybanlaptop.dto.BillImportDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class BillImportBUS {
    private BillImportDAO billImportDAO;

    public BillImportBUS(BillImportDAO billImportDAO) {
        this.billImportDAO = billImportDAO;
    }

    public ArrayList<BillImportDTO> getAllBillIm() throws SQLException {
        return billImportDAO.getAllBillImports();
    }

    public boolean insertBillImport(BillImportDTO billImportDTO) {
        try {
            return billImportDAO.insertBillImport(billImportDTO);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<BillImportDTO> filterBillExports(Date fromDate, Date toDate, String criteria, String searchText, String Loaids) throws SQLException {
        ArrayList<BillImportDTO> filteredList = new ArrayList<>();

        ArrayList<BillImportDTO> dss = getAllBillIm();
        ArrayList<BillImportDTO> ds = new ArrayList<>();


            if(Loaids.equals("DS nhập kho")) {
                for (BillImportDTO d : dss) {
                    if(d.getQuantity() < 0){
                        ds.add(d);
                    }
                }
           }else{
                for (BillImportDTO d : dss) {
                    if(d.getQuantity() >= 0){
                        ds.add(d);
                    }
                }
            }


        Timestamp fromTimestamp = null;
        Timestamp toTimestamp = null;
        if (fromDate != null && toDate != null) {
            fromTimestamp = new Timestamp(fromDate.getTime());
            toTimestamp = new Timestamp(toDate.getTime() + 86399999); // Thêm 1 ngày - 1ms
        }
        // Duyệt qua danh sách hóa đơn
        for (BillImportDTO bill : ds) {
            boolean matchesDate = true;
            boolean matchesText = true;

            // Lọc theo ngày
            if (fromTimestamp != null && toTimestamp != null) {
                Timestamp billDate = bill.getImportDate();
                if (billDate != null) {
                    matchesDate = billDate.compareTo(fromTimestamp) >= 0 && billDate.compareTo(toTimestamp) <= 0;
                } else {
                    matchesDate = false;
                }
            }

            // Lọc theo text
            if (searchText != null && !searchText.isEmpty()) {
                switch (criteria) {
                    case "Mã Sp":
                        matchesText = String.valueOf(bill.getIdProduct()).toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Nhân viên":
                        matchesText = bill.getNameAdmin() != null && bill.getNameAdmin().toLowerCase().contains(searchText.toLowerCase());
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
