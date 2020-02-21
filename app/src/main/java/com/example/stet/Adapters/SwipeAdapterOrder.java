package com.example.stet.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


public class SwipeAdapterOrder extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();

    public SwipeAdapterOrder(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    public CharSequence getPageTitle(int position){
        return lstTitles.get(position);
    }

    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTitles.add(title);
    }

}
