package com.example.infinity_courseproject.domain.util;
// timeservice
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.domain.entity.Assignment;
import com.example.infinity_courseproject.ui.util.EventBusTag;
import com.example.infinity_courseproject.ui.viewModel.AssignmentViewModel;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service implements LifecycleOwner {
    private final String TAG = "TimeService";
    private Timer timer = null;

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"TimeService->onCreate");

        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);


        this.init();

        timer.schedule(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {


                new Handler(Looper.getMainLooper()).post(() -> sendTimeChangedBroadcast());


            }
        }, 1000,1000);
    }


    private void init(){
        timer = new Timer();
        Intent timeIntent = new Intent();
    }


    String oldTime = "--",
            nowTome = "123";
    private final ArrayList<Assignment> itemBeanList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private void sendTimeChangedBroadcast(){


        nowTome = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        if (!oldTime.equals(nowTome)) {
            oldTime = nowTome;



            AssignmentViewModel assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                    this.getApplication()).create(AssignmentViewModel.class);
            //get and observe routines
            LiveData<List<Assignment>> assignmentLiveData = assignmentViewModel.getAssignmentsOrderByDueTime();
            assignmentLiveData.observe( this, assignments -> {
                Log.v("----------", assignments.toString());
                itemBeanList.clear();
                for (int i=0;i<assignments.size();i++){
                    if (!(assignments.get(i).getDueTime() == null)) {
                        String tt = assignments.get(i).getDueTime() + "";
                        tt = tt.substring(0, 10) + " " +
                                DateUtil.getFullTime(assignments.get(i).getDueTime().getHour()) + ":" +
                                DateUtil.getFullTime(assignments.get(i).getDueTime().getMinute()) + ":" +
                                DateUtil.getFullTime(assignments.get(i).getDueTime().getSecond());
                        try {
                            String td = DateUtil.dateToStamp(tt);
                            long sy = Long.parseLong(td) - System.currentTimeMillis();



                            if (sy > 0) {
                                int day = (int) (sy / 1000 / 60 / 60 / 24);
                                int hour = (int) (sy / 1000 / 60 / 60 % 24);
                                int minute = (int) (sy / 1000 / 60 % 60);
                                int second = (int) (sy / 1000 % 60);
//                String dsd = (day + " days " + hour + " hous " + minute + " minutes " + second + " seconds ");
                                if (day == 0 && hour == 23 && minute == 59 && second == 59) {
                                    EventBus.getDefault().post(new EventBusTag(10,assignments.get(i).getTitle(),"The assignment is upcoming"));
                                }
                                if (day == 0 && hour == 0 && minute == 0 && second == 0) {
                                    EventBus.getDefault().post(new EventBusTag(10,assignments.get(i).getTitle(),"the assignment is now due"));
                                }
                            }




                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //init listview







            });



        }

        Log.v("-----------", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));




    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
       // AssignmentViewModel.getsInstance().getStatus().removeObservers(this);
        super.onDestroy();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }




    @Override
    public ComponentName startService(Intent service) {
        Log.i(TAG,"TimeService->startService");
        return super.startService(service);
    }




}