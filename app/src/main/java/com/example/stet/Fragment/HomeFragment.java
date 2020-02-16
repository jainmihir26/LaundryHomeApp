package com.example.stet.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Activities.ClothSelectActivity;
import com.example.stet.Adapters.HomeAdapter;
import com.example.stet.Helper.ServiceTypes;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.HomeModel;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {



    private GridView mGridView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList;

    private TextView mUserName;

    private String userName1 ="AMAN" ;

    private String url_landing_page = "http://192.168.0.103:8000/apis/get_price_list/";

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_home,container,false);


        final Context thisContext = container.getContext() ;

        mGridView=view.findViewById(R.id.home_gridViewId);
        mUserName =view.findViewById(R.id.homeFragment_helloTextId);



        mArrayList=new ArrayList<>();
        mArrayList.add(new HomeModel(R.drawable.wash_and_fold));
        mArrayList.add(new HomeModel(R.drawable.iron));
        mArrayList.add(new HomeModel(R.drawable.wash_and_iron));
        mArrayList.add(new HomeModel(R.drawable.dry_clean));
        mHomeAdapter=new HomeAdapter(thisContext,mArrayList);
        mGridView.setAdapter(mHomeAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(mArrayList.get(position).getImage()){
                    case R.drawable.wash_and_fold :

//                        StringRequest stringRequest_wf = new StringRequest(Request.Method.POST,url_landing_page, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                                parseData(response);
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("token","1392e44b9fed4cf5007963dc77b0bc2cbe1dbbc6");
//                                return params;
//                            }
//                        };
//
//                        RequestQueue requestQueue_wf = Volley.newRequestQueue(getActivity());
//                        requestQueue_wf.add(stringRequest_wf);


                        ServiceTypes.service_type = "wash_fold";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;
                    case R.drawable.iron :

//                        StringRequest stringRequest_i = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                                parseData(response);
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("token","1392e44b9fed4cf5007963dc77b0bc2cbe1dbbc6");
//                                return params;
//                            }
//                        };
//
//                        RequestQueue requestQueue_i = Volley.newRequestQueue(getActivity());
//                        requestQueue_i.add(stringRequest_i);


                        ServiceTypes.service_type = "iron";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                    case R.drawable.wash_and_iron :

//                        StringRequest stringRequest_wi = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                                parseData(response);
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("token","1392e44b9fed4cf5007963dc77b0bc2cbe1dbbc6");
//                                return params;
//                            }
//                        };
//
//                        RequestQueue requestQueue_wi = Volley.newRequestQueue(getActivity());
//                        requestQueue_wi.add(stringRequest_wi);


                        ServiceTypes.service_type = "wash_iron";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                    case R.drawable.dry_clean:

//                        StringRequest stringRequest_dc = new StringRequest(Request.Method.POST, url_landing_page, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//                                parseData(response);
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> params = new HashMap<String,String>();
//                                params.put("token","1392e44b9fed4cf5007963dc77b0bc2cbe1dbbc6");
//                                return params;
//                            }
//                        };
//
//                        RequestQueue requestQueue_dc = Volley.newRequestQueue(getActivity());
//                        requestQueue_dc.add(stringRequest_dc);

                        ServiceTypes.service_type = "dry_clean";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                }


            }
        });
        return view;
    }

//    public void parseData(String response){
//        getActivity().deleteDatabase("cloth_selection_db");
//        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
//        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
//
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//
//            JSONArray price_json_list = (JSONArray) jsonObject.get("price_obj");
//
//
//            for(int i=0;i<price_json_list.length();i++){
//                JSONObject jsonObject_each =  (JSONObject) price_json_list.get(i);
//
//                int id = (int)jsonObject_each.get("item_id");
//                String category = (String)jsonObject_each.get("category");
//                String sub_category = (String)jsonObject_each.get("sub_category");
//                String item_name = (String)jsonObject_each.get("item_name");
//                Double item_price = (Double)jsonObject_each.get("item_price");
//
//                clothSelectorDbHelper.add_cloth(id,category,sub_category,item_name,item_price.intValue(),0,sqLiteDatabase);
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

}




