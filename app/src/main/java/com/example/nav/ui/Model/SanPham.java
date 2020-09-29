package com.example.nav.ui.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int idsanpham;
    private String tensanpham;
    private String hinhanhsanpham;
    private int giasanpham;
    private int idcuahang;
    private int idloaisanpham;
    private int soluongdaban;
    private int soluong;
    private int phantramkhuyenmai;

    public SanPham(int idsanpham, String tensanpham, String hinhanhsanpham, int giasanpham, int idcuahang, int idloaisanpham, int soluongdaban, int soluong, int phantramkhuyenmai) {
        this.idsanpham = idsanpham;
        this.tensanpham = tensanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.giasanpham = giasanpham;
        this.idcuahang = idcuahang;
        this.idloaisanpham = idloaisanpham;
        this.soluongdaban = soluongdaban;
        this.soluong = soluong;
        this.phantramkhuyenmai = phantramkhuyenmai;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public int getIdcuahang() {
        return idcuahang;
    }

    public void setIdcuahang(int idcuahang) {
        this.idcuahang = idcuahang;
    }

    public int getIdloaisanpham() {
        return idloaisanpham;
    }

    public void setIdloaisanpham(int idloaisanpham) {
        this.idloaisanpham = idloaisanpham;
    }

    public int getSoluongdaban() {
        return soluongdaban;
    }

    public void setSoluongdaban(int soluongdaban) {
        this.soluongdaban = soluongdaban;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getPhantramkhuyenmai() {
        return phantramkhuyenmai;
    }

    public void setPhantramkhuyenmai(int phantramkhuyenmai) {
        this.phantramkhuyenmai = phantramkhuyenmai;
    }
}
