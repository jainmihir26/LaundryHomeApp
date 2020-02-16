package com.example.stet.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Activities.RegisterActivity;
import com.example.stet.Helper.Urls;
import com.example.stet.Helper.UserDetailsSharedPreferences;
import com.example.stet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    private Button mLogout;
    private TextView mName ;
    private TextView mEmail ;
    private TextView mAddress ;
    private TextView mPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account,container,false);
        init(v);

        assert container != null;
        final Context thisContext=container.getContext();
        SharedPreferences sharedPreferences=thisContext.getSharedPreferences(UserDetailsSharedPreferences.sharedPreferences,Context.MODE_PRIVATE);
        String userName1=sharedPreferences.getString(UserDetailsSharedPreferences.firstName,"DEFAULT NAME");
        String userEmail1=sharedPreferences.getString(UserDetailsSharedPreferences.userEmail,"DEFAULT Email");
        String userAddress1=sharedPreferences.getString(UserDetailsSharedPreferences.userAddress,"DEFAULT ADDRESS");
        String phoneNumber1=sharedPreferences.getString(UserDetailsSharedPreferences.userPhoneNumber,"DEFAULT_PHONE");
        mName.setText(userName1);
        mEmail.setText(userEmail1);
        mAddress.setText(userAddress1);
        mPhoneNumber.setText(phoneNumber1);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutClicked(thisContext);
            }
        });


        return v;
    }

    private void logoutClicked(final Context thicContext) {
        final SharedPreferences sharedPreferences= thicContext.getSharedPreferences(UserDetailsSharedPreferences.sharedPreferences,Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        final String token=sharedPreferences.getString(UserDetailsSharedPreferences.userIdToken,"DEFAULT TOKEN");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.logoutUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(thicContext, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("error").equals("false")){
                                Toast.makeText(thicContext, "Logout Successful", Toast.LENGTH_SHORT).show();
                                    editor.clear();
                                    editor.apply();
                                Intent intent =new Intent(thicContext, RegisterActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Log.e(TAG, "onResponse: Something wrong");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(thicContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(thicContext, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(thicContext);
        requestQueue.add(stringRequest);
    }

    private void init(View view) {
        mName=view.findViewById(R.id.accountF_userNameId);
        mEmail=view.findViewById(R.id.accountF_userEmailId);
        mAddress=view.findViewById(R.id.accountF_userAddressId);
        mLogout=view.findViewById(R.id.accountF_logoutId);
        mPhoneNumber=view.findViewById(R.id.accountF_userPhoneNumberId);
    }
}
