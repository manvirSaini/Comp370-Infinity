package com.example.infinity_courseproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.data.database.MyStudyRoutineDB;
import com.example.infinity_courseproject.domain.entity.Assignment;
import com.example.infinity_courseproject.ui.recyclerViewAdapter.AssignmentRecViewAdapter;
import com.example.infinity_courseproject.ui.util.OnItemClickListener;
import com.example.infinity_courseproject.ui.viewModel.AssignmentViewModel;
import com.example.infinity_courseproject.ui.viewModel.CourseViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssignmentActivity extends BaseActivity
        implements OnItemClickListener {
    public static final int ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE = 1;
    public static final String ASSIGNMENT_ID = "assignment_id";

    public enum AssignmentFilterBy {ALL, NEUTRAL, COMPLETE, UPCOMING, OVERDUE}
    private static AssignmentFilterBy filter = AssignmentFilterBy.ALL; //by default

    private AssignmentViewModel assignmentViewModel;
    private CourseViewModel courseViewModel;

    private List<Assignment> assignmentList;
    private AssignmentRecViewAdapter assignmentRecViewAdapter;
    private RecyclerView assignmentRecyclerView;

    private TextView emptyRecyclerViewMsg;

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignments_main);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText(R.string.toolbar_label_assignments_section);


        findViewById(R.id.xiaoxi).setOnClickListener(view -> startActivity(
                new Intent(AssignmentActivity.this, HomeActivity.class)));

        //initialize recyclerview
        assignmentRecyclerView = findViewById(R.id.assignment_recyclerview);

        //empty message
        emptyRecyclerViewMsg = findViewById(R.id.assignments_empty_recyclerview_msg_text);

        //initialize viewmodels
        assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssignmentViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        assignmentRecyclerView.setHasFixedSize(true);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get and observe assignments
        LiveData<List<Assignment>> assignmentLiveData =
                assignmentViewModel.getAssignmentsOrderByDueTime();

        assignmentLiveData.observe(this, assignments -> {
            assignmentList = assignments;
            if (assignments.size() == 0)
                emptyRecyclerViewMsg.setVisibility(View.VISIBLE);

            assignmentRecViewAdapter = new AssignmentRecViewAdapter(assignments, courseViewModel,
                    AssignmentActivity.this);

            assignmentRecyclerView.setAdapter(assignmentRecViewAdapter);
        });

        //initialize filter spinner array
        //spinner to display filtering options
        Spinner showSpinner = findViewById(R.id.assignment_show_spinner);

        //populate filter spinner array
        ArrayList<String> showSpinnerArray = new ArrayList<>();
        showSpinnerArray.add("ALL");
        showSpinnerArray.add("Neutral");
        showSpinnerArray.add("Complete");
        showSpinnerArray.add("Upcoming");
        showSpinnerArray.add("Overdue");

        //create and set spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AssignmentActivity.this,
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
                if (assignmentList != null && assignmentList.size() != 0) {
                    AssignmentViewModel.update(assignmentList.get(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Assignment assignment =
                Objects.requireNonNull(assignmentViewModel.getAssignmentsOrderByDueTime().getValue()).get(position);
        Intent intent = new Intent(this, AssignmentAddEditActivity.class);
        intent.putExtra(ASSIGNMENT_ID, assignment.getId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        Assignment assignment =
                Objects.requireNonNull(assignmentViewModel.getAssignmentsOrderByDueTime().getValue()).get(position);
        AssignmentViewModel.delete(assignment);
    }

    /**
     * Onclick function for the add_assignment_fab
     */
    public void transitionToAddAssignmentSubsection(View view) {
        Intent intent = new Intent(this, AssignmentAddEditActivity.class);
        startActivityForResult(intent, ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ASSIGNMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AssignmentAddEditActivity.TITLE_REPLY);
            Integer courseId = data.getIntExtra(AssignmentAddEditActivity.COURSE_ID_REPLY, 0);
            if (courseId == 0) {
                courseId = null;
            }
            String dateStr = data.getStringExtra(AssignmentAddEditActivity.DATE_REPLY);

            LocalDateTime date = null;
            if (dateStr != null) {
                date = LocalDateTime.parse(dateStr, MyStudyRoutineDB.formatter);
            }

            String description = data.getStringExtra(AssignmentAddEditActivity.DESCRIPTION_REPLY);

            int daysPrior = data.getIntExtra(AssignmentAddEditActivity.PRIOR_DAYS_REPLY, 0);

            boolean complete = data.getBooleanExtra(AssignmentAddEditActivity.COMPLETE_REPLY, false);

            Assignment assignment = new Assignment(title, courseId, date, description, daysPrior, complete);
            AssignmentViewModel.insert(assignment);

            if (emptyRecyclerViewMsg.getVisibility() == View.VISIBLE)
                emptyRecyclerViewMsg.setVisibility(View.GONE);
        }

    }

    public static AssignmentFilterBy getFilter() {
        return filter;
    }

    public static void setFilter(AssignmentFilterBy filter) {
        AssignmentActivity.filter = filter;
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
        redirectActivity(this, HomeActivity.class);
    }

    public void clickAssignment(View view){ recreate(); }

    public void clickRoutine(View view){
        redirectActivity(this, RoutineActivity.class);
    }

    public void clickCourse(View view){
        redirectActivity(this, CourseActivity.class);
    }

    public void clickSetting(View view){
        redirectActivity(this, PassengerActivity.class);
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