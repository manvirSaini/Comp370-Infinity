package com.example.infinity_courseproject;

import android.view.View;
import android.widget.TextView;

import com.example.infinity_courseproject.roomDatabase.BaseActivity;
import com.example.infinity_courseproject.roomDatabase.MyApplication;
import com.example.infinity_courseproject.roomDatabase.SoundPoolUtil;
import com.example.infinity_courseproject.roomDatabase.ToastUtil;



public class PassengerActivity extends BaseActivity {


    TextView tv_0;

    TextView tv_1;


    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_passenger);


    }

    @Override
    protected void initData() {
        tv_0 =findViewById(R.id.tv_0); tv_1 =findViewById(R.id.tv_1);
    }

    @Override
    protected void initListener() {
         tv_0.setText(MyApplication.chakan == 0 ? "OFF" : "ON");
        tv_1.setText(MyApplication.shengyin  == 0 ? "OFF" : "ON");
        tv_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.chakan= (MyApplication.chakan == 0 ? 1 : 0);
                ToastUtil.showToast(myActivity, "SET SUCCESS");
                SoundPoolUtil.isplay();
                finish();
            }
        });
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.shengyin= (MyApplication.shengyin == 0 ? 1 : 0);
                ToastUtil.showToast(myActivity, "SET SUCCESS");
                SoundPoolUtil.isplay();
                finish();
            }
        });



    }
}
