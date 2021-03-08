package com.example.infinity_courseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRecViewAdapter;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity {

    private static final int ADD_ROUTINE_ACTIVITY_REQUEST_CODE = 1;

    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;

    private RoutineRecViewAdapter routineRecViewAdapter;
    private RecyclerView routineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_main);

        //initialize recyclerview
        //routineRecyclerView = findViewById(R.id.basic_recyclerview);
        routineRecyclerView = findViewById(R.id.routine_recyclerview);

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(PeriodViewModel.class);

        routineRecyclerView.setHasFixedSize(true);
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get and observe routines
        LiveData<List<Routine>> routines = routineViewModel.getAllRoutines();

        routines.observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                routineRecViewAdapter = new RoutineRecViewAdapter(routines,
                        RoutinesActivity.this, routineViewModel, periodViewModel);

                routineRecyclerView.setAdapter(routineRecViewAdapter);
            }
        });


    }

    /**
     * Onclick function for the add_routine_fab
     */
    public void transitionToAddRoutineSubsection(View view) {
        Intent intent = new Intent(this, RoutinesAddActivity.class);
        startActivityForResult(intent, ADD_ROUTINE_ACTIVITY_REQUEST_CODE);

    }

    public void transitionToEditRoutineSubsection() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ROUTINE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Routine routine = new Routine(
                    data.getStringExtra(RoutinesAddActivity.TITLE_REPLY),
                    data.getBooleanArrayExtra(RoutinesAddActivity.WEEKDAYS_REPLY),
                    data.getIntExtra(RoutinesAddActivity.START_HOUR_REPLY, 24),
                    data.getIntExtra(RoutinesAddActivity.START_MINUTE_REPLY, 0));

            RoutineViewModel.insert(routine);
        }

    }




}