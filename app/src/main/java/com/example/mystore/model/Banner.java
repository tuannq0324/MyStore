package com.example.mystore.model;

public class Banner {
    private String pId,pName,pImg,status;

    public Banner() {
    }

    public Banner(String pId, String pName, String pImg,String status) {
        this.pId = pId;
        this.pName = pName;
        this.pImg = pImg;
        this.status = status;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
