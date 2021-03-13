package com.example.infinity_courseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import java.util.ArrayList;
import java.util.List;

public class PeriodsEditActivity extends AppCompatActivity {
    private Spinner courseSpinner;
    private Spinner studySpinner;
    private Spinner breakSpinner;

    private CourseViewModel courseViewModel;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //initialize spinners
        courseSpinner = findViewById(R.id.period_course_spinner);
        studySpinner = findViewById(R.id.period_study_spinner);
        breakSpinner = findViewById(R.id.period_break_spinner);

        //course viewmodel
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        //populate course spinner

        LiveData<List<Course>> courseLiveData = courseViewModel.getAllCourses();
        courseLiveData.observe(this, courses -> {
            courseList = courses;
            ArrayList<String> courseSpinnerArray = new ArrayList<>();
            courseSpinnerArray.add("NONE");
            for (Course c : courses) {
                courseSpinnerArray.add(c.getTitle());
            }
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                    PeriodsEditActivity.this,
                    android.R.layout.simple_spinner_item, courseSpinnerArray);

            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(courseAdapter);
        });

        //populate study time spinner
        ArrayList<String> studySpinnerArray = new ArrayList<>();
        studySpinnerArray.add("30min");
        studySpinnerArray.add("1h");
        studySpinnerArray.add("1h 30min");
        studySpinnerArray.add("2h");
        studySpinnerArray.add("2h 30min");
        studySpinnerArray.add("3h");
        studySpinnerArray.add("3h 30min");
        studySpinnerArray.add("4h");

        ArrayAdapter<String> studyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, studySpinnerArray);
        studyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studySpinner.setAdapter(studyAdapter);

        //populate break time spinner
        ArrayList<String> breakSpinnerArray = new ArrayList<>();
        breakSpinnerArray.add("5min");
        breakSpinnerArray.add("10min");
        breakSpinnerArray.add("15min");
        breakSpinnerArray.add("30min");
        breakSpinnerArray.add("45min");
        breakSpinnerArray.add("1h");

        ArrayAdapter<String> breakAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, breakSpinnerArray);
        breakAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breakSpinner.setAdapter(breakAdapter);

    }

    public void updatePeriod(View view) {
        //initialize everything for update

        finish();
    }
}
