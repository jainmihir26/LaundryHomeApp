package com.example.stet.Models;

public class DataClothSelector {
    String id;
    String cloth;
    String price;

    public DataClothSelector(String id,String cloth,String price){
        this.id = id;
        this.cloth = cloth;
        this.price = price;
    }

    public String getCloth(){
        return cloth;
    }

    public String getPrice(){
        return price;
    }

    public String getId(){
        return id;
    }
}
