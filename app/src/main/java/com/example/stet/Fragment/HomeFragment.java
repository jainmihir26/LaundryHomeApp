package com.example.stet.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.stet.Adapters.HomeAdapter;
import com.example.stet.Models.HomeModel;
import com.example.stet.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {



    private GridView mGridView;
    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeModel> mArrayList;

    private TextView mUserName;

    private String userName1 ="AMAN" ;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_home,container,false);

        final Context thisContext = container.getContext() ;

        mGridView=view.findViewById(R.id.home_gridViewId);
        mUserName =view.findViewById(R.id.homeFragment_helloTextId);



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

//                switch(mArrayList.get(position).getImage()){
//                    case R.drawable.wash_and_fold :
//                        Intent intent=new Intent(thisContext, ClothsSelectActivity.class);
//                        intent.putExtra(PutExtraHelper.service_type, DatabaseHelper.washAndFoldId);
//                        startActivity(intent);
//                        break;
//                    case R.drawable.iron :
//                        intent = new Intent(thisContext, ClothsSelectActivity.class);
//                        intent.putExtra(PutExtraHelper.service_type, DatabaseHelper.ironId);
//                        startActivity(intent);
//                        break;
//                    case R.drawable.wash_and_iron :
//                        intent = new Intent(thisContext, ClothsSelectActivity.class);
//                        intent.putExtra(PutExtraHelper.service_type, DatabaseHelper.washAndIronId);
//                        startActivity(intent);
//                        break;
//
//                    case R.drawable.dry_clean:
//                        intent = new Intent(thisContext, ClothsSelectActivity.class);
//                        intent.putExtra(PutExtraHelper.service_type, DatabaseHelper.dryCleanId);
//                        startActivity(intent);
//                        break ;
//                }


            }
        });
        return view;
    }
}




