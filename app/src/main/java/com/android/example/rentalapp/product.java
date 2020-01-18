package com.android.example.rentalapp;

public class product {
    public String image, securityCost, userID, description, productName, rentalPrice, availabilityDuration, category;

    public product() {
        //Empty Constructor
    }

    public String getImage() {
        return image;
    }

    public String getSecurityCost() {
        return securityCost;
    }

    public String getUserID() {
        return userID;
    }

    public String getDescription() {
        return description;
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

    public String getCategory() {
        return category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSecurityCost(String securityCost) {
        this.securityCost = securityCost;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setAvailabilityDuration(String availabilityDuration) {
        this.availabilityDuration = availabilityDuration;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

