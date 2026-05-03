package com.example.ass4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { // <-- This line is now fixed!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the initial fragment (MainFragment) when the activity is first created
        if (savedInstanceState == null) {
            loadFragment(new MainFragment());
        }
    }

    /**
     * Helper method to load a fragment into the main container
     * @param fragment The fragment instance to load
     */
    private void loadFragment(Fragment fragment) {
        // 1. Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 2. Begin a FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 3. Replace the contents of the container with the new fragment
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);

        // 4. Add to back stack for handling system back press
        fragmentTransaction.addToBackStack(null);

        // 5. Commit the transaction
        fragmentTransaction.commit();
    }
}