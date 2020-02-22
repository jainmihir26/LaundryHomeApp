package com.example.stet.Models;

public class DataCurrentOrder {

    //private String order_confirm_status;
    private String order_id;
    private String order_place_date_time;
    private String order_status;
    private String order_amount;
    private String order_time;


    public DataCurrentOrder(String order_time,String order_id, String order_place_date_time, String order_status, String order_amount) {
        //this.order_confirm_status = order_confirm_status;
        this.order_id = order_id;
        this.order_place_date_time = order_place_date_time;
        this.order_status = order_status;
        this.order_amount = order_amount;
        this.order_time = order_time;
    }

//    public String getOrder_confirm_status() {
//        return order_confirm_status;
//    }

    public  String getOrder_time(){
        return order_time;
    }
    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_place_date_time() {
        return order_place_date_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_amount() {
        return order_amount;
    }
}
