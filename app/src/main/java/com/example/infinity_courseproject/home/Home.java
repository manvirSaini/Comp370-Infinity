package com.example.infinity_courseproject.home;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.AssignmentsActivity;
import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.RoutinesActivity;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.infinity_courseproject.RoutinesActivity.ID;
import static com.example.infinity_courseproject.RoutinesActivity.SHARED_ROUTINE;

public class Home extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    Button beginButton;
    Button showDueButton;
    ProgressBar progressBar;
    TextView countdownText;
    private Spinner menuSpinText;
    TextView periodStatus;
    private Handler mHandler = new Handler();
    private Boolean timerRunning = false;
    private long leftTime;
    private long mEndTime;
    private static long MILL_IN_FUTURE;

    private String currentRoutine = "";
    private List<Routine> routineList;
    ArrayList<Event> eve;
    private CountDownTimer countDownTimer;
    int counter_arr[] = {1, 2, 3};
    private ArrayList<Period> periods;
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

            if (currentRoutine == "" || currentRoutine == "NONE") {
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

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText("Home");

        // set routine spinner, progress, timer text, Begin and due button
        menuSpinText = findViewById(R.id.spinner1);
        progressBar = findViewById(R.id.progress_bar);
        countdownText = findViewById(R.id.countDownTextView);
        beginButton = findViewById(R.id.GoButton);
        showDueButton = findViewById(R.id.datesButton);
        periodStatus = findViewById(R.id.statusTextView);

        // timer handler

        Handler handler = new Handler();

        //initialize view models
        homeViewModel = new ViewModelProvider.AndroidViewModelFactory(
                Home.this.getApplication()).create(HomeViewModel.class);

        LiveData<List<Routine>> routineLiveData = homeViewModel.getAllRoutines();

        routineLiveData.observe(this, routines -> {
            routineList = routines;
            ArrayList<String> routineSpinnerArray = new ArrayList<>();
            routineSpinnerArray.add("NONE");
            for(int i = 0; i < routines.size(); i++) {
                Log.d("TAG", String.valueOf(routines.get(i)));
                routineSpinnerArray.add(routines.get(i).getTitle());
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
                    Log.i("parent getting id :", String.valueOf(parent.getSelectedItemId()));
                    currentRoutine = (String) parent.getItemAtPosition(position); // set the current routine's ID according to the selected routine
                    Log.i("HOME", "Currently selected routine" + currentRoutine);
                    if (currentRoutine != "NONE") {
                        homeViewModel.getByTitle(currentRoutine).observe(Home.this, routine -> {
                            Log.i("HOME", "Inside of current routine");
                            eve = new ArrayList<Event>();
                            periods = new ArrayList<Period>();
                            eve = routine.getEvents();
                            for (Event i : eve) {
                                periods.add(i.getPeriodAtIndex(0));
                                periods.add(i.getPeriodAtIndex(1));
                                //Log.i("HOME", "Period getPeriodAtIndex: " + p.getDevotion());
                            }
//                            Log.i("HOME","I will be checking values stored in my periods arraylist");
//                            for(int i=0;i<periods.size();i++){
//                                Log.i("HOME","This is devotion: "+periods.get(i).getDevotion());
//                                Log.i("HOME","This is devotion: "+periods.get(i).getMinutes());
//                            }
                        });
                    }
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
            MILL_IN_FUTURE = periods.get(timer_counter).getMinutes() * 60 * 1000;
            periodStatus.setText(""+periods.get(timer_counter).getDevotion());
            progress_counter = 0;
            progressBar.setMax((int) (periods.get(timer_counter).getMinutes() * 60)); // set the progress max equals to number of secomds in set time
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
    protected void onResume() {
        Log.i("RESUME","On Resume was called!");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("PAUSE","ON PAUSE was called!");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("HOME","ON STOP Activity is called!");
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
        Log.i("HOME","ON START Activity is called!");
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
//        @Override
//        public void onFinish() {
//            countDownTimer = "TIME'S UP!";
//            text1.setText(countDownTimer);
//            }
 //   }


    //Shared Prefs
    //TODO: Remove toast
    public void loadSaved(){
        SharedPreferences prefs = this.getSharedPreferences(SHARED_ROUTINE ,MODE_PRIVATE);

        String routine =  prefs.getString(ID,"None");

        Toast.makeText(this,  routine, Toast.LENGTH_LONG).show();
    }

    //Navigation drawer function START:
    public void clickMenu(View view){
        loadSaved();
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
        recreate();
    }

    public void clickAssignment(View view){ redirectActivity(this, AssignmentsActivity.class); }

    public void clickRoutine(View view){
        redirectActivity(this, RoutinesActivity.class);
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
//                });
//        }
//    }