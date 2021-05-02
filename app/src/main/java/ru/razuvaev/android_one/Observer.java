package ru.razuvaev.android_one;

import ru.razuvaev.android_one.repository.Note;

public interface Observer {
    void updateNote(Note note);
}
