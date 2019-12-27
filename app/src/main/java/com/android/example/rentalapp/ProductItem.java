package com.android.example.rentalapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProductItem {

    private String mImage;
    private String mName, mRentalprice, mTime;

    public ProductItem(){}

    public ProductItem(String image, String name, String rentalPrice, String time) {
        this.mImage = image;
        this.mName = name;
        this.mRentalprice = rentalPrice;
        this.mTime = time;
    }

    public String getProductimage(){
        return mImage;
    }

    public void setImage(String image){
        this.mImage = image;
    }

    public String getProductName() {
        return mName;
    }

    public void setName(String name){
        this.mName = name;
    }

    public String getRentalPrice(){return mRentalprice; }

    public void setRentalprice(String rentalPrice){
        this.mRentalprice = rentalPrice;
    }

    public String getTime(){
        return mTime;
    }

    public void setTime(String time){
        this.mTime = time;
    }

}
