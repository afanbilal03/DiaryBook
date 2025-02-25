package com.example.diarybook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inisialisasi elemen UI
        EditText noteInput = findViewById(R.id.noteInput);
        Button saveButton = findViewById(R.id.saveButton);

        // Menambahkan listener untuk tombol save
        saveButton.setOnClickListener(v -> {
            String note = noteInput.getText().toString().trim();

            if (note.isEmpty()) {
                Toast.makeText(MainActivity2.this, "Note cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan catatan ke SharedPreferences
            saveNoteToDatabase(note);

            Toast.makeText(MainActivity2.this, "Note saved!", Toast.LENGTH_SHORT).show();
            noteInput.setText("");
        });
    }

    // Simpan catatan ke SharedPreferences
    private void saveNoteToDatabase(String note) {
        SharedPreferences sharedPreferences = getSharedPreferences("DiaryBook", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("note", note);
        editor.apply();
    }
}
