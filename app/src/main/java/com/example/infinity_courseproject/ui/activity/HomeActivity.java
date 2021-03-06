package com.example.infinity_courseproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
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

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.domain.util.TimeService;
import com.example.infinity_courseproject.domain.entity.Assignment;
import com.example.infinity_courseproject.ui.viewModel.AssignmentViewModel;
import com.example.infinity_courseproject.domain.entity.Routine;
import com.example.infinity_courseproject.domain.nonEntity.Event;
import com.example.infinity_courseproject.domain.nonEntity.Period;
import com.example.infinity_courseproject.ui.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.infinity_courseproject.ui.activity.RoutineActivity.ID;
import static com.example.infinity_courseproject.ui.activity.RoutineActivity.SHARED_ROUTINE;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    Button beginButton;
    Button showDueButton;
    ProgressBar progressBar;
    TextView countdownText;
    private Spinner menuSpinText;
    TextView periodStatus;
    private final Handler mHandler = new Handler();
    private boolean timerRunning = false;
    private boolean routineRunning = false;
    private boolean spinnerTouched = false;
    private long leftTime;
    private long mEndTime;
    private static long MILL_IN_FUTURE;
    private String currentRoutine = "NONE"; // current routine to NONE
    ArrayList<Event> eve;
    private ArrayList<Period> periods;
    private CountDownTimer countDownTimer;
    //int counter_arr[] = {1, 2, 3, 4};
    int timer_counter, progress_counter = 0;

    LiveData<List<Assignment>> assignmentLiveData;

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    // when begin button is clicked
    public void buttonClicked(View View) {
        String ButtonText = beginButton.getText().toString();

        switch (ButtonText) {
            case "RESUME":
                Log.i("RESUME BUTTON: ", "MILL_IN_FUTURE" + MILL_IN_FUTURE);
                periodStatus.setText("" + periods.get(timer_counter).getDevotion() + " PERIOD");
                startTimer();
                beginButton.setText(R.string.home_action_button_pause_label);
                break;
            case "PAUSE":
                pauseTimer(); // call pause timer button

                beginButton.setText(R.string.home_action_button_resume_label);
                break;
            case "RESET":
                countdownText.setText(R.string.initial_countdown_timer_value);
                progressBar.setProgress(0); // reset progress and finish timer and run method

                beginButton.setText(R.string.home_action_button_begin_label);
                periodStatus.setText("");
                routineRunning = false;
                timer_counter = 0;

                break;
            default:
                Log.i("BEGIN BUTTON", "Current Routine: " + currentRoutine);
                if (currentRoutine.equals("NONE")) {
                    // toast message please select the routine first
                    Toast.makeText(HomeActivity.this, "Select the routine first!",
                            Toast.LENGTH_LONG).show();
                } else {
                    // this is begin button which will get data from database and begin the timer
                    routineRunning = true;
                    timer.run();
                    beginButton.setText(R.string.home_action_button_pause_label);
                }
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        startService(new Intent(this, TimeService.class));

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText(R.string.toolbar_label_home_section);

        // set routine spinner, progress, timer text, Begin and due button
        menuSpinText = findViewById(R.id.spinner1);
        progressBar = findViewById(R.id.progress_bar);
        countdownText = findViewById(R.id.countDownTextView);
        beginButton = findViewById(R.id.GoButton);
        showDueButton = findViewById(R.id.datesButton);
        periodStatus = findViewById(R.id.statusTextView);

        //initialize view models
        homeViewModel = new ViewModelProvider.AndroidViewModelFactory(
                HomeActivity.this.getApplication()).create(HomeViewModel.class);

        LiveData<List<Routine>> routineLiveData = homeViewModel.getAllRoutines();
        // get all the routines from database
        routineLiveData.observe(this, routines -> {
            ArrayList<String> routineSpinnerArray = new ArrayList<>();
            routineSpinnerArray.add("NONE");
            for (int i = 0; i < routines.size(); i++) {
                Log.d("ROUTINE SPINNER", String.valueOf(routines.get(i)));
                routineSpinnerArray.add(routines.get(i).getTitle());
            }
            ArrayAdapter routineAdapter = new ArrayAdapter(HomeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, routineSpinnerArray);
            routineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            menuSpinText.setAdapter(routineAdapter);
            menuSpinText.setSelected(false);  // must
            menuSpinText.setSelection(0, false);

            menuSpinText.setSelection(((ArrayAdapter) menuSpinText.getAdapter()).getPosition(currentRoutine));

            // to detect whether spinner was set manually by user
            menuSpinText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        spinnerTouched = true; // User DID touched the spinner!
                    }
                    return false;
                }
            });
            menuSpinText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    periods = null;
                    currentRoutine = (String) parent.getItemAtPosition(position); // set the current routine's ID according to the selected routine
                    Log.i("HOME", "ON ITEM SELECTED current routine" + currentRoutine);
                    if (!currentRoutine.equals("NONE")) {

                        homeViewModel.getByTitle(currentRoutine).observe(HomeActivity.this, routine -> {
                            eve = new ArrayList<>();
                            periods = new ArrayList<>();
                            eve = routine.getEvents();
                            for (Event i : eve) {
                                periods.add(i.getPeriodAtIndex(0));
                                periods.add(i.getPeriodAtIndex(1));
                            }
                            if(timer_counter < periods.size()){
                                //MILL_IN_FUTURE = counter_arr[timer_counter] * 60 * 1000;; // set millis on create
                                MILL_IN_FUTURE = periods.get(0).getMinutes() * 60000;
                                Log.i("MILL_IN_FUTURE", String.valueOf(MILL_IN_FUTURE));
                                progressBar.setMax((int) MILL_IN_FUTURE / 1000);
                                periodStatus.setText(periods.get(timer_counter).getDevotion().toString()+" PERIOD");
                                updateTimer();
                            }
                            else{
                                progressBar.setMax(100);
                                progressBar.setProgress(100);
                                beginButton.setText(R.string.home_action_button_reset_label);
                            }
                        });
                        Log.i("PERIODS", "Periods populate!");
                        Log.i("PERIODS", "Periods " + periods);
                    }
                    if (spinnerTouched) {
                        // current value NONE or not reset everything
                        if (timerRunning && countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        leftTime = 0;
                        MILL_IN_FUTURE = 0;
                        progressBar.setProgress(0);
                        progress_counter = 0;
                        timer_counter = 0;
                        beginButton.setText(R.string.home_action_button_begin_label);
                        routineRunning = false;
                        periodStatus.setText("");
                        updateTimer();
                    } else {
                        if (!currentRoutine.equals("NONE")) {
                            if (timerRunning) {
                                Log.i("HOME", "called start timer");
                                timerRunning = false; //set to flase since it is no longer running
                                startTimer();
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // probably method to setup default value for this part
                }
            });
        });

        AssignmentViewModel assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssignmentViewModel.class);
        //get and observe routines
        assignmentLiveData = assignmentViewModel.getAssignmentsOrderByDueTime();


        assignmentLiveData.observe(this, assignments ->
                Log.v("----------",assignments.toString()));

        findViewById(R.id.datesButton).setOnClickListener(view ->
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class)));
    }

    private final Runnable timer = new Runnable() {
        @Override
        public void run() {
            timerRunning = false;
            //MILL_IN_FUTURE = counter_arr[timer_counter] * 60 * 1000;
            MILL_IN_FUTURE = periods.get(timer_counter).getMinutes() * 60 * 1000;
            periodStatus.setText(periods.get(timer_counter).getDevotion().toString()+" PERIOD");
            progress_counter = 0;
            progressBar.setMax(periods.get(timer_counter).getMinutes() * 60); // set the progress max equals to number of secomds in set time
            //progressBar.setMax((int) (counter_arr[timer_counter] * 60)); // set the progress max equals to number of seconds in set time
            String dev = periods.get(timer_counter).getDevotion().toString();
            if (timer_counter > 0 && dev.equals("STUDY")) {
                periodStatus.setText(dev+" PERIOD");
                updateTimer();
                beginButton.setText(R.string.home_action_button_resume_label);
            } else {
                Log.i("BEGIN", "This is the value of MILL_IN_FUTUTRE" + MILL_IN_FUTURE);
                startTimer();
            }
        }
    };

    public void startTimer() {

        if (!timerRunning) {
            long time = (leftTime == 0) ? MILL_IN_FUTURE : leftTime;
            mEndTime = System.currentTimeMillis() + time;
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
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    // what should be done on finish
                    timerRunning = false;
                    leftTime = 0;
                    // reset progress
                    Log.i("Timer", "Timer Done !!");
                    timer_counter++;
                    if (timer_counter < periods.size()) {  //
                        progressBar.setProgress(0);
                        mHandler.postDelayed(timer, 1);
                    } else {
                        beginButton.setText(R.string.home_action_button_reset_label);
                        periodStatus.setText(R.string.message_upon_completing_routine);
                    }
                }
            };
            countDownTimer.start();
        }
    }

    public void updateTimer() {
        long time = (leftTime == 0) ? MILL_IN_FUTURE : leftTime;
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;
        Log.d("minutes: ", String.valueOf(minutes));
        Log.d("SecondsLeft: ", String.valueOf(seconds));
        String secondString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownText.setText(secondString);
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    @Override
    protected void onResume() {
        Log.i("RESUME", "On Resume was called!");
        super.onResume();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("TAG", "home key clicked");
    }

    @Override
    protected void onPause() {
        Log.i("PAUSE", "ON PAUSE was called!");
        super.onPause();
        SharedPreferences prefs = this.getSharedPreferences("PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SPINNER_SELECT", currentRoutine);
        editor.putString("BUTTON_TEXT", (String) beginButton.getText());
        editor.putLong("MILLI_FUTURE", MILL_IN_FUTURE);
        editor.putLong("LEFT_TIME", leftTime);
        editor.putInt("ARRAY_COUNTER", timer_counter);
        editor.putBoolean("TIMER_RUNNING", timerRunning);
        editor.putBoolean("ROUTINE_RUNNING", routineRunning);
        editor.putLong("END_TIME", mEndTime);
        editor.putInt("PROGRESS", progress_counter);

        editor.apply();

        Log.i("TIMER: ", String.valueOf(countDownTimer));
        if (countDownTimer != null) {
            pauseTimer(); // pause timer
        }
    }


    @Override
    protected void onStop() {
        Log.i("HOME", "ON STOP Activity is called!");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.i("HOME", "ON START Activity is called!");
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        timerRunning = prefs.getBoolean("TIMER_RUNNING", false);
        routineRunning = prefs.getBoolean("ROUTINE_RUNNING", false);

        //SharedPrefs for master routine
        SharedPreferences masterprefs = this.getSharedPreferences(SHARED_ROUTINE, MODE_PRIVATE);
        String routine = masterprefs.getString(ID, "NONE"); // get master routine selected
        currentRoutine = prefs.getString("SPINNER_SELECT", "NONE"); // get current routine selected

        if (routineRunning && HomeViewModel.getRoutine(currentRoutine) != null) {
            // When routine is running and is present in routine database
            leftTime = prefs.getLong("LEFT_TIME", 0);
            progress_counter = prefs.getInt("PROGRESS", 0);
            MILL_IN_FUTURE = prefs.getLong("MILLI_FUTURE", 0);
            mEndTime = prefs.getLong("END_TIME", 0);
            timer_counter = prefs.getInt("ARRAY_COUNTER", 0);
            String buttonText = prefs.getString("BUTTON_TEXT", "");
            progress_counter = prefs.getInt("PROGRESS", 0);
            progressBar.setMax((int) MILL_IN_FUTURE / 1000); // set max progress accordingly

            // get button texts and perform actions accordingly
            if (timerRunning) {
                // when timer is running
                int progress_c = (int) ((leftTime/1000)-(mEndTime - System.currentTimeMillis())/1000) + progress_counter;
                Log.i("PROGRESS","Progress after addition: "+progress_c);
                leftTime = (mEndTime) - System.currentTimeMillis();
                if (leftTime < 0 || leftTime == 0) {
                    // when timer is over
                    Log.i("OVER","left time"+leftTime);
                    leftTime = 0;
                    timer_counter +=1;// increase counter by one to get value of next period
                    progressBar.setProgress(0);
                    progress_counter = 0;
                    beginButton.setText(R.string.home_action_button_resume_label);
                    timerRunning = false;
                } else {
                    // When timer is not over yet
                    Log.i("NOT OVER","left time"+leftTime);
                    timerRunning = true;
                    progressBar.setProgress(progress_c);
                    progress_counter = progress_c;
                    beginButton.setText(R.string.home_action_button_pause_label);
                }
                updateTimer();
            } else {
                // When timer is not running but routine is running
                progressBar.setProgress(progress_counter);
                beginButton.setText(buttonText);
                if (buttonText.equals("RESUME")) {
                    updateTimer();
                }
            }
        } else {
            // set the master routine
            currentRoutine = routine;
        }
    }

    //Navigation drawer function START:
    public void clickMenu(View view) {
        openDrawer(drawer);
    }

    public static void openDrawer(DrawerLayout drawer) {

        drawer.openDrawer(GravityCompat.START);
    }

    public void clickIcon(View view) {
        closeDrawer(drawer);
    }

    public static void closeDrawer(DrawerLayout drawer) {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void clickHome(View view) {
        recreate();
    }

    public void clickAssignment(View view) {
        redirectActivity(this, AssignmentActivity.class);
    }

    public void clickRoutine(View view) {
        redirectActivity(this, RoutineActivity.class);
    }

    public void clickCourse(View view) {
        redirectActivity(this, CourseActivity.class);
    }

    public void clickSetting(View view){
        redirectActivity(this, PassengerActivity.class);
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
