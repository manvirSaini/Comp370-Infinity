package com.example.infinity_courseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRecViewAdapter;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutinesActivity extends AppCompatActivity
        implements RoutineRecViewAdapter.OnRoutineClickListener {

    private static final int ADD_ROUTINE_ACTIVITY_REQUEST_CODE = 1;
    public static final String ROUTINE_TITLE = "routine_title";

    private Spinner spinner;
    public enum FilterBy {ALL, GENERAL, SUN, MON, TUES, WED, THURS, FRI, SAT}
    private static FilterBy filter = FilterBy.ALL;

    private RoutineViewModel routineViewModel;

    private LiveData<List<Routine>> routineLiveData;
    private List<Routine> routineCopiedData;
    private RoutineRecViewAdapter routineRecViewAdapter;
    private RecyclerView routineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_main);

        //initialize recyclerview
        //routineRecyclerView = findViewById(R.id.basic_recyclerview);
        routineRecyclerView = findViewById(R.id.routine_recyclerview);

        //initialize viewmodels
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);

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
                        RoutinesActivity.this, routineViewModel,
                        RoutinesActivity.this);

                routineRecyclerView.setAdapter(routineRecViewAdapter);
            }
        });

        //initialize weekday spinner array
        spinner = findViewById(R.id.routine_show_spinner);

        //populate weekday spinner array
        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("ALL");
        spinnerArray.add("GENERAL");
        spinnerArray.add("Sunday");
        spinnerArray.add("Monday");
        spinnerArray.add("Tuesday");
        spinnerArray.add("Wednesday");
        spinnerArray.add("Thursday");
        spinnerArray.add("Friday");
        spinnerArray.add("Saturday");

        //create and set spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoutinesActivity.this,
                        android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //activity starts with "ALL" option selected
        spinner.setSelection(0);

        //spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                switch(position) {
                    case 0:
                        filter = FilterBy.ALL;
                        break;
                    case 1:
                        filter = FilterBy.GENERAL;
                        break;
                    case 2:
                        filter = FilterBy.SUN;
                        break;
                    case 3:
                        filter = FilterBy.MON;
                        break;
                    case 4:
                        filter = FilterBy.TUES;
                        break;
                    case 5:
                        filter = FilterBy.WED;
                        break;
                    case 6:
                        filter = FilterBy.THURS;
                        break;
                    case 7:
                        filter = FilterBy.FRI;
                        break;
                    case 8:
                        filter = FilterBy.SAT;
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

    /**
     * Onclick for routine recyclerview items
     * @param position - index of item in recyclerview list
     */
    @Override
    public void onRoutineClick(int position) {
        Routine routine =
                Objects.requireNonNull(routineViewModel.getRoutinesOrderByName().getValue()).get(position);
        Intent intent = new Intent(this, RoutinesAddActivity.class);
        intent.putExtra(ROUTINE_TITLE, routine.getTitle());
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
            ArrayList<Period> periods =
                    data.getParcelableArrayListExtra(RoutinesAddActivity.PERIOD_ARRAYLIST_REPLY);

            Routine routine = new Routine(
                    data.getStringExtra(RoutinesAddActivity.TITLE_REPLY).trim(),
                    data.getBooleanArrayExtra(RoutinesAddActivity.WEEKDAYS_REPLY),
                    startHour, startMin, periods);

            RoutineViewModel.insert(routine);
        }

    }

    public static FilterBy getFilter() {
        return filter;
    }

    public static void setFilter(FilterBy filter) {
        RoutinesActivity.filter = filter;
    }
}