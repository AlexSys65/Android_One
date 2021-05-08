package ru.razuvaev.android_one.ui;
/**
 * author: AlexSys65 alexsystems@mail.ru
 * practical work on the course Android beginner level GeekBrains
 * topic: Notes
 * Purpose: Launching an activity, managing fragments, receiving messages from fragments,
 * passing a message to the NoteRepository business logic class and vice versa.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;


public class MainActivity extends AppCompatActivity
        implements FragmentSendDataListener, PublisherHolder {

    protected final Publisher mPublisher = new Publisher();
    protected FragmentManager myFragmentManager;
    protected boolean mIsLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drawer_layout);

        mIsLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (!mIsLandscape) {
            myFragmentManager = getSupportFragmentManager();
            Fragment fragment = myFragmentManager.findFragmentById(R.id.fragment_container);
            if (fragment == null) {
                myFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new NotesFragment())
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO: запомнить заметку и при повороте вытащить по умолчанию
    }

    @Override
    public void onSendData(Note selectedItem) {
        myFragmentManager = getSupportFragmentManager();

        if (mIsLandscape) {
            myFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, DetailFragment.newInstance(selectedItem))
                    .commit();
        } else {
            myFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailFragment.newInstance(selectedItem))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public Publisher getPublisher() {
        return mPublisher;
    }
}