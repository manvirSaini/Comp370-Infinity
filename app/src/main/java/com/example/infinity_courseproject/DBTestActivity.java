package com.example.infinity_courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import courses.Course;
import courses.CourseViewModel;
import routines.Routine;
import routines.RoutineViewModel;
import routines.periods.Period;
import routines.periods.PeriodViewModel;

public class DBTestActivity extends AppCompatActivity {
    private static final int ADD_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private CourseViewModel courseViewModel;
    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        //textView3 = findViewById(R.id.text3);

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);
        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(PeriodViewModel.class);
        //delete all previous entries
        RoutineViewModel.deleteAll();
        CourseViewModel.deleteAll();
        PeriodViewModel.deleteAll();

        //creating observers
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (!courses.isEmpty()) {
                    textView1.setText(courses.get(0).getTitle());
                }

            }
        });

        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                if (!routines.isEmpty()) {
                    textView2.setText(routines.get(0).getTitle());
                }
            }
        });

        periodViewModel.getAllPeriods().observe(this, new Observer<List<Period>>() {
            @Override
            public void onChanged(List<Period> periods) {
                if (!periods.isEmpty()) {
                    textView3.setText(String.valueOf(periods.get(0).getPosition()));
                }
            }
        });

        //creating entries
//        Routine routine = new Routine("routine", new boolean[7]);
//        Course course = new Course("course");
//
//        //inserting entries
//        RoutineViewModel.insert(routine);
//        CourseViewModel.insert(course);
//
//        for (int i = 0; i < 4; i++) {
//            PeriodViewModel.insert(
//                    new Period(i, course.getTitle(), routine.getTitle()));
//        }


    }
}