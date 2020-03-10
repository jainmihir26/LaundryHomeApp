package com.example.stet.Models;

public class OfferData {
    private String title;
    private String description ;

    public OfferData(String title, String description, String validity) {
        this.title = title;
        this.description = description;
        this.validity = validity;
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