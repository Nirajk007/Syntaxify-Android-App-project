package com.example.lab1ass;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
    }

    public void setBg1(View view) {
        mainLayout.setBackgroundResource(R.drawable.bg1);
    }

    public void setBg2(View view) {
        mainLayout.setBackgroundResource(R.drawable.bg2);
    }

    public void setBg3(View view) {
        mainLayout.setBackgroundResource(R.drawable.bg3);
    }
}