package com.example.femalefitnessapp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private final LiveData<List<Exercise>> favorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        favorites = db.favoritesDAO().loadFavorites();
    }

    public LiveData<List<Exercise>> getFavorites() {
        return favorites;
    }
}