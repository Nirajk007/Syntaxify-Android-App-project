package com.example.portfolioapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAbout, btnProjects, btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbout = findViewById(R.id.btnAbout);
        btnProjects = findViewById(R.id.btnProjects);
        btnContact = findViewById(R.id.btnContact);

        // Explicit Intent → About Page
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Explicit Intent → Projects Page
        btnProjects.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
            startActivity(intent);
        });

        // Explicit Intent → Contact Page
        btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        });
    }
}