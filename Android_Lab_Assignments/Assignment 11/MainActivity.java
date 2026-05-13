package com.example.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editMarks, editId;
    Button btnAddData, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Database Helper
        myDb = new DatabaseHelper(this);

        // Map UI components
        editName = findViewById(R.id.editName);
        editMarks = findViewById(R.id.editMarks);
        editId = findViewById(R.id.editId);
        
        btnAddData = findViewById(R.id.btnAddData);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Call our event listener methods
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    // ==========================================
    // 1. ADD DATA EVENT
    // ==========================================
    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(), editMarks.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ==========================================
    // 2. VIEW ALL EVENT
    // ==========================================
    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    // Show message if no data is found
                    showMessage("Error", "Nothing found in the database");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Name: ").append(res.getString(1)).append("\n");
                    buffer.append("Marks: ").append(res.getString(2)).append("\n\n");
                }

                // Show all data in a popup alert dialog
                showMessage("Database Records", buffer.toString());
            }
        });
    }

    // ==========================================
    // 3. UPDATE DATA EVENT
    // ==========================================
    public void UpdateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(
                        editId.getText().toString(),
                        editName.getText().toString(),
                        editMarks.getText().toString());
                        
                if (isUpdate)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ==========================================
    // 4. DELETE DATA EVENT
    // ==========================================
    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to build and show the AlertDialog
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
