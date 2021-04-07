package com.example.infinity_courseproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentRecViewAdapter;
import com.example.infinity_courseproject.courses.CourseViewModel;
import com.example.infinity_courseproject.roomDatabase.BaseActivity;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.RoutinesAddEditViewModel;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.events.EventRecViewAdapter;
import com.example.infinity_courseproject.routines.periods.Period;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The RoutinesAddActivity is the UI controller for the UI presented in the routines_add.xml file.
 * Author - David Semke
 */
public class RoutinesAddActivity extends BaseActivity implements LifecycleOwner,
        EventRecViewAdapter.OnEventClickListener {

    private static final int EDIT_EVENT_ACTIVITY_REQUEST_CODE = 1;
    private static final int MAX_NUM_OF_EVENTS = 10;
    public static final String EVENT_TO_EDIT = "event";
    public static final String EVENT_TO_EDIT_INDEX = "index";


    //routineToBeUpdated is null if an edit is not taking place, not null otherwise
    private static Routine routineToBeUpdated;

    //intent reply constants
    public static final String TITLE_REPLY = "title_reply";
    public static final String WEEKDAYS_REPLY = "weekdays_reply";
    public static final String START_HOUR_REPLY = "start_hour_reply";
    public static final String START_MINUTE_REPLY = "start_minute_reply";
    public static final String EVENT_ARRAYLIST_REPLY = "period_arraylist_reply";

    //UI elements
    private EditText enterTitle;
    private com.nex3z.togglebuttongroup.MultiSelectToggleGroup groupWeekdays;
    private TextView startHour;
    private TextView startMinute;
    private TextView totalTime;

    //non-livedata list of routines
    List<Routine> routineList;

    //view models
    RoutinesAddEditViewModel routinesAddEditViewModel;
    RoutineViewModel routineViewModel;
    CourseViewModel courseViewModel;
    private RecyclerView eventRecyclerView;

    //adapters
    private EventRecViewAdapter eventRecViewAdapter;

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_add_edit);

        //initialize UI components
        enterTitle = findViewById(R.id.add_routine_title_edittext);

        groupWeekdays = findViewById(R.id.group_weekdays);


        startHour = findViewById(R.id.start_hour_textview);
        startMinute = findViewById(R.id.start_minute_textview);
        totalTime = findViewById(R.id.add_routine_total_time);

        eventRecyclerView = findViewById(R.id.basic_recyclerview);

        routinesAddEditViewModel = new ViewModelProvider(this).get(RoutinesAddEditViewModel.class);
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);
        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseViewModel.class);

        LiveData<List<Routine>> routineLiveData = routineViewModel.getRoutinesOrderByName();

        //set routine list
        routineLiveData.observe(this, routines -> {
            routineList = routines;
        });

        //set up event recyclerview and observe its live data
        LiveData<ArrayList<Event>> eventLiveData = routinesAddEditViewModel.getEventLiveData();
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventLiveData.observe(this, events -> {
            eventRecViewAdapter = new EventRecViewAdapter(
                    events, RoutinesAddActivity.this,
                    courseViewModel, RoutinesAddActivity.this);
            eventRecyclerView.setAdapter(eventRecViewAdapter);

            //update total time
            routinesAddEditViewModel.updateTotalTime(events);
            totalTime.setText(routinesAddEditViewModel.getTotalTimeInHoursAndMinutes());
        });

        //get data in the case of an edit - startActivity occurs from RoutinesActivity
        Bundle data = getIntent().getExtras();
        if (data != null) {
            int routineId = data.getInt(RoutinesActivity.ROUTINE_ID);

            routineViewModel.get(routineId).observe(this, routine -> {
                routineToBeUpdated = routine;

                enterTitle.setText(routine.getTitle());
                boolean[] weekdays = routine.getWeekdays();

                for (int i = 0; i < 7; i++) {
                    if (weekdays[i]) {
                        int id = groupWeekdays.getChildAt(i).getId();
                        groupWeekdays.check(id);
                    }
                }

                if (routine.getStartHour() != null) {
                    routinesAddEditViewModel.setStartHour(routine.getStartHour());
                    startHour.setText(String.format("%02d", routinesAddEditViewModel.getStartHour()));
                }
                if (routine.getStartMinute() != null) {
                    routinesAddEditViewModel.setStartMin(routine.getStartMinute());
                    startMinute.setText(String.format("%02d", routinesAddEditViewModel.getStartMin()));
                }
                routinesAddEditViewModel.setEventLiveData(routine.getEvents());
            });
        }
        else
            routinesAddEditViewModel.addEvent();

    }

    @Override
    public void onEventClick(int position) {
        Event event = routinesAddEditViewModel.get(position);
        Intent intent = new Intent(this, EventsEditActivity.class);
        intent.putExtra(EVENT_TO_EDIT, event);
        intent.putExtra(EVENT_TO_EDIT_INDEX, position);
        startActivityForResult(intent, EDIT_EVENT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onEventLongClick(int position) {
        if (routinesAddEditViewModel.getEventCopiedData().size() == 1) {
            Toast.makeText(this, R.string.minimum_of_one_event, Toast.LENGTH_SHORT).show();
        }
        else
            routinesAddEditViewModel.removeEvent(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("update", "onActivityResult: " + resultCode);
        if (requestCode == EDIT_EVENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Event event = data.getParcelableExtra(EventsEditActivity.EVENT_REPLY);
            int index = data.getIntExtra(EventsEditActivity.INDEX_REPLY, 0);
            routinesAddEditViewModel.updateEvent(index, event);
        }

    }

    /**
     * Onclick function for 'done' button when adding routine.
     * Returns to routine section due to delivery of activity result.
     * @param view - 'done' button
     */
    public void addOrUpdateRoutine(View view) {

        //intent to return to routine section
        Intent replyIntent = new Intent();

        if (!TextUtils.isEmpty(enterTitle.getText())) {
            //title, weekdays, startHour, startMin
            String title = enterTitle.getText().toString().trim();

            if (routineList != null) {
                if (!(routineToBeUpdated != null && routineToBeUpdated.getTitle().equals(title))) {
                    for (Routine r : routineList) {
                        if (r.getTitle().equals(title)) {
                            Toast.makeText(this, R.string.title_already_exists, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

            }


            //first index will be the id for sunday button...
            int[] buttonIds = new int[7];
            for (int i = 0; i < groupWeekdays.getChildCount(); i++) {
                buttonIds[i] = groupWeekdays.getChildAt(i).getId();
            }

            Set<Integer> idsOfClicked = groupWeekdays.getCheckedIds();
            boolean[] weekdays = new boolean[7];

            //determine weekdays routine is to be associated with
            for (Integer i : idsOfClicked) {
                for (int k = 0; k < 7; k++) {
                    if (i == buttonIds[k])
                        weekdays[k] = true;
                }
            }

            Integer startHour = routinesAddEditViewModel.getStartHour();
            Integer startMin = routinesAddEditViewModel.getStartMin();

            ArrayList<Event> events = routinesAddEditViewModel.getEventCopiedData();

            //final period gets 0 minutes
            List<Period> periods = events.get(events.size()-1).getPeriods();
            periods.get(1).setMinutes(0);

            //in the event that this is an update, not a new routine...
            if (routineToBeUpdated != null) {
                if (startHour == 24) {
                    startHour = null;
                    startMin = null;
                }
                Routine routine = new Routine(title, weekdays, startHour, startMin, events);
                routine.setId(routineToBeUpdated.getId());
                RoutineViewModel.update(routine);
                routineToBeUpdated = null;
            }
            else {
                replyIntent.putExtra(TITLE_REPLY, title);
                replyIntent.putExtra(WEEKDAYS_REPLY, weekdays);
                replyIntent.putExtra(START_HOUR_REPLY, startHour);
                replyIntent.putExtra(START_MINUTE_REPLY, startMin);
                replyIntent.putParcelableArrayListExtra(EVENT_ARRAYLIST_REPLY, events);

                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
        else {
            Toast.makeText(this, R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Onclick method for 'back' button
     * @param view - 'back' button
     */
    public void transitionToRoutineSection(View view) {
        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);

        finish();
    }

    //onclicks for incrementing and decrementing

    @SuppressLint("DefaultLocale")
    public void incrementStartHour(View view) {
        routinesAddEditViewModel.incrementStartHour();
        startHour.setText(String.format("%02d", routinesAddEditViewModel.getStartHour()));
    }

    @SuppressLint("DefaultLocale")
    public void decrementStartHour(View view) {
        routinesAddEditViewModel.decrementStartHour();
        startHour.setText(String.format("%02d", routinesAddEditViewModel.getStartHour()));
    }

    @SuppressLint("DefaultLocale")
    public void incrementStartMinute(View view) {
        routinesAddEditViewModel.incrementStartMinute();
        startMinute.setText(String.format("%02d", routinesAddEditViewModel.getStartMin()));
    }

    @SuppressLint("DefaultLocale")
    public void decrementStartMinute(View view) {
        routinesAddEditViewModel.decrementStartMinute();
        startMinute.setText(String.format("%02d", routinesAddEditViewModel.getStartMin()));
    }

    /**
     * Onclick method for fab button used for adding events
     * @param view - add_event_fab
     */
    public void addEvent(View view) {
        if (routinesAddEditViewModel.getEventCopiedData().size() == MAX_NUM_OF_EVENTS) {
            Toast.makeText(this, R.string.max_num_of_events_reached, Toast.LENGTH_SHORT).show();
        }
        else
            routinesAddEditViewModel.addEvent();
    }

}