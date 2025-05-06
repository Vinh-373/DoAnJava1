package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.ThongKeDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
public class ThongKeBUS {
    private ThongKeDAO thongKeDAO;
    public ThongKeBUS(ThongKeDAO thongKeDAO) {
        this.thongKeDAO = thongKeDAO;
    }

    public int getTongSanPham() throws SQLException {
        return thongKeDAO.getTongSanPham();
    }

    public int getTongKhachHang() throws SQLException {
        return thongKeDAO.getTongKhachHang();
    }

    public int getTongNhanVien() throws SQLException {
        return thongKeDAO.getTongNhanVien();
    }
    
    public List<Object[]> getDoanhThu7NgayGanNhat() throws SQLException {
        return thongKeDAO.getDoanhThu7NgayGanNhat();
    }
    public List<Object[]> getDailyStatistics(int year, int month) throws SQLException {
        // Lấy danh sách doanh thu (khởi tạo với tất cả các ngày trong tháng)
        List<Object[]> statsList = thongKeDAO.getDailyRevenue(year, month);

        // Tính vốn cho các ngày
        thongKeDAO.calculateDailyCapital(statsList, year, month);

        // Tính lợi nhuận và định dạng dữ liệu cho bảng
        for (Object[] stats : statsList) {
            double revenue = (double) stats[1];
            double capital = (double) stats[2];
            stats[3] = revenue - capital; // Lợi nhuận
            // Chuyển ngày thành String để khớp với bảng
            stats[0] = String.valueOf(stats[0]);
        }

        return statsList;
    }
    public List<Object[]> getMonthlyStatistics(int year) throws SQLException {
        List<Object[]> statsList = thongKeDAO.getMonthlyRevenue(year);
        thongKeDAO.calculateMonthlyCapital(statsList, year);

        for (Object[] stats : statsList) {
            double revenue = (double) stats[1];
            double capital = (double) stats[2];
            stats[3] = revenue - capital;
            stats[0] = "Tháng " + stats[0];
        }

        return statsList;
    }
    public List<Object[]> getYearlyStatistics(int startYear) throws SQLException {
        List<Object[]> statsList = thongKeDAO.getYearlyRevenue(startYear);
        thongKeDAO.calculateYearlyCapital(statsList, startYear);

        for (Object[] stats : statsList) {
            double revenue = (double) stats[1];
            double capital = (double) stats[2];
            stats[3] = revenue - capital;
            stats[0] = String.valueOf(stats[0]);
        }

        return statsList;
    }
    public List<Object[]> getCustomerStatistics(String customerName, LocalDate startDate, LocalDate endDate) throws SQLException {
        return thongKeDAO.getCustomerStatistics(customerName, startDate, endDate);
    }
    public List<Object[]> getProductStatistics(String productName, LocalDate startDate, LocalDate endDate) throws SQLException {
        return thongKeDAO.getProductStatistics(productName, startDate, endDate);
    }
    public List<Object[]> getInventoryStatistics(String productName) throws SQLException {
        return thongKeDAO.getInventoryStatistics(productName);
    }

}