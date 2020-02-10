package com.example.stet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final String url = "http://192.168.43.27:8000/apis/register/";

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

                if(password.equals(confirm_password)){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegisterActivity.this,response, Toast.LENGTH_SHORT).show();
                            parseData(response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("first_name",first_name);
                            params.put("last_name",last_name);
                            params.put("email",email);
                            params.put("phone_no",phone_number);
                            params.put("password",password);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(RegisterActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    private void parseData(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.getString("error").equals("false")){
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
