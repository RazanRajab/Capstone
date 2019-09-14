package com.example.femalefitnessapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesDAO {

    @Query("SELECT * FROM Favorites")
    LiveData<List<Exercise>> loadFavorites();

    @Insert
    void insertExercise(Exercise e);

    @Delete
    void deleteExercise(Exercise e);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExercise(Exercise e);

    @Query("SELECT * FROM Favorites WHERE id = :id")
    LiveData<Exercise> loadExerciseById(int id);
}
