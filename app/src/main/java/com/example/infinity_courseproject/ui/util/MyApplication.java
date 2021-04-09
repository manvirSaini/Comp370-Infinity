package com.example.infinity_courseproject.ui.util;

import android.app.Application;



public class MyApplication extends Application {


    private static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    public static SoundPoolUtil soundPoolUtil;

    public static int shengyin = 1;
    public static int chakan = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        soundPoolUtil = SoundPoolUtil.getInstance(singleton);

    }



}
