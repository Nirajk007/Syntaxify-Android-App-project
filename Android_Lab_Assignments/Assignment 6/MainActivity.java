package com.example.uicontrolsdemo; 

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private EditText editName;
    private CheckBox checkStudent;
    private RadioGroup radioGroupPlatform;
    private ToggleButton toggleNotifications;
    private Button btnSubmit;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize views by linking them to the XML IDs
        editName = findViewById(R.id.editName);
        checkStudent = findViewById(R.id.checkStudent);
        radioGroupPlatform = findViewById(R.id.radioGroupPlatform);
        toggleNotifications = findViewById(R.id.toggleNotifications);
        btnSubmit = findViewById(R.id.btnSubmit);
        textResult = findViewById(R.id.textResult);

        // --- EVENT HANDLER DEMONSTRATIONS ---

        // Event Handler for CheckBox (Real-time change detection)
        checkStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(MainActivity.this, "Student mode activated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Event Handler for RadioGroup (Real-time change detection)
        radioGroupPlatform.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadio = findViewById(checkedId);
                String platform = selectedRadio.getText().toString();
                Toast.makeText(MainActivity.this, "Selected: " + platform, Toast.LENGTH_SHORT).show();
            }
        });

        // Event Handler for ToggleButton (Real-time change detection)
        toggleNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status = isChecked ? "Enabled" : "Disabled";
                Toast.makeText(MainActivity.this, "Notifications " + status, Toast.LENGTH_SHORT).show();
            }
        });

        // Event Handler for the main Submit Button (Gathering all data on click)
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gather data from Text Field
                String name = editName.getText().toString();
                if (name.isEmpty()) name = "No name entered";

                // Gather data from CheckBox
                String studentStatus = checkStudent.isChecked() ? "Yes" : "No";

                // Gather data from RadioGroup
                String platform = "None Selected";
                int selectedRadioId = radioGroupPlatform.getCheckedRadioButtonId();
                if (selectedRadioId != -1) { // -1 means nothing is checked
                    RadioButton selectedRadio = findViewById(selectedRadioId);
                    platform = selectedRadio.getText().toString();
                }

                // Gather data from ToggleButton
                String notifs = toggleNotifications.isChecked() ? "ON" : "OFF";

                // Build the final summary string
                String summary = "--- User Profile ---\n\n" +
                        "Name: " + name + "\n" +
                        "Is Student: " + studentStatus + "\n" +
                        "Platform: " + platform + "\n" +
                        "Notifications: " + notifs;

                // Display it in the TextView
                textResult.setText(summary);
            }
        });
    }
}
