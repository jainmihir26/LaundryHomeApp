package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.R;

import java.util.Calendar;

public class OrderDetails extends AppCompatActivity {
    private static final String TAG = "OrderDetails";
    private Button mPickUpDateToday;
    private Button mPickUpDateTomorrow;
    private Button mPickUpDateTheAfterTomorrow;
    private Button mDeliveryDateTomorrow;
    private Button mDeliveryDateTheAfter;
    private Button mDeliveryDateTheAfterAfter;
    private Button mEdit;
    private String pickUpDate ;
    private String deliveryDate ;
    private String pickUpTime ;
    private String deliveryTime ;

    private Button mPickupTime10 ;
    private Button mPickupTime12 ;
    private Button mPickupTime4 ;
    private Button mDelivery10 ;
    private Button mDelivery12 ;
    private Button mDelivery4 ;
    private Button mProceed;
    private TextView address_orderDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        init();

        buttonsClicked();

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "setText1: pickup date : "+pickUpDate);
                Log.i(TAG, "setText1: delivery date : "+deliveryDate);
                Log.i(TAG, "setText1: pickUp time  : "+pickUpTime);
                Log.i(TAG, "setText1: delivery time : "+deliveryTime);

                Intent intent = new Intent(OrderDetails.this,Order.class);
                intent.putExtra("pickup_date",pickUpDate);
                intent.putExtra("delivery_date",deliveryDate);
                intent.putExtra("pickup_time",pickUpTime);
                intent.putExtra("delivery_time",deliveryTime);
                startActivity(intent);
            }
        });
    }

    private void buttonsClicked() {

        mPickUpDateToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpDate=mPickUpDateToday.getText().toString();
                mPickUpDateToday.setPressed(true);
                mPickUpDateTomorrow.setPressed(false);
                mPickUpDateTheAfterTomorrow.setPressed(false);
                mPickUpDateToday.setBackgroundColor(Color.BLUE);
                mPickUpDateTomorrow.setBackgroundColor(Color.GRAY);
                mPickUpDateTheAfterTomorrow.setBackgroundColor(Color.GRAY);
            }
        });

        mPickUpDateTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpDate=mPickUpDateTomorrow.getText().toString();
                mPickUpDateToday.setPressed(false);
                mPickUpDateTomorrow.setPressed(true);
                mPickUpDateTheAfterTomorrow.setPressed(false);
                mPickUpDateTomorrow.setBackgroundColor(Color.BLUE);
                mPickUpDateToday.setBackgroundColor(Color.GRAY);
                mPickUpDateTheAfterTomorrow.setBackgroundColor(Color.GRAY);
            }
        });
        mPickUpDateTheAfterTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpDate=mPickUpDateTheAfterTomorrow.getText().toString();
                mPickUpDateToday.setPressed(false);
                mPickUpDateTomorrow.setPressed(false);
                mPickUpDateTheAfterTomorrow.setPressed(true);
                mPickUpDateTomorrow.setBackgroundColor(Color.GRAY);
                mPickUpDateToday.setBackgroundColor(Color.GRAY);
                mPickUpDateTheAfterTomorrow.setBackgroundColor(Color.BLUE);

            }
        });
        mDeliveryDateTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryDate=mDeliveryDateTomorrow.getText().toString();
                mDeliveryDateTomorrow.setPressed(true);
                mDeliveryDateTheAfter.setPressed(false);
                mDeliveryDateTheAfterAfter.setPressed(false);
                mDeliveryDateTomorrow.setBackgroundColor(Color.BLUE);
                mDeliveryDateTheAfter.setBackgroundColor(Color.GRAY);
                mDeliveryDateTheAfterAfter.setBackgroundColor(Color.GRAY);

            }
        });

        mDeliveryDateTheAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryDate=mDeliveryDateTheAfter.getText().toString();
                mDeliveryDateTomorrow.setPressed(false);
                mDeliveryDateTheAfter.setPressed(true);
                mDeliveryDateTheAfterAfter.setPressed(true);
                mDeliveryDateTomorrow.setBackgroundColor(Color.GRAY);
                mDeliveryDateTheAfter.setBackgroundColor(Color.BLUE);
                mDeliveryDateTheAfterAfter.setBackgroundColor(Color.GRAY);
            }
        });

        mDeliveryDateTheAfterAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryDate=mDeliveryDateTheAfterAfter.getText().toString();
                mDeliveryDateTomorrow.setPressed(false);
                mDeliveryDateTheAfter.setPressed(false);
                mDeliveryDateTheAfterAfter.setPressed(true);
                mDeliveryDateTomorrow.setBackgroundColor(Color.GRAY);
                mDeliveryDateTheAfter.setBackgroundColor(Color.GRAY);
                mDeliveryDateTheAfterAfter.setBackgroundColor(Color.BLUE);

            }
        });
        mPickupTime10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpTime=mPickupTime10.getText().toString();
                mPickupTime10.setPressed(true);
                mPickupTime12.setPressed(false);
                mPickupTime4.setPressed(false);

                mPickupTime10.setBackgroundColor(Color.BLUE);
                mPickupTime12.setBackgroundColor(Color.GRAY);
                mPickupTime4.setBackgroundColor(Color.GRAY);
            }
        });

        mPickupTime12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpTime=mPickupTime12.getText().toString();
                mPickupTime10.setPressed(false);
                mPickupTime12.setPressed(true);
                mPickupTime4.setPressed(false);

                mPickupTime10.setBackgroundColor(Color.GRAY);
                mPickupTime12.setBackgroundColor(Color.BLUE);
                mPickupTime4.setBackgroundColor(Color.GRAY);
            }
        });

        mPickupTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpTime=mPickupTime4.getText().toString();
                mPickupTime10.setPressed(false);
                mPickupTime12.setPressed(false);
                mPickupTime4.setPressed(true);

                mPickupTime10.setBackgroundColor(Color.GRAY);
                mPickupTime12.setBackgroundColor(Color.GRAY);
                mPickupTime4.setBackgroundColor(Color.BLUE);
            }
        });

        mDelivery4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryTime=mDelivery4.getText().toString();
                mDelivery4.setPressed(true);
                mDelivery10.setPressed(false);
                mDelivery12.setPressed(false);

                mDelivery4.setBackgroundColor(Color.BLUE);
                mDelivery10.setBackgroundColor(Color.GRAY);
                mDelivery12.setBackgroundColor(Color.GRAY);

            }
        });

        mDelivery10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryTime=mDelivery10.getText().toString();
                mDelivery4.setPressed(false);
                mDelivery10.setPressed(true);
                mDelivery12.setPressed(false);

                mDelivery4.setBackgroundColor(Color.GRAY);
                mDelivery10.setBackgroundColor(Color.BLUE);
                mDelivery12.setBackgroundColor(Color.GRAY);

            }
        });
        mDelivery12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryTime=mDelivery12.getText().toString();
                mDelivery4.setPressed(false);
                mDelivery10.setPressed(false);
                mDelivery12.setPressed(true);

                mDelivery4.setBackgroundColor(Color.GRAY);
                mDelivery10.setBackgroundColor(Color.GRAY);
                mDelivery12.setBackgroundColor(Color.BLUE);

            }
        });



        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPickUpDateToday.setPressed(false);
