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

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    private EditText mOtp;
    private EditText mPassword;
    private EditText mReEnterPassword;
    private Button mProceed;
    private String phoneNumber ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        phoneNumber=getIntent().getStringExtra("phone_no");
        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String otp=mOtp.getText().toString();
                final String password=mPassword.getText().toString();
                String reEnterPassword=mReEnterPassword.getText().toString();
                if(password.length()<4){
                    mPassword.setError("Password too short!");
                    mPassword.requestFocus();
                    return;
                }
                if(!password.equals(reEnterPassword)){
                    mReEnterPassword.setError("Doesen't match with password!");
                    mReEnterPassword.requestFocus();
                    return;
                }

                StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.changePassword, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPasswordActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onErrorResponse: ",error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("otp",otp);
                        params.put("phone_no",phoneNumber);
                        params.put("new_password",password);
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
                Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                Log.d(TAG, "parseData: "+jsonObject.getString("message"));
                Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(ForgotPasswordActivity.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                Toast.makeText(ForgotPasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ForgotPasswordActivity.this,ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } catch (JSONException e) {
            Toast.makeText(ForgotPasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, " parseData: ",e );
            e.printStackTrace();
        }
    }

    private void init() {
        mOtp=findViewById(R.id.otpEnter_forgot);
        mPassword=findViewById(R.id.password_forgot);
        mReEnterPassword=findViewById(R.id.re_enterPassword_forgot);
        mProceed=findViewById(R.id.buttonProceedId_forgot);
    }
}
