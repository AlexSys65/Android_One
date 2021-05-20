package ru.razuvaev.android_one.repository;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FirestoreNotesRepository implements NotesRepository {

    private static final String NOTES = "notes";
    private static final String DATE = "Date";
    private static final String DESCRIPTION = "Description";


    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getNotes(CallBack<ArrayList<Note>> callBack) {
        fireStore.collection(NOTES)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        ArrayList<Note> tmp = new ArrayList<>();
                        QuerySnapshot result = task.getResult();
                        if (result == null) {
                            return;
                        }
                        List<DocumentSnapshot> docs = result.getDocuments();
                        for (DocumentSnapshot doc : docs) {
                            String id = doc.getId();
                            String dateNote = doc.getString(DATE);
                            String descriptionNote = doc.getString(DESCRIPTION);

                            assert descriptionNote != null;
                            tmp.add(new Note(id, dateNote, descriptionNote));
                        }
                        tmp.sort(Comparator.comparing(Note::getDateTime).reversed());
                        callBack.onSuccess(tmp);

                    } else {
                        callBack.onError(task.getException());
                    }


                });
    }

    @Override
    public void addNotes(String dateNote, String descriptionNote, CallBack<Note> callBack) {
        HashMap<String, String> data = new HashMap<>();

        data.put(DATE, dateNote);
        data.put(DESCRIPTION, descriptionNote);

        fireStore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callBack.onSuccess(new Note(task.getResult().getId(), dateNote, descriptionNote));
                    } else {
                        callBack.onError(task.getException());
                    }
                });
    }

    @Override
    public void remove(Note item, CallBack<Note> callBack) {

        fireStore.collection(NOTES)
                .document(item.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            callBack.onSuccess(item);
                        } else {
                            callBack.onError(task.getException());
                        }

                    }
                });

    }



}
