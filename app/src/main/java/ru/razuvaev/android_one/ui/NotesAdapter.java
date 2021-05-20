package ru.razuvaev.android_one.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> mData;
    private final Fragment mFragment;


    private OnNoteClicked mClickListener;


    public NotesAdapter(Fragment fragment, ArrayList<Note> mData) {
        this.mFragment = fragment;
        this.mData = mData;
    }

    public void addData(ArrayList<Note> toAdd) {
        if (mData != null) {
            mData.clear();
        }
        mData = toAdd;
    }

    public void deleteNote(int index) {
        mData.remove(index);
        notifyItemRemoved(index);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = mData.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public OnNoteClicked getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnNoteClicked clickListener) {
        this.mClickListener = clickListener;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        protected TextView mDateNote;
        protected TextView mNameNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            mFragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(v -> {
                if (getClickListener() != null) {
                    getClickListener().onNoteClicked(mData.get(getAdapterPosition()));
                }
            });

            mDateNote = itemView.findViewById(R.id.date_note);
            mNameNote = itemView.findViewById(R.id.name_note);
        }

        public void bind(Note note) {
            mDateNote.setText(note.getDateTime());
            mNameNote.setText(note.getNameNote());
        }
    }


    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }
}

