package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Helper.Urls;
import com.example.stet.Helper.UserDetailsSharedPreferences;
import com.example.stet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyOtpActivity extends AppCompatActivity {
    private static final String TAG = "VerifyOtpActivity";
    private EditText mOtp;
    private Button mProceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked();
            }


        });
    }
    private void buttonClicked() {
        final String otp1=mOtp.getText().toString();
        if(otp1.length()<6){
            mOtp.setError("Please Enter a Valid Otp");
            mOtp.requestFocus();
            return ;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.verifyOtpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("error").equals("false")) {
                                Intent intent=new Intent(VerifyOtpActivity.this,MapsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                        Toast.makeText(VerifyOtpActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences(UserDetailsSharedPreferences.sharedPreferences,MODE_PRIVATE);
                String phoneNumber=sharedPreferences.getString(UserDetailsSharedPreferences.userPhoneNumber,"111");


                params.put("phone_no",phoneNumber);
                params.put("otp",otp1);
                params.put("token",sharedPreferences.getString(UserDetailsSharedPreferences.userIdToken,"null"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(VerifyOtpActivity.this);
        requestQueue.add(stringRequest);
    }

    private void init() {
        mOtp=findViewById(R.id.otp_VerifyOtpId);
        mProceed=findViewById(R.id.proceed_VerifyOtp);
    }
}
