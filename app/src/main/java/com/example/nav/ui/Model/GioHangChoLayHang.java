package com.example.nav.ui.Model;

import java.io.Serializable;

// để sử dụng cho fragment theo dõi đơn hàng, của người mua và chủ chủ shop

public class GioHangChoLayHang implements Serializable {
    private int idsp;
    private String tensp;
    private long giasp;
    private String hinhanhsp;
    private int soluongsp;
    private String tennguoimua;
    private int tinhtrang;
    private int idchitietdonhang;

    public GioHangChoLayHang(int idsp, String tensp, long giasp, String hinhanhsp, int soluongsp, String tennguoimua, int tinhtrang, int idchitietdonhang) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.soluongsp = soluongsp;
        this.tennguoimua = tennguoimua;
        this.tinhtrang = tinhtrang;
        this.idchitietdonhang = idchitietdonhang;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public String getTennguoimua() {
        return tennguoimua;
    }

    public void setTennguoimua(String tennguoimua) {
        this.tennguoimua = tennguoimua;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getIdchitietdonhang() {
        return idchitietdonhang;
    }

    public void setIdchitietdonhang(int idchitietdonhang) {
        this.idchitietdonhang = idchitietdonhang;
    }
}
