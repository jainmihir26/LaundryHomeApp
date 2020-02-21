package com.example.stet.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Fragment.AccountFragment;
import com.example.stet.Fragment.CartFragment;
import com.example.stet.Fragment.HomeFragment;
import com.example.stet.Fragment.OfferFragment;
import com.example.stet.Fragment.OrderFragment;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
//        sharedPreferencesConfig.write_PriceObjId(0);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.PriceObjUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_order:
                            selectedFragment = new OrderFragment();
                            break;
                        case R.id.nav_offer:
                            selectedFragment = new OfferFragment();
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new CartFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    private void parseData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
            int present_price_obj_id = sharedPreferencesConfig.read_PriceObjId();
            if(jsonObject.getInt("price_obj_id") != present_price_obj_id ){
                getApplicationContext().deleteDatabase("cloth_selection_db");
                ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
                JSONArray price_json_list = (JSONArray) jsonObject.get("price_obj");
                for(int i=0;i<price_json_list.length();i++){
                    JSONObject jsonObject_each =  (JSONObject) price_json_list.get(i);

                    int id = (int)jsonObject_each.get("item_id");
                    String category = (String)jsonObject_each.get("category");
                    String sub_category = (String)jsonObject_each.get("sub_category");
                    String item_name = (String)jsonObject_each.get("item_name");
                    Double item_price = (Double)jsonObject_each.get("item_price");
                    sharedPreferencesConfig.write_PriceObjId(id);

                    clothSelectorDbHelper.add_cloth(id,category,sub_category,item_name,item_price.intValue(),0,sqLiteDatabase);

            }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.PriceObjUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
