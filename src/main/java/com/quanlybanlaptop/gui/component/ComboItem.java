package com.quanlybanlaptop.gui.component;

public class ComboItem {
    private int id;
    private String name;

    public ComboItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public ComboItem( String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name; // hiển thị name trên ComboBox
    }
}

