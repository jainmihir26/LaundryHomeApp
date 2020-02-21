package com.example.stet.Models;

public class DataAddress {

    int id;
    String address;
    String address_type;


    public DataAddress(int id,String address,String address_type){
        this.id = id;
        this.address = address;
        this.address_type = address_type;
    }

    public String getAddress(){
        return address;
    }


    public int getId(){
        return id;
    }

    public String getAddressType(){
        return address_type;
    }

}