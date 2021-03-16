package com.example.infinity_courseproject.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.periods.Period;


import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    ProgressBar progressBar;
    TextView timerTextView;
    private Spinner  menuSpinText;
    private int progress = 0;
    private int totalPeriods = 0;
    ArrayList<Period> pr = new ArrayList<Period>(); //declare periods arraylist



    public void buttonClicked(View view) {

        //initialize view models
        homeViewModel = new ViewModelProvider.AndroidViewModelFactory(
                Home.this.getApplication()).create(HomeViewModel.class);

        progressBar = findViewById(R.id.progress_bar);
        timerTextView = findViewById(R.id.countDownTextView);

        Period p1 = new Period(1, 1, 1, 1);
        Period p2 = new Period(2, 1, 2, 2);
        Period p3 = new Period(3, 2, 1, 1);

        ArrayList<Period> arr = new ArrayList<Period>();
        arr.add(p1);
        arr.add(p2);
        arr.add(p3);

        boolean[] week = {false, true, false, true, false, false, true};

        Routine r = new Routine("Routine1", week, 14, 15, arr);

        //inserting routines
        //homeViewModel.deleteAll();
        // homeViewModel.insert(r);

//         get routine from the the id
//         then get periods array
//         iterate over the different periods using countdown timer


        homeViewModel.get(1).observe(this, routine -> {
            pr = routine.getPeriods(); // populate the Periods Array List

        });

        totalPeriods = pr.size();
        progressBar.setMax(100);
        // divide the progress so that it is a sum of study period intervals
        // get the total length of periods array like how many
        // arrange or sort by priority value


//        // try to iterate over the elements
        for(Period i :pr){

            Log.d("TAG","Period : "+i.getPosition());
            Log.d("TAG","Period : "+i.getStudyMinutes());
            Log.d("TAG","Period : "+i.getBreakMinutes());
            Log.d("TAG","Period : "+i.getStudyTimeInHoursAndMinutes());
            // get the countdown to start
            CountDownTimer countDownTimer =  new CountDownTimer(i.getStudyMinutes()*60*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished/1000);
                        progressBar.setProgress((int) millisUntilFinished*1000);
                }

                @Override
                public void onFinish() {
                    Log.i("On finish","Timer for "+i.getStudyMinutes());
                        progressBar.setProgress((int) 100/totalPeriods);
                }
            }.start();

    }

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

}

    public void updateTimer (int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes*60);
        String secondString = Integer.toString(seconds);
        if(secondString.equals("0")){
            secondString = "00";
        }

        timerTextView.setText(Integer.toString(minutes)+":"+ Integer.toString(seconds));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // On create I want to create and set up spinner drop down menu
        menuSpinText = findViewById(R.id.spinner1);
        // set up database as well in this case

       String [] routines = {"Routine 1","Routine 2","Routine 3","Routine 4"};
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,routines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSpinText.setAdapter(adapter);
        menuSpinText.setSelected(false);  // must
        menuSpinText.setSelection(0,false);
        menuSpinText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("parent id :", String.valueOf(parent.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // probably method to setup default value for this part
            }
        });

    }

    private void updateProgress(){
        progressBar.setProgress(progress);
        // probably some other text
    }

}

