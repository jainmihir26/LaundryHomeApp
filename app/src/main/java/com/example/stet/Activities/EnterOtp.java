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

public class EnterOtp extends AppCompatActivity {
    private static final String TAG = "EnterOtp";
    private EditText mOtp;
    private Button mVerify ;
    String phoneNumber,token1;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        mOtp=findViewById(R.id.otp_enterId);
        mVerify=findViewById(R.id.verify_enterId);
        password_edit_text = findViewById(R.id.password_enterId);




        phoneNumber=getIntent().getStringExtra("phone_no");
        final SharedPreferencesConfig sharedPreferencesConfig=new SharedPreferencesConfig(EnterOtp.this);
        token1=sharedPreferencesConfig.read_token();
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp=mOtp.getText().toString();
                final String password=password_edit_text.getText().toString();


                StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.updatePhoneNo, new Response.Listener<String>() {
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
                        SharedPreferencesConfig sharedPreferencesConfig1 = new SharedPreferencesConfig(getApplicationContext());
                        params.put("phone_no",sharedPreferencesConfig.read_phone_number());
                        params.put("new_phone_no",phoneNumber);
                        params.put("token",token1);
                        params.put("otp",otp);
                        params.put("password",password);

                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(EnterOtp.this);
                requestQueue.add(stringRequest);

            }
        });
    }
    private void parseData(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("error").equals("false")){
                Toast.makeText(EnterOtp.this, "Phone Number Changed Successfully.", Toast.LENGTH_SHORT).show();
                SharedPreferencesConfig sharedPreferencesConfig=new SharedPreferencesConfig(EnterOtp.this);
                sharedPreferencesConfig.write_phone_number(phoneNumber);
                Intent intent=new Intent(EnterOtp.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(EnterOtp.this, "Something went wrong "+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EnterOtp.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Log.e(TAG, "parseData: "+response );
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseData: ",e );
            e.printStackTrace();
        }
    }
}