//                mPickUpDateTomorrow.setPressed(false);
//                mPickUpDateTheAfterTomorrow.setPressed(false);
//                mDeliveryDateTomorrow.setPressed(false);
//                mDeliveryDateTheAfter.setPressed(false);
//                mDeliveryDateTheAfterAfter.setPressed(false);
//                mPickupTime4.setPressed(false);
//                mPickupTime10.setPressed(false);
//                mPickupTime12.setPressed(false);
//                mDelivery4.setPressed(false);
//                mDelivery10.setPressed(false);
//                mDelivery12.setPressed(false);
//
//                mPickUpDateToday.setBackgroundColor(Color.GRAY);
//                mPickUpDateTomorrow.setBackgroundColor(Color.GRAY);
//                mPickUpDateTheAfterTomorrow.setBackgroundColor(Color.GRAY);
//                mDeliveryDateTomorrow.setBackgroundColor(Color.GRAY);
//                mDeliveryDateTheAfter.setBackgroundColor(Color.GRAY);
//                mDeliveryDateTheAfterAfter.setBackgroundColor(Color.GRAY);
//
//                mPickupTime4.setBackgroundColor(Color.GRAY);
//                mPickupTime10.setBackgroundColor(Color.GRAY);
//                mPickupTime12.setBackgroundColor(Color.GRAY);
//
//                mDelivery4.setBackgroundColor(Color.GRAY);
//                mDelivery10.setBackgroundColor(Color.GRAY);
//                mDelivery12.setBackgroundColor(Color.GRAY);

                Intent intent = new Intent(OrderDetails.this,MapsActivity.class);
                intent.putExtra("from_activity","OrderDetails");
                startActivity(intent);

            }
        });

    }

    private void init() {
        mPickUpDateToday=findViewById(R.id.todayPickup_orderDetailsId);
        mPickUpDateTomorrow=findViewById(R.id.tomorrowPickup_orderDetailsId);
        mPickUpDateTheAfterTomorrow=findViewById(R.id.theyAfterTomorrowPickup_orderDetailsId);

        mDeliveryDateTomorrow=findViewById(R.id.tomorrowDelivery_orderDetailsId);
        mDeliveryDateTheAfter=findViewById(R.id.theAfterTmrvDelivery_orderDetailsId);
        mDeliveryDateTheAfterAfter=findViewById(R.id.theAfterAfterDelivery_orderDetailsId);
        mEdit=findViewById(R.id.edit_orderDetailsId);


        mPickupTime10=findViewById(R.id.pickUp_10_30);
        mPickupTime12=findViewById(R.id.pickUp_12_30);
        mPickupTime4=findViewById(R.id.pickUp_4);

        mDelivery10=findViewById(R.id.delivery_10_30);
        mDelivery12=findViewById(R.id.delivery_12_30);
        mDelivery4=findViewById(R.id.delivery_4);

        mProceed=findViewById(R.id.proceed_OrderDetailsId);
        address_orderDetails=findViewById(R.id.OrderDetails_userAddressId);



        setText1();

    }

    private void setText1() {
        Calendar now = Calendar.getInstance();
        int currentDate=now.get(Calendar.DAY_OF_MONTH);
        String currentDay=String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String currentMonth=String.valueOf(now.get(Calendar.MONTH)+1 );
        String tomorrow=String.valueOf(now.get(Calendar.DAY_OF_MONTH)+1);
        String theAfterTmrv=String.valueOf(now.get(Calendar.DAY_OF_MONTH)+2);
        String theAfterAfter=String.valueOf(now.get(Calendar.DAY_OF_MONTH)+3);
        String myMonth ;
        switch (currentMonth){
            case "1":
                myMonth="Jan" ;
                break;
            case "2":
                myMonth="Feb" ;
                break;
            case "3":
                myMonth="Mar" ;
                break;
            case "4":
                myMonth="Apr" ;
                break;

            case "5":
                myMonth="May" ;
                break;

            case "6":
                myMonth="Jun" ;
                break;

            case "7":
                myMonth="Jul" ;
                break;

            case "8":
                myMonth="Aug" ;
                break;

            case "9":
                myMonth="Sep" ;
                break;

            case "10":
                myMonth="Oct" ;
                break;

            case "11":
                myMonth="Nov" ;
                break;
            case "12":
                myMonth="Dec" ;
                break;
            default:
                myMonth="default month";
        }

        mPickUpDateToday.setText("Today\n "+currentDay+" "+myMonth);
        mPickUpDateTomorrow.setText("Tomorrow\n"+tomorrow+" "+myMonth);
        mPickUpDateTheAfterTomorrow.setText(theAfterTmrv+" "+myMonth);

        mDeliveryDateTomorrow.setText("Tomorrow\n"+tomorrow+" "+myMonth);
        mDeliveryDateTheAfter.setText(theAfterTmrv+" "+myMonth);
        mDeliveryDateTheAfterAfter.setText(theAfterAfter+" "+myMonth);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);

        address_orderDetails.setText(sharedPreferencesConfig.read_address());
    }
}