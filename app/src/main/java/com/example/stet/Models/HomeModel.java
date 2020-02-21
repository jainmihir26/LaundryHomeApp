package com.example.stet.Models;
public class HomeModel {
    private int image ;
    private int count_cloth;


    public HomeModel(int image,int count_cloth) {
        this.image = image;
        this.count_cloth = count_cloth;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCount_cloth(){
        return count_cloth;
    }

    public void setCount_cloth(int count_cloth){
        this.count_cloth = count_cloth;
    }
}