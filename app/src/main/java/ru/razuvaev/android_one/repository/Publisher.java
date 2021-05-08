package ru.razuvaev.android_one.repository;

import java.util.ArrayList;
import java.util.List;

import ru.razuvaev.android_one.Observer;

public class Publisher {
    private final List<Observer> mObserverList = new ArrayList<>();

    public void addObserver(Observer observer) {
        mObserverList.add(observer);
    }

    public void removeObserver(Observer observer) {
        mObserverList.remove(observer);
    }

    public void notify(Note note) {
        for (Observer observer : mObserverList) {
            observer.updateNote(note);
        }
    }
}
