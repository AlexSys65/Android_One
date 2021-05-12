package ru.razuvaev.android_one.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.Observer;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;

public class DetailFragment extends Fragment implements Observer {

    protected static final String KEY_NOTE = "NOTE";
    protected TextView mDetail;
    protected TextView mDateTime;
    protected FragmentSendDataListener mFragmentSendDataListener;

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
        try {
            mFragmentSendDataListener = (FragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement the interface OnFragmentSendDataListener");
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.removeObserver(this);
        }
        mFragmentSendDataListener = null;
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

        assert getArguments() != null;
        Note note = getArguments().getParcelable(KEY_NOTE);

        Toolbar toolbar = view.findViewById(R.id.detail_tool_bar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.action_search_detail): {
                    Toast.makeText(requireContext(), "Search in detail note", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (R.id.action_edit_detail): {
                    Toast.makeText(requireContext(), "edit note", Toast.LENGTH_SHORT).show();
                    editDetail(note);
                }
            }
            return true;
        });

        mDetail = view.findViewById(R.id.detail_note);
        mDateTime = view.findViewById(R.id.date_field);

        mDetail.setOnClickListener(v -> editDetail(note));

        mDateTime.setText(note.getDateTime());
        mDetail.setText(note.getDescription());
    }



    private void editDetail(Note note) {
        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notify(note);
        }
        mFragmentSendDataListener.onSendData(note, "edit");
    }

    @Override
    public void updateNote(Note note) {
        mDateTime.setText(note.getDateTime());
        mDetail.setText(note.getDescription());
    }
}
