package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FloatingActionButton login_button;
    private EditText phone_number_login;
    private EditText password_login;
    private TextView sign_up;
    private TextView forget_password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        final String urlLogin = "http://192.168.1.208:8000/apis/login/";
        login_button = findViewById(R.id.sign_in_login);
        phone_number_login = findViewById(R.id.phone_number_login);
        password_login = findViewById(R.id.password_login);
        sign_up = findViewById(R.id.sign_up_login);
        forget_password = findViewById(R.id.forget_password_login);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone_no = phone_number_login.getText().toString().trim();
                final String password = password_login.getText().toString().trim();

                if(validateEntities(phone_no,password)){

                }else{

                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlLogin, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this,response, Toast.LENGTH_SHORT).show();
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("phone_no",phone_no);
                        params.put("password",password);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    private boolean validateEntities(String phone_no, String password) {
        if(phone_no.isEmpty() || phone_no.length()<10){
            phone_number_login.setError("Not A Valid Phone Number");
            phone_number_login.requestFocus();
            return  false;
        }
        if(password.isEmpty() || password.length()<4){
            password_login.setError("Not A Valid Password.");
            password_login.requestFocus();
            return false;
        }
        return true;
    }

    public void parseData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.getString("error").equals("false")){

                String addressInJson=jsonObject.getString("address");
                Log.d(TAG, "parseData:address is  "+addressInJson);
                String firstName=jsonObject.getString("first_name");
                Log.d(TAG, "parseData:firstname is  "+ firstName);
                String lastName=jsonObject.getString("last_name");
                Log.d(TAG, "parseData: lastname "+ lastName);
                String phoneNumber=jsonObject.getString("phone_no");
                Log.d(TAG, "parseData: phone_no "+phoneNumber);
                String email=jsonObject.getString("email");
                Log.d(TAG, "parseData:email is  "+email);
                JSONObject jsonObject_address =jsonObject.getJSONObject("address");




                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
                sharedPreferencesConfig.write_full_name(firstName+" "+lastName);
                sharedPreferencesConfig.write_email(email);
                sharedPreferencesConfig.write_phone_number(phoneNumber);
                sharedPreferencesConfig.write_token(jsonObject.getString("token"));
                sharedPreferencesConfig.write_address(jsonObject_address.getString("main_address"));



                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

