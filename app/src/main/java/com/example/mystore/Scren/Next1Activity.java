package com.example.mystore.Scren;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mystore.HomeActivity;
import com.example.mystore.LoginActivity;
import com.example.mystore.R;

public class Next1Activity extends AppCompatActivity {
    Button button1;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next1);
        button1 = (Button) findViewById(R.id.button);
        skip = findViewById(R.id.skip);
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent next2 = new Intent(Next1Activity.this, Next2Activity.class);
           startActivity(next2);
        }
    });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }
}