package com.example.stet.Models;

public class DataModel {
    private String item;
    private String service_type;
    private int price;



    public int getPrice() {
        return price;
    }

    public String getItem() {
        return item;
    }

    public String getService_type() {
        return service_type;
    }

    public DataModel(String item, String service_type,int price) {

        this.item = item;
        this.service_type = service_type;
        this.price = price;


    }


}
