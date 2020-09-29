package com.example.nav.ui.Model;

public class LoaiSanPham {
    private int idloaisanpham;
    private String tenloaisanpham;
    private String hinhanhloaisanpham;

    public LoaiSanPham(int idloaisanpham, String tenloaisanpham, String hinhanhloaisanpham) {
        this.idloaisanpham = idloaisanpham;
        this.tenloaisanpham = tenloaisanpham;
        this.hinhanhloaisanpham = hinhanhloaisanpham;
    }

    public int getIdloaisanpham() {
        return idloaisanpham;
    }

    public void setIdloaisanpham(int idloaisanpham) {
        this.idloaisanpham = idloaisanpham;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }

    public String getHinhanhloaisanpham() {
        return hinhanhloaisanpham;
    }

    public void setHinhanhloaisanpham(String hinhanhloaisanpham) {
        this.hinhanhloaisanpham = hinhanhloaisanpham;
    }
}
