package com.example.stet.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.stet.Models.OfferData;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ApplyPromotionActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private ArrayList<OfferData>arrayList=new ArrayList<>();
    private MyAdapter myAdapter ;
    private String amountToBePaid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_promotion);

         amountToBePaid=getIntent().getStringExtra("amount_to_be_paid");

        recyclerView=findViewById(R.id.recyclerView_applyPromotion);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(ApplyPromotionActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.promotionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(ApplyPromotionActivity.this);
                params.put("token",sharedPreferencesConfig.read_token());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ApplyPromotionActivity.this);
        requestQueue.add(stringRequest);

    }

    private void parseData(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray promos_list = (JSONArray) jsonObject.get("promos");

            for(int i=0;i<promos_list.length();i++){
                JSONObject jsonObject_each =  (JSONObject)promos_list.get(i);

                int id = (int)jsonObject_each.get("id");
                String description = (String)jsonObject_each.get("promo_title");
                String expiry = (String)jsonObject_each.get("expiry");
                String title = (String)jsonObject_each.get("code");
                double discountPercentage=(double) jsonObject_each.get("discount_per");
                double minAmount= (double) jsonObject_each.get("min_amt");
                double maxDiscount= (double) jsonObject_each.get("max_discount");

//                data.add(
//                        new DataPromotion(id,promo_title,expiry,code)
//                );
                arrayList.add(new OfferData(title,description,expiry,minAmount,discountPercentage,maxDiscount));


            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        myAdapter = new MyAdapter(ApplyPromotionActivity.this,arrayList,amountToBePaid);
        recyclerView.setAdapter(myAdapter);

    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private ArrayList<OfferData> arrayList;
    private Context context;
    private String amountToBePaid;

    public MyAdapter(Context context,ArrayList<OfferData>arrayList,String amountToBePaid){
        this.context=context;
        this.arrayList=arrayList ;
        this.amountToBePaid=amountToBePaid ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_promotion,parent,false);
        MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTitle.setText(arrayList.get(position).getTitle());
        holder.mDescription.setText(arrayList.get(position).getDescription());
        holder.mValidity.setText(arrayList.get(position).getValidity());

        holder.mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(amountToBePaid )< (arrayList.get(position).getMinAmount())){
                    Toast.makeText(context, "Please order above "+arrayList.get(position).getMinAmount()+" to avail this offer.", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(context,Order.class);
                    double initAmount=Double.parseDouble(amountToBePaid );

                    Log.d(TAG, "onClick: "+" Discount perce: "+String.valueOf(arrayList.get(position).getDiscountPercenrage()) );
                    intent.putExtra("discount_percentage",String.valueOf(arrayList.get(position).getDiscountPercenrage()) );

                    intent.putExtra("max_discount",String.valueOf(arrayList.get(position).getMaxDiscount()));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle ;
        TextView mDescription ;
        TextView mValidity ;
        Button mApply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.title_ApplyPromotionId);
            mDescription=itemView.findViewById(R.id.description_ApplyPromotionId);
            mValidity=itemView.findViewById(R.id.validity_ApplyPromotionId);
            mApply=itemView.findViewById(R.id.apply_buttonId);
        }
    }
}