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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class RoutinesAddActivity extends AppCompatActivity implements LifecycleOwner {

    public static final String TITLE_REPLY = "title_reply";
    public static final String WEEKDAYS_REPLY = "weekdays_reply";
    public static final String START_HOUR_REPLY = "start_hour_reply";
    public static final String START_MINUTE_REPLY = "start_minute_reply";

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

        //create a new period (not yet in database)
        //note that routineTitle is null here
        ArrayList<Period> periodArrayList = new ArrayList<>();
        periodArrayList.add(new Period(0, 60, 15,
                null, null));

        periodRecyclerView.setHasFixedSize(true);
        periodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize LiveData list
        routinesAddEditViewModel.setPeriodLiveData(periodArrayList);
        routinesAddEditViewModel.getPeriodLiveData().observe(this, periodListUpdateObserver);

    }


    /**
     * Onclick function for 'done' button when adding routine
     * @param view
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
                    case 0:
                        weekdays[6] = true;
                        break;
                    case 1:
                        weekdays[0] = true;
                        break;
                    case 2:
                        weekdays[1] = true;
                        break;
                    case 3:
                        weekdays[2] = true;
                        break;
                    case 4:
                        weekdays[3] = true;
                        break;
                    case 5:
                        weekdays[4] = true;
                        break;
                    case 6:
                        weekdays[5] = true;
                        break;
                }
            }

            Integer startHourInt = Integer.parseInt(startHour.getText().toString());
            Integer startMinInt = Integer.parseInt(startMinute.getText().toString());

            replyIntent.putExtra(TITLE_REPLY, title);
            replyIntent.putExtra(WEEKDAYS_REPLY, weekdays);
            replyIntent.putExtra(START_HOUR_REPLY, startHourInt);
            replyIntent.putExtra(START_MINUTE_REPLY, startMinInt);
            setResult(RESULT_OK, replyIntent);

            finish();
        }
        else {
            Toast.makeText(this, R.string.toast_empty_title, Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        }

    }

    /**
     * Onclick method for 'back' button
     * @param view - add_routine_fab
     */
    public void transitionToRoutineSection(View view) {
//        Intent replyIntent = new Intent();
//        setResult(RESULT_OK, replyIntent);
    }

    public void incrementStartHour(View view) {
        routinesAddEditViewModel.incrementStartHour();
        startHour.setText(routinesAddEditViewModel.getStartHour());
    }

    public void decrementStartHour(View view) {
        routinesAddEditViewModel.decrementStartHour();
        startHour.setText(routinesAddEditViewModel.getStartHour());
    }

    public void incrementStartMinute(View view) {
        routinesAddEditViewModel.incrementStartMinute();
        startMinute.setText(routinesAddEditViewModel.getStartMin());
    }

    public void decrementStartMinute(View view) {
        routinesAddEditViewModel.decrementStartMinute();
        startMinute.setText(routinesAddEditViewModel.getStartMin());
    }

    public void addPeriod(View view) {

    }


}