import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class Example {
    public static void main(String[] args) {
        // Tạo đối tượng JDateChooser
        JDateChooser dateChooser = new JDateChooser();

        // Lấy ngày hiện tại và set vào JDateChooser
        Date currentDate = new Date(); // Tạo đối tượng Date từ thời gian hệ thống
        dateChooser.setDate(currentDate); // Set vào JDateChooser

        // Lấy ngày từ JDateChooser
        Date selectedDate = dateChooser.getDate();

        // In ra ngày đã chọn
        System.out.println("Ngày đã chọn: " + selectedDate);
    }
}
