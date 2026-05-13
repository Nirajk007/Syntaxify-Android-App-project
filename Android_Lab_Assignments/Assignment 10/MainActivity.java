package com.example.storagedemo; 

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText editInputData;
    private TextView textLoadedData;
    private Button btnSave, btnLoad;

    // Define a constant filename for our internal storage file
    private static final String FILE_NAME = "lab_data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editInputData = findViewById(R.id.editInputData);
        textLoadedData = findViewById(R.id.textLoadedData);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        // --- SAVE BUTTON LOGIC ---
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToInternalStorage();
            }
        });

        // --- LOAD BUTTON LOGIC ---
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromInternalStorage();
            }
        });
    }

    /**
     * Method to save the text from the EditText into internal storage.
     */
    private void saveToInternalStorage() {
        String textToSave = editInputData.getText().toString();

        if (textToSave.isEmpty()) {
            Toast.makeText(this, "Please enter some text first", Toast.LENGTH_SHORT).show();
            return;
        }

        FileOutputStream fos = null;

        try {
            // Context.MODE_PRIVATE means only THIS app can access this file
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            
            // Write the text as bytes
            fos.write(textToSave.getBytes());
            
            // Clear the input box after saving
            editInputData.getText().clear();
            
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving file!", Toast.LENGTH_SHORT).show();
        } finally {
            // Always close the file output stream in a finally block
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to read the text from internal storage and display it in the TextView.
     */
    private void loadFromInternalStorage() {
        FileInputStream fis = null;

        try {
            // Open the file for reading
            fis = openFileInput(FILE_NAME);
            
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            // Read the file line by line
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            // Display the result
            textLoadedData.setText(sb.toString());
            Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No saved data found.", Toast.LENGTH_SHORT).show();
        } finally {
            // Always close the file input stream
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
