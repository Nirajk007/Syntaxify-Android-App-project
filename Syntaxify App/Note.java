package com.example.syntaxify;

public class Note {
    private int id;
    private String language;
    private String title;
    private String content;
    private String timestamp;

    public Note(int id, String language, String title, String content, String timestamp) {
        this.id = id;
        this.language = language;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Note() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}