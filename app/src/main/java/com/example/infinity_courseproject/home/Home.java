package com.example.infinity_courseproject.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentViewModel;
import com.example.infinity_courseproject.courses.CourseViewModel;

import java.util.List;

public class Home  extends AppCompatActivity {


    private AssignmentViewModel assignmentViewModel;
    private CourseViewModel courseViewModel;

    private LiveData<List<Assignment>> assignmentLiveData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //initialize recyclerview
//        routineRecyclerView = findViewById(R.id.routine_recyclerview);


        //initialize viewmodels
        assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssignmentViewModel.class);
        //get and observe routines
        assignmentLiveData = assignmentViewModel.getAssignmentsOrderByDueTime();


        assignmentLiveData.observe(this, new Observer<List<Assignment>>() {
            @Override
            public void onChanged(List<Assignment> assignments) {
                Log.v("----------",assignments.toString());
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, NotificationAtivity.class));
            }
        });
    }


}
