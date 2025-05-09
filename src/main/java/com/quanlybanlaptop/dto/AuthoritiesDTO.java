package com.quanlybanlaptop.dto;

public class AuthoritiesDTO {
    private int idRole, idAuthority;
    private String authorityName;
    private boolean isChecked;
    public AuthoritiesDTO(int idRole, int idAuthority, String authorityName, boolean isChecked) {
        this.idRole = idRole;
        this.idAuthority = idAuthority;
        this.authorityName = authorityName;
        this.isChecked = isChecked;
    }
    public int getIdRole() {
        return idRole;
    }
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
    public int getIdAuthority() {
        return idAuthority;
    }
    public void setIdAuthority(int idAuthority) {
        this.idAuthority = idAuthority;
    }
    public String getAuthorityName() {
        return authorityName;
    }
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
