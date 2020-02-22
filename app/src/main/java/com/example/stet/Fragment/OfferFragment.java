package com.example.stet.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.stet.Models.DataPromotion;
import com.example.stet.Models.LoadingDialog;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OfferFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<DataPromotion> data = new ArrayList<>();
    private ListAdapter mListadapter;
    private LoadingDialog loadingDialog ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offer,container,false);

        mRecyclerView = v.findViewById(R.id.recycler_view_promotions);
        assert container != null;
        loadingDialog=new LoadingDialog((Activity) container.getContext());


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        loadingDialog.startLoadingDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.promotionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
                loadingDialog.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("promotion", "onErrorResponse: ",error );
                loadingDialog.dismissDialog();
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

        return v;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataPromotion> dataList;

        public ListAdapter(ArrayList<DataPromotion> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewPromoTitle;
            TextView textViewDateExpiry;
            TextView textViewUseCode;
            Button promoCode;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewPromoTitle = (TextView) itemView.findViewById(R.id.promo_title);
                this.textViewDateExpiry = (TextView) itemView.findViewById(R.id.promo_date_expiry);
                this.textViewUseCode = (TextView) itemView.findViewById(R.id.use_code_promo);
                this.promoCode = (Button) itemView.findViewById(R.id.promo_code);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_promo, parent, false);

            ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewPromoTitle.setText(dataList.get(position).getTitle());
            holder.textViewDateExpiry.setText(dataList.get(position).getDate());
            holder.promoCode.setText(dataList.get(position).getCode());


            holder.promoCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
            JSONArray promos_list = (JSONArray) jsonObject.get("promos");

            for(int i=0;i<promos_list.length();i++){
                JSONObject jsonObject_each =  (JSONObject)promos_list.get(i);

                int id = (int)jsonObject_each.get("id");
                String promo_title = (String)jsonObject_each.get("promo_title");
                String expiry = (String)jsonObject_each.get("expiry");
                String code = (String)jsonObject_each.get("code");
                data.add(
                        new DataPromotion(id,promo_title,expiry,code)
                );
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }



        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter(mListadapter);
    }
}



