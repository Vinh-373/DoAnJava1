package com.quanlybanlaptop.dto;

public class AdminDTO {
        private int id, idRole;
        private String name;
        private String gender;
        private String email;
        private String contact;
        private String password;
        private int status;

        public AdminDTO() {}

        public AdminDTO(int id,int idRole, String name, String gender, String email, String contact, String password, int status) {
            this.id = id;
            this.idRole = idRole;
            this.name = name;
            this.gender = gender;
            this.email = email;
            this.contact = contact;
            this.password = password;
            this.status = status;
        }

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getContact() { return contact; }
        public void setContact(String contact) { this.contact = contact; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public int getIdRole() { return idRole; }
        public void setIdRole(int idRole) { this.idRole = idRole; }
}
