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
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePhoneNumberActivity extends AppCompatActivity {
    private static final String TAG = "ChangePhoneNumberActivi";
    private EditText mPhoneNumber;
    private Button mSave;

    private String phoneNumber1,token1 ,newPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);
        mPhoneNumber=findViewById(R.id.phoneNumberId);
        mSave=findViewById(R.id.saveId);
        SharedPreferencesConfig sharedPreferencesConfig=new SharedPreferencesConfig(ChangePhoneNumberActivity.this);
        token1=sharedPreferencesConfig.read_token();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone=mPhoneNumber.getText().toString();
                if(newPhone.length()<10){
                    mPhoneNumber.setError("Invalid Phone Number");
                    mPhoneNumber.requestFocus();
                    return;
                }
                SharedPreferencesConfig sharedPreferencesConfig=new SharedPreferencesConfig(ChangePhoneNumberActivity.this);
                String oldPhone=sharedPreferencesConfig.read_phone_number();
                if(newPhone.equals(oldPhone)){
                    mPhoneNumber.setError("Phone Number not changed.");
                    mPhoneNumber.requestFocus();
                    mPhoneNumber.getText().clear();
                    return;
                }
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.otpForUpdatePhone, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ",error );
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("phone_no",newPhone);
                        params.put("token",token1);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(ChangePhoneNumberActivity.this);
                requestQueue.add(stringRequest);

            }
        });

    }

    private void parseData(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("error").equals("false")){
                Intent intent=new Intent(ChangePhoneNumberActivity.this,EnterOtp.class);
                intent.putExtra("phone_no",newPhone);
                startActivity(intent);

            }else{
                Toast.makeText(ChangePhoneNumberActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseData: ",e );
            e.printStackTrace();
        }
    }
}
