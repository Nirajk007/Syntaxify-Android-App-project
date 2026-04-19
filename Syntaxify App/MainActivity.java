package com.example.syntaxify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.syntaxify.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;
    private LanguageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Syntaxify - Languages");

        databaseHelper = new DatabaseHelper(this);

        // Initialize adapter with a fresh empty list
        adapter = new LanguageAdapter(new ArrayList<>(),
                language -> {
                    Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                    intent.putExtra("EXTRA_LANGUAGE", language);
                    startActivity(intent);
                },
                this::showEditDeleteOptionsDialog
        );

        binding.recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewLanguages.setAdapter(adapter);

        binding.fabAddLanguage.setOnClickListener(v -> showAddLanguageDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLanguages();
    }

    private void loadLanguages() {
        // Fetch fresh data from the database and pass it safely to the adapter
        List<String> freshLanguages = databaseHelper.getAllLanguages();
        adapter.updateLanguages(freshLanguages);
    }

    private void showAddLanguageDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_language_input, null);
        EditText etLanguageName = dialogView.findViewById(R.id.etLanguageName);

        new AlertDialog.Builder(this)
                .setTitle("Add New Language")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newLanguage = etLanguageName.getText().toString().trim();
                    if (newLanguage.isEmpty()) {
                        Toast.makeText(this, "Language name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (databaseHelper.addLanguage(newLanguage)) {
                        loadLanguages();
                        Toast.makeText(this, "Language added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Language already exists", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditDeleteOptionsDialog(String currentLanguage) {
        String[] options = {"Edit Language", "Delete Language"};

        new AlertDialog.Builder(this)
                .setTitle(currentLanguage + " Options")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showEditLanguageDialog(currentLanguage);
                    } else if (which == 1) {
                        showDeleteConfirmationDialog(currentLanguage);
                    }
                })
                .show();
    }

    private void showEditLanguageDialog(String currentLanguage) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_language_input, null);
        EditText etLanguageName = dialogView.findViewById(R.id.etLanguageName);
        etLanguageName.setText(currentLanguage);

        new AlertDialog.Builder(this)
                .setTitle("Edit Language")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newLanguage = etLanguageName.getText().toString().trim();
                    if (newLanguage.isEmpty()) {
                        Toast.makeText(this, "Language name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newLanguage.equalsIgnoreCase(currentLanguage)) {
                        return;
                    }
                    if (databaseHelper.updateLanguage(currentLanguage, newLanguage)) {
                        loadLanguages();
                        Toast.makeText(this, "Language updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Language name already exists", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmationDialog(String language) {
        new AlertDialog.Builder(this)
                .setTitle("Delete " + language + "?")
                .setMessage("Are you sure? This will permanently delete the language and ALL notes associated with it.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    databaseHelper.deleteLanguage(language);
                    loadLanguages();
                    Toast.makeText(this, language + " deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}