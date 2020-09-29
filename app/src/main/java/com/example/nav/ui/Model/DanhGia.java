package com.example.nav.ui.Model;

public class DanhGia {
    private int masanpham;
    private String tennguoimua;
    private String nhanxet;
    private float sosao;

    public DanhGia(int masanpham, String tennguoimua, String nhanxet, float sosao) {
        this.masanpham = masanpham;
        this.tennguoimua = tennguoimua;
        this.nhanxet = nhanxet;
        this.sosao = sosao;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTennguoimua() {
        return tennguoimua;
    }

    public void setTennguoimua(String tennguoimua) {
        this.tennguoimua = tennguoimua;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }

    public float getSosao() {
        return sosao;
    }

    public void setSosao(float sosao) {
        this.sosao = sosao;
    }
}
