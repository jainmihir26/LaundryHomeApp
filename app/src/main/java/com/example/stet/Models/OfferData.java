package com.example.stet.Models;

public class OfferData {
    private String title;
    private String description ;
    private double minAmount ;
    private double discountPercenrage ;
    private double maxDiscount ;

    public OfferData(String title, String description, String validity,double minAmount , double discountPercenrage, double maxDiscount) {
        this.title = title;
        this.description = description;
        this.validity = validity;
        this.minAmount= minAmount ;
        this.discountPercenrage=discountPercenrage ;
        this.maxDiscount=maxDiscount ;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public double getDiscountPercenrage() {
        return discountPercenrage;
    }

    private String validity ;

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}