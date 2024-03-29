package com.example.stet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ClothSelectActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwipeAdapter swipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_select);

        tabLayout = findViewById(R.id.tablayout_cloth_select);

        viewPager = findViewById(R.id.cloth_pager_container);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager());


        swipeAdapter.AddFragment(new TopFragment(),"Top");
        swipeAdapter.AddFragment(new BottomFragment(),"Bottom");
        swipeAdapter.AddFragment(new HouseholdFragment(),"Household");
        swipeAdapter.AddFragment(new DressFragment(),"Dress");



        viewPager.setAdapter(swipeAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
