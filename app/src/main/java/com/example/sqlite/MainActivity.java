package com.example.sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> noteList;
    final int REQUEST_CODE = 1;
    private final String DB_NAME = "notesSQLite";
    private final int version = 5;

    private SQLiteDatabase database;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new MyDatabaseHelper(MainActivity.this, DB_NAME, null, version);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        noteList = new ArrayList<>();
        load_records();
        Intent intentAppendActivity = new Intent(this, AppendActivity.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        NoteAdapter noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intentAppendActivity, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            database = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            long rowId = database.insert("notes", null, contentValues);
            Log.i("Hello", "Insert Text: " + name);
            database.close();
            load_records();
        }
    }

    private void load_records() {
        noteList.clear();
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query("notes", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            do {
                String name = cursor.getString(nameIndex);
                noteList.add(name);
                Log.i("Hello", "Text: " + name);
            } while (cursor.moveToNext());
        } else {
            Log.i("Hello", "Cursor is empty");
        }
        cursor.close();
        database.close();
    }

}