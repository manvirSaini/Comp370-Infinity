package com.example.infinity_courseproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.infinity_courseproject.home.Home;
import com.example.infinity_courseproject.roomDatabase.SoundPoolUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationDrawerActivity extends AppCompatActivity {

    protected static DrawerLayout drawer;

    //TODO: Delete class for good copy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer3);

        drawer = findViewById(R.id.drawer_layout);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                Intent intent;
//
//                switch(item.getItemId()) {
//
//                    case R.id.nav_home:
//                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.nav_assignment:
//                        intent = new Intent(NavigationDrawerActivity.this, AssignmentsActivity.class);
//                        NavigationDrawerActivity.this.startActivity(intent);
//                        break;
//
//                    case R.id.nav_routines:
//                        intent = new Intent(NavigationDrawerActivity.this, RoutinesActivity.class);
//                        NavigationDrawerActivity.this.startActivity(intent);
//                        break;
//                }
//
//                drawer.closeDrawer(GravityCompat.START);
//
//                return true;
//            }
//        });
        findViewById(R.id.layout_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtil.isplay();
                startActivity(new Intent(NavigationDrawerActivity.this, PassengerActivity.class));
            }
        });
    }

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
        recreate();
    }

    public void clickAssignment(View view){
        redirectActivity(this, AssignmentsActivity.class);
    }

    public void clickRoutine(View view){
        redirectActivity(this, RoutinesActivity.class);
    }


    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);

        closeDrawer(drawer);
    }
}