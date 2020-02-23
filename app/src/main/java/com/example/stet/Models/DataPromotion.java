package com.example.stet.Models;

public class DataPromotion {
    int id;
    String title;
    String date_expire;
    String code;


    public DataPromotion(int id,String title,String date_expire,String code){
        this.id = id;
        this.title = title;
        this.date_expire = date_expire;
        this.code = code;

    }

    public int getId(){
        return id;
    }

    public String getTitle()
    { return title; }

    public  String getDate(){
        return date_expire;
    }

    public String getCode(){
        return code;
    }

}
