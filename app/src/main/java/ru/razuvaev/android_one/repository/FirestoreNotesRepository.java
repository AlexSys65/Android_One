package ru.razuvaev.android_one.repository;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FirestoreNotesRepository implements NotesRepository {

    private static final String NOTES = "notes";
    private static final String DATE = "Date";
    private static final String DESCRIPTION = "Description";


    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(CallBack<ArrayList<Note>> callBack) {
        fireStore.collection(NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            ArrayList<Note> tmp = new ArrayList<>();
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();
                            for (DocumentSnapshot doc : docs) {
                                String id = doc.getId();
                                String dateNote = doc.getString(DATE);
                                String descriptionNote = doc.getString(DESCRIPTION);

                                assert descriptionNote != null;
                                tmp.add(new Note(id, dateNote, descriptionNote));
                            }
                            callBack.onSuccess(tmp);

                        } else {
                            callBack.onError(task.getException());
                        }


                    }
                });
    }

    @Override
    public void addNotes(String dateNote, String descriptionNote, CallBack<Note> callBack) {
        HashMap<String, Object> data = new HashMap<>();

        data.put(DATE, dateNote);
        data.put(DESCRIPTION, descriptionNote);

        fireStore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if (task.isSuccessful()) {
                            callBack.onSuccess(new Note(task.getResult().getId(), dateNote, descriptionNote));
                        } else {
                            callBack.onError(task.getException());
                        }
                    }
                });
    }

    @Override
    public void remove(Note item, CallBack<Object> callBack) {

    }





  /* // @Override
    public static ArrayList<Note> getNotes() {

        ArrayList<Note> listNotes = new ArrayList<>();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

        for (int i = 0; i < 20; i++) {
            listNotes.add(new Note(Integer.toString(i), dateFormat.format(new Date()), i + "-я ЗАМЕТКА. C помощью метода finish() можно завершить работу активности. " +
                    "Если приложение состоит из одной активности, то этого делать не следует, так как система сама завершит работу приложения. " +
                    "Если же приложение содержит несколько активностей, между которыми нужно переключаться, то данный метод позволяет экономить ресурсы. "));
        }
        return listNotes;
    }*/
}
