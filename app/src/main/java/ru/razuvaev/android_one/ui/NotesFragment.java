package ru.razuvaev.android_one.ui;

import android.annotation.SuppressLint;
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

import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.CallBack;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.FirestoreNotesRepository;

public class NotesFragment extends Fragment {

    protected FirestoreNotesRepository mRepository = new FirestoreNotesRepository();

    protected FragmentSendDataListener mFragmentSendDataListener;
    protected ArrayList<Note> mNotes;


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
                    addNote();
                    break;
                }
                /*case (R.id.action_delete): {
                    Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show();
                }*/
            }
            return true;
        });

        NotesAdapter adapter = new NotesAdapter(this, mNotes);
        RecyclerView noteList = view.findViewById(R.id.note_list);

        if (mNotes == null) {
            FirestoreNotesRepository fireStore = new FirestoreNotesRepository();
            fireStore.getNotes(new CallBack<ArrayList<Note>>() {
                @Override
                public void onSuccess(ArrayList<Note> value) {
                    mNotes = value;
                    adapter.addData(mNotes);
                    noteList.setAdapter(adapter);
                    noteList.invalidate();
                }

                @Override
                public void onError(Throwable error) {

                }
            });
        }


        adapter.setClickListener(note -> {
            if (getActivity() instanceof PublisherHolder) {
                PublisherHolder holder = (PublisherHolder) getActivity();
                holder.getPublisher().notify(note);
            }
            mFragmentSendDataListener.onSendData(note, "view");
        });

        noteList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        noteList.setAdapter(adapter);
    }

    private void addNote() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        String dateNote = dateFormat.format(new Date());
        Note newNote = new Note("", dateNote, "");
        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notify(newNote);
        }
        mFragmentSendDataListener.onSendData(newNote, "edit");
        String s = newNote.getDescription();
    }

    private void deleteItem(Note note, int index) {

        mRepository.remove(note, new CallBack<Note>() {
            @Override
            public void onSuccess(Note value) {

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.action_open_context): {
                Toast.makeText(requireContext(), "Open note", Toast.LENGTH_SHORT).show();
                return true;
            }
            case (R.id.action_update_context): {
                Toast.makeText(requireContext(), "\n" +
                        "Choose note", Toast.LENGTH_SHORT).show();
                return true;
            }
            case (R.id.action_delete_context): {
                Toast.makeText(requireContext(), "Delete note", Toast.LENGTH_SHORT).show();
                return true;
            }
            default: {
                Toast.makeText(requireContext(), "Default action", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
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