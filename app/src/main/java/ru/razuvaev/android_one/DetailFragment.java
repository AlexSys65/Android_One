package ru.razuvaev.android_one;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.razuvaev.android_one.repository.Note;

public class DetailFragment extends Fragment {

    private static final String KEY_NOTE = "NOTE";

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Note note) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, note);

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText detail = view.findViewById(R.id.detail_note);
        assert getArguments() != null;
        Note note = getArguments().getParcelable(KEY_NOTE);
        detail.setText(note.getDescription());
    }
}
