package com.quanlybanlaptop.dto;

public class CTQDTO {
    private int idRole;
    private int idAuthority;
    private int idCTQ;
    private String name;
    private boolean status;
    public CTQDTO(int idRole, int idAuthority, int idCTQ, String name, boolean status) {
        this.idRole = idRole;
        this.idAuthority = idAuthority;
        this.idCTQ = idCTQ;
        this.name = name;
        this.status = status;
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
    public int getIdCTQ() {
        return idCTQ;
    }
    public void setIdCTQ(int idCTQ) {
        this.idCTQ = idCTQ;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

}
