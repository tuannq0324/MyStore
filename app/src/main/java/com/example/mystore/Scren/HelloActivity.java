package com.example.mystore.Scren;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mystore.R;

public class HelloActivity extends AppCompatActivity {
    ImageView helloSc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        helloSc = (ImageView) findViewById(R.id.hello);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Next1Activity.class));
                finish();
            }
        }, 3000);

    }
}