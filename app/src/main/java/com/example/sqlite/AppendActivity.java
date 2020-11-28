package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AppendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append);

        TextInputLayout idLayout = findViewById(R.id.idLayout);
        TextInputEditText value = findViewById(R.id.idEditText);
        Button save = findViewById(R.id.saveButton);
        Button cancel = findViewById(R.id.cancelButton);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (value.getText().toString().isEmpty()) {
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name", value.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

}