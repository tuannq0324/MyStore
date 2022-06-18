package com.example.mystore.model;

import java.io.Serializable;
import java.util.HashMap;

public class Product implements Serializable {
    private String ProductID;
    private String productName;
    private String priceProduct;
    private String moreInfo;
    private String description;
    private HashMap<String,String> imgThumnal;
    private String number;
    private String status;

    public Product() {
    }

    public Product(String ProductID, String productName, String priceProduct, String moreInfo, String decription, HashMap<String, String> imgThumnal, String number, String status) {
        this.ProductID = ProductID;
        this.productName = productName;
        this.priceProduct = priceProduct;
        this.moreInfo = moreInfo;
        this.description = description;
        this.imgThumnal = imgThumnal;
        this.number = number;
        this.status = status;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getImgThumnal() {
        return imgThumnal;
    }

    public void setImgThumnal(HashMap<String, String> imgThumnal) {
        this.imgThumnal = imgThumnal;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
