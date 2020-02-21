package com.example.stet.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stet.Models.ProgressBarAnimation;
import com.example.stet.R;

public class SampleActivity extends AppCompatActivity {
    ProgressBar progress;
    ProgressBarAnimation anim;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        anim= new ProgressBarAnimation(progress, 100, 10000);
        progress=findViewById(R.id.progress_bar);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
