package com.example.mystore.model;

import java.io.Serializable;

public class Users implements Serializable {
    public String userKey,email,fullName,password,img;

    public Users() {
    }

    public Users(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public Users(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Users(String userKey, String email, String password, String fullName) {
        this.userKey = userKey;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Users(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

