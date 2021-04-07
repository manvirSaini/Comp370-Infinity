package com.example.infinity_courseproject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.infinity_courseproject.home.Home;
import com.example.infinity_courseproject.roomDatabase.BaseActivity;
import com.example.infinity_courseproject.roomDatabase.MyApplication;
import com.example.infinity_courseproject.roomDatabase.SoundPoolUtil;
import com.example.infinity_courseproject.roomDatabase.ToastUtil;



public class PassengerActivity extends BaseActivity {


    TextView tv_0;

    TextView tv_1;

    //navigation drawer stuff
    static DrawerLayout drawer;
    TextView toolbarName;

    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_passenger);

        //initialize navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbarName = findViewById(R.id.toolbar_name);
        toolbarName.setText("Options");
    }

    @Override
    protected void initData() {
        tv_0 =findViewById(R.id.tv_0); tv_1 =findViewById(R.id.tv_1);
    }

    @Override
    protected void initListener() {
         tv_0.setText(MyApplication.chakan == 0 ? "ON" : "OFF");
        tv_1.setText(MyApplication.shengyin  == 0 ? "ON" : "OFF");
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

    //Navigation drawer function START:
    public void clickMenu(View view){
        openDrawer(drawer);
    }

    public static void openDrawer(DrawerLayout drawer) {

        drawer.openDrawer(GravityCompat.START);
    }

    public void clickIcon(View view){
        closeDrawer(drawer);

    }

    public static void closeDrawer(DrawerLayout drawer) {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void clickHome(View view){
        redirectActivity(this, Home.class);
    }

    public void clickAssignment(View view){ redirectActivity(this, AssignmentsActivity.class); }

    public void clickRoutine(View view){
        redirectActivity(this, RoutinesActivity.class);
    }

    public void clickCourse(View view){
        redirectActivity(this, CourseActivity.class);
    }

    public void clickSetting(View view){
        recreate();
    }


    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);

        closeDrawer(drawer);
    }
    //END of navigation drawer functions
}
