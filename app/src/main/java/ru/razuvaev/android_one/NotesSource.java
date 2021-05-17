package ru.razuvaev.android_one;

import ru.razuvaev.android_one.repository.Note;

public interface NotesSource {
    NotesSource init(NotesSourceResponse notesSourceResponse);
    Note getNote(int position);
    int size();
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNote();
}
