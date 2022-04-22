package com.example.triviamania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class google_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        WebView myWebView = (WebView) findViewById(R.id.webview);
        Log.d("what link?", "onCreate: "+uri);
        myWebView.loadUrl(uri);
    }
}