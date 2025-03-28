/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Lê Quang Hoàng
 */
public class AdminDTO {
    private String adminID;
    private String adminName;
    private String gender;
    private String email;
    private String contact;
    private String password;

    // Constructor mặc định
    public AdminDTO() {}

    // Constructor có tham số
    public AdminDTO(String adminID, String adminName, String gender, String email, String contact, String password) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    // Getter & Setter
    public String getAdminID() { return adminID; }
    public void setAdminID(String adminID) { this.adminID = adminID; }

    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Phương thức toString()
    @Override
    public String toString() {
        return "AdminDTO{" +
                "adminID='" + adminID + '\'' +
                ", adminName='" + adminName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

