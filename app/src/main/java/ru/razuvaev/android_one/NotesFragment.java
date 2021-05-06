package ru.razuvaev.android_one;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.NoteRepository;

public class NotesFragment extends Fragment {


    private OnFragmentSendDataListener fragmentSendDataListener;
    ArrayList<Note> notes;


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


        if (notes == null) {
            notes = NoteRepository.getNotes();
        }
        String[] nameNotes = new String[notes.size()];
        for (int i = 0; i < notes.size(); i++) {
            nameNotes[i] = notes.get(i).getNameNote();
        }
        ListView noteList = view.findViewById(R.id.note_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nameNotes);
        noteList.setAdapter(adapter);

        noteList.setOnItemClickListener((parent, view1, position, id) -> {
            Note selectedItem = notes.get(position);
            if (getActivity() instanceof PublisherHolder) {
                PublisherHolder holder = (PublisherHolder) getActivity();
                holder.getPublisher().notify(selectedItem);
            }
            fragmentSendDataListener.onSendData(selectedItem);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement the interface OnFragmentSendDataListener");
        }
    }

    @Override
    public void onDetach() {
        fragmentSendDataListener = null;
        super.onDetach();
    }
}