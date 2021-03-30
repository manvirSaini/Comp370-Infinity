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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.AssignmentsActivity;
import com.example.infinity_courseproject.MainActivity;
import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.RoutinesActivity;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;


import java.util.ArrayList;
import java.util.List;

import static com.example.infinity_courseproject.RoutinesActivity.ID;
import static com.example.infinity_courseproject.RoutinesActivity.SHARED_ROUTINE;

public class Home extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    ProgressBar progressBar;
    TextView timerTextView;
    private Spinner  menuSpinText;
    private int progress = 0;
    private int totalPeriods = 0;
    private int currentRoutineID = 0;
    private List<Routine> routineList;
    ArrayList<Period> pr;//  //declare periods arraylist
    private int mNbOfRounds= 0;
    //// parent.getItemAtPosition(pos)

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    // Shared prefs
//    public static final String SHARED_ROUTINE = "routine_id";
//    public static final String ID = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText("Home");

        menuSpinText = findViewById(R.id.spinner1);
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

        ArrayList<Event> ev = new ArrayList<>();

        boolean[] week = {false, true, false, true, false, false, true};

        Routine r = new Routine("Routine1", week,13,05, ev);
        Routine r2 = new Routine("Routine2", week, 13, 05, ev);
        Routine r3 = new Routine("Routine3", week, 15, 15, ev);
        Routine r4 = new Routine("Routine4", week, 10, 25, ev);
//        //inserting routines
        homeViewModel.deleteAll();
        homeViewModel.insert(r);
        homeViewModel.insert(r2);
        homeViewModel.insert(r3);
        homeViewModel.insert(r4);

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
                    android.R.layout.simple_spinner_dropdown_item,routineSpinnerArray);

            routineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            menuSpinText.setAdapter(routineAdapter);
            menuSpinText.setSelected(false);  // must
            menuSpinText.setSelection(0,false);
            menuSpinText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("parent value :", String.valueOf(parent.getSelectedItem()));
                    Log.i("list1 id :", String.valueOf(listId.get(position)));
                    Log.i("parent getting id :", String.valueOf(parent.getSelectedItemId()));
                    currentRoutineID = listId.get(position); // set the current routine's ID according to the selected routine
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // probably method to setup default value for this part
                }
            });
        });
    }

    public void buttonClicked(View view) {


        progressBar = findViewById(R.id.progress_bar);
        timerTextView = findViewById(R.id.countDownTextView);

//         get routine from the the id
//         then get periods array
//         iterate over the different periods using countdown timer
        if(currentRoutineID==0){
            // toast message please select the routine first
            Toast.makeText(Home.this, "Please select the routine First!",
                    Toast.LENGTH_LONG).show();
        }
        else{
                homeViewModel.get(currentRoutineID).observe(this, routine -> {
                            pr = new ArrayList<Period>();
//                pr = routine.getPeriods(); // populate the Periods Array List
                            totalPeriods = pr.size();
                            // i want total time for studyperiods
                            int MAX_NB_ROUNDS_VALUE = pr.size();
                            for (Period i : pr) {
                                //j++;
//                    //Log.d("This loop time ", String.valueOf(j));
//                    Log.d("TAG","Period Position: "+i.getPosition());
//                    Log.d("TAG","Period StudyMinutes : "+i.getStudyMinutes());
//                    Log.d("TAG","Period BreakMinutes: "+i.getBreakMinutes());
//                    Log.d("TAG","Period StudytimeInHoursnMin: "+i.getStudyTimeInHoursAndMinutes());
//                    // get total times of all the periods -- and set that to max of progres bar
//                    //progressBar.setMax(100)
//                    //countDownTimer.count
//                    CountDownTimer countDownTimer =  new CountDownTimer(i.getStudyMinutes()*60*1000,1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            updateTimer((int)millisUntilFinished/1000);
//                            int prog = (int) millisUntilFinished/1000;
//                            progressBar.setProgress(prog);
//                            //progressBar. .Increment(1);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            if( mNbOfRounds <= MAX_NB_ROUNDS_VALUE )
//                            {
//
//                                Handler handler=new Handler();
//                                handler.postDelayed(new Runnable() {
//
//                                    @Override
//                                    public void run() {start(); }},1000);
//
//                            }
//                            //Don't forget to increment the nb of rounds:
//                            mNbOfRounds+= 1;
//                            Log.i("On finish","Timer for "+i.getStudyMinutes());
//                            //progressBar.setProgress((int) 100/totalPeriods);
//                        }

//                    }.start();
//
//                }
//            });


//
                                //progressBar.setMax(100);
                                // divide the progress so that it is a sum of study period intervals
                                // get the total length of periods array like how many
                                // arrange or sort by priority value


//        // try to iterate over the elements


//        homeViewModel.getAllRoutines().observe(this, routines ->{
//            for(Routine routine: routines){
//                Log.d("TAG","onButtonClicked ID = "+routine.getId());
//                Log.d("TAG","onButtonClicked TITLE = "+routine.getTitle());
//                Log.d("TAG","onButtonClicked Total time = "+routine.getTotalTimeInHoursAndMinutes());
//                Log.d("TAG","onButtonClicked All periods = "+routine.getPeriods());
//            }
//        });

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

                                //progressBar.setProgress(50);
//        }

                }
                });
        }
    }

//    public void updateTimer (int secondsLeft){
//        int minutes = secondsLeft/60;
//        int seconds = secondsLeft - (minutes*60);
//        String secondString = Integer.toString(seconds);
//        if(secondString.equals("0")){
//            secondString = "00";
//        }
//
//        timerTextView.setText(Integer.toString(minutes)+":"+ Integer.toString(seconds));
//    }


                    private void updateProgress () {
                        progressBar.setProgress(progress);
                        // probably some other text
                    }

//    public class MyCount extends CountDownTimer {
//
//        public MyCount(long millisUntilFinished, long countDownInterval) {
//            super(millisUntilFinished, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            updateTimer((int)millisUntilFinished/1000);
//            int prog = (int) millisUntilFinished/1000;
//            progressBar.setProgress(prog);
//            //progressBar. .Increment(1);
//        }
//
//        @Override
//        public void onFinish() {
//            Log.i("On finish","Timer for "+i.getStudyMinutes());
//            //progressBar.setProgress((int) 100/totalPeriods);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//
//            millisUntilFinishedInt = millisUntilFinished;
//            seconds = millisUntilFinishedInt/1000;
//            milliseconds = millisUntilFinishedInt-(millisUntilFinishedInt/1000)*1000;
//            countDownTimer = "TIME: " + seconds + "." + milliseconds ;
//            text1.setText(countDownTimer);
//        }
//
//        @Override
//        public void onFinish() {
//            countDownTimer = "TIME'S UP!";
//            text1.setText(countDownTimer);
//            }
 //   }


    //Shared Prefs
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