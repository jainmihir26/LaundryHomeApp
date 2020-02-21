package com.example.stet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText first_name_register;
    EditText last_name_register;
    EditText email_register;
    EditText phone_number_register;
    EditText password_register;
    EditText confirm_password_register;
    TextView sign_in;
    FloatingActionButton button_register;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
        sharedPreferencesConfig.write_PriceObjId(0);


        checkAlreadyUser();

        first_name_register = findViewById(R.id.first_name_register);
        last_name_register = findViewById(R.id.last_name_register);
        email_register = findViewById(R.id.email_register);
        phone_number_register = findViewById(R.id.phone_number_register);
        password_register = findViewById(R.id.password_register);
        confirm_password_register = findViewById(R.id.confirm_password_register);
        sign_in = findViewById(R.id.sign_in_register);
        button_register = findViewById(R.id.button_register);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }


        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String first_name = first_name_register.getText().toString().trim();
                final String last_name = last_name_register.getText().toString().trim();
                final String email = email_register.getText().toString().trim();
                final String phone_number = phone_number_register.getText().toString().trim();
                final String password = password_register.getText().toString().trim();
                final String confirm_password = confirm_password_register.getText().toString().trim();

                if (!validateEntities(first_name, last_name, email, phone_number, password, confirm_password)) {
                    return;
                } else {
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
                sharedPreferencesConfig.write_full_name(first_name + " " + last_name);
                sharedPreferencesConfig.write_email(email);
                sharedPreferencesConfig.write_phone_number(phone_number);


                if (password.equals(confirm_password)) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.registerUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                            parseData(response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("first_name", first_name);
                            params.put("last_name", last_name);
                            params.put("email", email);
                            params.put("phone_no", phone_number);
                            params.put("password", password);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(RegisterActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });
    }

    private boolean validateEntities(String first_name,String last_name,String email,String phone_number,String password,String confirm_password ) {
        if(password.length() <4){
            password_register.setError("Password length too short.");
            password_register.requestFocus();
            return false;
        }
        if(!password.equals(confirm_password)){
            confirm_password_register.setError("Doesn't match with password.");
            confirm_password_register.requestFocus();
            return  false;
        }
        if(first_name.isEmpty()){
            first_name_register.setError("Please Enter A Valid First Name");
            first_name_register.requestFocus();
            return false;
        }
        if(last_name.isEmpty()){
            last_name_register.setError("Please Enter A Valid Last Name");
            last_name_register.requestFocus();
            return false;
        }
        if(email.isEmpty()){
            email_register.setError("Please Enter A Valid Email Id ");
            email_register.requestFocus();
            return false;
        }
        if(phone_number.isEmpty() || phone_number.length()<10){
            phone_number_register.setError("Please Enter A Valid Phone Number.");
            phone_number_register.requestFocus();
            return false;
        }

        return true;
    }


    private void checkAlreadyUser() {
        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
        String token=sharedPreferencesConfig.read_token();
        if(token.equals("DEFAULT_TOKEN")){
            return ;
        }else{
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void parseData(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("error").equals("false")){

                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(this);
                sharedPreferencesConfig.write_token(jsonObject.getString("token"));


                Intent intent = new Intent(getApplicationContext(),VerifyOtpActivity.class);
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
