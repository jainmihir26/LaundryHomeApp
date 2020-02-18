package com.example.stet.Models;

public class DataClothCart {

    int id;
    String cloth_quantity;
    String service_type;
    int cost;

    public DataClothCart(int id,String cloth_quantity,String service_type,int cost){
        this.id = id;
        this.cloth_quantity = cloth_quantity;
        this.service_type = service_type;
        this.cost = cost;
    }

    public String getClothQuantity(){
        return cloth_quantity;
    }

    public String getServiceType(){
        return service_type;
    }

    public int getCost(){
        return cost;
    }

    public int getId(){
        return id;
    }

}
