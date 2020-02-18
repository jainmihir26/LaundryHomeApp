package com.example.stet.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClothSelectorDbHelper extends SQLiteOpenHelper {

    public ClothSelectorDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("database operation","database created");
    }

    public static final String DATABASE_NAME = "cloth_selection_db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE = "create table "+ClothSelectorContract.ClothEntry.TABLE_NAME+
                                               "("+ ClothSelectorContract.ClothEntry.CLOTH_ID+" number,"+
                                                ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE+" text,"+
                                                ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY+" text,"+
                                                ClothSelectorContract.ClothEntry.CLOTH_NAME+" text,"+
                                                ClothSelectorContract.ClothEntry.CLOTH_PRICE+" number,"+
                                                ClothSelectorContract.ClothEntry.CLOTH_COUNT+" number);";


    public static final String DROP_TABLE = "drop table if exists "+ClothSelectorContract.ClothEntry.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Database operation","table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void add_cloth(int cloth_id,String service_type,String subcategory,String cloth_name,int cloth_price,int cloth_count,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_ID,cloth_id);
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE,service_type);
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY,subcategory);
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_NAME,cloth_name);
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_PRICE,cloth_price);
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_COUNT,cloth_count);
        sqLiteDatabase.insert(ClothSelectorContract.ClothEntry.TABLE_NAME,null,contentValues);
        Log.d("Database Operation","One row is inserted");
    }

    public Cursor readContacts(SQLiteDatabase sqLiteDatabase){
        String[] projections = {ClothSelectorContract.ClothEntry.CLOTH_ID,ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE,ClothSelectorContract.ClothEntry.CLOTH_SUBCATEGORY,ClothSelectorContract.ClothEntry.CLOTH_NAME,ClothSelectorContract.ClothEntry.CLOTH_PRICE,ClothSelectorContract.ClothEntry.CLOTH_COUNT};
        Cursor cursor = sqLiteDatabase.query(ClothSelectorContract.ClothEntry.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    public void updateClothCount(int cloth_id,int cloth_count,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_COUNT,cloth_count);
        String selection = ClothSelectorContract.ClothEntry.CLOTH_ID +"="+cloth_id;

        sqLiteDatabase.update(ClothSelectorContract.ClothEntry.TABLE_NAME,contentValues,selection,null);

    }

    public void updateCountToZero(int cloth_id,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClothSelectorContract.ClothEntry.CLOTH_COUNT,0);
        String selection = ClothSelectorContract.ClothEntry.CLOTH_ID +"="+cloth_id;
        sqLiteDatabase.update(ClothSelectorContract.ClothEntry.TABLE_NAME,contentValues,selection,null);

    }

}
