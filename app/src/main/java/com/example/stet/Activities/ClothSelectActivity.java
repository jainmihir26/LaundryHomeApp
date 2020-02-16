package com.example.stet.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stet.Adapters.SwipeAdapter;
import com.example.stet.Fragment.BottomFragment;
import com.example.stet.Fragment.DressFragment;
import com.example.stet.Fragment.HouseholdFragment;
import com.example.stet.Fragment.TopFragment;
import com.example.stet.Models.ClothSelectorContract;
import com.example.stet.Models.ClothSelectorDbHelper;
import com.example.stet.R;
import com.google.android.material.tabs.TabLayout;

public class ClothSelectActivity extends AppCompatActivity  implements TopFragment.TotalChangeTop, BottomFragment.TotalChangeBottom, HouseholdFragment.TotalChangeHousehold, DressFragment.TotalChangeDress {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SwipeAdapter swipeAdapter;
    TextView quantity_wash_fold,amount_wash_fold;
    private Button mNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_select);

        tabLayout = findViewById(R.id.tablayout_cloth_select);

        viewPager = findViewById(R.id.cloth_pager_container);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        quantity_wash_fold = findViewById(R.id.quantity_wash_fold);
        amount_wash_fold = findViewById(R.id.amount_wash_fold);
        mNext=findViewById(R.id.next_ClothSelectId);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=amount_wash_fold.getText().toString();
                String amt=amount.substring(6);
//                Toast.makeText(ClothSelectActivity.this, "amount is "+amt, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ClothSelectActivity.this,UpiPaymentActivity.class);
                intent.putExtra("amount",amt);
                startActivity(intent);
            }
        });



        ClothSelectorDbHelper clothSelectorDbHelper = new ClothSelectorDbHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = clothSelectorDbHelper.getReadableDatabase();
        Cursor cursor = clothSelectorDbHelper.readContacts(sqLiteDatabase);
        int total_cost = 0;
        int total_count = 0;
        while (cursor.moveToNext())
        {
            int cloth_price = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_PRICE));
            int cloth_count = cursor.getInt(cursor.getColumnIndexOrThrow(ClothSelectorContract.ClothEntry.CLOTH_COUNT));
            total_cost += cloth_price*cloth_count;
            total_count += cloth_count;
        }

        amount_wash_fold.setText("Total:"+total_cost);
        quantity_wash_fold.setText(total_count+" Items");







        swipeAdapter.AddFragment(new TopFragment(),"Top");
        swipeAdapter.AddFragment(new BottomFragment(),"Bottom");
        swipeAdapter.AddFragment(new HouseholdFragment(),"Household");
        swipeAdapter.AddFragment(new DressFragment(),"Dress");




        viewPager.setAdapter(swipeAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void total_count_change_bottom(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");

    }

    @Override
    public void total_count_change_dress(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
    }

    @Override
    public void total_count_change_household(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
    }

    @Override
    public void total_count_change_top(int total, int count) {
        amount_wash_fold.setText("Total:"+total);
        quantity_wash_fold.setText(count+" Items");
    }


}
