package com.example.stet.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.stet.R;

public class TermsAndPolicyActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_policy);
        init();

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://codinghub.org.in/about.html");

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    private void init() {
        webView=findViewById(R.id.web_view);
    }
}