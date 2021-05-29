package com.demo.listoftheday;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Objects;

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
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Objects.requireNonNull(getSupportActionBar()).hide();
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);

        viewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteDescription = findViewById(R.id.editTextNoteDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        Intent intent = getIntent();
        idOfChangingNote = intent.getIntExtra("id", 0);
        if (idOfChangingNote != 0) {
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
        noteTitle = String.valueOf(editTextNoteTitle.getText());
        noteDescription = String.valueOf(editTextNoteDescription.getText());
        dayOfTheWeek = spinnerDayOfWeek.getSelectedItem().toString();


        if(isFilled(noteTitle, noteDescription)){
            Note note = new Note (noteTitle, noteDescription, dayOfTheWeek, priority);
            viewModel.insertNote(note);
            Intent backToMenuIntent = new Intent(AddNoteActivity.this, MainActivity.class);
            startActivity(backToMenuIntent);

        }


    }

    private boolean isFilled(String noteTitle, String noteDescription) {
        return !noteTitle.isEmpty() && !noteDescription.isEmpty();
    }

}