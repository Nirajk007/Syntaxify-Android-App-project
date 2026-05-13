package com.example.intentdemo; // Replace with your actual package name

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExplicit = findViewById(R.id.btnExplicitIntent);
        Button btnImplicit = findViewById(R.id.btnImplicitIntent);

        // --- EXPLICIT INTENT ---
        // Targets a specific component (SecondActivity) within your app
        btnExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitIntent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(explicitIntent);
            }
        });

        // --- IMPLICIT INTENT ---
        // Asks the Android system (API 27+) to find an app that can handle this URI
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.android.com";
                Intent implicitIntent = new Intent(Intent.ACTION_VIEW);
                implicitIntent.setData(Uri.parse(url));
                startActivity(implicitIntent);
            }
        });
    }
}
