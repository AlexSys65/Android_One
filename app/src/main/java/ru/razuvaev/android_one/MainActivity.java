package ru.razuvaev.android_one;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;


import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;

public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener, PublisherHolder {

    private Publisher publisher = new Publisher();
    FragmentManager myFragmentManager;
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = getResources().getBoolean(R.bool.isLandscape); //!!

        if (!isLandscape) {
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
        //TODO запомнить заметку и при повороте вытащить по умолчанию
    }

    @Override
    public void onSendData(Note selectedItem) {
         myFragmentManager = getSupportFragmentManager();

        if (isLandscape) {
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

        return publisher;
    }
}