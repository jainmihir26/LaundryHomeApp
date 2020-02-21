package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Fragment.CurrentOrder;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.DataAddress;
import com.example.stet.Models.DataCurrentOrder;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectAddressActivity extends AppCompatActivity {


    Button add_new_address_button;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private ArrayList<DataAddress> data = new ArrayList<>();
    private String main_address=null;
    private int main_address_id=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        add_new_address_button = findViewById(R.id.new_address_button);
        mRecyclerView = findViewById(R.id.recycler_view_addresses);

        add_new_address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectAddressActivity.this,MapsActivity.class);
                intent.putExtra("from_activity","OrderDetails");
                startActivity(intent);
            }


        });



        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.FetchAddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SelectAddressActivity.this,response, Toast.LENGTH_SHORT).show();
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Select Address", "onErrorResponse: ",error );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
                params.put("token",sharedPreferencesConfig.read_token());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataAddress> dataList;

        public ListAdapter(ArrayList<DataAddress> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewAddress;
            Button selectAddress;
            Button deleteAddress;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewAddress = (TextView) itemView.findViewById(R.id.address_recyler_view_item);
                this.selectAddress = (Button) itemView.findViewById(R.id.select_address_recycler_view_item);
                this.deleteAddress = (Button) itemView.findViewById(R.id.delete_address);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_address, parent, false);

            ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {


            for(int i=0;i<dataList.size();i++){
                if(dataList.get(i).getAddressType().equals("main")){
                    main_address = dataList.get(i).getAddress();
                    main_address_id = dataList.get(i).getId();
                    break;
                }

            }





            holder.textViewAddress.setText(dataList.get(position).getAddress());
            holder.selectAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
                    sharedPreferencesConfig.write_address(dataList.get(position).getAddress());
                    sharedPreferencesConfig.write_AddressId(dataList.get(position).getId());
                    startActivity(new Intent(SelectAddressActivity.this,OrderDetails.class));
                }
            });

            if(dataList.get(position).getId() == main_address_id){
                holder.deleteAddress.setVisibility(View.GONE);
            }

            holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

                    if(dataList.get(position).getId() == sharedPreferencesConfig.read_address_id()){
                        Log.d("Select address",Integer.toString(dataList.get(position).getId()));
                        sharedPreferencesConfig.write_AddressId(main_address_id);
                        sharedPreferencesConfig.write_address(main_address);
                    }


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.RemoveAddress, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseDataDelete(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Select Address", "onErrorResponse: ",error );
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
                            params.put("token",sharedPreferencesConfig.read_token());
                            params.put("address_id",Integer.toString(dataList.get(position).getId()));
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }
            });

        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }

    public void parseDataDelete(String response){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            String error = (String)jsonObject.get("error");

            if(error.equals("false")){
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }else{
                Toast.makeText(this, "Not deleted", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void parseData(String response){

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            JSONArray address_list = (JSONArray)jsonObject.get("addresses_list");

            for(int i=0;i<address_list.length();i++){
                JSONObject address = (JSONObject)address_list.get(i);

               int id=(int)address.get("id");
                String main_address = (String)address.get("main_address");
                String address_type = (String)address.get("address_type");
                String area = (String)address.get("area");
                String landmark = (String)address.get("landmark");
                String apt_number = (String)address.get("apt_number");
                String building_name = (String)address.get("building_name");



                data.add(new DataAddress(id,main_address+" "+landmark+" "+apt_number+" "+building_name+area,address_type));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mListadapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SelectAddressActivity.this,OrderDetails.class));
        finish();
    }
}
