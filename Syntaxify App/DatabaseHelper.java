package com.example.syntaxify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "syntaxify_db";
    private static final int DATABASE_VERSION = 2; // Incremented for database migration

    // Notes Table
    private static final String TABLE_NOTES = "syntax_notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LANGUAGE = "language";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // Languages Table
    private static final String TABLE_LANGUAGES = "languages";
    private static final String COLUMN_LANG_ID = "id";
    private static final String COLUMN_LANG_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesTable = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LANGUAGE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT)";
        db.execSQL(createNotesTable);

        String createLanguagesTable = "CREATE TABLE " + TABLE_LANGUAGES + " (" +
                COLUMN_LANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LANG_NAME + " TEXT UNIQUE)";
        db.execSQL(createLanguagesTable);

        insertDefaultLanguages(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String createLanguagesTable = "CREATE TABLE " + TABLE_LANGUAGES + " (" +
                    COLUMN_LANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LANG_NAME + " TEXT UNIQUE)";
            db.execSQL(createLanguagesTable);
            insertDefaultLanguages(db);
        }
    }

    private void insertDefaultLanguages(SQLiteDatabase db) {
        String[] defaults = {"C", "C++", "Java", "Python", "JavaScript", "HTML", "CSS"};
        for (String lang : defaults) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LANG_NAME, lang);
            db.insert(TABLE_LANGUAGES, null, values);
        }
    }

    // --- Language Methods ---

    public List<String> getAllLanguages() {
        List<String> languages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LANGUAGES, null, null, null, null, null, COLUMN_LANG_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                languages.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANG_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return languages;
    }

    public boolean addLanguage(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LANGUAGES + " WHERE LOWER(" + COLUMN_LANG_NAME + ") = LOWER(?)", new String[]{name});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LANG_NAME, name);
        long result = db.insert(TABLE_LANGUAGES, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateLanguage(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LANGUAGES + " WHERE LOWER(" + COLUMN_LANG_NAME + ") = LOWER(?)", new String[]{newName});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues langValues = new ContentValues();
        langValues.put(COLUMN_LANG_NAME, newName);
        db.update(TABLE_LANGUAGES, langValues, COLUMN_LANG_NAME + " = ?", new String[]{oldName});

        ContentValues noteValues = new ContentValues();
        noteValues.put(COLUMN_LANGUAGE, newName);
        db.update(TABLE_NOTES, noteValues, COLUMN_LANGUAGE + " = ?", new String[]{oldName});

        db.close();
        return true;
    }

    public void deleteLanguage(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LANGUAGES, COLUMN_LANG_NAME + " = ?", new String[]{name});
        db.delete(TABLE_NOTES, COLUMN_LANGUAGE + " = ?", new String[]{name});
        db.close();
    }

    // --- Note Methods ---

    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE, note.getLanguage());
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_CONTENT, note.getContent());
        values.put(COLUMN_TIMESTAMP, getCurrentTimestamp());

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE, note.getLanguage());
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_CONTENT, note.getContent());
        values.put(COLUMN_TIMESTAMP, getCurrentTimestamp());

        int rows = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
        return rows;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> getNotesByLanguage(String language) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_LANGUAGE + " = ?", new String[]{language}, null, null, COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                );
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Note note = new Note(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
            );
            cursor.close();
            return note;
        }
        return null;
    }

    private String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
    }
}