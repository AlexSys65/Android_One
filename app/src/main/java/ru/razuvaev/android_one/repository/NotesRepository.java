package ru.razuvaev.android_one.repository;

import java.util.ArrayList;
import java.util.List;

public interface NotesRepository {

    void getNotes(CallBack<ArrayList<Note>> callBack);

    void addNotes(String date, String s, CallBack<Note> callBack);

    void remove(Note item, CallBack<Note> callBack);
}
