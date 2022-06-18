package com.example.mystore.listener;

import com.example.mystore.model.Banner;
import com.example.mystore.model.Product;

import java.util.ArrayList;

public interface HomeListener {
    void getAllProductSuccess(ArrayList<Product> productList);
    void getProductFailure(Exception ex);
}
