package com.example.stet.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

public class RazorPayEarlier extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "RazorPayEarlier";
    private Button mPay;
    RazorpayClient razorpayClient ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay_earlier);
        mPay=findViewById(R.id.payButton);

        Checkout.preload(getApplicationContext());

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {
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
            SharedPreferencesConfig sharedPreferencesConfig =new SharedPreferencesConfig(RazorPayEarlier.this);
            String full_name1=sharedPreferencesConfig.read_full_name();
            String email1=sharedPreferencesConfig.read_email();
            String phone_no1=sharedPreferencesConfig.read_phone_number();


            options.put("name", full_name1);

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", " Complete Your Payment ");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "123456");
            options.put("currency", "INR");




            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", email1);
            preFill.put("contact", "8446772185");

            options.put("prefill", preFill);


            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(RazorPayEarlier.this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(RazorPayEarlier.this, "Payment Error "+ s, Toast.LENGTH_SHORT).show();
    }
}
