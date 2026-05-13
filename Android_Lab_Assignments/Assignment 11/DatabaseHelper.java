package com.example.sqlitedemo; 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and Table names
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    
    // Column names
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MARKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table when the database is first created
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ==========================================
    // CRUD OPERATIONS
    // ==========================================

    // 1. CREATE (Insert Data)
    public boolean insertData(String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // returns true if inserted successfully
    }

    // 2. READ (Get All Data)
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Returns a Cursor object containing all rows
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    // 3. UPDATE (Update Existing Data)
    public boolean updateData(String id, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        
        // Update the row where the ID matches
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    // 4. DELETE (Remove Data)
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row where the ID matches and return the number of affected rows
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
