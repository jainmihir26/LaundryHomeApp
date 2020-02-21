package com.example.stet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Helper.Urls;
import com.example.stet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumberActivity extends AppCompatActivity {
    private static final String TAG = "PhoneNumberActivity";
    EditText mPhoneNumber ;
    Button mProceed ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        init();
        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber=mPhoneNumber.getText().toString();
                if(phoneNumber.length()<10){
                    mPhoneNumber.setError("Please enter a valid phone-number.");
                    mPhoneNumber.requestFocus();
                    return;
                }
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.generateOtpUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PhoneNumberActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: ",error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("phone_no",phoneNumber);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        });

    }

    private void parseData(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("error").equals("false")){
                Intent intent=new Intent(PhoneNumberActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("phone_no",mPhoneNumber.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Intent intent=new Intent(PhoneNumberActivity.this,PhoneNumberActivity.class);
                Toast.makeText(PhoneNumberActivity.this, "Sorry! Wrong Phone Number!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        mPhoneNumber=findViewById(R.id.phoneNumberId);
        mProceed=findViewById(R.id.buttonProceedId);
    }
}
