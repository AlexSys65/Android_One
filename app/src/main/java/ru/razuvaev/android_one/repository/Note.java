package ru.razuvaev.android_one.repository;


import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private final String mId;
    private  String dateTime;
    private  String nameNote;
    private  String description;
    private static final int END_INDEX_NAME = 80;

    public Note(String id, String dateTime, String description) {
        this.mId = id;
        this.dateTime = dateTime;
        int mEndSubStr = 0;
        if (description.length() < END_INDEX_NAME) {
            mEndSubStr = description.length();
        } else {
            mEndSubStr = END_INDEX_NAME;
        }
        this.nameNote = description.substring(0, mEndSubStr);
        this.description = description;
    }

    protected Note(Parcel in) {
        mId = in.readString();
        dateTime = in.readString();
        nameNote = in.readString();
        description = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote() {
        this.nameNote = description.substring(0, END_INDEX_NAME);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        this.description = s;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String s) {
        this.dateTime = s;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(dateTime);
        dest.writeString(nameNote);
        dest.writeString(description);

    }
}
