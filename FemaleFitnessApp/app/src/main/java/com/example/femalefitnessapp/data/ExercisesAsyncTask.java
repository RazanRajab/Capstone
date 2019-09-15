package com.example.femalefitnessapp.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExercisesAsyncTask extends AsyncTask<Void, Void, List<Exercise>> {
    private FirebaseFirestore db;
    private List<Exercise> exercises;

    public ExercisesAsyncTask(){
        exercises = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        db.collection("exercises")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Log.d("Fetch Exercises", document.getId() + " => " + document.getData());
                            Exercise e=document.toObject(Exercise.class);
                            e.setId(Integer.parseInt(document.getId()));
                            exercises.add(e);
                        }
                        // update based on adapter
                        onPostExecute(exercises);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Fetch Exercises", "Error getting documents.", e);
                    }
                });
        return exercises;
    }

    protected void onPostExecute(List<Exercise> result) {
    }
}
