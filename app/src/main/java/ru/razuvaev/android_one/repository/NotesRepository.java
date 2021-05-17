package ru.razuvaev.android_one.repository;

import java.util.ArrayList;
import java.util.List;

public interface NotesRepository {

    void getNotes(CallBack<ArrayList<Note>> callBack);

    void addNotes(CallBack<Note> callBack);

    void remove(Note item, CallBack<Object> callBack);
}
