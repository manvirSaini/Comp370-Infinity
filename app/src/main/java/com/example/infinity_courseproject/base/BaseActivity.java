package com.example.infinity_courseproject.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * @Author: Paper
 * time :2019/9/4  10:26
 * desc:
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


    private Fragment currentV4Fragment;
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentV4Fragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }

        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }

        if (currentV4Fragment != null && currentV4Fragment.isVisible()) {
            transaction.hide(currentV4Fragment);
//            transaction.remove(currentV4Fragment);
        }

        currentV4Fragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }

}
