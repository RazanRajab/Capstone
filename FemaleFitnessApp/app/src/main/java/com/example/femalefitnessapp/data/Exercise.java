package com.example.femalefitnessapp.data;

public class Exercise {

    private int id;
    private String name;
    private String video_url;
    private int repeat_times;
    private boolean favorite;

    public Exercise(){

    }

    public Exercise(int id, String name, String video_url, int repeat_times, boolean favorite) {
        this.id = id;
        this.name = name;
        this.video_url = video_url;
        this.repeat_times = repeat_times;
        this.favorite = favorite;
    }

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
