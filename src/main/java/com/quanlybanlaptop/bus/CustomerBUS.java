package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.CustomerDAO;
import com.quanlybanlaptop.dto.CustomerDTO;

import java.util.ArrayList;

public class CustomerBUS {
    private CustomerDAO customerDAO;

    public CustomerBUS() {
        customerDAO = new CustomerDAO();
    }

    // Lấy tất cả khách hàng
    public ArrayList<CustomerDTO> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // Thêm khách hàng
    public boolean addCustomer(CustomerDTO customer) {
        if (isValidCustomer(customer)) {
            return customerDAO.addCustomer(customer);
        }
        return false;
    }

    // Cập nhật khách hàng
    public boolean updateCustomer(CustomerDTO customer) {
        if (isValidCustomer(customer)) {
            return customerDAO.updateCustomer(customer);
        }
        return false;
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int id) {
        CustomerDTO customer = customerDAO.getCustomerById(id);
        if (customer != null) {
            customer.setStatus(0); // đổi trạng thái thành không hoạt động
            return customerDAO.updateCustomer(customer);
        }
        return false;
    }

    public CustomerDTO getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }
    // Kiểm tra dữ liệu đầu vào
    private boolean isValidCustomer(CustomerDTO customer) {
        return customer.getName() != null && !customer.getName().trim().isEmpty()
                && customer.getPhone() != null && !customer.getPhone().trim().isEmpty()
                && customer.getCitizenId() != null && !customer.getCitizenId().trim().isEmpty();
    }
}

