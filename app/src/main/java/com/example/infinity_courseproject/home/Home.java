package com.example.infinity_courseproject.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    Button beginButton;
    Button showDueButton;
    ProgressBar progressBar;
    TextView countdownText;
    private Spinner menuSpinText;
    private Handler mHandler = new Handler();
    private Boolean timerRunning = false;
    private long leftTime;
    private long mEndTime;
    private static long MILL_IN_FUTURE;

    private int currentRoutineID = 0;
    private List<Routine> routineList;
    ArrayList<Event> eve;
    private CountDownTimer countDownTimer;
    int counter_arr[] = {1, 2, 3};
    int timer_counter, progress_counter = 0;

    // when begin button is clicked
    public void buttonClicked(View View) {
        String ButtonText = beginButton.getText().toString();
        Log.i("ButtonText", "The text of this button");

        if (ButtonText.equals("RESUME")) {
            //when resume set text pause and start timer again
            //timer.run();
            startTimer(leftTime);
            beginButton.setText("PAUSE");
        } else if (ButtonText.equals("PAUSE")) {
            // change text to resume
            pauseTimer(); // call pause timer button
            beginButton.setText("RESUME");
        } else if (ButtonText.equals("RESET")) {
            // reset progress and finish timer and run method
            progressBar.setProgress(0);
            beginButton.setText("BEGIN");
        } else {
            Log.i("BEGIN", "When button text does not match anyone!");

            if (currentRoutineID == 0) {
                // toast message please select the routine first
                Toast.makeText(Home.this, "Please select the routine First!",
                        Toast.LENGTH_LONG).show();
            } else {
                // this is begin button which will get data from datbase and begin the timer
                timer.run();
                beginButton.setText("PAUSE");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // set routine spinner, progress, timer text, Begin and due button
        menuSpinText = findViewById(R.id.spinner1);
        progressBar = findViewById(R.id.progress_bar);
        countdownText = findViewById(R.id.countDownTextView);
        beginButton = findViewById(R.id.GoButton);
        showDueButton = findViewById(R.id.datesButton);


        // timer handler

        Handler handler = new Handler();

        //initialize view models
        homeViewModel = new ViewModelProvider.AndroidViewModelFactory(
                Home.this.getApplication()).create(HomeViewModel.class);

        // insert values into database
        Period studyPeriod = new Period(Period.Devotion.STUDY, 60);
        Period breakPeriod = new Period(Period.Devotion.BREAK, 15);

        ArrayList<Period> arr = new ArrayList<>();
        arr.add(studyPeriod);
        arr.add(breakPeriod);

        Event event = new Event(arr, 0);
        Event event1 = new Event(arr, 1);
//        eve.add(event);
//        eve.add(event1);

        ArrayList<Event> ev = new ArrayList<>();

        boolean[] week = {false, true, false, true, false, false, true};

        Routine r = new Routine("Routine1", week, 13, 05, ev);
        Routine r2 = new Routine("Routine2", week, 13, 05, ev);
        Routine r3 = new Routine("Routine3", week, 15, 15, ev);
        Routine r4 = new Routine("Routine4", week, 10, 25, ev);
        //inserting routines
//        homeViewModel.deleteAll();
//        homeViewModel.insert(r);
//        homeViewModel.insert(r2);
//        homeViewModel.insert(r3);
//        homeViewModel.insert(r4);

        LiveData<List<Routine>> routineLiveData = homeViewModel.getAllRoutines();
        ArrayList<Integer> listId = new ArrayList<>();
        routineLiveData.observe(this, routines -> {
            routineList = routines;
            ArrayList<String> routineSpinnerArray = new ArrayList<>();
            //routineSpinnerArray.add("NONE");
            //int selectedRoutinePosition = 0;
            for (int i = 0; i < routines.size(); i++) {
                Log.d("TAG", String.valueOf(routines.get(i)));
                routineSpinnerArray.add(routines.get(i).getTitle());
                int id = routines.get(i).getId();
                Log.d("This id for routine", String.valueOf(id));
                listId.add(id);
            }
            Log.d("TAG", String.valueOf(routineSpinnerArray));

            ArrayAdapter routineAdapter = new ArrayAdapter(Home.this,
                    android.R.layout.simple_spinner_dropdown_item, routineSpinnerArray);

            routineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            menuSpinText.setAdapter(routineAdapter);
            menuSpinText.setSelected(false);  // must
            menuSpinText.setSelection(0, false);
            menuSpinText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("parent value :", String.valueOf(parent.getSelectedItem()));
                    Log.i("list1 id :", String.valueOf(listId.get(position)));
                    Log.i("parent getting id :", String.valueOf(parent.getSelectedItemId()));
                    currentRoutineID = listId.get(position); // set the current routine's ID according to the selected routine

                    homeViewModel.get(currentRoutineID).observe(Home.this, routine -> {
                        Log.i("HOME", "Inside of current routine");
                        eve = new ArrayList<Event>();
//                    eve = routine.getEvents(); // populate the Periods Array List
                        Period studyPeriod = new Period(Period.Devotion.STUDY, 60);
                        Period breakPeriod = new Period(Period.Devotion.BREAK, 15);

                        ArrayList<Period> arr = new ArrayList<>();
                        arr.add(studyPeriod);
                        arr.add(breakPeriod);
                        Event event = new Event(arr, 0);
                        Event event1 = new Event(arr, 1);
                        eve.add(event);
                        eve.add(event1);
                        for (Event i : eve) {
                            //startTimer(i.getStudyMinutes());
                            //startTimer(i.getBreakMinutes());

                            Log.i("HOME", "Period study: " + i.getStudyMinutes());
                            Log.i("HOME", "Period break: " + i.getBreakMinutes());

                        }
                    });

                    // when item is selected
                    // reset progress, timer
                    if (timerRunning) {
                        countDownTimer.cancel();
                    }
                    leftTime = 0;
                    //mHandler.removeCallbacks(timer);
                    progressBar.setProgress(0);
                    progress_counter = 0;
                    timer_counter = 0;
                    beginButton.setText("BEGIN");
                    countdownText.setText("00:00");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // probably method to setup default value for this part

                }
            });
        });
    }

    public void datesButton(View view) {
        // stop repeating task
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            //Log.i("RUN","To see if it still runs when timer is stopped");
            timerRunning = false;
            MILL_IN_FUTURE = (counter_arr[timer_counter]) * 60 * 1000;
            progress_counter = 0;
            progressBar.setMax((int) (counter_arr[timer_counter] * 60)); // set the progress max equals to number of secomds in set time
            startTimer(MILL_IN_FUTURE);
            // I want to run the timer back to back
//            int arr [] = {1,3,4,5}; (* 60 * 1000)
        }
    };

    public void startTimer(long millis) {

        if (!timerRunning) {
            mEndTime = System.currentTimeMillis() + leftTime;
            timerRunning = true;
            long time = (leftTime == 0 || leftTime == millis) ? millis : leftTime;
            timerRunning = true;
            countDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long tick) {
                    leftTime = tick;
                    Log.i("TICK", "leftTime = " + leftTime);
                    progress_counter = progress_counter + 1;
                    Log.i("TICK", "progres_counter = " + progress_counter);
                    // update the progress
                    progressBar.setProgress(progress_counter);
                    Log.i("Timer", "Time Left : " + tick / 1000);
                    updateTimer((int) tick);
                }

                @Override
                public void onFinish() {
                    // what should be done on finish
                    timerRunning = false;
                    leftTime = 0;
                    // reset progress
                    Log.i("Timer", "Timer Done !!");
                    timer_counter++;
                    if (timer_counter < counter_arr.length) {
                        progressBar.setProgress(0);
                        mHandler.postDelayed(timer, 1);
                    } else {
                        beginButton.setText("RESET");
                    }
                }
            };
            countDownTimer.start();
        }
    }

    //
    public void updateTimer(int mTimeLeftInMillis) {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        Log.d("minutes: ", String.valueOf(minutes));
        Log.d("SecondsLeft: ", String.valueOf(seconds));

        String secondString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownText.setText(secondString);
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        //updateButtons();
    }


