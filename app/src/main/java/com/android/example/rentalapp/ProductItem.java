package com.android.example.rentalapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ProductItem {

    public String image, securityCost, userID, description;
    public String productName, rentalPrice, availabilityDuration;

    public ProductItem() {
        //Empty Constructor
    }

    public ProductItem(String image, String productName, String rentalPrice, String availabilityDuration) {
        this.image = image;
        this.productName = productName;
        this.rentalPrice = rentalPrice;
        this.availabilityDuration = availabilityDuration;
    }

    public String getImage() {
        return image;
    }

    public String getProductName() {
        return productName;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public String getAvailabilityDuration() {
        return availabilityDuration;
    }

    public String getSecurityCost() {
        return securityCost;
    }

    public String getDescription() {
        return description;
    }


    public String getUserID() {
        return userID;
    }
}


