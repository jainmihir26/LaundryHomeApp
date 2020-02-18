package com.example.stet.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.stet.R;

import java.util.ArrayList;

public class SharedPreferencesConfig {

    private Context context;
    private SharedPreferences sharedPreferencesIntroductionSlider;
    private SharedPreferences sharedPreferencesPriceObjId;
    private SharedPreferences sharedPreferencesName;
    private SharedPreferences sharedPreferencesPhoneNumber;
    private SharedPreferences sharedPreferencesEmail;
    private SharedPreferences sharedPreferencesToken;
    private SharedPreferences sharedPreferencesAddress;


    public  void  clear_PriceObjId(){
        SharedPreferences.Editor editor2 = sharedPreferencesPriceObjId.edit();
        editor2.clear();editor2.commit();
    }
    public void clear_preferences(){
        SharedPreferences.Editor editor1 = sharedPreferencesIntroductionSlider.edit();
        SharedPreferences.Editor editor2 = sharedPreferencesPriceObjId.edit();
        SharedPreferences.Editor editor3 = sharedPreferencesName.edit();
        SharedPreferences.Editor editor4 = sharedPreferencesEmail.edit();
        SharedPreferences.Editor editor5 = sharedPreferencesPhoneNumber.edit();
        SharedPreferences.Editor editor6 = sharedPreferencesToken.edit();
        SharedPreferences.Editor editor7 = sharedPreferencesAddress.edit();




        editor1.clear();editor1.commit();
        editor2.clear();editor2.commit();
        editor3.clear();editor3.commit();
        editor4.clear();editor4.commit();
        editor5.clear();editor5.commit();
        editor6.clear();editor6.commit();
        editor7.clear();editor7.commit();


    }

    public SharedPreferencesConfig(Context context){

        this.context = context;
        sharedPreferencesIntroductionSlider = context.getSharedPreferences(context.getResources().getString(R.string.IS_FIRST_TIME_LAUNCH), Context.MODE_PRIVATE);
        sharedPreferencesPriceObjId = context.getSharedPreferences(context.getResources().getString(R.string.PRICE_OBJ_ID),context.MODE_PRIVATE);
        sharedPreferencesName = context.getSharedPreferences(context.getResources().getString(R.string.FULL_NAME),context.MODE_PRIVATE);
        sharedPreferencesPhoneNumber = context.getSharedPreferences(context.getResources().getString(R.string.PHONE_NUMBER),Context.MODE_PRIVATE);
        sharedPreferencesEmail = context.getSharedPreferences(context.getResources().getString(R.string.EMAIL),Context.MODE_PRIVATE);
        sharedPreferencesToken = context.getSharedPreferences(context.getResources().getString(R.string.TOKEN),Context.MODE_PRIVATE);
        sharedPreferencesAddress = context.getSharedPreferences(context.getResources().getString(R.string.ADDRESS),Context.MODE_PRIVATE);
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

    public void write_full_name(String full_name){
        SharedPreferences.Editor sharedPreferencesName_editor = sharedPreferencesName.edit();
        sharedPreferencesName_editor.putString(context.getResources().getString(R.string.FULL_NAME),full_name);
        sharedPreferencesName_editor.commit();
    }

    public void write_phone_number(String phone_number){
        SharedPreferences.Editor sharedPreferencesPhoneNumber_editor = sharedPreferencesPhoneNumber.edit();
        sharedPreferencesPhoneNumber_editor.putString(context.getResources().getString(R.string.PHONE_NUMBER),phone_number);
        sharedPreferencesPhoneNumber_editor.commit();
    }

    public void write_token(String token){
        SharedPreferences.Editor sharedPreferencesToken_editor = sharedPreferencesToken.edit();
        sharedPreferencesToken_editor.putString(context.getResources().getString(R.string.TOKEN),token);
        sharedPreferencesToken_editor.commit();
    }

    public void write_email(String email){
        SharedPreferences.Editor sharedPreferencesEmail_editor = sharedPreferencesEmail.edit();
        sharedPreferencesEmail_editor.putString(context.getResources().getString(R.string.EMAIL),email);
        sharedPreferencesEmail_editor.commit();
    }

    public void write_address(String address){
        SharedPreferences.Editor sharedPreferencesAddress_editor = sharedPreferencesAddress.edit();
        sharedPreferencesAddress_editor.putString(context.getResources().getString(R.string.ADDRESS),address);
        sharedPreferencesAddress_editor.commit();
    }


    public boolean read_FirstTimeLaunch(){
        return sharedPreferencesIntroductionSlider.getBoolean(context.getResources().getString(R.string.IS_FIRST_TIME_LAUNCH),true);
    }

    public int read_PriceObjId(){
        return sharedPreferencesPriceObjId.getInt(context.getResources().getString(R.string.PRICE_OBJ_ID),0);
    }

    public String read_full_name(){
        return sharedPreferencesName.getString(context.getResources().getString(R.string.FULL_NAME),"NoName");
    }

    public String read_email(){
        return sharedPreferencesEmail.getString(context.getResources().getString(R.string.EMAIL),"NoEmail");
    }

    public String read_phone_number(){
        return sharedPreferencesPhoneNumber.getString(context.getResources().getString(R.string.PHONE_NUMBER),"0000000000");
    }

    public String read_token(){
        return sharedPreferencesToken.getString(context.getResources().getString(R.string.TOKEN),"DEFAULT_TOKEN");
    }

    public String read_address(){
        return sharedPreferencesAddress.getString(context.getResources().getString(R.string.ADDRESS),"NULL_ADDRESS");
    }





}
