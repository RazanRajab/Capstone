package com.example.femalefitnessapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.femalefitnessapp.R;
import com.example.femalefitnessapp.data.Exercise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder>{

 class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_name)
    TextView name;

    private ViewHolder(View itemView) {
        super(itemView);
        itemView.setTag(this);
        itemView.setOnClickListener(onExerciseClickListener);
        ButterKnife.bind(this, itemView);
    }
}

    private List<Exercise> exercises;
    private View.OnClickListener onExerciseClickListener;
    private Context context;

    // Pass in the tasks array into the constructor
    public ExercisesAdapter(List<Exercise> exercises , Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    public void setExercises(List<Exercise> exercises){
        this.exercises = exercises;
    }

    public List<Exercise> getExercises(){
        return exercises;
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        onExerciseClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View TaskView = inflater.inflate(R.layout.exercise_card, parent, false);

        // Return a new holder instance
        return new ViewHolder(TaskView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise e = exercises.get(position);
        holder.name.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
