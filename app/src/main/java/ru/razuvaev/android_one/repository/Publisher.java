package ru.razuvaev.android_one.repository;

import java.util.ArrayList;
import java.util.List;

import ru.razuvaev.android_one.Observer;

public class Publisher {
    private final List<Observer> observerList = new ArrayList<>();

    public void addObserver(Observer observer) {observerList.add(observer);}

    public void  removeObserver(Observer observer){ observerList.remove(observer);}

    public void notify(Note note) {
        for (Observer observer : observerList) {
            observer.updateNote(note);
        }
    }
}
