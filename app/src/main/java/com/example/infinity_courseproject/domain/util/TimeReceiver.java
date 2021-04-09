package com.example.infinity_courseproject.domain.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.infinity_courseproject.ui.activity.HomeActivity;

public class TimeReceiver extends BroadcastReceiver {

    HomeActivity homeActivity;

    @Override
    public void onReceive(Context context, Intent intent) {

        homeActivity = new HomeActivity();


    }
}
