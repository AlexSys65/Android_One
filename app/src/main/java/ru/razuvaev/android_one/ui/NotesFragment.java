package ru.razuvaev.android_one.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.NoteRepository;

public class NotesFragment extends Fragment {

    protected NoteRepository repository = new NoteRepository();

    private FragmentSendDataListener mFragmentSendDataListener;
    ArrayList<Note> mNotes;


    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.tool_bar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.action_search): {
                    Toast.makeText(requireContext(), "Search", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (R.id.action_add): {
                    Toast.makeText(requireContext(), "Add", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (R.id.action_delete): {
                    Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        if (mNotes == null) mNotes = repository.getNotes();

        NotesAdapter adapter = new NotesAdapter(this, mNotes);

        adapter.setClickListener(note -> {
            if (getActivity() instanceof PublisherHolder) {
                PublisherHolder holder = (PublisherHolder) getActivity();
                holder.getPublisher().notify(note);
            }
            mFragmentSendDataListener.onSendData(note);
        });

        RecyclerView noteList = view.findViewById(R.id.note_list);
        noteList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        noteList.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mFragmentSendDataListener = (FragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement the interface OnFragmentSendDataListener");
        }
    }

    @Override
    public void onDetach() {
        mFragmentSendDataListener = null;
        super.onDetach();
    }
}