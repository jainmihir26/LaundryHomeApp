package com.example.stet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.stet.Models.HomeModel;
import com.example.stet.R;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {


    private Context mContext ;
    private ArrayList<HomeModel>mArrayList ;
    private LayoutInflater mLayoutInflater;

    public HomeAdapter(Context mContext, ArrayList<HomeModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;

        mLayoutInflater=LayoutInflater.from(mContext) ;

    }



    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 ;
        if(view==null){
            view1= mLayoutInflater.inflate(R.layout.row_item,viewGroup,false);
//            view1.setElevation(0);

            ImageView mImageView=view1.findViewById(R.id.fitInGrid_imageId);
            mImageView.setImageResource(mArrayList.get(i).getImage());

            TextView mtextView = view1.findViewById(R.id.cloth_selected_grid);
            mtextView.setText("Clothes Selected:"+Integer.toString(mArrayList.get(i).getCount_cloth()));
        }else{
            view1=view ;
        }

        return view1;
    }
}