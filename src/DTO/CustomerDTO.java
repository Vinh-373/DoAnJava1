package DTO;

import java.time.LocalDate;

public class CustomerDTO {
    private String customerID;   // Mã khách hàng
    private String fullName;     // Họ và tên
    private String gender;       // Giới tính
    private LocalDate dateOfBirth; // Ngày sinh
    private String email;        // Email
    private String contact;      // Số điện thoại
    private String address;      // Địa chỉ
    private String password;     // Mật khẩu

    // Constructors
    public CustomerDTO() {}

    public CustomerDTO(String customerID, String fullName, String gender, LocalDate dateOfBirth, String email, String contact, String address, String password) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.password = password;
    }

    // Getters and Setters
    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
