package com.example.stet.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {
    private static final String TAG = "EditAccountActivity";
    private EditText mFirstName ;
    private EditText mLastName;
    private EditText mEmail;
    private TextView mPhoneNumber;
    private Button mSave;
    String newFirstName,newLastName,newEmail,userToken;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        init();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFirstName=mFirstName.getText().toString();
                newEmail=mEmail.getText().toString();
                newLastName=mLastName.getText().toString();
                SharedPreferencesConfig sharedPreferencesConfig=new SharedPreferencesConfig(EditAccountActivity.this);
                final String userToken=sharedPreferencesConfig.read_token();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.updateProfile, new Response.Listener<String>() {
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
                        params.put("first_name",newFirstName);
                        params.put("last_name",newLastName);
                        params.put("email",newEmail);
                        params.put("token",userToken);

                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(EditAccountActivity.this);
                requestQueue.add(stringRequest);

            }
        });

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAccountActivity.this,ChangePhoneNumberActivity.class));
            }
        });
    }

    private void parseData(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("error").equals("false")){
                Toast.makeText(EditAccountActivity.this, "Profile Successfully changed.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "parseData: "+response);
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(EditAccountActivity.this);
                sharedPreferencesConfig.write_full_name(newFirstName+" "+newLastName);
                sharedPreferencesConfig.write_email(newEmail);
                Intent intent=new Intent(EditAccountActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(EditAccountActivity.this, "Something went wrong."+response, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "parseData: "+response );
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseData: ",e );
            e.printStackTrace();
        }
    }

    private void init() {
        mFirstName=findViewById(R.id.newFirstName_Id);
        mLastName=findViewById(R.id.newLastName_Id);
        mEmail=findViewById(R.id.newEmailId);
        mPhoneNumber=findViewById(R.id.chanhgePhoneNumberId);
        mSave=findViewById(R.id.saveId);
    }

}
