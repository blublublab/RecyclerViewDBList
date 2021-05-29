package com.demo.listoftheday;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {
    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Insert
    void insertNote(Note note);

        @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllDataLive();

        @Query("DELETE FROM notes")
        void deleteAllNotes();
}
