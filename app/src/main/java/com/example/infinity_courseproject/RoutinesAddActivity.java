package com.example.infinity_courseproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.RoutinesAddEditViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodRecViewAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
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
import java.util.Set;

public class RoutinesAddActivity extends AppCompatActivity implements LifecycleOwner,
        PeriodRecViewAdapter.OnPeriodClickListener {

    private static final int EDIT_PERIOD_ACTIVITY_REQUEST_CODE = 1;
    public static final String PERIOD_TO_EDIT = "period";
    private static Routine routineToBeUpdated;

    public static final String TITLE_REPLY = "title_reply";
    public static final String WEEKDAYS_REPLY = "weekdays_reply";
    public static final String START_HOUR_REPLY = "start_hour_reply";
    public static final String START_MINUTE_REPLY = "start_minute_reply";
    public static final String PERIOD_ARRAYLIST_REPLY = "period_arraylist_reply";
    private static final int MAX_NUM_OF_PERIODS = 10;

    private EditText enterTitle;
    private com.nex3z.togglebuttongroup.MultiSelectToggleGroup groupWeekdays;
    private TextView startHour;
    private TextView startMinute;
    private TextView totalTime;


    RoutinesAddEditViewModel routinesAddEditViewModel;
    RoutineViewModel routineViewModel;
    private PeriodRecViewAdapter periodRecViewAdapter;
    private RecyclerView periodRecyclerView;

    private final Observer<ArrayList<Period>> periodListUpdateObserver = new Observer<ArrayList<Period>>() {

        @Override
        public void onChanged(ArrayList<Period> periods) {
            periodRecViewAdapter = new PeriodRecViewAdapter(
                    periods, RoutinesAddActivity.this,
                    RoutinesAddActivity.this);
            periodRecyclerView.setAdapter(periodRecViewAdapter);

            //update total time
            routinesAddEditViewModel.updateTotalTime(periods);
            totalTime.setText(routinesAddEditViewModel.getTotalTimeInHoursAndMinutes());
        }
    };


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routines_add);

        //initialize UI components
        enterTitle = findViewById(R.id.add_routine_title_edittext);

        groupWeekdays = findViewById(R.id.group_weekdays);


        startHour = findViewById(R.id.start_hour_textview);
        startMinute = findViewById(R.id.start_minute_textview);
        totalTime = findViewById(R.id.add_routine_total_time_textview);

        periodRecyclerView = findViewById(R.id.basic_recyclerview);

        routinesAddEditViewModel = new ViewModelProvider(this).get(RoutinesAddEditViewModel.class);
        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(RoutineViewModel.class);

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
                routinesAddEditViewModel.setPeriodLiveData(routine.getPeriods());
            });
        }
        else
            routinesAddEditViewModel.addPeriod();

        //set up period recyclerview and observe its live data
        periodRecyclerView.setHasFixedSize(true);
        periodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        routinesAddEditViewModel.getPeriodLiveData().observe(this, periodListUpdateObserver);

    }

    @Override
    public void onPeriodClick(int position) {
        Period period = routinesAddEditViewModel.get(position);
        Intent intent = new Intent(this, PeriodsEditActivity.class);
        intent.putExtra(PERIOD_TO_EDIT, period);
        startActivityForResult(intent, EDIT_PERIOD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onPeriodLongClick(int position) {
        routinesAddEditViewModel.removePeriod(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("update", "onActivityResult: " + resultCode);
        if (requestCode == EDIT_PERIOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Period period = data.getParcelableExtra(PeriodsEditActivity.PERIOD_REPLY);
            routinesAddEditViewModel.updatePeriod(period);
        }

    }

    //______________________________________________________________________________________________
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
            String title = enterTitle.getText().toString();

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

            int startHourInt = routinesAddEditViewModel.getStartHour();
            int startMinInt = routinesAddEditViewModel.getStartMin();
            ArrayList<Period> periods = routinesAddEditViewModel.getPeriodCopiedData();

            //in the event that this is an update, not a new routine...
            if (routineToBeUpdated != null) {
                Routine routine = new Routine(title, weekdays, startHourInt, startMinInt, periods);
                routine.setId(routineToBeUpdated.getId());
                RoutineViewModel.update(routine);
                routineToBeUpdated = null;
            }
            else {
                replyIntent.putExtra(TITLE_REPLY, title);
                replyIntent.putExtra(WEEKDAYS_REPLY, weekdays);
                replyIntent.putExtra(START_HOUR_REPLY, startHourInt);
                replyIntent.putExtra(START_MINUTE_REPLY, startMinInt);
                replyIntent.putParcelableArrayListExtra(PERIOD_ARRAYLIST_REPLY, periods);

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
     * Onclick method for fab button used for adding periods
     * @param view - add_period_fab
     */
    public void addPeriod(View view) {
        if (routinesAddEditViewModel.getPeriodCopiedData().size() == MAX_NUM_OF_PERIODS) {
            Toast.makeText(this, R.string.max_num_of_periods_reached, Toast.LENGTH_SHORT).show();
        }
        else
            routinesAddEditViewModel.addPeriod();
    }

}