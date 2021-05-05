package ru.razuvaev.android_one.repository;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class NoteRepository {


    public static ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

        for (int i = 0; i < 20; i++) {
            notes.add(new Note(dateFormat.format(new Date()), i + "-я ЗАМЕТКА. C помощью метода finish() можно завершить работу активности. " +
                    "Если приложение состоит из одной активности, то этого делать не следует, так как система сама завершит работу приложения. " +
                    "Если же приложение содержит несколько активностей, между которыми нужно переключаться, то данный метод позволяет экономить ресурсы. "));
        }
        return notes;
    }
}
