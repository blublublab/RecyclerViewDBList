package com.demo.listoftheday;

import android.app.Application;
import android.content.Context;
import android.telecom.Call;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class MainViewModel extends AndroidViewModel {
    private final AppSingletonDatabase appSingletonDatabase;
    private final LiveData<List<Note>> notes;
    public MainViewModel(@NonNull Application application) {
        super(application);
        appSingletonDatabase = AppSingletonDatabase.getInstance(getApplication());
        notes = appSingletonDatabase.notesDao().getAllDataLive();

    }




    public LiveData<List<Note>> getAllNotes() {
        return notes;
    }


    public void insertNote(Note note){
     callRx(note, new  Exception().getStackTrace()[0].getMethodName());

    }
    public void deleteNote(Note note){
     callRx(note,  new  Exception().getStackTrace()[0].getMethodName());

    }

    public void deleteAllNotes() {
       callRx(null, new  Exception().getStackTrace()[0].getMethodName());
    }



    private void callRx(Note note, String action){
        Observable.fromCallable(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
               switch(action) {
                    case "deleteNote":
                        appSingletonDatabase.notesDao().deleteNote(note);
                        break;
                   case "insertNote":
                        appSingletonDatabase.notesDao().insertNote(note);
                        break;
                   case "deleteAllNotes":
                       appSingletonDatabase.notesDao().deleteAllNotes();
                       break;
                }
                return true;
            }
        }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe();

    }
    }


