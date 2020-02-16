package com.example.stet.Models;

import android.provider.BaseColumns;

public final class ClothSelectorContract {

    private ClothSelectorContract(){}

    public static class ClothEntry implements BaseColumns{

        public static final String TABLE_NAME = "cloths_info";
        public static final  String CLOTH_ID = "cloth_id";
        public static final String CLOTH_SERVICE_TYPE = "cloth_service_type";
        public static final String CLOTH_SUBCATEGORY = "cloth_sub_category";
        public static final String CLOTH_NAME = "cloth_name";
        public static final String CLOTH_PRICE = "cloth_price";
        public static final String CLOTH_COUNT = "cloth_count";


    }
}
