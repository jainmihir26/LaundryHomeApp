package com.example.stet.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Adapters.SwipeAdapter;
import com.example.stet.Fragment.BottomFragment;
import com.example.stet.Fragment.CartFragment;
import com.example.stet.Fragment.DressFragment;
import com.example.stet.Fragment.HouseholdFragment;
import com.example.stet.Fragment.TopFragment;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClothSelectActivity extends AppCompatActivity  implements TopFragment.TotalChangeTop, BottomFragment.TotalChangeBottom, HouseholdFragment.TotalChangeHousehold, DressFragment.TotalChangeDress {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwipeAdapter swipeAdapter;
    TextView quantity_wash_fold,amount_wash_fold;
    private Button mNext,mAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_select);

        tabLayout = findViewById(R.id.tablayout_cloth_select);

        viewPager = findViewById(R.id.cloth_pager_container);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        quantity_wash_fold = findViewById(R.id.quantity_wash_fold);
        amount_wash_fold = findViewById(R.id.amount_wash_fold);
        mNext=findViewById(R.id.next_ClothSelectId);
        mAddToCart = findViewById(R.id.addToCart_ClothSelectId);

        mNext.setVisibility(View.INVISIBLE);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                    Toast.makeText(ClothSelectActivity.this, "next pressed", Toast.LENGTH_SHORT).show();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST,Urls.dataFromCartUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ClothSelectActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

                        JSONArray array=new JSONArray();

                        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
                        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
                        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

                        while (cursor.moveToNext())
                        {
                            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
                            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));
                            if(count !=0 ){
                                JSONObject obj=new JSONObject();
                                try {
                                    obj.put("item_id",id);
                                    obj.put("quantity",count);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(obj);
                            }

                        }

                        cursor.close();
                        params.put("token",sharedPreferencesConfig.read_token());
                        params.put("items",array.toString());
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


                Intent intent=new Intent(ClothSelectActivity.this,OrderDetails.class);
                startActivity(intent);
            }
        });

        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment()).commit();
            }
        });


        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);
        int total_cost = 0;
        int total_count = 0;
        while (cursor.moveToNext())
        {
            int cloth_price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
            total_count += cloth_count;
        }

        amount_wash_fold.setText("Total:"+total_cost);
        quantity_wash_fold.setText(total_count+" Items");
        change_next_visiblilty(total_count);


        swipeAdapter.AddFragment(new TopFragment(),"Top");
        swipeAdapter.AddFragment(new BottomFragment(),"Bottom");
        swipeAdapter.AddFragment(new HouseholdFragment(),"Household");
        swipeAdapter.AddFragment(new DressFragment(),"Dress");




        viewPager.setAdapter(swipeAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void total_count_change_bottom(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
        change_next_visiblilty(count);


    }

    @Override
    public void total_count_change_dress(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
        change_next_visiblilty(count);

    }

    @Override
    public void total_count_change_household(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
        change_next_visiblilty(count);

    }

    @Override
    public void total_count_change_top(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
        change_next_visiblilty(count);
    }

    public void change_next_visiblilty(int total_count){

        if(total_count>0){
            mNext.setVisibility(View.VISIBLE);
        }
        else{
            mNext.setVisibility(View.INVISIBLE);
        }
    }
}
