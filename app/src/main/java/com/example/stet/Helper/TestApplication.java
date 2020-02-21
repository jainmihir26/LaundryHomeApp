package com.example.stet.Helper;


import android.app.Application;

public class TestApplication extends Application {

    private static TestApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized TestApplication getInstance() {
        return mInstance;
    }


}