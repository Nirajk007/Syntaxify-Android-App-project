package com.example.syntaxify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.syntaxify.databinding.ActivityLanguageBinding;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    private ActivityLanguageBinding binding;
    private DatabaseHelper databaseHelper;
    private NoteAdapter adapter;
    private String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentLanguage = getIntent().getStringExtra("EXTRA_LANGUAGE");
        if (currentLanguage != null) {
            setTitle(currentLanguage + " Notes");
        }

        databaseHelper = new DatabaseHelper(this);

        binding.recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));

        binding.fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(LanguageActivity.this, AddEditNoteActivity.class);
            intent.putExtra("EXTRA_LANGUAGE", currentLanguage);
            startActivity(intent);
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adapter != null) adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        setupSwipeToDelete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        List<Note> notes = databaseHelper.getNotesByLanguage(currentLanguage);
        if (adapter == null) {
            adapter = new NoteAdapter(this, notes, note -> {
                Intent intent = new Intent(LanguageActivity.this, AddEditNoteActivity.class);
                intent.putExtra("EXTRA_NOTE_ID", note.getId());
                intent.putExtra("EXTRA_LANGUAGE", currentLanguage);
                startActivity(intent);
            });
            binding.recyclerViewNotes.setAdapter(adapter);
        } else {
            adapter.updateNotes(notes);
        }
        binding.etSearch.setText("");
    }

    private void setupSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note noteToDelete = adapter.getNoteAt(position);
                databaseHelper.deleteNote(noteToDelete.getId());
                adapter.removeNoteAt(position);
                Toast.makeText(LanguageActivity.this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerViewNotes);
    }
}