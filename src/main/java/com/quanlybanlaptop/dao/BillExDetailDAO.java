package com.quanlybanlaptop.dao;

import com.quanlybanlaptop.dto.BillExDetailDTO;
import com.quanlybanlaptop.dto.BillExportDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillExDetailDAO {
    private Connection connection;

    public BillExDetailDAO(Connection connection) {
        this.connection = connection;
    }

    private BillExDetailDTO createBillExDetailDTO(ResultSet rs) throws SQLException {
        return new BillExDetailDTO(
                rs.getInt("id_bill_ex"),
                rs.getInt("id_product"),
                rs.getInt("quantity"),
                rs.getBigDecimal("unit_price"),
                rs.getString("num_seri")
        );
    }

    // Thêm chi tiết hóa đơn xuất
    public void addBillExportDetail(BillExDetailDTO billExportDetailDTO) throws SQLException {
        String query = "INSERT INTO BILL_EXPORT_DETAIL (id_bill_ex, id_product, num_seri, unit_price) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, billExportDetailDTO.getIdBillEx());
            ps.setInt(2, billExportDetailDTO.getIdProduct());
            ps.setString(3, billExportDetailDTO.getSeri());
            ps.setBigDecimal(4, billExportDetailDTO.getUnitPrice());
            ps.executeUpdate();
        }
    }

    // Lấy danh sách chi tiết hóa đơn xuất theo id hóa đơn xuất
    public ArrayList<BillExDetailDTO> getBillExportDetailsByBillExId(int idBillEx) throws SQLException {
        String query = "SELECT \n" +
                "    id_bill_ex, \n" +
                "    id_product,\n" +
                "    COUNT(*) AS quantity,\n" +
                "    MAX(unit_price) AS unit_price,\n" +
                "    COUNT(*) * MAX(unit_price) AS total_price,\n" +
                "    0 AS num_seri\n" +
                "FROM BILL_EXPORT_DETAIL\n" +
                "WHERE id_bill_ex = ?\n" +
                "GROUP BY id_bill_ex, id_product";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBillEx);
            ResultSet rs = ps.executeQuery();
            ArrayList<BillExDetailDTO> billExportDetails = new ArrayList<>();
            while (rs.next()) {
                BillExDetailDTO detail = createBillExDetailDTO(rs);
                billExportDetails.add(detail);
            }
            return billExportDetails;
        }
    }

    // Xóa chi tiết hóa đơn xuất
//    public void deleteBillExportDetail(int idBillEx, int idProduct, String numSeri) throws SQLException {
//        String query = "DELETE FROM BILL_EXPORT_DETAIL WHERE id_bill_ex = ? AND id_product = ? AND num_seri = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, idBillEx);
//            ps.setInt(2, idProduct);
//            ps.setString(3, numSeri);
//            ps.executeUpdate();
//        }
//    }
//
//    // Cập nhật chi tiết hóa đơn xuất
//    public void updateBillExportDetail(BillExDetailDTO billExportDetailDTO) throws SQLException {
//        String query = "UPDATE BILL_EXPORT_DETAIL SET unit_price = ? WHERE id_bill_ex = ? AND id_product = ? AND num_seri = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setDouble(1, billExportDetailDTO.getUnitPrice());
//            ps.setInt(2, billExportDetailDTO.getIdBillEx());
//            ps.setInt(3, billExportDetailDTO.getIdProduct());
//            ps.setString(4, billExportDetailDTO.getNumSeri());
//            ps.executeUpdate();
//        }
//    }
}
