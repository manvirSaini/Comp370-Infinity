package com.example.infinity_courseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRecViewAdapter;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutinesActivity extends MainActivity
        implements RoutineRecViewAdapter.OnRoutineClickListener {

    private static final int ADD_ROUTINE_ACTIVITY_REQUEST_CODE = 1;
    public static final String ROUTINE_ID = "routine_id";

    private Spinner showSpinner; //spinner to display filtering options
    public enum RoutineFilterBy {ALL, GENERAL, SUN, MON, TUES, WED, THURS, FRI, SAT}
    private static RoutineFilterBy filter = RoutineFilterBy.ALL;

    private Spinner masterRoutineSpinner; //spinner to allow selection of master routine

    private RoutineViewModel routineViewModel;

    private LiveData<List<Routine>> routineLiveData;
    private List<Routine> routineCopiedData;
    private RoutineRecViewAdapter routineRecViewAdapter;
    private RecyclerView routineRecyclerView;

    static DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_main);

        //Initialize Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);

        //initialize recyclerview
        routineRecyclerView = findViewById(R.id.routine_recyclerview);

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);

        /**
         * The master routine should update upon leaving the routines section
         * Perhaps shared preferences?
         */
        masterRoutineSpinner = findViewById(R.id.master_routine_spinner);


        routineRecyclerView.setHasFixedSize(true);
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //get and observe routines
        routineLiveData = routineViewModel.getRoutinesOrderByName();
        routineCopiedData = routineLiveData.getValue();

        routineLiveData.observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                routineCopiedData = routines;
                routineRecViewAdapter = new RoutineRecViewAdapter(routines,
                        RoutinesActivity.this,
                        RoutinesActivity.this);

                routineRecyclerView.setAdapter(routineRecViewAdapter);

                //update master routine spinner
                ArrayList<String> masterRoutineSpinnerArray = new ArrayList<>();
                masterRoutineSpinnerArray.add("NONE");
                for (Routine r : routines) {
                    masterRoutineSpinnerArray.add(r.getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoutinesActivity.this,
                        android.R.layout.simple_spinner_item, masterRoutineSpinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                masterRoutineSpinner.setAdapter(adapter);
            }
        });

        //initialize filter spinner array
        showSpinner = findViewById(R.id.routine_show_spinner);

        //populate filter spinner array
        ArrayList<String> showSpinnerArray = new ArrayList<>();
        showSpinnerArray.add("ALL");
        showSpinnerArray.add("GENERAL");
        showSpinnerArray.add("Sunday");
        showSpinnerArray.add("Monday");
        showSpinnerArray.add("Tuesday");
        showSpinnerArray.add("Wednesday");
        showSpinnerArray.add("Thursday");
        showSpinnerArray.add("Friday");
        showSpinnerArray.add("Saturday");

        //create and set spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoutinesActivity.this,
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
                        filter = RoutineFilterBy.ALL;
                        break;
                    case 1:
                        filter = RoutineFilterBy.GENERAL;
                        break;
                    case 2:
                        filter = RoutineFilterBy.SUN;
                        break;
                    case 3:
                        filter = RoutineFilterBy.MON;
                        break;
                    case 4:
                        filter = RoutineFilterBy.TUES;
                        break;
                    case 5:
                        filter = RoutineFilterBy.WED;
                        break;
                    case 6:
                        filter = RoutineFilterBy.THURS;
                        break;
                    case 7:
                        filter = RoutineFilterBy.FRI;
                        break;
                    case 8:
                        filter = RoutineFilterBy.SAT;
                }

                //trigger livedata onchanged function
                if (routineCopiedData != null && routineCopiedData.size() != 0) {
                    RoutineViewModel.update(routineCopiedData.get(0));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


    }

    //______________________________________________________________________________________________

    /**
     * Onclick for routine recyclerview items
     * @param position - index of item in recyclerview list
     */
    @Override
    public void onRoutineClick(int position) {
        Routine routine =
                Objects.requireNonNull(routineViewModel.getRoutinesOrderByName().getValue()).get(position);
        Intent intent = new Intent(this, RoutinesAddActivity.class);
        intent.putExtra(ROUTINE_ID, routine.getId());
        startActivity(intent);
    }

    @Override
    public void onRoutineLongClick(int position) {
        Routine routine =
                Objects.requireNonNull(routineViewModel.getRoutinesOrderByName().getValue()).get(position);
        RoutineViewModel.delete(routine);
    }

    /**
     * Onclick function for the add_routine_fab
     */
    public void transitionToAddRoutineSubsection(View view) {
        Intent intent = new Intent(this, RoutinesAddActivity.class);
        startActivityForResult(intent, ADD_ROUTINE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ROUTINE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Integer startHour = data.getIntExtra(RoutinesAddActivity.START_HOUR_REPLY, 24);
            Integer startMin = data.getIntExtra(RoutinesAddActivity.START_MINUTE_REPLY, 0);
            if(startHour == 24) {
                startHour = null;
                startMin = null;
            }
            ArrayList<Event> events =
                    data.getParcelableArrayListExtra(RoutinesAddActivity.EVENT_ARRAYLIST_REPLY);

            Routine routine = new Routine(
                    data.getStringExtra(RoutinesAddActivity.TITLE_REPLY).trim(),
                    data.getBooleanArrayExtra(RoutinesAddActivity.WEEKDAYS_REPLY),
                    startHour, startMin, events);

            RoutineViewModel.insert(routine);
        }

    }

    public static RoutineFilterBy getFilter() {
        return filter;
    }

    public static void setFilter(RoutineFilterBy filter) {
        RoutinesActivity.filter = filter;
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

    //   TODO: change mainactivity to home
    public void clickHome(View view){
        redirectActivity(this, MainActivity.class);
    }

    public void clickAssignment(View view){
        redirectActivity(this, AssignmentsActivity.class);
    }

    public void clickRoutine(View view){
        recreate();
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