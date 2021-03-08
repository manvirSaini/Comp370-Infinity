package com.example.infinity_courseproject.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.List;

public class Home extends AppCompatActivity {

    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;
    TextView timerTextView;
    TextView routineTextView;
    private Spinner  menuSpinText;
    private int progress = 0;



    public void buttonClicked(View view){
        CountDownTimer countDownTimer =  new CountDownTimer(progress*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                Log.i("On finish","Timer Finished!");
            }
        }.start();
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

        menuSpinText = findViewById(R.id.spinner1);
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

            }
        });
//        routTextView = findViewById(R.id.textView);
//        //initialize viewmodels
//        routineViewModel = new ViewModelProvider.AndroidViewModelFactory(
//            this.getApplication()).create(RoutineViewModel.class);
//        periodViewModel = new ViewModelProvider.AndroidViewModelFactory(
//                this.getApplication()).create(PeriodViewModel.class);
//
//        //timerSeekbar = findViewById(R.id.timerSeekBar);
//        timerTextView = findViewById(R.id.countDownTextView);
//
//        routineViewModel.getAllRoutines().observe(this, new Observer<List<Routine>>() {
//            @Override
//            public void onChanged(List<Routine> routines) {
//                if (!routines.isEmpty()) {
//                    routTextView.setText(routines.get(0).getTitle());
//                }
//            }
//        });

//        periodViewModel.getAllPeriods().observe(this, new Observer<List<Period>>() {
//            @Override
//            public void onChanged(List<Period> periods) {
//                if (!periods.isEmpty()) {
//                    textView3.setText(String.valueOf(periods.get(0).getPosition()));
//                }
//            }
//        });

        //timerSeekbar.setMax(600);
       // timerSeekbar.setProgress(30);

//        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                updateTimer(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }
}

