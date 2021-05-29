package com.demo.listoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final List<Note> notes = new ArrayList<>();
    private LiveData<List<Note>> notesLiveData;
    private RecyclerView recyclerViewNotes;
    private NotesAdapter notesAdapter;
    private Intent intent;
    private MainViewModel viewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        getNotes();


        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notesAdapter = new NotesAdapter((ArrayList<Note>) notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(notesAdapter);
        notesAdapter.setOnNoteListener(new NotesAdapter.OnNoteListener() {
            @Override
            public void onNoteLongClick(int position) {
                // int id = notesArrayList.get(position).getId();
                // intent.putExtra("id", id);
                // startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeNote(viewHolder.getAdapterPosition());

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void removeNote(int position) {
        Note note = notes.get(position);
        viewModel.deleteNote(note);

    }

    public void onClickAddNote(View view) {
        intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getNotes() {
        LiveData<List<Note>> notesFromDB = viewModel.getAllNotes();
        notesFromDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                notes.clear();
                notes.addAll(notesFromLiveData);
                notesAdapter.notifyDataSetChanged();
            }
        });
    }

}