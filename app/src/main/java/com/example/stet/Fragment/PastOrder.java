package com.example.stet.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.DataCurrentOrder;
import com.example.stet.Models.DataPastOrder;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PastOrder extends Fragment {


    private ProgressBar mProgressBarLoading;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private ArrayList<DataPastOrder> data = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_order, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_fragment_past_order);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.getOrderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),response, Toast.LENGTH_SHORT).show();
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("past_order", "onErrorResponse: ",error );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
                params.put("token",sharedPreferencesConfig.read_token());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return view;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataPastOrder> dataList;

        public ListAdapter(ArrayList<DataPastOrder> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewOrderConfirmStatus;
            TextView textViewDateTimeOrder;
            TextView textViewOrderId;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewOrderConfirmStatus = (TextView) itemView.findViewById(R.id.order_status_past_order);
                this.textViewDateTimeOrder = (TextView) itemView.findViewById(R.id.time_date_past_order_textview);
                this.textViewOrderId = (TextView) itemView.findViewById(R.id.order_past_id_textView);


            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_past_order, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position)
        {
            holder.textViewOrderConfirmStatus.setText(dataList.get(position).getOrder_status().substring(0,1).toUpperCase()+dataList.get(position).getOrder_status().substring(1)+"!");
            holder.textViewDateTimeOrder.setText(dataList.get(position).getOrder_time());
            holder.textViewOrderId.setText("Order Id:"+dataList.get(position).getOrder_id());

        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }



    void parseData(String response){

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            JSONArray orders = (JSONArray)jsonObject.get("orders");

            for(int i=0;i<orders.length();i++){
                JSONObject order = (JSONObject)orders.get(i);

                String order_id = (String)order.get("order_id");
//                JSONObject cart_dict = (JSONObject) order.get("cart_dict");
                Double price = (Double) order.get("price");
                String order_status = (String)order.get("order_status");
                String delivery_date = (String)order.get("delivery_date");
                String delivery_time = (String)order.get("delivery_time");
                String order_time = (String)order.get("order_time");
                String date = order_time.split("T",2)[0];
                String time = order_time.split("T",2)[1];
                time = time.substring(0,8);

                if((order_status.equals("failed") || order_status.equals("delivered"))){
                    data.add(new DataPastOrder(date+","+time,order_id,delivery_date+delivery_time,order_status,Double.toString(price)));
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mListadapter);
    }


}


