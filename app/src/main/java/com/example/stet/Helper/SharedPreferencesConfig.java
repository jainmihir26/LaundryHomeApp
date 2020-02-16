package com.example.stet.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.stet.R;

import java.util.ArrayList;

public class SharedPreferencesConfig {

    private Context context;
    private SharedPreferences sharedPreferencesIntroductionSlider;
    private SharedPreferences sharedPreferencesPriceObjId;

    public void clear_preferences(){
        SharedPreferences.Editor editor1 = sharedPreferencesIntroductionSlider.edit();
        SharedPreferences.Editor editor2 = sharedPreferencesPriceObjId.edit();



        editor1.clear();editor2.clear();
        editor1.commit();editor2.clear();
    }

    public SharedPreferencesConfig(Context context){

        this.context = context;
        sharedPreferencesIntroductionSlider = context.getSharedPreferences(context.getResources().getString(R.string.IS_FIRST_TIME_LAUNCH), Context.MODE_PRIVATE);
        sharedPreferencesPriceObjId = context.getSharedPreferences(context.getResources().getString(R.string.PRICE_OBJ_ID),context.MODE_PRIVATE);
    }

    public void write_FirstTimeLaunch(boolean isFirstTime){
        SharedPreferences.Editor sharedPreferencesIntroductionSlider_editor = sharedPreferencesIntroductionSlider.edit();
        sharedPreferencesIntroductionSlider_editor.putBoolean(context.getResources().getString(R.string.IS_FIRST_TIME_LAUNCH),isFirstTime);
        sharedPreferencesIntroductionSlider_editor.commit();
    }

    public void write_PriceObjId(int price_obj_id){
        SharedPreferences.Editor sharedPreferencesPriceObjId_editor = sharedPreferencesPriceObjId.edit();
        sharedPreferencesPriceObjId_editor.putInt(context.getResources().getString(R.string.PRICE_OBJ_ID),price_obj_id);
        sharedPreferencesPriceObjId_editor.commit();
    }



    public boolean read_FirstTimeLaunch(){
        return sharedPreferencesIntroductionSlider.getBoolean(context.getResources().getString(R.string.IS_FIRST_TIME_LAUNCH),true);
    }

    public int read_PriceObjId(){
        return sharedPreferencesPriceObjId.getInt(context.getResources().getString(R.string.PRICE_OBJ_ID),0);
    }



}
