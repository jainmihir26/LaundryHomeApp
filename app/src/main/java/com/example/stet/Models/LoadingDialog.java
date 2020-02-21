package com.example.stet.Models;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.stet.R;

public class LoadingDialog {
    private Activity activity ;
    private AlertDialog alertDialog;
    public LoadingDialog(Activity activity){
        this.activity=activity;
    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}