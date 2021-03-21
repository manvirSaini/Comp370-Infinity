package com.example.infinity_courseproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;
import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentViewModel;
import com.example.infinity_courseproject.assignments.AssignmentsAddEditViewModel;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsAddActivity extends AppCompatActivity {

    private static Assignment assignmentToBeUpdated;

    public static final String TITLE_REPLY = "title_reply";
    public static final String COURSE_ID_REPLY = "course_reply";
    public static final String DATE_REPLY = "date_reply";
    public static final String DESCRIPTION_REPLY = "description_reply";
    public static final String PRIOR_DAYS_REPLY = "prior_days_reply";
    public static final String COMPLETE_REPLY = "complete_reply";

    //onclicks for checkboxes and buttons beneath onCreate
    private EditText enterTitle;

    private Spinner courseSpinner;

    private CourseViewModel courseViewModel;
    private List<Course> courseList;

    private EditText enterYear;
    private EditText enterMonth;
    private EditText enterDay;

    private TextView dueHour;
    private TextView dueMinute;

    private EditText description;

    private TextView daysPriorToUpcoming;

    private CheckBox daysPriorCheckbox;
    private CheckBox assignDueDateCheckbox;
    private CheckBox markCompleteCheckbox;

    //containers
    private ViewGroup daysPriorContainer;
    private ViewGroup dueTimeAndDaysPriorContainer;

    private AssignmentsAddEditViewModel assignmentsAddEditViewModel;
    AssignmentViewModel assignmentViewModel;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignments_add);

        //initialize UI components
        enterTitle = findViewById(R.id.add_assignment_title_edittext);

        courseSpinner = findViewById(R.id.add_assignment_course_spinner);

        enterYear = findViewById(R.id.add_assignment_year_edittext);
        enterMonth = findViewById(R.id.add_assignment_month_edittext);
        enterDay = findViewById(R.id.add_assignment_day_edittext);
        dueHour = findViewById(R.id.add_assignment_due_hour_textview);
        dueMinute = findViewById(R.id.add_assignment_due_minute_textview);

        description = findViewById(R.id.add_assignment_description_edittext);

        daysPriorToUpcoming = findViewById(R.id.add_assignment_prior_days_value);

        daysPriorContainer = findViewById(R.id.add_assignment_days_prior_container);
        dueTimeAndDaysPriorContainer = findViewById(R.id.add_assignment_due_time_and_days_prior_container);

        daysPriorCheckbox = findViewById(R.id.add_assignment_notify_me_prior_checkbox);
        assignDueDateCheckbox = findViewById(R.id.add_assignment_assign_due_date_checkbox);
        markCompleteCheckbox = findViewById(R.id.add_assignment_mark_as_complete_checkbox);

        //create viewmodels
        assignmentsAddEditViewModel = new ViewModelProvider(this).get(AssignmentsAddEditViewModel.class);
        assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssignmentViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        //preset daysPrior
        daysPriorToUpcoming.setText("01");

        //get data in the case of an edit - startActivity occurs from AssignmentsActivity
        Bundle data = getIntent().getExtras();
        if (data != null) {
            int assignmentId = data.getInt(AssignmentsActivity.ASSIGNMENT_ID);
            assignmentToBeUpdated = assignmentViewModel.getImmediate(assignmentId);

            assignmentViewModel.get(assignmentId).observe(this, assignment -> {
                enterTitle.setText(assignment.getTitle());
                LocalDateTime date = assignment.getDueTime();
                if (date != null) {
                    int year = date.getYear();
                    int month = date.getMonthValue();
                    int day = date.getDayOfMonth();
                    int hour = date.getHour();
                    int minute = date.getMinute();
                    enterYear.setText(String.format("%04d", year));
                    enterMonth.setText(String.format("%02d", month));
                    enterDay.setText(String.format("%02d", day));
                    dueHour.setText(String.format("%02d", hour));
                    dueMinute.setText(String.format("%02d", minute));
                    assignmentsAddEditViewModel.setDueHour(hour);
                    assignmentsAddEditViewModel.setDueMin(minute);

                    if (assignment.getDaysPriorToUpcoming() == 0) {
                        daysPriorCheckbox.setChecked(false);
                        daysPriorContainer.setVisibility(View.GONE);
                        daysPriorToUpcoming.setText("01");
                    }
                    else {
                        daysPriorToUpcoming.setText(String.format("%02d", assignment.getDaysPriorToUpcoming()));
                        assignmentsAddEditViewModel.setDaysPrior(assignment.getDaysPriorToUpcoming());
                    }
                }
                else {
                    assignDueDateCheckbox.setChecked(false);
                    dueTimeAndDaysPriorContainer.setVisibility(View.GONE);
                }

                description.setText(assignment.getDescription());

                if (assignment.isComplete()) {
                    assignmentsAddEditViewModel.setMarkedAsComplete(true);
                    markCompleteCheckbox.setChecked(true);
                }
            });
        }

        //set up spinner
        LiveData<List<Course>> courseLiveData = courseViewModel.getAllCourses();
        courseLiveData.observe(this, courses -> {
            courseList = courses;
            ArrayList<String> courseSpinnerArray = new ArrayList<>();
            courseSpinnerArray.add("NONE");
            int selectedCoursePosition = 0;
            for (int i = 0; i < courses.size(); i++) {
                courseSpinnerArray.add(courses.get(i).getTitle());
                if (assignmentToBeUpdated != null && assignmentToBeUpdated.getCourseId() != null &&
                        assignmentToBeUpdated.getCourseId() == courses.get(i).getId()) {
                    //select i + 1 to account for the NONE option at position 0
                    selectedCoursePosition = i+1;
                    break;
                }
            }
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                    AssignmentsAddActivity.this,
                    android.R.layout.simple_spinner_item, courseSpinnerArray);

            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseSpinner.setAdapter(courseAdapter);
            courseSpinner.setSelection(selectedCoursePosition);
        });

    }

    public void addOrUpdateAssignment(View view) {
        //intent to return to routine section
        Intent replyIntent = new Intent();
        if (!TextUtils.isEmpty(enterTitle.getText())) {
            //title, course, due date, description, duedate, complete
            String title = enterTitle.getText().toString().trim();
            Integer courseId = null;
            String dateStr = null;
            int daysPrior = 0;

            //course
            for (Course course : courseList) {
                if (course.getTitle().equals(courseSpinner.getSelectedItem())) {
                    courseId = course.getId();
                }
            }

            //due time
            if (dueTimeAndDaysPriorContainer.getVisibility() != View.GONE) {
                if ((TextUtils.isEmpty(enterYear.getText())
                    || TextUtils.isEmpty(enterMonth.getText())
                    || TextUtils.isEmpty(enterDay.getText()))) {
                    Toast.makeText(this, R.string.toast_invalid_date, Toast.LENGTH_SHORT).show();
                    return;
                }

                String year = enterYear.getText().toString();
                String month = enterMonth.getText().toString();
                String day = enterDay.getText().toString();
                String hour = String.valueOf(assignmentsAddEditViewModel.getDueHour());
                String minute = String.valueOf(assignmentsAddEditViewModel.getDueMin());

                if (hour.equals("24")) {
                    hour = "00";
                    minute = "00";
                }

                dateStr = assignmentsAddEditViewModel.convertToLocalDateTimeParsable(
                        year, month, day, hour, minute);

                if (!assignmentsAddEditViewModel.dateIsValid(dateStr)) {
                    Toast.makeText(this, R.string.toast_invalid_date, Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!assignmentsAddEditViewModel.dateIsInTheFuture(dateStr)) {
                    Toast.makeText(this, R.string.toast_past_date, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            String descriptor = description.getText().toString();

            if (daysPriorContainer.getVisibility() != View.GONE &&
            dueTimeAndDaysPriorContainer.getVisibility() != View.GONE) {
                daysPrior = assignmentsAddEditViewModel.getDaysPrior();
            }

            boolean markedAsComplete = assignmentsAddEditViewModel.isMarkedAsComplete();

            //in the event that this is an update, not a new routine...
            if (assignmentToBeUpdated != null) {
                LocalDateTime dateTime = null;
                if (dateStr != null)
                    dateTime = LocalDateTime.parse(dateStr, myStudyRoutineDB.formatter);
                Assignment assignment = new Assignment(title, courseId, dateTime, descriptor,
                        daysPrior, markedAsComplete);
                assignment.setId(assignmentToBeUpdated.getId());
                AssignmentViewModel.update(assignment);
                assignmentToBeUpdated = null;
            }
            else {
                replyIntent.putExtra(TITLE_REPLY, title);
                replyIntent.putExtra(COURSE_ID_REPLY, courseId);
                replyIntent.putExtra(DATE_REPLY, dateStr);
                replyIntent.putExtra(DESCRIPTION_REPLY, descriptor);
                replyIntent.putExtra(PRIOR_DAYS_REPLY, daysPrior);
                replyIntent.putExtra(COMPLETE_REPLY, markedAsComplete);

                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
        else {
            Toast.makeText(this, R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
        }
    }

    public void transitionToAssignmentSection(View view) {
        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);

        finish();
    }

    //checkbox onclick
    public void hideOrShowDateTimeAndDaysPriorSelection(View view) {
        if (dueTimeAndDaysPriorContainer.getVisibility() != View.GONE) {
            dueTimeAndDaysPriorContainer.setVisibility(View.GONE);
        }
        else
            dueTimeAndDaysPriorContainer.setVisibility(View.VISIBLE);
    }

    //checkbox onclick
    public void hideOrShowDaysPriorSelection(View view) {
        if (daysPriorContainer.getVisibility() != View.GONE) {
            daysPriorContainer.setVisibility(View.GONE);
        }
        else
           daysPriorContainer.setVisibility(View.VISIBLE);
    }

    @SuppressLint("DefaultLocale")
    public void incrementDueHour(View view) {
        assignmentsAddEditViewModel.incrementDueHour();
        dueHour.setText(String.format("%02d", assignmentsAddEditViewModel.getDueHour()));
    }

    @SuppressLint("DefaultLocale")
    public void decrementDueHour(View view) {
        assignmentsAddEditViewModel.decrementDueHour();
        dueHour.setText(String.format("%02d", assignmentsAddEditViewModel.getDueHour()));
    }

    @SuppressLint("DefaultLocale")
    public void incrementDueMinute(View view) {
        assignmentsAddEditViewModel.incrementDueMinute();
        dueMinute.setText(String.format("%02d", assignmentsAddEditViewModel.getDueMin()));
    }

    @SuppressLint("DefaultLocale")
    public void decrementDueMinute(View view) {
        assignmentsAddEditViewModel.decrementDueMinute();
        dueMinute.setText(String.format("%02d", assignmentsAddEditViewModel.getDueMin()));
    }


    @SuppressLint("DefaultLocale")
    public void incrementPriorDays(View view) {
        assignmentsAddEditViewModel.incrementDaysPrior();
        daysPriorToUpcoming.setText(String.format("%02d", assignmentsAddEditViewModel.getDaysPrior()));
    }

    @SuppressLint("DefaultLocale")
    public void decrementPriorDays(View view) {
        assignmentsAddEditViewModel.decrementDaysPrior();
        daysPriorToUpcoming.setText(String.format("%02d", assignmentsAddEditViewModel.getDaysPrior()));
    }


    public void markAsComplete(View view) {
        if (assignmentsAddEditViewModel.isMarkedAsComplete())
            assignmentsAddEditViewModel.setMarkedAsComplete(false);
        else
            assignmentsAddEditViewModel.setMarkedAsComplete(true);

    }
}