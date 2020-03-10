package com.example.stet.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Activities.MainActivity;
import com.example.stet.Adapters.PopUpAdapter;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataCurrentOrder;
import com.example.stet.Models.DataModel;
import com.example.stet.Models.LoadingDialog;
import com.example.stet.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    private LoadingDialog loadingDialog;
    private ViewPager layout_MainMenu;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_order, container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view_fragment_current_order);
        Log.d("Current order", "onCreateView: ");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        loadingDialog = new LoadingDialog(getActivity());

        layout_MainMenu = view.findViewById(R.id.tablayout_order_fragment);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.getOrderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
        private ViewPager currentPastViewPager;
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
            ImageView order_placed;
            ImageView order_pick_up;
            ImageView order_processing;
            ImageView out_for_delivery;
            TextView view_details_textView;





            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewOrderConfirmStatus = (TextView) itemView.findViewById(R.id.order_confirm_textView);
                this.textViewDateTimeOrder = (TextView) itemView.findViewById(R.id.time_date_order_textview);
                this.textViewOrderId = (TextView) itemView.findViewById(R.id.order_id_textView);
                this.textViewOrderCost = (TextView) itemView.findViewById(R.id.total_amount_order_textView);
                this.button_getHelp = (Button) itemView.findViewById(R.id.get_help_order);
                this.cancel_order = (Button) itemView.findViewById(R.id.cancel_order_button);
                this.order_placed = (ImageView) itemView.findViewById(R.id.order_placed);
                this.order_pick_up = (ImageView) itemView.findViewById(R.id.order_pick_up);
                this.order_processing = (ImageView) itemView.findViewById(R.id.order_processing);
                this.out_for_delivery = (ImageView) itemView.findViewById(R.id.out_for_delivery);
                this.view_details_textView = (TextView) itemView.findViewById(R.id.view_details_current_order);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_current_order_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //holder.textViewOrderConfirmStatus.setText(dataList.get(position).getOrder_confirm_status());
            holder.textViewDateTimeOrder.setText(dataList.get(position).getOrder_time());
            holder.textViewOrderId.setText("Order Id:" + dataList.get(position).getOrder_id());
            holder.textViewOrderCost.setText(dataList.get(position).getOrder_amount() + "$");


            holder.button_getHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Get Helped Clicked for order id" + dataList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Canceled Clicked for order id" + dataList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
                }
            });

            if (dataList.get(position).getOrder_status().equals("pickup_pending")) {
                holder.order_placed.setImageResource(R.drawable.green_dots);
            }

            if (dataList.get(position).getOrder_status().equals("delivery_pending")) {
                holder.order_placed.setImageResource(R.drawable.green_dots);
                holder.order_pick_up.setImageResource(R.drawable.green_dots);
                holder.order_processing.setImageResource(R.drawable.green_dots);
            }


            holder.view_details_textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    currentPastViewPager = view.findViewById(R.id.current_past_order_view_pager);
//                    currentPastViewPager.setClickable(false);

//                    layout_MainMenu.setClickable(false);



                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.demo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                    //layout_MainMenu.setAlpha(1);
                    popupWindow.getAnimationStyle();

                    RecyclerView mPopupRecyclerView;
                    mPopupRecyclerView = popupView.findViewById(R.id.recycler_view_popup);
                    ArrayList<DataModel> models;
                    RecyclerView.Adapter mAdapter;

                    models = new ArrayList<>();


                    JSONArray cart_items  = dataList.get(position).getCart_items();

                    for (int i=0;i<cart_items.length();i++){
                        try {

                            JSONObject jsonObject = (JSONObject) cart_items.get(i);

                            int item_id = (int)jsonObject.get("item_id");
                            int quantity = (int)jsonObject.get("quantity");

                            String service_type = null;
                            String cloth_item = null;
                            int price = 0;

                            ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
                            SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
                            Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

                            while (cursor.moveToNext())
                            {
                                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));

                                if(id == item_id){
                                    Log.d("in current", "Id MAtched: ");
                                    service_type = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
                                    cloth_item = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME));
                                    price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
                                    break;
                                }
                            }
                            cursor.close();
                            clothSelectorDbHelper.close();
                            Log.d("in current",quantity + cloth_item+service_type+price*quantity);
                            models.add(new DataModel(quantity + cloth_item,service_type,price*quantity));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    mAdapter = new PopUpAdapter(getActivity(),models);
                    mPopupRecyclerView.setHasFixedSize(true);
                    mPopupRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mPopupRecyclerView.setAdapter(mAdapter);


                    Button btn = (Button) popupView.findViewById(R.id.closePopupBtn);
                    TextView textView = popupView.findViewById(R.id.txtclose);


                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    popupWindow.setWidth(1000);
                    popupWindow.setHeight(1000);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                    popupWindow.update();
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

                Log.d("Current Order", "Parse ");

                String order_id = (String)order.get("order_id");
                //JSONObject cart_dict = (JSONObject) order.get("cart_dict");
                JSONArray cart = (JSONArray) order.get("cart_dict");
                Double price = (double)order.get("price");
                String order_status = (String)order.get("order_status");
                String pick_up_date = (String)order.get("pickup_date");
                String pick_up_time = (String)order.get("pickup_time");
                String order_time = (String)order.get("order_time");

                String date = order_time.split("T",2)[0];
                String time = order_time.split("T",2)[1];
                time = time.substring(0,8);


                if(!(order_status.equals("failed") || order_status.equals("delivered"))){
                    data.add(new DataCurrentOrder(date+" "+time,order_id,pick_up_date+pick_up_time,order_status,Double.toString(price),cart));
                }


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


