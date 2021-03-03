package com.example.infinity_courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.List;

public class RoutinesActivity extends AppCompatActivity {

    private static final int ADD_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);


        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(PeriodViewModel.class);

        CourseViewModel c = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        //delete all previous entries
        //CourseViewModel.deleteAll();
        //RoutineViewModel.deleteAll();
        //PeriodViewModel.deleteAll();

        //creating observers
        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                if (!routines.isEmpty()) {

                }
            }
        });

        periodViewModel.getAllPeriods().observe(this, new Observer<List<Period>>() {
            @Override
            public void onChanged(List<Period> periods) {
                if (!periods.isEmpty()) {

                }
            }
        });

        //creating entries
        Routine routine = null;
        try {
            routine = new Routine("routine", new boolean[7], null, null);
        } catch (Routine.IllegalStartTimeException e) {
            e.printStackTrace();
        } catch (Routine.IllegalWeekdaysLengthException e) {
            e.printStackTrace();
        }

        //inserting entries
        RoutineViewModel.insert(routine);

        for (int i = 0; i < 4; i++) {
            PeriodViewModel.insert(
                    new Period(i, 0, 0, null, "routine"));
        }
    }


}