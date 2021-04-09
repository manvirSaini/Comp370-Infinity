package com.example.infinity_courseproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.domain.entity.Course;
import com.example.infinity_courseproject.ui.recyclerViewAdapter.CourseRecViewAdapter;
import com.example.infinity_courseproject.ui.viewModel.CourseViewModel;
import com.example.infinity_courseproject.ui.util.OnItemClickListener;

import java.util.List;
import java.util.Objects;


public class CourseActivity extends AppCompatActivity
        implements OnItemClickListener {
    public static final int ADD_COURSE_ACTIVITY_REQUEST_CODE = 1;
    public static final String COURSE_ID = "course_id";

    private CourseViewModel courseViewModel;

    private List<Course> courseList;
    private CourseRecViewAdapter courseRecViewAdapter;
    private RecyclerView courseRecyclerView;

    private TextView emptyRecyclerViewMsg;

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_main);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText(R.string.toolbar_label_courses_section);

        //initialize recyclerview
        courseRecyclerView = findViewById(R.id.course_recyclerview);

        //empty message
        emptyRecyclerViewMsg = findViewById(R.id.courses_empty_recyclerview_msg_text);

        //initialize viewmodel
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        courseRecyclerView.setHasFixedSize(true);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get and observe courses
        LiveData<List<Course>> courseLiveData = courseViewModel.getAllCourses();

        courseLiveData.observe(this, courses -> {
            courseList = courses;
            if (courseList.size() == 0)
                emptyRecyclerViewMsg.setVisibility(View.VISIBLE);

            courseRecViewAdapter = new CourseRecViewAdapter(courses,
                    CourseActivity.this);

            courseRecyclerView.setAdapter(courseRecViewAdapter);
        });
    }

    @Override
    public void onItemClick(int position) {
        Course course =
                Objects.requireNonNull(courseViewModel.getAllCourses().getValue()).get(position);
        Intent intent = new Intent(this, CourseAddEditActivity.class);
        intent.putExtra(COURSE_ID, course.getId());
        startActivity(intent);
    }


    @Override
    public void onItemLongClick(int position) {
        Course course =
                Objects.requireNonNull(courseViewModel.getAllCourses().getValue()).get(position);
        CourseViewModel.delete(course);
    }


    /**
     * Onclick function for the add_course_fab
     */
    public void transitionToAddCourseSubsection(View view) {
        Intent intent = new Intent(this, CourseAddEditActivity.class);
        startActivityForResult(intent, ADD_COURSE_ACTIVITY_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String title = data.getStringExtra(CourseAddEditActivity.TITLE_REPLY);

            String profName = data.getStringExtra(CourseAddEditActivity.PROF_NAME_REPLY);

            String description = data.getStringExtra(CourseAddEditActivity.DESCRIPTION_REPLY);

            Course course = new Course(title, profName, description);
            CourseViewModel.insert(course);

            if (emptyRecyclerViewMsg.getVisibility() == View.VISIBLE)
                emptyRecyclerViewMsg.setVisibility(View.GONE);
        }

    }




    //Navigation drawer functions START:

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

    public void clickAssignment(View view){
        redirectActivity(this, AssignmentActivity.class);
    }

    public void clickRoutine(View view){
        redirectActivity(this, RoutineActivity.class);
    }

    public void clickCourse(View view){
        recreate();
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

