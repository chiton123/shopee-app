package com.example.nav.ui.Model;
// so luong toi da là số hàng có trong kho của shop
public class GioHang {
    private int idsp;
    private String tensp;
    private long giasp;
    private String hinhanhsp;
    private int soluongsp;
    private int soluongtoida;

    public GioHang(int idsp, String tensp, long giasp, String hinhanhsp, int soluongsp, int soluongtoida) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.soluongsp = soluongsp;
        this.soluongtoida = soluongtoida;
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

    public int getSoluongtoida() {
        return soluongtoida;
    }

    public void setSoluongtoida(int soluongtoida) {
        this.soluongtoida = soluongtoida;
    }
}
