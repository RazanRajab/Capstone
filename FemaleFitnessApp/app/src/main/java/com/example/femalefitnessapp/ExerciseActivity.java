package com.example.femalefitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.femalefitnessapp.data.AppDatabase;
import com.example.femalefitnessapp.data.AppExecutors;
import com.example.femalefitnessapp.data.Exercise;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends YouTubeBaseActivity {

    @BindView(R.id.repeat_times)
    TextView repeating;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.add_favorite)
    Button favorite;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.youtube_view)
    YouTubePlayerView mPlayerView;

    private Exercise e;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private final String YOUTUBE_API = "AIzaSyA8_ylvbhPtbCsdpWsQcngi9d99XB3ktwg";

    private AppDatabase db;
    private LiveData<Exercise> f;
    private boolean fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);

        db = AppDatabase.getInstance(getApplicationContext());

        String Extra = getIntent().getStringExtra(Exercise.class.getName());
        Gson gson = new Gson();
        if (savedInstanceState == null) {
            e = gson.fromJson(Extra, Exercise.class);
        }
        //setSupportActionBar(mToolBar);
        title.setText(e.getName());
        //setTitle("");
        repeating.setText("Repeat "+e.getRepeat_times()+" Times");
        initYoutubePlayer(e);

       /* mLifecycleOwner = new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return mLifecycleRegistry;
            }
        };
        mLifecycleRegistry=new LifecycleRegistry(mLifecycleOwner);

        */

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (fav) {
                            //remove from database
                            db.favoritesDAO().deleteExercise(e);
                        } else {
                            //add to database
                            //FavoriteMovie(m);
                            db.favoritesDAO().insertExercise(e);
                        }
                    }
                });
                if (fav) {
                    favorite.setText("Add to Favorite");
                    Toast.makeText(ExerciseActivity.this,
                            "Removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setText("Remove from Favorite");
                    Toast.makeText(ExerciseActivity.this,
                            "Added To Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        f = db.favoritesDAO().loadExerciseById(e.getId());
        f.observeForever(new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise exercise) {
                if (f.getValue() != null) {
                    fav = true;
                    favorite.setText("Remove from Favorite");
                }
                else {
                    fav = false;
                }
            }
        });
        if (fav) {
            favorite.setText("Remove from Favorite");
        }
        super.onResume();
    }

    private void initYoutubePlayer(Exercise e){
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(e.getVideo_url());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        mPlayerView.initialize(YOUTUBE_API, mOnInitializedListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("exercise", e);
        super.onSaveInstanceState(outState);
    }
}
