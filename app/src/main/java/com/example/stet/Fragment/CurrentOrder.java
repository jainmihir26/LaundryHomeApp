package com.example.stet.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CurrentOrder extends Fragment {


    private ProgressBar mProgressBarLoading;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;
    private ArrayList<DataCurrentOrder> data = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_order, container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view_fragment_current_order);
        Log.d("Current order", "onCreateView: ");
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
                Log.e("promotion", "onErrorResponse: ",error );
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
        private ArrayList<DataCurrentOrder> dataList;

        public ListAdapter(ArrayList<DataCurrentOrder> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewOrderConfirmStatus;
            TextView textViewDateTimeOrder;
            TextView textViewOrderId;
            TextView textViewOrderCost;
            Button button_getHelp,cancel_order;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewOrderConfirmStatus = (TextView) itemView.findViewById(R.id.order_confirm_textView);
                this.textViewDateTimeOrder = (TextView) itemView.findViewById(R.id.time_date_order_textview);
                this.textViewOrderId = (TextView) itemView.findViewById(R.id.order_id_textView);
                this.textViewOrderCost = (TextView) itemView.findViewById(R.id.total_amount_order_textView);
                this.button_getHelp = (Button) itemView.findViewById(R.id.get_help_order);
                this.cancel_order = (Button) itemView.findViewById(R.id.cancel_order_button);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_current_order_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            //holder.textViewOrderConfirmStatus.setText(dataList.get(position).getOrder_confirm_status());
            holder.textViewDateTimeOrder.setText(dataList.get(position).getOrder_place_date_time());
            holder.textViewOrderId.setText("Order Id:"+dataList.get(position).getOrder_id());
            holder.textViewOrderCost.setText(dataList.get(position).getOrder_amount()+"$");

            holder.button_getHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Get Helped Clicked for order id"+dataList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Canceled Clicked for order id"+dataList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
                }
            });



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
                //JSONArray cart_dict = (JSONArray) order.get("cart_dict");
                Double price = (double)order.get("price");
                String order_status = (String)order.get("order_status");
                String pick_up_date = (String)order.get("pickup_date");
                String pick_up_time = (String)order.get("pickup_time");
                data.add(new DataCurrentOrder(order_id,pick_up_date+pick_up_time,order_status,Double.toString(price)));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mListadapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("current_order", "onResume: ");
    }


}


