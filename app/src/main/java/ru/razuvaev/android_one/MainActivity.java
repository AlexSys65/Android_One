package ru.razuvaev.android_one;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;


import ru.razuvaev.android_one.repository.Note;

public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener {

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
    }

    @Override
    public void onSendData(Note selectedItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, DetailFragment.newInstance(selectedItem))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailFragment.newInstance(selectedItem))
                    .addToBackStack(null)
                    .commit();
        }
    }
}