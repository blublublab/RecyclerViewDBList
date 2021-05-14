package com.demo.listoftheday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  {
    private ArrayList<Note> notes = new ArrayList<>();
    private RecyclerView recyclerViewNotes;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;
    private NotesAdapter notesAdapter;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        intent = new Intent(this, AddNoteActivity.class);
        //Help commands
        Log.i("LOG" , String.format("Create command is :  %s", NotesContract.NoteEntry.CREATE_COMMAND));
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(NotesContract.NoteEntry.TABLE_NAME, null , null, null ,null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NoteEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_DAY_OF_WEEK));
            int priority  = cursor.getInt(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_PRIORITY));
            notes.add(new Note(title, description, dayOfWeek, priority, id));
        }
        cursor.close();


        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notesAdapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(notesAdapter);
        notesAdapter.setOnNoteListener(new NotesAdapter.OnNoteListener() {
            @Override
            public void onNoteLongClick(int position) {
                int id = notes.get(position).getId();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int id = notes.get(viewHolder.getAdapterPosition()).getId();
                String whereDelete = NotesContract.NoteEntry._ID + " = ?";
                String[] whereArgs = new String[]{String.valueOf(id)};
                database.delete(NotesContract.NoteEntry.TABLE_NAME, whereDelete, whereArgs );
                notes.remove(viewHolder.getAdapterPosition());
                notesAdapter.notifyDataSetChanged();

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }
    public void onClickAddNote(View view) {
        startActivity(intent);
    }

}