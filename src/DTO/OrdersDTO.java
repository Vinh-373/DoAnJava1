package DTO;
import java.time.*;

public class OrdersDTO {
    private String orderNo;
    private String customerId;
    private LocalDate dateOrder;
    private LocalTime timeOrder;

    // Constructor không tham số
    public OrdersDTO() {}

    // Constructor đầy đủ tham số
    public OrdersDTO(String orderNo, String customerId, LocalDate dateOrder, LocalTime timeOrder) {
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
    }

    // Getters and Setters
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public LocalDate getDateOrder() { return dateOrder; }
    public void setDateOrder(LocalDate dateOrder) { this.dateOrder = dateOrder; }

    public LocalTime getTimeOrder() { return timeOrder; }
    public void setTimeOrder(LocalTime timeOrder) { this.timeOrder = timeOrder; }
}
