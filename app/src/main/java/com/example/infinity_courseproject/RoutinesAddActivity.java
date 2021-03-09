package com.example.infinity_courseproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodRecViewAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class RoutinesAddActivity extends AppCompatActivity implements LifecycleOwner {

    public static final String TITLE_REPLY = "title_reply";
    public static final String WEEKDAYS_REPLY = "weekdays_reply";
    public static final String START_HOUR_REPLY = "start_hour_reply";
    public static final String START_MINUTE_REPLY = "start_minute_reply";
    public static final String PERIOD_ARRAYLIST_REPLY = "period_arraylist_reply";

    private EditText enterTitle;
    private com.nex3z.togglebuttongroup.MultiSelectToggleGroup groupWeekdays;
    private TextView startHour;
    private TextView startMinute;
    private TextView totalTime;

    RoutinesAddEditViewModel routinesAddEditViewModel;

    private PeriodRecViewAdapter periodRecViewAdapter;
    private RecyclerView periodRecyclerView;

    private Button backButton;
    private Button doneButton;

    private Observer<ArrayList<Period>> periodListUpdateObserver = new Observer<ArrayList<Period>>() {

        @Override
        public void onChanged(ArrayList<Period> periods) {
            periodRecViewAdapter = new PeriodRecViewAdapter(
                    periods, RoutinesAddActivity.this);
            periodRecyclerView.setAdapter(periodRecViewAdapter);

            //update total time
            routinesAddEditViewModel.updateTotalTime(periods);
            totalTime.setText(routinesAddEditViewModel.getTotalTimeInHoursAndMinutes());
        }
    };


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

        doneButton = findViewById(R.id.done_button);
        backButton = findViewById(R.id.back_button);

        routinesAddEditViewModel = new ViewModelProvider(this).get(RoutinesAddEditViewModel.class);

        periodRecyclerView.setHasFixedSize(true);
        periodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize LiveData list
        routinesAddEditViewModel.addPeriod();
        routinesAddEditViewModel.getPeriodLiveData().observe(this, periodListUpdateObserver);

    }


    /**
     * Onclick function for 'done' button when adding routine.
     * Returns to routine section due to delivery of activity result.
     * @param view - 'done' button
     */
    public void addRoutine(View view) {

        //intent to return to routine section
        Intent replyIntent = new Intent();
        if (!TextUtils.isEmpty(enterTitle.getText())) {
            //title, weekdays, startHour, startMin
            String title = enterTitle.getText().toString();


            Set<Integer> idsOfClicked = groupWeekdays.getCheckedIds();

            boolean[] weekdays = new boolean[7];

            for (Integer i : idsOfClicked) {
                switch(i) {
                    case 2131231114:
                        weekdays[6] = true;
                        break;
                    case 2131230970:
                        weekdays[0] = true;
                        break;
                    case 2131231165:
                        weekdays[1] = true;
                        break;
                    case 2131231176:
                        weekdays[2] = true;
                        break;
                    case 2131231145:
                        weekdays[3] = true;
                        break;
                    case 2131230904:
                        weekdays[4] = true;
                        break;
                    case 2131231055:
                        weekdays[5] = true;
                        break;
                }
            }

            int startHourInt = routinesAddEditViewModel.getStartHour();
            int startMinInt = routinesAddEditViewModel.getStartMin();
            Log.d("RR", "addRoutine: period arraylist reached");
            ArrayList<Period> periods = routinesAddEditViewModel.getPeriodCopiedData();
            for (Period p : periods) {
                p.setRoutineTitle(title);
            }

            replyIntent.putExtra(TITLE_REPLY, title);
            replyIntent.putExtra(WEEKDAYS_REPLY, weekdays);
            replyIntent.putExtra(START_HOUR_REPLY, startHourInt);
            replyIntent.putExtra(START_MINUTE_REPLY, startMinInt);

            //Log.d("RR", "addRoutine: period arraylist reached");
            //replyIntent.putExtra(PERIOD_ARRAYLIST_REPLY, periods);

            Log.d("RR", "addRoutine: result ok reached");
            setResult(RESULT_OK, replyIntent);

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

    public void incrementStartHour(View view) {
        routinesAddEditViewModel.incrementStartHour();
        startHour.setText(String.valueOf(routinesAddEditViewModel.getStartHour()));
    }

    public void decrementStartHour(View view) {
        routinesAddEditViewModel.decrementStartHour();
        startHour.setText(String.valueOf(routinesAddEditViewModel.getStartHour()));
    }

    public void incrementStartMinute(View view) {
        routinesAddEditViewModel.incrementStartMinute();
        startMinute.setText(String.valueOf(routinesAddEditViewModel.getStartMin()));
    }

    public void decrementStartMinute(View view) {
        routinesAddEditViewModel.decrementStartMinute();
        startMinute.setText(String.valueOf(routinesAddEditViewModel.getStartMin()));
    }

    /**
     * Onclick method for fab button used for adding periods
     * @param view - add_period_fab
     */
    public void addPeriod(View view) {
        routinesAddEditViewModel.addPeriod();
    }
    
}