package com.example.stet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandingActivity extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4;
    private String url_landing_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        btn1 = findViewById(R.id.button_1_landing);
        btn2 = findViewById(R.id.button_2_landing);
        btn3 = findViewById(R.id.button_3_landing);
        btn4 = findViewById(R.id.button_4_landing);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.service_type = "wash_fold";
                startActivity(new Intent(LandingActivity.this,ClothSelectActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.service_type = "wash_iron";
                startActivity(new Intent(LandingActivity.this,ClothSelectActivity.class));

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.service_type = "iron";
                startActivity(new Intent(LandingActivity.this,ClothSelectActivity.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.service_type = "dry_clean";
                startActivity(new Intent(LandingActivity.this,ClothSelectActivity.class));
            }
        });

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        parseData(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<String,String>();
//                        params.put("service_id","1");
//                        return params;
//                    }
//                };
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        parseData(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<>();
//                        params.put("service_id","2");
//                        return params;
//                    }
//                };
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//            }
//        });
//
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        parseData(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<>();
//                        params.put("service_id","3");
//                        return params;
//                    }
//                };
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//            }
//        });
//
//
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        parseData(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<>();
//                        params.put("service_id","4");
//                        return params;
//                    }
//                };
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);
//            }
//        });
    }


//    private void parseData(String response){
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//
//            if(jsonObject.getString("error").equals("false")){
//
//
//
//
//
//
//
//
//
//
//
//            }else{
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
}