// TO-DO
// Activity to be restored when destroyed using shared pref
// other landscape layout
// events data iteration
// TEXT to show which item is being pulled out
// Alarm manager things
// Try progress to update only once

// methods for saving the state and getting it back
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("MILLIS_LEFT", leftTime);
        editor.putBoolean("TIMER_RUNNING", timerRunning);
        editor.putLong("END_TIME", mEndTime);
        editor.putInt("PROGRESS", progress_counter);
        editor.apply();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        leftTime = prefs.getLong("MILLIS_LEFT", 0);
        timerRunning = prefs.getBoolean("TIMER_RUNNING", false);
        progress_counter = prefs.getInt("PROGRESS",0);
        //updateTimer();
        //updateButtons();
        if (timerRunning) {
            mEndTime = prefs.getLong("END_TIME", 0);
            leftTime = mEndTime - System.currentTimeMillis();
            if (leftTime < 0) {
                leftTime = 0;
                timerRunning = false;
                updateTimer((int) leftTime);
                //updateButtons();
            } else {
                progressBar.setProgress(progress_counter);
                startTimer(leftTime);
            }
        }
    }

}



    // divide the progress so that it is a sum of study period intervals
    // get the total length of periods array like how many
    // arrange or sort by priority value

//        homeViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
//            @Override
//
//            for(Rountine routine: )
//            public void onChanged(List<Routine> routines) {
//                if (!routines.isEmpty()) {
//                    routineTextView.setText(routines.get(0).getTitle());
//                    Log.d("TAG","This : "+routines.get(0).getId());
//                    Log.d("TAG","This : "+routines.get(0).getPeriods());
//                    Log.d("TAG","This : "+routines.get(0).getStartHour());
//                    Log.d("TAG","This : "+routines.get(0).getWeekdays());
//                    Log.d("TAG","This : "+routines.get(0).getTotalTimeInHoursAndMinutes());
//                }
//            }
//        });


//public void buttonClicked(View view) {
//
////         get routine from the the id
////         then get periods array
////         iterate over the different periods using countdown timer
//        if(currentRoutineID==0){
//            // toast message please select the routine first
//            Toast.makeText(Home.this, "Please select the routine First!",
//                    Toast.LENGTH_LONG).show();
//        }
//        else{
//                homeViewModel.get(currentRoutineID).observe(this, routine -> {
//                    Log.i("HOME","Inside of current routine");
//                    eve = new ArrayList<Event>();
////                    eve = routine.getEvents(); // populate the Periods Array List
//
//
//                    Period studyPeriod = new Period(Period.Devotion.STUDY, 60);
//                    Period breakPeriod = new Period(Period.Devotion.BREAK, 15);
//
//                    ArrayList<Period> arr = new ArrayList<>();
//                    arr.add(studyPeriod);
//                    arr.add(breakPeriod);
//
//                    Event event = new Event(arr, 0);
//                    Event event1 = new Event(arr, 1);
//
//                  eve.add(event);
//                  eve.add(event1);
//
//                    for(Event i : eve){
//
//                        startTimer(i.getStudyMinutes());
//                        startTimer(i.getBreakMinutes());
//
//                        Log.i("HOME","Period study: "+i.getStudyMinutes());
//                        Log.i("HOME","Period break: "+i.getBreakMinutes());
//
//                    }
//
//                });
//        }
//    }