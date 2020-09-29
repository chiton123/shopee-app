package com.example.nav.ui.Model;

import java.io.Serializable;

public class NganHang implements Serializable {
    private int idnganhang;
    private String tennganhang;
    private int hinhnganhang;

    public NganHang(int idnganhang, String tennganhang, int hinhnganhang) {
        this.idnganhang = idnganhang;
        this.tennganhang = tennganhang;
        this.hinhnganhang = hinhnganhang;
    }

    public int getIdnganhang() {
        return idnganhang;
    }

    public void setIdnganhang(int idnganhang) {
        this.idnganhang = idnganhang;
    }

    public String getTennganhang() {
        return tennganhang;
    }

    public void setTennganhang(String tennganhang) {
        this.tennganhang = tennganhang;
    }

    public int getHinhnganhang() {
        return hinhnganhang;
    }

    public void setHinhnganhang(int hinhnganhang) {
        this.hinhnganhang = hinhnganhang;
    }
}
