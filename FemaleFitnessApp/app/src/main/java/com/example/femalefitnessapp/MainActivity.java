package com.example.femalefitnessapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.femalefitnessapp.adapters.ExercisesAdapter;
import com.example.femalefitnessapp.data.AppDatabase;
import com.example.femalefitnessapp.data.Exercise;
import com.example.femalefitnessapp.data.ExercisesAsyncTask;
import com.example.femalefitnessapp.data.FavoritesViewModel;
import com.example.femalefitnessapp.widget.FavoriteExercisesWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar myToolBar;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private ExercisesAdapter mExercisesAdapter;

    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Context context;
    private List<Exercise> favorites = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(myToolBar);
        title.setText("Workouts List");
        setTitle("");

        context = getApplicationContext();
        initRecyclerView();
        db = AppDatabase.getInstance(getApplicationContext());
        setUpViewModel();

        callTheAsycTask();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                callTheAsycTask();
                break;
            case R.id.favorite:
                mExercisesAdapter.setExercises(favorites);
                mExercisesAdapter.notifyDataSetChanged();
                break;
            case R.id.log_out:
                LogOut();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    private void LogOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mExercisesAdapter = new ExercisesAdapter(exercises, context);
        mRecyclerView.setAdapter(mExercisesAdapter);

        mExercisesAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Intent n = new Intent(context, ExerciseActivity.class);
                Gson gson = new Gson();
                n.putExtra(Exercise.class.getName(), gson.toJson(mExercisesAdapter.getExerecises().get(position)));
                startActivity(n);
            }
        });
    }

    private void callTheAsycTask(){
         new ExercisesAsyncTask(){
             @Override
             protected void onPostExecute(List<Exercise> result) {
                 super.onPostExecute(result);
                 mExercisesAdapter.setExercises(result);
                 mExercisesAdapter.notifyDataSetChanged();
             }
         }.execute();
    }

    public void setUpViewModel(){
            Log.d("MyLog","setUpViewModel");
            FavoritesViewModel viewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
            //movies.addAll(favorites);
            viewModel.getFavorites().observe(this, new Observer<List<Exercise>>() {
                @Override
                public void onChanged(List<Exercise> exercises) {
                    favorites.clear();
                    favorites.addAll(exercises);
                    mExercisesAdapter.notifyDataSetChanged();
                    for (int i=0; i< exercises.size();i++)
                    Log.d("MyLogWidget", exercises.get(i).getId()+"");
                    addFavoritesToWidget(exercises);
                }
            });
    }

    private void addFavoritesToWidget(List<Exercise> e){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, FavoriteExercisesWidget.class));
        FavoriteExercisesWidget.updateAppWidget(context, appWidgetManager, appWidgetIds, e);
    }

}