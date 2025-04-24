package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.BillExDetailDAO;
import com.quanlybanlaptop.dto.BillExDetailDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillExDetailBUS {
    private BillExDetailDAO dao;

    public BillExDetailBUS(BillExDetailDAO dao) {
        this.dao = dao;
    }

    // Thêm chi tiết hóa đơn xuất
    public void addBillExportDetail(BillExDetailDTO billExportDetailDTO) throws SQLException {
        dao.addBillExportDetail(billExportDetailDTO);
    }

    // Lấy danh sách chi tiết hóa đơn xuất theo id hóa đơn xuất
    public ArrayList<BillExDetailDTO> getBillExportDetailsByBillExId(int idBillEx) throws SQLException {
        return dao.getBillExportDetailsByBillExId(idBillEx);
    }

    // Xóa chi tiết hóa đơn xuất
//    public void deleteBillExportDetail(int idBillEx, int idProduct, String numSeri) throws SQLException {
//        billExportDetailDAO.deleteBillExportDetail(idBillEx, idProduct, numSeri);
//    }
//
//    // Cập nhật chi tiết hóa đơn xuất
//    public void updateBillExportDetail(BillExDetailDTO billExportDetailDTO) throws SQLException {
//        billExportDetailDAO.updateBillExportDetail(billExportDetailDTO);
//    }
}
