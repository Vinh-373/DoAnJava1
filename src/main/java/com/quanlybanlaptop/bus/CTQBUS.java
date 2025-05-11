package com.quanlybanlaptop.bus;

import com.quanlybanlaptop.dao.CTQDAO;

public class CTQBUS {
    private CTQDAO ctqdao;
    public CTQBUS(CTQDAO ctqdao) {
        this.ctqdao = ctqdao;
    }
    public boolean isChecked(int idRole, int idAuthority, int idCTQ) {
        return ctqdao.isChecked(idRole, idAuthority, idCTQ);
    }
    public boolean updateIsChecked(int idRole, int idAuthority, int idCTQ, boolean isChecked) {
        return ctqdao.updateIsChecked(idRole, idAuthority,idCTQ, isChecked);
    }
    public boolean insert(int idRole) {
        return ctqdao.insert(idRole);
    }
}
