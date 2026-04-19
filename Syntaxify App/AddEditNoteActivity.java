package com.example.syntaxify;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.syntaxify.databinding.ActivityAddEditNoteBinding;
import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity {

    private ActivityAddEditNoteBinding binding;
    private DatabaseHelper databaseHelper;
    private int noteId = -1;
    private List<String> dynamicLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Dynamically load languages from the database
        dynamicLanguages = databaseHelper.getAllLanguages();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dynamicLanguages);
        binding.spinnerLanguage.setAdapter(spinnerAdapter);

        String passedLanguage = getIntent().getStringExtra("EXTRA_LANGUAGE");
        if (passedLanguage != null) {
            int spinnerPosition = spinnerAdapter.getPosition(passedLanguage);
            if(spinnerPosition != -1) {
                binding.spinnerLanguage.setSelection(spinnerPosition);
            }
        }

        if (getIntent().hasExtra("EXTRA_NOTE_ID")) {
            setTitle("Edit Note");
            noteId = getIntent().getIntExtra("EXTRA_NOTE_ID", -1);
            Note existingNote = databaseHelper.getNoteById(noteId);
            if (existingNote != null) {
                binding.etTitle.setText(existingNote.getTitle());
                binding.etContent.setText(existingNote.getContent());
                int spinnerPosition = spinnerAdapter.getPosition(existingNote.getLanguage());
                if(spinnerPosition != -1) {
                    binding.spinnerLanguage.setSelection(spinnerPosition);
                }
            }
        } else {
            setTitle("Add Note");
        }

        binding.fabSaveNote.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = binding.etTitle.getText().toString().trim();
        String content = binding.etContent.getText().toString().trim();

        if (binding.spinnerLanguage.getSelectedItem() == null) {
            Toast.makeText(this, "Please add a language first", Toast.LENGTH_SHORT).show();
            return;
        }

        String language = binding.spinnerLanguage.getSelectedItem().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please insert a title and syntax rule", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setLanguage(language);

        if (noteId == -1) {
            databaseHelper.insertNote(note);
        } else {
            note.setId(noteId);
            databaseHelper.updateNote(note);
        }

        finish();
    }
}