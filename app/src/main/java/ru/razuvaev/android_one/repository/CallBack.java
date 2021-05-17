package ru.razuvaev.android_one.repository;

public interface CallBack<T> {

    void onSuccess(T value);

    void onError(Throwable error);

}
