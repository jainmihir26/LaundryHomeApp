package com.example.stet.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.stet.Activities.ClothSelectActivity;
import com.example.stet.Activities.MainActivity;
import com.example.stet.Activities.OrderDetails;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataClothCart;
import com.example.stet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {
    private Button placeOrderButton;
    private TextView addMoreTextView;
    private TextView totalAmountTextView;
    private RecyclerView cartItemsRecyclerView;
    private ListAdapter mListadapter;

    int flag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart,container,false);

        ClothSelectorDbHelper clothSelectorDbHelper1 = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase1 = clothSelectorDbHelper1.getReadableDatabase();
        Cursor cursor1 = clothSelectorDbHelper1.readContacts(sqLiteDatabase1);


        while (cursor1.moveToNext())
        {
            int count = cursor1.getInt(cursor1.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            if(count !=0){
                flag = 1;
            }
        }

        if (flag != 1){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragmentEmpty()).commit();
        }

        placeOrderButton = v.findViewById(R.id.place_order_button);
        addMoreTextView = v.findViewById(R.id.add_more_textView);
        totalAmountTextView = v.findViewById(R.id.total_amount_textView);
        cartItemsRecyclerView = v.findViewById(R.id.recycler_view_fragment_cart);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.dataFromCartUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());

                        JSONArray array=new JSONArray();

                        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
                        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
                        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

                        while (cursor.moveToNext())
                        {
                            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
                            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));
                            if(count !=0 ){
                                JSONObject obj=new JSONObject();
                                try {
                                    obj.put("item_id",id);
                                    obj.put("quantity",count);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(obj);
                            }

                        }

                        cursor.close();
                        params.put("token",sharedPreferencesConfig.read_token());
                        params.put("items",array.toString());
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);


                Intent intent=new Intent(getActivity(),OrderDetails.class);
                startActivity(intent);




            }
        });

        ClothSelectorDbHelper clothSelectorDbHelper_totalCost = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase_totalCost = clothSelectorDbHelper_totalCost.getReadableDatabase();
        Cursor cursor_totalCost = clothSelectorDbHelper_totalCost.readContacts(sqLiteDatabase_totalCost);
        int total_cost = 0;
        while (cursor_totalCost.moveToNext())
        {
            int cloth_price = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor_totalCost.getInt(cursor_totalCost.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
        }
        cursor_totalCost.close();

        totalAmountTextView.setText(total_cost+" $");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);


        ArrayList<DataClothCart> data = new ArrayList<>();
        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

        while (cursor.moveToNext())
        {
            String cloth_name = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_NAME));
            String service_type = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));


            if(count !=0){
                data.add(
                        new DataClothCart(id,cloth_name+" "+count,service_type,price*count)
                );
            }

        }
        cursor.close();



        mListadapter = new ListAdapter(data);
        cartItemsRecyclerView.setAdapter(mListadapter);

        addMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            }
        });


        return v;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataClothCart> dataList;

        public ListAdapter(ArrayList<DataClothCart> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewClothQuantity;
            TextView textViewServiceType;
            TextView textViewClothCost;
            Button delete_item;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.textViewClothQuantity = (TextView) itemView.findViewById(R.id.cloth_quntity_type_recycler_view_item);
                this.textViewServiceType = (TextView) itemView.findViewById(R.id.service_type_cart);
                this.textViewClothCost = (TextView) itemView.findViewById(R.id.cost_per_item_textView);
                this.delete_item = (Button) itemView.findViewById(R.id.delete_item_cart);

            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart_item, parent, false);

            ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            holder.textViewClothQuantity.setText(dataList.get(position).getClothQuantity());
            String service_type;

            switch(dataList.get(position).getServiceType()){
                case "wash_fold":
                    service_type = "(Wash & Fold)";
                    break;
                case "wash_iron":
                    service_type = "(Wash & Iron)";
                    break;
                case "iron":
                    service_type= "(Iron)";
                    break;
                case "dry_clean":
                    service_type="(DryClean)";
                    break;
                default:
                    service_type="(No Service)";
            }
            holder.textViewServiceType.setText(service_type);
            holder.textViewClothCost.setText(Integer.toString(dataList.get(position).getCost())+" $");

            holder.delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
                    SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getWritableDatabase();
                    clothSelectorDbHelper.updateCountToZero(dataList.get(position).getId(),sqLiteDatabase);
                    clothSelectorDbHelper.close();

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment()).commit();

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }



    }
}



