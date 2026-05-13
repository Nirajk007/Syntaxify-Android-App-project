package com.example.ratingprogressdemo; 
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare Variables for our UI elements
    private RatingBar ratingBar;
    private TextView textRatingResult;
    
    private ProgressBar progressBar;
    private TextView textProgressResult;
    private Button btnIncreaseProgress;
    
    // Variable to track the current progress
    private int currentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        ratingBar = findViewById(R.id.ratingBar);
        textRatingResult = findViewById(R.id.textRatingResult);
        
        progressBar = findViewById(R.id.progressBar);
        textProgressResult = findViewById(R.id.textProgressResult);
        btnIncreaseProgress = findViewById(R.id.btnIncreaseProgress);

        // ==============================================================
        // 2. RatingBar Event Handler
        // ==============================================================
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Update the TextView whenever the user taps or drags the stars
                textRatingResult.setText("Your Rating: " + rating + " Stars");
            }
        });

        // ==============================================================
        // 3. ProgressBar Event Handler (triggered via Button)
        // ==============================================================
        btnIncreaseProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if progress is less than the max (100)
                if (currentProgress < 100) {
                    currentProgress += 10; // Increase by 10%
                    
                    // Update the visual bar
                    progressBar.setProgress(currentProgress);
                    
                    // Update the text below the bar
                    textProgressResult.setText(currentProgress + "%");
                    
                    // If we just hit 100%, show a message
                    if (currentProgress == 100) {
                        Toast.makeText(MainActivity.this, "Download Complete!", Toast.LENGTH_SHORT).show();
                        btnIncreaseProgress.setText("Done!");
                        btnIncreaseProgress.setEnabled(false); // Disable button when finished
                    }
                }
            }
        });
    }
}
