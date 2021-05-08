package ru.razuvaev.android_one.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;


import ru.razuvaev.android_one.Observer;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;

public class DetailFragment extends Fragment implements Observer {

    private static final String KEY_NOTE = "NOTE";
    private TextInputEditText mDetail;
    private TextView mDateTime;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Note note) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, note);

        fragment.setArguments(bundle);
        return fragment;
    }

    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.addObserver(this);
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.removeObserver(this);
        }
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.detail_tool_bar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.action_search_detail): {
                    Toast.makeText(requireContext(), "Search in detail note", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (R.id.action_delete_detail): {
                    Toast.makeText(requireContext(), "Delete note", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        mDetail = view.findViewById(R.id.detail_note);
        mDateTime = view.findViewById(R.id.date_field);
        assert getArguments() != null;
        Note note = getArguments().getParcelable(KEY_NOTE);
        mDateTime.setText(note.getDateTime());
        mDetail.setText(note.getDescription());
    }

    @Override
    public void updateNote(Note note) {
        mDateTime.setText(note.getDateTime());
        mDetail.setText(note.getDescription());
    }
}
