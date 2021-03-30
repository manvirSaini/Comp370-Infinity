package com.example.infinity_courseproject.base;


import android.app.Application;




//import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {

    public static final String TAG = "-----------";
    public static final String TAG2 = "++++++++++";
    public static final String TAG_finger = "finger-----------";
    public static String imgPath;//拍照的img路径
    private static MyApplication singleton;

    public static String curUser;

    //
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;


    }





}
