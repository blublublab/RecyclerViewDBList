package com.demo.listoftheday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotesDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;
    public NotesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.NoteEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesContract.NoteEntry.DROP_COMMAND);
        onCreate(db);
    }
}
