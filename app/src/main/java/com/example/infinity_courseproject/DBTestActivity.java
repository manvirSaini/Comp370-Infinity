package com.example.infinity_courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView enterTitle;
    private TextView enterProf;
    private TextView enterDescription;
    private Button saveInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterTitle = findViewById(R.id.enter_title);
        enterProf = findViewById(R.id.enter_prof);
        enterDescription = findViewById(R.id.enter_description);
        saveInfo = findViewById((R.id.save_button));


        saveInfo.setOnClickListener(view -> {

            if(!TextUtils.isEmpty(enterTitle.getText()) &&
                    !TextUtils.isEmpty(enterProf.getText()) &&
                    !TextUtils.isEmpty(enterDescription.getText())) {


                Course course = new Course(
                        enterTitle.getText().toString(),
                        enterProf.getText().toString(),
                        enterDescription.getText().toString());

                CourseViewModel.insert(course);

            }else{
                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);
        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(PeriodViewModel.class);

        //delete all previous entries
//        RoutineViewModel.deleteAll();
//        CourseViewModel.deleteAll();
//        PeriodViewModel.deleteAll();

        //creating observers
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (!courses.isEmpty()) {
                    enterTitle.setText(courses.get(0).getTitle());
                }

            }
        });
//
//        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
//            @Override
//            public void onChanged(List<Routine> routines) {
//                if (!routines.isEmpty()) {
//                    textView2.setText(routines.get(0).getTitle());
//                }
//            }
//        });
//
//        periodViewModel.getAllPeriods().observe(this, new Observer<List<Period>>() {
//            @Override
//            public void onChanged(List<Period> periods) {
//                if (!periods.isEmpty()) {
//                    textView3.setText(String.valueOf(periods.get(0).getPosition()));
//                }
//            }
//        });

//        creating entries
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