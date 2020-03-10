package com.example.stet.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stet.Models.DataModel;
import com.example.stet.R;

import java.util.ArrayList;

public class PopUpAdapter extends RecyclerView.Adapter<PopUpAdapter.HomeAdapterViewHolder> {
    private ArrayList<DataModel> model = new ArrayList<>();
    private Context mContext;
    public PopUpAdapter(Context context,ArrayList<DataModel> model){
        this.model = model;
        mContext=context;
    }
    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);
        HomeAdapterViewHolder ha = new HomeAdapterViewHolder(v);
        return ha;


    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, final int position) {


        final String item = model.get(position).getItem();
        final String service_type = model.get(position).getService_type();
        final int price = model.get(position).getPrice();



        holder.setPrice(price);
        holder.setItem(item);
        holder.setServiceType(service_type);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class HomeAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView Item;
        private TextView ServiceType;
        private TextView price;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);


            Item = itemView.findViewById(R.id.textViewItem);
            ServiceType = itemView.findViewById(R.id.textViewServiceType);
            price = itemView.findViewById(R.id.textViewPrice);

        }

        public void setItem(String item) {
            this.Item.setText(item);
        }
        public void setServiceType(String service_type){
            this.ServiceType.setText(service_type);
        }
        public void setPrice(int price){
            this.price.setText(price+"$");
        }
    }
}
