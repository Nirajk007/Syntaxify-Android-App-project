package com.example.menudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textMenuResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the TextView
        textMenuResult = findViewById(R.id.textMenuResult);
    }

    // ==============================================================
    // 1. Inflate (Create) the Menu
    // ==============================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This links the options_menu.xml file to this activity
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    // ==============================================================
    // 2. Handle Menu Item Clicks (The Event Handler)
    // ==============================================================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        // Get the ID of the item that was clicked
        int id = item.getItemId();

        // Use an if-else chain to determine which item was clicked
        if (id == R.id.menu_settings) {
            textMenuResult.setText("You clicked: Settings");
            Toast.makeText(this, "Opening Settings...", Toast.LENGTH_SHORT).show();
            return true;
            
        } else if (id == R.id.menu_profile) {
            textMenuResult.setText("You clicked: User Profile");
            return true;
            
        } else if (id == R.id.menu_help) {
            textMenuResult.setText("You clicked: Help & Feedback");
            return true;
            
        } else if (id == R.id.menu_logout) {
            textMenuResult.setText("You clicked: Logout");
            Toast.makeText(this, "Logging out successfully.", Toast.LENGTH_SHORT).show();
            return true;
        }

        // If the ID isn't recognized, let the superclass handle it
        return super.onOptionsItemSelected(item);
    }
}
