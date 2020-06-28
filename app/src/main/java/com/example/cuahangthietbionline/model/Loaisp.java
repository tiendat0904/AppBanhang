package com.example.cuahangthietbionline.model;

public class Loaisp {
    public int id;
    public String Tenloaisp;
    public String hinhAnhloaisp;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHinhAnhloaisp() {
        return hinhAnhloaisp;
    }

    public void setHinhAnhloaisp(String hinhAnhloaisp) {
        this.hinhAnhloaisp = hinhAnhloaisp;
    }



    public Loaisp(int id, String tenloaisp, String hinhAnhloaisp) {
        this.id = id;
        Tenloaisp = tenloaisp;
        this.hinhAnhloaisp = hinhAnhloaisp;
    }


}
