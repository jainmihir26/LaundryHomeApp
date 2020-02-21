package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Fragment.CartFragment;
import com.example.stet.Fragment.HomeFragment;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataClothCart;
import com.example.stet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order extends AppCompatActivity {

    private TextView addMoreTextView;
    private TextView totalAmountTextView;
    private RecyclerView cartItemsRecyclerView;
    private ListAdapter mListadapter;
    private Button placeOrder;
    String pickUpDate;
    String deliveryDate;
    String pickUpTime;
    String deliveryTime;
    private TextView pick_up_order_details;
    private TextView delivery_order_details;
    private TextView address_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        pickUpDate = getIntent().getStringExtra("pickup_date");
        deliveryDate = getIntent().getStringExtra("delivery_date");
        pickUpTime = getIntent().getStringExtra("pickup_time");
        deliveryTime = getIntent().getStringExtra("delivery_time");

        pick_up_order_details = findViewById(R.id.pickup_order_details);
        delivery_order_details = findViewById(R.id.delivery_order_details);
        address_order = findViewById(R.id.address_order);

        pick_up_order_details.setText(pickUpDate+" "+pickUpTime);
        delivery_order_details.setText(deliveryDate+" "+deliveryTime);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
        address_order.setText(sharedPreferencesConfig.read_address());



        addMoreTextView = findViewById(R.id.add_more_textView);
        totalAmountTextView = findViewById(R.id.total_amount_textView);
        cartItemsRecyclerView = findViewById(R.id.recycler_view_order);
        placeOrder = findViewById(R.id.place_order_button_order);

        ClothSelectorDbHelper clothSelectorDbHelper_totalCost = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase_totalCost = clothSelectorDbHelper_totalCost.getReadableDatabase();
        Cursor cursor_totalCost = clothSelectorDbHelper_totalCost.readContacts(sqLiteDatabase_totalCost);
        int total_cost = 0;
        while (cursor_totalCost.moveToNext())
        {
            int cloth_price = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
        }
        cursor_totalCost.close();

        totalAmountTextView.setText(total_cost+" "+getString(R.string.Rs));

        final String pay_sum = Integer.toString(total_cost);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);


        ArrayList<DataClothCart> data = new ArrayList<>();
        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

        while (cursor.moveToNext())
        {
            String cloth_name = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME));
            String service_type = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));


            if(count !=0){
                data.add(
                        new DataClothCart(id,cloth_name+" "+count,service_type,price*count)
                );
            }

        }
        cursor.close();


        mListadapter = new ListAdapter(data);
        cartItemsRecyclerView.setAdapter(mListadapter);

        addMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order.this,ClothSelectActivity.class));
            }
        });



        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().deleteDatabase("cloth_selection_db");
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
                sharedPreferencesConfig.clear_PriceObjId();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.PickDropUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Order.this,response, Toast.LENGTH_SHORT).show();
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
                        params.put("token",sharedPreferencesConfig.read_token());
                        params.put("pickup_date",pickUpDate);
                        params.put("delivery_date",deliveryDate);
                        params.put("pickup_time",pickUpTime);
                        params.put("delivery_time",deliveryTime);
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);




                Toast.makeText(Order.this, "amount is "+pay_sum, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Order.this,UpiPaymentActivity.class);
                intent.putExtra("amount",pay_sum);
                startActivity(intent);
            }
        });


    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataClothCart> dataList;

        public ListAdapter(ArrayList<DataClothCart> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewClothQuantity;
            TextView textViewServiceType;
            TextView textViewClothCost;
            Button removeItemOrder;


            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewClothQuantity = (TextView) itemView.findViewById(R.id.cloth_quntity_type_recycler_view_item);
                this.textViewServiceType = (TextView) itemView.findViewById(R.id.service_type_cart);
                this.textViewClothCost = (TextView) itemView.findViewById(R.id.cost_per_item_textView);
                this.removeItemOrder = (Button) itemView.findViewById(R.id.delete_item_cart);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart_item, parent, false);

            ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewClothQuantity.setText(dataList.get(position).getClothQuantity());
            holder.textViewServiceType.setText(dataList.get(position).getServiceType());
            holder.textViewClothCost.setText(Integer.toString(dataList.get(position).getCost())+" "+getString(R.string.Rs));
            holder.removeItemOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
                    SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
                    clothSelectorDbHelper.updateCountToZero(dataList.get(position).getId(),sqLiteDatabase);
                    clothSelectorDbHelper.close();

                    finish();
                    startActivity(getIntent());

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}

