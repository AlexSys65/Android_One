package ru.razuvaev.android_one.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.Observer;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.CallBack;
import ru.razuvaev.android_one.repository.FirestoreNotesRepository;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;

public class DetailFragment extends Fragment implements Observer {

    protected static final String KEY_NOTE = "NOTE";
    protected TextView mDetail;
    protected TextView mDateTime;
    protected FragmentSendDataListener mFragmentSendDataListener;
    protected FirestoreNotesRepository mRepository = new FirestoreNotesRepository();
    boolean selectionResult;

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
                    editDetail(note);
                }
                case (R.id.action_delete_note): {
                    if (showAlertDialog()) {
                        deleteNote(note, item.getItemId());
                    }
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

    private boolean showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setCancelable(false)
                .setPositiveButton(R.string.positive, (dialog, which) -> selectionResult = true)
                .setNegativeButton(R.string.negative, (dialog, which) -> selectionResult = false).show();

        return selectionResult;
    }

    private void deleteNote(Note note, int index) {

        mRepository.remove(note, new CallBack<Note>() {
            @Override
            public void onSuccess(Note value) {
                // все прошло хорошо и объект удален на firestore, удалить в адаптере и обновить RecyclerView
            }

            @Override
            public void onError(Throwable error) {

            }
        });

    }


    public void editDetail(Note note) {
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
