package com.quanlybanlaptop.dto;

import java.sql.Date;

public class CustomerDTO {
    private int id;
    private String name;
    private Date birthdate;
    private String gender;
    private String phone;
    private String citizenId;
    private int status;

    // Constructor không tham số
    public CustomerDTO() {}

    // Constructor đầy đủ tham số
    public CustomerDTO(int id, String name, Date birthdate, String gender, String phone, String citizenId, int status) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.citizenId = citizenId;
        this.status = status;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
