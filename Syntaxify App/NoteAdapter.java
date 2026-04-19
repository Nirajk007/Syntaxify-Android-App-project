package com.example.syntaxify;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private List<Note> notesFull;
    private OnNoteClickListener listener;
    private Context context;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public NoteAdapter(Context context, List<Note> notes, OnNoteClickListener listener) {
        this.context = context;
        this.notes = notes;
        this.notesFull = new ArrayList<>(notes);
        this.listener = listener;
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        this.notesFull = new ArrayList<>(newNotes);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        notes.clear();
        if (text.isEmpty()) {
            notes.addAll(notesFull);
        } else {
            text = text.toLowerCase();
            for (Note item : notesFull) {
                if (item.getTitle().toLowerCase().contains(text) || item.getContent().toLowerCase().contains(text)) {
                    notes.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public void removeNoteAt(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.tvTimestamp.setText(note.getTimestamp());

        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));

        holder.ivCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Syntax Code", note.getContent());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvTimestamp;
        ImageView ivCopy;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
            tvTimestamp = itemView.findViewById(R.id.tvNoteTimestamp);
            ivCopy = itemView.findViewById(R.id.ivCopy);
        }
    }
}