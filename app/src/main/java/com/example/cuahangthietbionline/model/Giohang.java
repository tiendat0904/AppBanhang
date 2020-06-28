package com.example.cuahangthietbionline.model;

public class Giohang {
    public int idsp;
    public String tensp;
    public long gisp;
    public String hinhsp;
    public int soluongsp;

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

    public long getGisp() {
        return gisp;
    }

    public void setGisp(long gisp) {
        this.gisp = gisp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }



    public Giohang(int idsp, String tensp, long gisp, String hinhsp, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.gisp = gisp;
        this.hinhsp = hinhsp;
        this.soluongsp = soluongsp;
    }


}
