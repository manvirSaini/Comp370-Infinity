package com.example.infinity_courseproject.roomDatabase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.home.Home;
import com.gsls.gt.GT;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class BaseActivity extends AppCompatActivity {
    public Context myContext;
    public Activity myActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
        myContext = this;
        myActivity = this;
        setContent();
        initData();
        initListener();
    }

    protected void setContent() {}

    protected  void initData(){};

    protected  void initListener(){};

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBus_Tag event) {

        if(event.getTag()==10)
        {
            new GT.GT_Notification(this)
                    .setNotifyId(0x1)
                    .setChanelId(getClass().getName())
                    .setChanelDescription("GTnotification")
                    .setChanelName("GT name")
                    .sendingNotice(R.drawable.ic_launcher_background,event.getTitle(),event.getContent(),0,true,true, Home.class);

        }



    }
}