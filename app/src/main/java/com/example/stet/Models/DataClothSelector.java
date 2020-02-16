package com.example.stet;

public class DataClothSelector {
    int id;
    String cloth;
    int price;
    int quantity;

    public DataClothSelector(int id,String cloth,int price,int quantity){
        this.id = id;
        this.cloth = cloth;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCloth(){
        return cloth;
    }

    public int getPrice(){
        return price;
    }

    public int getId(){
        return id;
    }

    public int getQuantity(){
        return quantity;
    }
}
