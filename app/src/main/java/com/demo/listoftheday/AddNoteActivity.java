package com.demo.listoftheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.demo.listoftheday.R.string.material_hour_selection;
import static com.demo.listoftheday.R.string.radio_button_1;
import static com.demo.listoftheday.R.string.toast_alert_not_all_strings_set;

public class AddNoteActivity extends AppCompatActivity {
    private RadioGroup radioGroupPriority;
    private EditText editTextNoteTitle;
    private EditText editTextNoteDescription;
    private Spinner spinnerDayOfWeek;
    private String noteTitle;
    private String noteDescription;
    private int priority;
    private int idOfChangingNote;
    private String dayOfTheWeek;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().hide();
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDescription = findViewById(R.id.editTextNoteDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        Intent intent = getIntent();
        idOfChangingNote = intent.getIntExtra("id", 0);
        if (idOfChangingNote != 0) {
            String selection = "_id  = ? ";
            String[] selectionArgs = new String[]{String.valueOf(idOfChangingNote)};
            Cursor cursor = database.query(NotesContract.NoteEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                noteTitle = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_TITLE));
                noteDescription = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_DESCRIPTION));
                dayOfTheWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_DAY_OF_WEEK));
                priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_PRIORITY));
                editTextNoteTitle.setText(noteTitle);
                editTextNoteDescription.setText(noteDescription);
                //
            }
            cursor.close();
            radioGroupPriority.clearCheck();

            switch (priority) {
                case 1:
                    radioButton1.toggle();
                    break;
                case 2:
                    radioButton2.toggle();
                case 3:
                    radioButton3.toggle();

            }
            ;
        }

            radioGroupPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = radioGroupPriority.findViewById(checkedId);
                    int index = radioGroupPriority.indexOfChild(radioButton);
                    Log.i("displayLog", " index:  " + (index) + " checkedID:  " + (checkedId));
                    switch (index) {
                        case 0:
                            priority = 1;
                            break;
                        case 1:
                            priority = 2;
                            break;
                        case 2:
                            priority = 3;
                            break;

                    }
                }
            });


        }

    public void onClickAddNote (View view){
        noteTitle = editTextNoteTitle.getText().toString();
        noteDescription = editTextNoteDescription.getText().toString();
        dayOfTheWeek = spinnerDayOfWeek.getSelectedItem().toString();
        if (isFilled(noteTitle, noteDescription)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NoteEntry.COLUMN_TITLE, noteTitle);
            contentValues.put(NotesContract.NoteEntry.COLUMN_DESCRIPTION, noteDescription);
            contentValues.put(NotesContract.NoteEntry.COLUMN_DAY_OF_WEEK, dayOfTheWeek);
            contentValues.put(NotesContract.NoteEntry.COLUMN_PRIORITY, priority);
            database.insert(NotesContract.NoteEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Log.i("LOG", "Поля не заполнены");
            Toast.makeText(this, toast_alert_not_all_strings_set, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !noteDescription.isEmpty();
    }

}