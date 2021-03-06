package com.example.infinity_courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRecViewAdapter;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity {

    private static final int ADD_COURSE_ACTIVITY_REQUEST_CODE = 1;

    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;

    private RoutineRecViewAdapter routineRecViewAdapter;
    private RecyclerView routineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_recyclerview);

        routineRecyclerView = findViewById(R.id.basic_recycler_view);

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(PeriodViewModel.class);

        //creating entries
//        Routine routine = null;
//        Routine anothaRoutine = null;
//        boolean[] weekdays = new boolean[]{true, false, false, false, false, false, true};
//        try {
//            routine = new Routine("routine", new boolean[7], null, null);
//            anothaRoutine = new Routine("anothaRoutine", weekdays, null, null);
//        } catch (Routine.IllegalStartTimeException e) {
//            e.printStackTrace();
//        } catch (Routine.IllegalWeekdaysLengthException e) {
//            e.printStackTrace();
//        }
//
//        //inserting entries
//        RoutineViewModel.insert(routine);
//        RoutineViewModel.insert(anothaRoutine);
//
//        for (int i = 0; i < 4; i++) {
//            PeriodViewModel.insert(
//                    new Period(i, 0, 0, null, "routine"));
//            PeriodViewModel.insert(
//                    new Period(i, 0, 0, null, "anothaRoutine"));
//        }

        routineRecyclerView.setHasFixedSize(true);
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(RoutinesActivity.this));

        //sorting routines
        LiveData<List<Routine>> routines = routineViewModel.getAllRoutines();


        routines.observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {

                routineRecViewAdapter = new RoutineRecViewAdapter(routines,
                        RoutinesActivity.this, routineViewModel, periodViewModel);

                routineRecyclerView.setAdapter(routineRecViewAdapter);
            }
        });





        //        periodViewModel.getAllPeriods().observe(this, new Observer<List<Period>>() {
//            @Override
//            public void onChanged(List<Period> periods) {
//
//            }
//        });
    }


}