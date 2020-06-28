package com.example.cuahangthietbionline.model;

import java.io.Serializable;

public class Sanpham implements Serializable {

    private int id;
    private String tensp;
    private Integer giasp;
    private String imgsp;
    private String motasp;
    private int id_sp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getImgsp() {
        return imgsp;
    }

    public void setImgsp(String imgsp) {
        this.imgsp = imgsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }



    public Sanpham(int id, String tensp, Integer giasp, String imgsp, String motasp, int id_sp) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.imgsp = imgsp;
        this.motasp = motasp;
        this.id_sp = id_sp;
    }


}
