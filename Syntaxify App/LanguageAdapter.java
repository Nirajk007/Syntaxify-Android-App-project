package com.example.syntaxify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private List<String> languages;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(String language);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(String language);
    }

    public LanguageAdapter(List<String> languages, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.languages = languages;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void updateLanguages(List<String> newLanguages) {
        this.languages.clear();
        this.languages.addAll(newLanguages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        String lang = languages.get(position);
        holder.tvLanguage.setText(lang);

        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(lang));

        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(lang);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView tvLanguage;
        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLanguage = itemView.findViewById(R.id.tvLanguageName);
        }
    }
}