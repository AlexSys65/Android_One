package ru.razuvaev.android_one.repository;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class NoteRepository {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        for (int i = 0; i < 20; i++) {
            notes.add(new Note(LocalDateTime.now().format(formatter), i + "-я ЗАМЕТКА. C помощью метода finish() можно завершить работу активности. " +
                    "Если приложение состоит из одной активности, то этого делать не следует, так как система сама завершит работу приложения. " +
                    "Если же приложение содержит несколько активностей, между которыми нужно переключаться, то данный метод позволяет экономить ресурсы. "));
        }
        return notes;
    }
}
