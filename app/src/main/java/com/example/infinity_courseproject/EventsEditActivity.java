package com.example.infinity_courseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;
import java.util.List;

public class EventsEditActivity extends AppCompatActivity {
    public static final String EVENT_REPLY = "event_reply";
    public static final String INDEX_REPLY = "index_reply";
    private Spinner courseSpinner;
    private Spinner studySpinner;
    private Spinner breakSpinner;

    private List<Course> courseList;
    private Event eventToEdit;
    private int indexOfEventToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_edit);

        //initialize spinners
        TextView eventLabel = findViewById(R.id.event_edit_label_text);
        courseSpinner = findViewById(R.id.event_course_spinner);
        studySpinner = findViewById(R.id.event_study_spinner);
        breakSpinner = findViewById(R.id.event_break_spinner);

        //course viewmodel
        CourseViewModel courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        //the bundle to be received from RoutinesAddActivity
        Bundle data = getIntent().getExtras();
        eventToEdit = data.getParcelable(RoutinesAddActivity.EVENT_TO_EDIT);
        indexOfEventToEdit = data.getInt(RoutinesAddActivity.EVENT_TO_EDIT_INDEX);

        //fill label
        int eventNum = indexOfEventToEdit + 1;
        eventLabel.setText("Event " + eventNum);

        //populate course spinner through observing course live data
        LiveData<List<Course>> courseLiveData = courseViewModel.getAllCourses();
        courseLiveData.observe(this, courses -> {
            courseList = courses;
            ArrayList<String> courseSpinnerArray = new ArrayList<>();
            courseSpinnerArray.add("NONE");
            int selectedCoursePosition = 0;
            for (int i = 0; i < courses.size(); i++) {
                courseSpinnerArray.add(courses.get(i).getTitle());
                if (eventToEdit.getCourseId() == courses.get(i).getId()) {
                    //select i + 1 to account for the NONE option at position 0
                    selectedCoursePosition = i+1;
                }
            }
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                    EventsEditActivity.this,
                    android.R.layout.simple_spinner_item, courseSpinnerArray);

            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           // courseSpinner.setAdapter(courseAdapter);
            courseSpinner.setAdapter(courseAdapter);
            courseSpinner.setSelection(selectedCoursePosition);
        });

        //populate study time spinner
        ArrayList<String> studySpinnerArray = new ArrayList<>();
        studySpinnerArray.add("30min");
        studySpinnerArray.add("1h");
        studySpinnerArray.add("1h 30min");
        studySpinnerArray.add("2h");
        studySpinnerArray.add("2h 30min");
        studySpinnerArray.add("3h");

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

        //initial selections for study and break spinners
        String studyTime = eventToEdit.getStudyTimeInHoursAndMinutes();
        for (int i = 0; i < studySpinnerArray.size(); i++) {
            if (studyTime.equals(studySpinnerArray.get(i)))
                studySpinner.setSelection(i);
        }
        String breakTime = eventToEdit.getBreakTimeInHoursAndMinutes();
        for (int i = 0; i < breakSpinnerArray.size(); i++) {
            if (breakTime.equals(breakSpinnerArray.get(i))) {
                breakSpinner.setSelection(i);
                break;
            }
        }


    }

    /**
     * Onclick for 'done' button
     * @param view - 'done' button
     */
    public void updatePeriod(View view) {
        Intent replyIntent = new Intent();
        int courseId = 0;
        if (courseList.size() != 0 && courseSpinner.getSelectedItemPosition() != 0) {
            //the -1 is to account for the NONE option
            courseId = courseList.get(courseSpinner.getSelectedItemPosition() - 1).getId();
        }
        int studyTime = parseHourMinuteStringIntoMinutes(studySpinner.getSelectedItem().toString());
        int breakTime = parseHourMinuteStringIntoMinutes(breakSpinner.getSelectedItem().toString());

        Period studyPeriod = new Period(Period.Devotion.STUDY, studyTime);
        Period breakPeriod = new Period(Period.Devotion.BREAK, breakTime);
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(studyPeriod);
        periods.add(breakPeriod);
        Event event = new Event(periods, courseId);

        replyIntent.putExtra(EVENT_REPLY, event);
        replyIntent.putExtra(INDEX_REPLY, indexOfEventToEdit);

        setResult(RESULT_OK, replyIntent);

        finish();
    }

    /**
     * Onclick for 'back' button
     * @param view - 'back' button
     */
    public void transitionToAddRoutineSection(View view) {
        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);

        finish();
    }

    /**
     * Get the total time in minutes from a string of format "Xh Ymin", where X and Y are integers.
     * Also accepts strings with just minutes or just hours.
     * @param hourMinuteString - string of hours and minutes
     * @return - total minutes
     */
    private int parseHourMinuteStringIntoMinutes(String hourMinuteString) {
        int minutes = 0;
        String next;
        if (hourMinuteString.contains(" ")) {
            next = hourMinuteString.substring(0, hourMinuteString.indexOf("h"));
            minutes += Integer.parseInt(next)*60;
            next = hourMinuteString.substring(hourMinuteString.indexOf(" ")+1, hourMinuteString.indexOf("m"));
            minutes += Integer.parseInt(next);
        }
        else {
            if (hourMinuteString.contains("h")) {
                next = hourMinuteString.substring(0, hourMinuteString.indexOf("h"));
                minutes += Integer.parseInt(next)*60;
            }
            else {
                next = hourMinuteString.substring(0, hourMinuteString.indexOf("m"));
                minutes += Integer.parseInt(next);
            }
        }
        return minutes;
    }
}
