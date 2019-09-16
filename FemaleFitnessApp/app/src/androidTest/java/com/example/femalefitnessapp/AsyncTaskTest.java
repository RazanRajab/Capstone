package com.example.femalefitnessapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.femalefitnessapp.data.Exercise;
import com.example.femalefitnessapp.data.ExercisesAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    @Test
    public void test() {
        try {
            List<Exercise> exercises = new ExercisesAsyncTask().execute().get();
            assertNotNull(exercises);
            assertEquals("Forward Lunge",exercises.get(0).getName());
            assertNotSame(0,exercises.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}