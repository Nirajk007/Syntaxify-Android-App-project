package com.example.portfolioapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    Button btnLinkedin, btnEmail, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btnLinkedin = findViewById(R.id.btnLinkedin);
        btnEmail = findViewById(R.id.btnEmail);
        btnCall = findViewById(R.id.btnCall);

        // Implicit Intent → Open LinkedIn
        btnLinkedin.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com"));
            startActivity(intent);
        });

        // Implicit Intent → Send Email
        btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:example@gmail.com"));
            startActivity(intent);
        });

        // Implicit Intent → Dial Number
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:9876543210"));
            startActivity(intent);
        });
    }
}