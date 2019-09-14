package com.example.femalefitnessapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorites")
public class Exercise implements Parcelable {

    @PrimaryKey
    private int id;
    private String name;
    private String video_url;
    private int repeat_times;

    @Ignore
    public Exercise(){ }

    public Exercise(int id, String name, String video_url, int repeat_times) {
        this.id = id;
        this.name = name;
        this.video_url = video_url;
        this.repeat_times = repeat_times;
    }

    @Ignore
    protected Exercise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        video_url = in.readString();
        repeat_times = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getRepeat_times() {
        return repeat_times;
    }

    public void setRepeat_times(int repeat_times) {
        this.repeat_times = repeat_times;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(video_url);
        parcel.writeInt(repeat_times);
    }
}
