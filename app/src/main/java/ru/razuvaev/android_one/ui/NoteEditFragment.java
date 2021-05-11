package ru.razuvaev.android_one.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import ru.razuvaev.android_one.FragmentSendDataListener;
import ru.razuvaev.android_one.Observer;
import ru.razuvaev.android_one.R;
import ru.razuvaev.android_one.repository.Note;
import ru.razuvaev.android_one.repository.Publisher;

public class NoteEditFragment extends Fragment implements Observer {

    private static final String KEY_NOTE_EDIT = "NOTE_EDIT";
    protected TextView mEditDate;
    protected TextInputEditText mDescription;
    protected FragmentSendDataListener mFragmentSendDataListener;
    protected Calendar mDateAndTime = Calendar.getInstance();

    public NoteEditFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public static NoteEditFragment newInstance(Note note) {
        NoteEditFragment fragment = new NoteEditFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE_EDIT, note);

        fragment.setArguments(bundle);
        return fragment;
    }

    protected Publisher publisher;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditDate = view.findViewById(R.id.date_edit);
        if (mEditDate.getText().toString().isEmpty()) {
            setInitialDateTime();
        }

        mDescription = view.findViewById(R.id.edit_text_note);


        assert getArguments() != null;
        Note note = getArguments().getParcelable(KEY_NOTE_EDIT);

        Toolbar toolbar = view.findViewById(R.id.edit_tool_bar);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save_detail) {
                Toast.makeText(requireContext(), "Save detail", Toast.LENGTH_SHORT).show();
                saveChangeNote(note, mEditDate, mDescription);
            }
            return true;
        });

        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Edit date", Toast.LENGTH_SHORT).show();
                setDate(v);
                setTime(v);
            }
        });
        mEditDate.setText(note.getDateTime());
        mDescription.setText(note.getDescription());
    }

    private void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(getContext(), t,
                mDateAndTime.get(Calendar.HOUR_OF_DAY),
                mDateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mDateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDateAndTime.set(Calendar.YEAR, year);
            mDateAndTime.set(Calendar.MONTH, monthOfYear);
            mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        mEditDate.setText(DateUtils.formatDateTime(getContext(),
                mDateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    private void saveChangeNote(Note note, TextView mEditDate, TextInputEditText mDescription) {
       /* note.setDateTime(mEditDate.toString());
        note.setDescription(mDescription.toString());
        note.setNameNote();*/
        //TODO Передать измененный объект note во фрагмент деталей, вернуться к режиму просмотра.
    }


    @Override
    public void updateNote(Note note) {

    }
}
