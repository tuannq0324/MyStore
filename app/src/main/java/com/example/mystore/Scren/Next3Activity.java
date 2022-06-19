package com.example.mystore.Scren;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystore.HomeActivity;
import com.example.mystore.LoginActivity;
import com.example.mystore.R;

public class Next3Activity extends AppCompatActivity {
    Button button1;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next3);
        button1 = (Button) findViewById(R.id.button);
        skip = findViewById(R.id.skip);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
                    }
                }, 0000);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }
}