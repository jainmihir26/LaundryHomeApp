package com.example.stet.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.stet.Adapters.MpagerAdapter;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.R;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager view_pager_slider;
    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private MpagerAdapter mpagerAdapter;
    private int [] layouts = {R.layout.slider1, R.layout.slider2,R.layout.slider3};

    private Button next,skip;

    private SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesConfig = new SharedPreferencesConfig(this);

        sharedPreferencesConfig.clear_preferences();

        if(!sharedPreferencesConfig.read_FirstTimeLaunch()){
            sharedPreferencesConfig.write_FirstTimeLaunch(false);
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            finish();
        }



        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);

        next = findViewById(R.id.next_btn);
        skip = findViewById(R.id.skip_btn);

        view_pager_slider = findViewById(R.id.view_pager_slider);
        mpagerAdapter = new MpagerAdapter(layouts,this);

        view_pager_slider.setAdapter(mpagerAdapter);

        Dots_Layout = (LinearLayout)findViewById(R.id.dotsLayout);
        createDots(0);


        view_pager_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNextSlide();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sliderPrefrences.writtingSharedPreferences();
                sharedPreferencesConfig.write_FirstTimeLaunch(false);
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                finish();

            }
        });
    }

    private void createDots(int current_position){

        if(Dots_Layout != null){
            Dots_Layout.removeAllViews();
        }

        dots = new ImageView[layouts.length];

        for(int i=0;i<layouts.length;i++){
            dots[i] = new ImageView(this);

            if(i == current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i],params);
        }
    }

    private void loadHome(){
        sharedPreferencesConfig.write_FirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        finish();
    }

    private void loadNextSlide(){
        int nextSlide = view_pager_slider.getCurrentItem()+1;
        if(nextSlide < layouts.length){
            view_pager_slider.setCurrentItem(nextSlide);
        }else{
            loadHome();
        }
    }

}
