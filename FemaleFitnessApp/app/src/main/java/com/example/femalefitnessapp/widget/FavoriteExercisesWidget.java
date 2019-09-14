package com.example.femalefitnessapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.femalefitnessapp.R;
import com.example.femalefitnessapp.data.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteExercisesWidget extends AppWidgetProvider {

    private static List<Exercise> exercises = new ArrayList<>();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, List<Exercise> favorites) {

        exercises = favorites;
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_exercises_widget);
            Intent intent = new Intent(context, widgetListAdapter.class);
            views.setRemoteAdapter(R.id.widget_favorite_list, intent);
            ComponentName component = new ComponentName(context, widgetListAdapter.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_favorite_list);
            appWidgetManager.updateAppWidget(component, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static List<Exercise> getExercises(){
        return exercises;
    }
}

