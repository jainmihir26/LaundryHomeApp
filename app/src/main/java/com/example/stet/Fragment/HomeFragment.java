package com.example.stet.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.stet.Activities.ClothSelectActivity;
import com.example.stet.Adapters.HomeAdapter;
import com.example.stet.Helper.ServiceTypes;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Models.HomeModel;
import com.example.stet.R;


import java.util.ArrayList;

public class HomeFragment extends Fragment {



    private GridView mGridView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList;
    private TextView mUserName;
    private TextView display_username;
    private String url_landing_page = "http://192.168.0.103:8000/apis/get_price_list/";

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_home,container,false);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
        display_username = view.findViewById(R.id.homeFragment_helloTextId);
        display_username.setText("Hello "+sharedPreferencesConfig.read_full_name());

        final Context thisContext = container.getContext() ;
        mGridView=view.findViewById(R.id.home_gridViewId);



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

                        ServiceTypes.service_type = "wash_fold";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;
                    case R.drawable.iron :

                       ServiceTypes.service_type = "iron";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                    case R.drawable.wash_and_iron :

                        ServiceTypes.service_type = "wash_iron";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                    case R.drawable.dry_clean:

                        ServiceTypes.service_type = "dry_clean";
                        startActivity(new Intent(getActivity(),ClothSelectActivity.class));
                        break;

                }


            }
        });
        return view;
    }
}




