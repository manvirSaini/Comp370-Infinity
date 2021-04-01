package com.example.infinity_courseproject;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentRecViewAdapter;
import com.example.infinity_courseproject.assignments.AssignmentViewModel;
import com.example.infinity_courseproject.assignments.AssignmentsAddEditViewModel;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.home.Home;
import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRecViewAdapter;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssignmentsActivity extends Home
        implements AssignmentRecViewAdapter.OnAssignmentClickListener{
    public static final int ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE = 1;
    public static final String ASSIGNMENT_ID = "assignment_id";

    private Spinner showSpinner; //spinner to display filtering options
    public enum AssignmentFilterBy {ALL, NEUTRAL, COMPLETE, UPCOMING, OVERDUE}
    private static AssignmentFilterBy filter = AssignmentFilterBy.ALL; //by default

    private AssignmentViewModel assignmentViewModel;
    private CourseViewModel courseViewModel;

    private LiveData<List<Assignment>> assignmentLiveData;
    private List<Assignment> assignmentCopiedData;
    private AssignmentRecViewAdapter assignmentRecViewAdapter;
    private RecyclerView assignmentRecyclerView;

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignments_main);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText("Assignments");


//        Course c = new Course("I AM ALIVE", "I", "said");
//        CourseViewModel.insert(c);

        //initialize recyclerview
        assignmentRecyclerView = findViewById(R.id.assignment_recyclerview);

        //initialize viewmodels
        assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssignmentViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        assignmentRecyclerView.setHasFixedSize(true);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get and observe routines
        assignmentLiveData = assignmentViewModel.getAssignmentsOrderByDueTime();
        assignmentCopiedData = assignmentLiveData.getValue();

        assignmentLiveData.observe(this, new Observer<List<Assignment>>() {
            @Override
            public void onChanged(List<Assignment> assignments) {
                assignmentCopiedData = assignments;
                assignmentRecViewAdapter = new AssignmentRecViewAdapter(assignments,
                        AssignmentsActivity.this, assignmentViewModel, courseViewModel,
                        AssignmentsActivity.this);

                assignmentRecyclerView.setAdapter(assignmentRecViewAdapter);
            }
        });

        //initialize filter spinner array
        showSpinner = findViewById(R.id.assignment_show_spinner);

        //populate filter spinner array
        ArrayList<String> showSpinnerArray = new ArrayList<>();
        showSpinnerArray.add("ALL");
        showSpinnerArray.add("Neutral");
        showSpinnerArray.add("Complete");
        showSpinnerArray.add("Upcoming");
        showSpinnerArray.add("Overdue");

        //create and set spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignmentsActivity.this,
                android.R.layout.simple_spinner_item, showSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        showSpinner.setAdapter(adapter);

        //spinner listener
        showSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                switch(position) {
                    case 0:
                        filter = AssignmentFilterBy.ALL;
                        break;
                    case 1:
                        filter = AssignmentFilterBy.NEUTRAL;
                        break;
                    case 2:
                        filter = AssignmentFilterBy.COMPLETE;
                        break;
                    case 3:
                        filter = AssignmentFilterBy.UPCOMING;
                        break;
                    case 4:
                        filter = AssignmentFilterBy.OVERDUE;
                }

                //trigger livedata onChanged function
                if (assignmentCopiedData != null && assignmentCopiedData.size() != 0) {
                    AssignmentViewModel.update(assignmentCopiedData.get(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }

    @Override
    public void onAssignmentClick(int position) {
        Assignment assignment =
                Objects.requireNonNull(assignmentViewModel.getAssignmentsOrderByDueTime().getValue()).get(position);
        Intent intent = new Intent(this, AssignmentsAddActivity.class);
        intent.putExtra(ASSIGNMENT_ID, assignment.getId());
        startActivity(intent);
    }

    @Override
    public void onAssignmentLongClick(int position) {
        Assignment assignment =
                Objects.requireNonNull(assignmentViewModel.getAssignmentsOrderByDueTime().getValue()).get(position);
        AssignmentViewModel.delete(assignment);
    }

    /**
     * Onclick function for the add_routine_fab
     */
    public void transitionToAddAssignmentSubsection(View view) {
        Intent intent = new Intent(this, AssignmentsAddActivity.class);
        startActivityForResult(intent, ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AssignmentsAddActivity.TITLE_REPLY);
            Integer courseId = data.getIntExtra(AssignmentsAddActivity.COURSE_ID_REPLY, 0);
            if (courseId == 0) {
                courseId = null;
            }
            String dateStr = data.getStringExtra(AssignmentsAddActivity.DATE_REPLY);

            LocalDateTime date = null;
            if (dateStr != null) {
                date = LocalDateTime.parse(dateStr, myStudyRoutineDB.formatter);
            }

            String description = data.getStringExtra(AssignmentsAddActivity.DESCRIPTION_REPLY);

            int daysPrior = data.getIntExtra(AssignmentsAddActivity.PRIOR_DAYS_REPLY, 0);

            boolean complete = data.getBooleanExtra(AssignmentsAddActivity.COMPLETE_REPLY, false);

            Assignment assignment = new Assignment(title, courseId, date, description, daysPrior, complete);
            AssignmentViewModel.insert(assignment);
        }

    }

    public static AssignmentFilterBy getFilter() {
        return filter;
    }

    public static void setFilter(AssignmentFilterBy filter) {
        AssignmentsActivity.filter = filter;
    }

    //Navigation drawer function START:
    public void clickMenu(View view){
        openDrawer(drawer);
    }

    public static void openDrawer(DrawerLayout drawer) {

        drawer.openDrawer(GravityCompat.START);
    }

    public void clickIcon(View view){
        closeDrawer(drawer);

    }

    public static void closeDrawer(DrawerLayout drawer) {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void clickHome(View view){
        redirectActivity(this, Home.class);
    }

    public void clickAssignment(View view){ recreate(); }

    public void clickRoutine(View view){
        redirectActivity(this, RoutinesActivity.class);
    }

    //TODO: Make sure redirects go to desired activity
    public void clickCourse(View view){
        redirectActivity(this, Home.class);
    }

    public void clickSetting(View view){
        redirectActivity(this, Home.class);
    }

    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);

        closeDrawer(drawer);
    }
    //END of navigation drawer functions
}