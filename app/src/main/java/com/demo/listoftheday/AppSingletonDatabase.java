package com.demo.listoftheday;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public  abstract  class AppSingletonDatabase extends RoomDatabase {
    private static AppSingletonDatabase instance;
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();


    public static AppSingletonDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppSingletonDatabase.class, DB_NAME).build();


            }
            return instance;
        }
    }

    public abstract  NotesDao notesDao();
}
