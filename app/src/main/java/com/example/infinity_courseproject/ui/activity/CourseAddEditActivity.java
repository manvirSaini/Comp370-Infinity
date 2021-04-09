package com.example.infinity_courseproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.domain.entity.Course;
import com.example.infinity_courseproject.ui.viewModel.CourseViewModel;

import java.util.List;

public class CourseAddEditActivity extends BaseActivity {
    private static Course courseToBeUpdated;

    //constants identifying extras of return intent
    public static final String TITLE_REPLY = "title_reply";
    public static final String PROF_NAME_REPLY = "prof_name_reply";
    public static final String DESCRIPTION_REPLY = "description_reply";

    //UI components
    private EditText enterTitle;
    private EditText enterProfName;
    private EditText enterDescription;

    private List<Course> courseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_add_edit);

        //initialize UI components
        enterTitle = findViewById(R.id.courses_add_edit_title_edittext);
        enterProfName = findViewById(R.id.courses_add_edit_prof_edittext);
        enterDescription = findViewById(R.id.courses_add_edit_description_edittext);

        //initialize courseViewModel
        CourseViewModel courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        LiveData<List<Course>> courseLiveData = courseViewModel.getAllCourses();

        courseLiveData.observe(this, courses -> courseList = courses);

        //get data in the case of an edit - startActivity occurs from CourseActivity
        Bundle data = getIntent().getExtras();
        if (data != null) {
            int courseId = data.getInt(CourseActivity.COURSE_ID);

            courseViewModel.get(courseId).observe(this, course -> {
                courseToBeUpdated = course;
                enterTitle.setText(course.getTitle());
                enterProfName.setText(course.getProfessor());
                enterDescription.setText(course.getDescription());
            });
        }

    }

    public void addOrUpdateCourse(View view) {
        //intent to return to course section
        Intent replyIntent = new Intent();

        if (!TextUtils.isEmpty(enterTitle.getText())) {

            String title = enterTitle.getText().toString().trim();

            if (courseList != null) {
                if (!(courseToBeUpdated != null && courseToBeUpdated.getTitle().equals(title))) {
                    for (Course c : courseList) {
                        if (c.getTitle().equals(title)) {
                            Toast.makeText(this, R.string.title_already_exists, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

            }

            String profName = enterProfName.getText().toString().trim();
            String description = enterDescription.getText().toString().trim();

            if (profName.length() == 0)
                profName = null;
            if (description.length() == 0)
                description = null;

            //in the event that this is an update, not a new course...
            if (courseToBeUpdated != null) {
                Course course = new Course(title, profName, description);
                course.setId(courseToBeUpdated.getId());
                CourseViewModel.update(course);
                courseToBeUpdated = null;
            }
            else {
                replyIntent.putExtra(TITLE_REPLY, title);
                replyIntent.putExtra(PROF_NAME_REPLY, profName);
                replyIntent.putExtra(DESCRIPTION_REPLY, description);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
        else {
            Toast.makeText(this, R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
        }
    }

    public void transitionToCourseSection(View view) {
        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);

        finish();
    }
}