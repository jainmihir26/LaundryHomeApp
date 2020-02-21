package com.example.stet.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.stet.Adapters.SwipeAdapterOrder;
import com.example.stet.R;
import com.google.android.material.tabs.TabLayout;

public class OrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwipeAdapterOrder swipeAdapterOrder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order,container,false);
        tabLayout = v.findViewById(R.id.tablayout_order_fragment);
        viewPager = v.findViewById(R.id.current_past_order_view_pager);
        swipeAdapterOrder = new SwipeAdapterOrder(getChildFragmentManager());
        swipeAdapterOrder.AddFragment(new CurrentOrder(),"Current");
        swipeAdapterOrder.AddFragment(new PastOrder(),"Past");
        viewPager.setAdapter(swipeAdapterOrder);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
}
