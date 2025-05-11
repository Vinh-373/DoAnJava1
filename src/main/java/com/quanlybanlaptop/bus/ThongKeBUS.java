package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.ThongKeDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        // Lấy danh sách doanh thu và vốn
        List<Object[]> revenueList = thongKeDAO.getDailyRevenue(year, month);
        List<Object[]> capitalList = thongKeDAO.getDailyCapital(year, month);

        // Kết hợp dữ liệu và tính lợi nhuận
        List<Object[]> statsList = new ArrayList<>();
        for (int i = 0; i < revenueList.size(); i++) {
            String day = (String) revenueList.get(i)[0]; // Lấy ngày từ revenueList
            double revenue = (Double) revenueList.get(i)[1];
            double capital = (Double) capitalList.get(i)[1];
            double profit = revenue - capital;

            statsList.add(new Object[]{day, capital, revenue, profit});
        }

        return statsList;
    }
    // Lấy thống kê theo tháng trong năm
    public List<Object[]> getMonthlyStatistics(int year) throws SQLException {
        // Lấy danh sách doanh thu và vốn
        List<Object[]> revenueList = thongKeDAO.getMonthlyRevenue(year);
        List<Object[]> capitalList = thongKeDAO.getMonthlyCapital(year);

        // Kết hợp dữ liệu và tính lợi nhuận
        List<Object[]> statsList = new ArrayList<>();
        for (int i = 0; i < revenueList.size(); i++) {
            String month = (String) revenueList.get(i)[0]; // Lấy tháng từ revenueList
            double revenue = (Double) revenueList.get(i)[1];
            double capital = (Double) capitalList.get(i)[1];
            double profit = revenue - capital;

            statsList.add(new Object[]{month, capital, revenue, profit});
        }

        return statsList;
    }
   public List<Object[]> getYearlyStatistics(int startYear) throws SQLException {
        // Lấy danh sách doanh thu và vốn
        List<Object[]> revenueList = thongKeDAO.getYearlyRevenue(startYear);
        List<Object[]> capitalList = thongKeDAO.getYearlyCapital(startYear);

        // Kết hợp dữ liệu và tính lợi nhuận
        List<Object[]> statsList = new ArrayList<>();
        for (int i = 0; i < revenueList.size(); i++) {
            String year = (String) revenueList.get(i)[0]; // Lấy năm từ revenueList
            double revenue = (Double) revenueList.get(i)[1];
            double capital = (Double) capitalList.get(i)[1];
            double profit = revenue - capital;

            statsList.add(new Object[]{year, capital, revenue, profit});
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