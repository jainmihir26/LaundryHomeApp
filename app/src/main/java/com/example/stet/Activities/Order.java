package com.example.stet.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataClothCart;
import com.example.stet.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "Order";

    private TextView addMoreTextView;
    private TextView totalAmountTextView;
    private RecyclerView cartItemsRecyclerView;
    private ListAdapter mListadapter;
    private TextView mHaveAPromoCode ;
    private TextView mDiscountPrice ;
    private TextView mPayableTextView;

    private Button placeOrder;
    String pickUpDate;
    String deliveryDate;
    String pickUpTime;
    String deliveryTime;
    private TextView pick_up_order_details;
    private TextView delivery_order_details;
    private TextView address_order;
    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Checkout.preload(getApplicationContext());

        ClothSelectorDbHelper clothSelectorDbHelper1 = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase1 = clothSelectorDbHelper1.getReadableDatabase();
        Cursor cursor1 = clothSelectorDbHelper1.readContacts(sqLiteDatabase1);

        double total_cost = 0;
        while (cursor1.moveToNext())
        {
            int count = cursor1.getInt(cursor1.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            if(count !=0){
                flag = 1;
            }
        }

        if (flag != 1){
            startActivity(new Intent(Order.this,MainActivity.class));
            finish();
        }


        pickUpDate = getIntent().getStringExtra("pickup_date");
        deliveryDate = getIntent().getStringExtra("delivery_date");
        pickUpTime = getIntent().getStringExtra("pickup_time");
        deliveryTime = getIntent().getStringExtra("delivery_time");

        pick_up_order_details = findViewById(R.id.pickup_order_details);
        delivery_order_details = findViewById(R.id.delivery_order_details);
        address_order = findViewById(R.id.address_order);
        mDiscountPrice=findViewById(R.id.discount_textView);
        mPayableTextView=findViewById(R.id.payable_textView);



//        mPayableTextView.setText(getIntent().getStringExtra("amount_after_discount"));

        pick_up_order_details.setText(pickUpDate+" "+pickUpTime);
        delivery_order_details.setText(deliveryDate+" "+deliveryTime);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
        address_order.setText(sharedPreferencesConfig.read_address());



        addMoreTextView = findViewById(R.id.add_more_textView);
        totalAmountTextView = findViewById(R.id.total_amount_textView);
        cartItemsRecyclerView = findViewById(R.id.recycler_view_order);
        placeOrder = findViewById(R.id.place_order_button_order);
        mHaveAPromoCode=findViewById(R.id.have_a_promocode);



        ClothSelectorDbHelper clothSelectorDbHelper_totalCost = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase_totalCost = clothSelectorDbHelper_totalCost.getReadableDatabase();
        Cursor cursor_totalCost = clothSelectorDbHelper_totalCost.readContacts(sqLiteDatabase_totalCost);

        while (cursor_totalCost.moveToNext())
        {
            int cloth_price = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
        }
        cursor_totalCost.close();

        totalAmountTextView.setText(total_cost+" "+getString(R.string.Rs));

        final String pay_sum = Double.toString(total_cost);

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




        final double finalTotal_cost1 = total_cost;
        mHaveAPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Order.this,ApplyPromotionActivity.class);
                Log.d(TAG, "onClick: "+" Amount to be paid : "+ finalTotal_cost1);
                intent.putExtra("amount_to_be_paid",String.valueOf(finalTotal_cost1));
                startActivity(intent);
            }
        });


        String percentageDiscount=getIntent().getStringExtra("discount_percentage");
        Log.d(TAG, "onCreate: "+" percentage discount "+percentageDiscount);
        Log.d(TAG, "onCreate: "+" total cost "+total_cost);
        double discountPrice=0;

        double maxDiscount ;
        if(getIntent().getStringExtra("max_discount")!=null) {
             maxDiscount= Double.parseDouble(Objects.requireNonNull(getIntent().getStringExtra("max_discount")));
        }else{
            maxDiscount=0;
        }

        if(percentageDiscount!=null) {
            discountPrice =(total_cost)*(Double.parseDouble(percentageDiscount))/100;

            if(discountPrice > maxDiscount){
                discountPrice=maxDiscount ;
            }

        } else{
            discountPrice = 0 /100;
        }

        mDiscountPrice.setText(String.valueOf((-1)*discountPrice)+" "+getString(R.string.Rs));


        final double payableAmount=total_cost - discountPrice;
        mPayableTextView.setText(String.valueOf(payableAmount)+" "+getString(R.string.Rs));


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment(payableAmount * 100);
            }
        });


    }

    public void startPayment(double amount_in_paisa) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_7BDdTfDDnOS4kT");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();


            RazorpayClient razorpay = new RazorpayClient("rzp_live_7BDdTfDDnOS4kT", "3HH9sh70Z4gfYg7qosDRjrIv");

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            SharedPreferencesConfig sharedPreferencesConfig =new SharedPreferencesConfig(getApplicationContext());
            String full_name1=sharedPreferencesConfig.read_full_name();
            String email1=sharedPreferencesConfig.read_email();
            String phone_no1=sharedPreferencesConfig.read_phone_number();
            String amount_to_be_paid=Double.toString(amount_in_paisa);

            options.put("name", full_name1);

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", " Complete Your Payment ");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            options.put("currency", "INR");




            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", amount_to_be_paid);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email1);
            preFill.put("contact", phone_no1);

            options.put("prefill", preFill);


            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.PickDropUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this,response, Toast.LENGTH_SHORT).show();
                parseData(response);
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
                params.put("address_id",Integer.toString(sharedPreferencesConfig.read_address_id()));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Error.", Toast.LENGTH_SHORT).show();

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


            String service_type;

            switch(dataList.get(position).getServiceType()){
                case "wash_fold":
                    service_type = "(Wash & Fold)";
                    break;
                case "wash_iron":
                    service_type = "(Wash & Iron)";
                    break;
                case "iron":
                    service_type= "(Iron)";
                    break;
                case "dry_clean":
                    service_type="(DryClean)";
                    break;
                default:
                    service_type="(No Service)";
            }


            holder.textViewServiceType.setText(service_type);
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

    public void parseData(String response)
    {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response);
            if(jsonObject.getString("error").equals("false")){


                String order_id = jsonObject.getString("order_id");
                Intent intent = new Intent(Order.this,UpiPaymentActivity.class);
                intent.putExtra("order_id",order_id);
                Log.d("order idddd",order_id);
                startActivity(intent);
                finish();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}