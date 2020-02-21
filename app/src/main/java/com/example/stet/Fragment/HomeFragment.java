package com.example.stet.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.Models.DataClothCart;
import com.example.stet.Models.HomeModel;
import com.example.stet.R;


import java.util.ArrayList;

public class HomeFragment extends Fragment {



    private GridView mGridView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList;
    private TextView mUserName;
    private TextView display_username;
    private int wash_fold_count=0;
    private int iron_count=0;
    private int wash_iron_count=0;
    private int dry_clean_count=0;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        int wash_fold_count=0;
        int iron_count=0;
        int wash_iron_count=0;
        int dry_clean_count=0;

        View view =inflater.inflate(R.layout.fragment_home,container,false);

        SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
        display_username = view.findViewById(R.id.homeFragment_helloTextId);
        display_username.setText("Hello "+sharedPreferencesConfig.read_full_name().substring(0,1).toUpperCase()+sharedPreferencesConfig.read_full_name().substring(1));

        final Context thisContext = container.getContext() ;
        mGridView=view.findViewById(R.id.home_gridViewId);

        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);

        while (cursor.moveToNext())
        {
            String service_type = cursor.getString(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_SERVICE_TYPE));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_ID));

            switch (service_type){
                case "wash_fold":
                    wash_fold_count+=count;
                    break;
                case "iron":
                    iron_count+=count;
                    break;
                case "wash_iron":
                    wash_iron_count+=count;
                    break;
                case "dry_clean":
                    dry_clean_count+=count;
                    break;

            }
        }
        cursor.close();




        mArrayList=new ArrayList<>();
        mArrayList.add(new HomeModel(R.drawable.wash_and_fold,wash_fold_count));
        mArrayList.add(new HomeModel(R.drawable.iron,iron_count));
        mArrayList.add(new HomeModel(R.drawable.wash_and_iron,wash_iron_count));
        mArrayList.add(new HomeModel(R.drawable.dry_clean,dry_clean_count));
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




