package com.example.diarybook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inisialisasi elemen UI
        EditText noteInput = findViewById(R.id.noteInput);
        Button saveButton = findViewById(R.id.saveButton);
        ListView noteListView = findViewById(R.id.noteListView);

        // Inisialisasi ListView dan data
        noteList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
        noteListView.setAdapter(adapter);

        // Muat catatan yang sudah disimpan
        loadNotes();

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
            noteInput.setText(""); // Kosongkan input

            // Perbarui tampilan
            loadNotes();
        });

        // Listener untuk menghapus catatan
        noteListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNote = noteList.get(position);

            noteList.remove(selectedNote); // Hapus dari daftar
            SharedPreferences sharedPreferences = getSharedPreferences("DiaryBook", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Set<String> updatedNotes = new HashSet<>(noteList); // Perbarui catatan
            editor.putStringSet("notes", updatedNotes);
            editor.apply();

            adapter.notifyDataSetChanged(); // Perbarui tampilan
            Toast.makeText(MainActivity2.this, "Note deleted!", Toast.LENGTH_SHORT).show();
        });
    }

    // Simpan catatan ke SharedPreferences
    private void saveNoteToDatabase(String note) {
        SharedPreferences sharedPreferences = getSharedPreferences("DiaryBook", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Ambil catatan yang sudah ada
        Set<String> notes = sharedPreferences.getStringSet("notes", new HashSet<>());
        notes.add(note); // Tambahkan catatan baru

        // Simpan kembali
        editor.putStringSet("notes", notes);
        editor.apply();
    }

    // Muat catatan dari SharedPreferences
    private void loadNotes() {
        SharedPreferences sharedPreferences = getSharedPreferences("DiaryBook", MODE_PRIVATE);
        Set<String> notes = sharedPreferences.getStringSet("notes", new HashSet<>());

        noteList.clear(); // Bersihkan data lama
        noteList.addAll(notes); // Tambahkan data baru
        adapter.notifyDataSetChanged(); // Perbarui tampilan
    }
}
