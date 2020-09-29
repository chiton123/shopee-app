package com.example.nav.ui.Model;

public class SanPhamShop {
    private int idloaisanpham;
    private String tenloai;
    private String hinhanhloai;
    private int soluong;

    public SanPhamShop(int idloaisanpham, String tenloai, String hinhanhloai, int soluong) {
        this.idloaisanpham = idloaisanpham;
        this.tenloai = tenloai;
        this.hinhanhloai = hinhanhloai;
        this.soluong = soluong;
    }

    public int getIdloaisanpham() {
        return idloaisanpham;
    }

    public void setIdloaisanpham(int idloaisanpham) {
        this.idloaisanpham = idloaisanpham;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanhloai() {
        return hinhanhloai;
    }

    public void setHinhanhloai(String hinhanhloai) {
        this.hinhanhloai = hinhanhloai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
