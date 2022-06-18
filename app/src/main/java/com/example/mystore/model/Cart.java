package com.example.mystore.model;

import java.util.HashMap;

public class Cart {
    private String ProductID, productName, priceProduct, quantity, discount;
    private HashMap<String,String> imgThumnal;

    public Cart() {
    }

    public Cart(String productID,HashMap<String,String> imgThumnal, String productName, String priceProduct, String quantity, String discount) {
        ProductID = productID;
        this.imgThumnal = imgThumnal;
        this.productName = productName;
        this.priceProduct = priceProduct;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public HashMap<String, String> getImgThumnal() {
        return imgThumnal;
    }

    public void setImgThumnal(HashMap<String, String> imgThumnal) {
        this.imgThumnal = imgThumnal;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
