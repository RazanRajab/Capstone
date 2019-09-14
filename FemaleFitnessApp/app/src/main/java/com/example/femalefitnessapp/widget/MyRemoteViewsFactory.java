package com.example.femalefitnessapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.femalefitnessapp.R;
import com.example.femalefitnessapp.data.Exercise;

import java.util.List;

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Exercise> exercises;

    public MyRemoteViewsFactory(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        exercises = FavoriteExercisesWidget.getExercises();
    }
    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_card);
        Exercise e = exercises.get(i);
        views.setTextViewText(R.id.exercise_info, e.getName()+" : "+e.getRepeat_times()+" Times");
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}