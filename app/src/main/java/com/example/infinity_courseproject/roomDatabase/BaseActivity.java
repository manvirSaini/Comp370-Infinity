package com.example.infinity_courseproject.roomDatabase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context myContext;
    public Activity myActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        myActivity = this;
        setContent();
        initData();
        initListener();
    }

    protected void setContent() {

    }

    protected abstract void initData();

    protected abstract void initListener();

}