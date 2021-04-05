//package com.example.infinity_courseproject;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.infinity_courseproject.roomDatabase.BaseRecyclerAdapter;
//import com.example.infinity_courseproject.roomDatabase.EventBus_Tag;
//import com.example.infinity_courseproject.roomDatabase.MyRVViewHolder;
//import com.example.infinity_courseproject.courses.Course2;
//import com.example.infinity_courseproject.home.Home;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CourseActivity extends Home {
//    RecyclerView lv;
//
//    private List<Course2> itemBeanList = new ArrayList();
//    private MyAdapter myAdapter;
//
//    //navigation drawer stuff
//    static DrawerLayout drawer;
//    TextView toolbarName;
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.course_main);
//        EventBus.getDefault().unregister(this);
//        EventBus.getDefault().register(this);
//
//        //initialize navigation drawer
//        drawer = findViewById(R.id.drawer_layout);
//        toolbarName = findViewById(R.id.toolbar_name);
//        toolbarName.setText("Courses");
//
//        lv = findViewById(R.id.lv);
//
//        @SuppressLint("WrongConstant")
//        LinearLayoutManager manager = new LinearLayoutManager(CourseActivity.this, LinearLayoutManager.VERTICAL, false);
//        if (null == manager)
//            return;
//        lv.setLayoutManager(manager);
//        myAdapter = new MyAdapter(CourseActivity.this, itemBeanList, R.layout.item_meal);
//        lv.setAdapter(myAdapter);
//        EventBus.getDefault().post(new EventBus_Tag(1));
//
//        myAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View view, int position) {
////
//                itemBeanList.remove(position);
//                myAdapter.notifyDataSetChanged();
//
//            }
//        });
//
//        findViewById(R.id.tvadd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                new CourseDialog(MainActivity.this, null, 0).showDialog();
//                Intent intent = new Intent(CourseActivity.this,AddActivity.class);
////                intent.putExtra("bean",null);
//                intent.putExtra("pos",0);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(EventBus_Tag event) {
//        switch (event.getTag()) {
//            case 1:
//
//                if (itemBeanList.size() == 0) {
//                    for (int i = 0; i < 5; i++) {
//                        Course2 course2 = new Course2();
//                        String c0 = "", c1 = "", c2 = "", c3 = "";
//                        switch (i) {
//                            case 0:
//                                c0 = System.currentTimeMillis() + "";
//                                c1 = "COMP370";
//                                c2 = "Opeyemi Adesina";
//                                c3 = "Software Engineering";
//                                break;
//                            case 1:
//                                c0 = System.currentTimeMillis() + "";
//                                c1 = "COMP371";
//                                c2 = "Opeyemi Adesina";
//                                c3 = "Software Engineering";
//                                break;
//                            case 2:
//                                c0 = System.currentTimeMillis() + "";
//                                c1 = "COMP372";
//                                c2 = "Opeyemi Adesina";
//                                c3 = "Software Engineering";
//                                break;
//                            case 3:
//                                c0 = System.currentTimeMillis() + "";
//                                c1 = "COMP373";
//                                c2 = "Opeyemi Adesina";
//                                c3 = "Software Engineering";
//                                break;
//                            default:
//                                c0 = System.currentTimeMillis() + "";
//                                c1 = "COMP374";
//                                c2 = "Opeyemi Adesina";
//                                c3 = "Software Engineering";
//                                break;
//                        }
//                        course2.setmId(c0);
//                        course2.setTitle(c1);
//                        course2.setProfessor(c2);
//                        course2.setDescription(c3);
//
//                        itemBeanList.add(course2);
//                    }
//
//                }
//                myAdapter.notifyDataSetChanged();
//                break;
//            case 2:
//                itemBeanList.add((Course2) event.getObject());
//                myAdapter.notifyDataSetChanged();
//                break;
//            case 3:
//                itemBeanList.remove(event.getPosition());
//                itemBeanList.add((Course2) event.getObject());
//                myAdapter.notifyDataSetChanged();
//                break;
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    class MyAdapter extends BaseRecyclerAdapter<Course2> {
//
//
//        public MyAdapter(Context context, List<Course2> datas, int layoutId) {
//            super(context, datas, layoutId, onItemClickListener);
//        }
//
//        @Override
//        public void setView(MyRVViewHolder holder, final Course2 bean, int position) {
//            if (null == holder || null == bean)
//                return;
//            //init view
//            TextView tv_1 = holder.getView(R.id.tv_1);
//            TextView tv_2 = holder.getView(R.id.tv_2);
//
//            LinearLayout layout_item = holder.getView(R.id.layout_item);
//
//            //set view
//            tv_1.setText(bean.getTitle());
//            tv_2.setText(bean.getProfessor());
//
//
//            layout_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    new CourseDialog(MainActivity.this, bean, position).showDialog();
//                    Intent intent = new Intent(CourseActivity.this,AddActivity.class);
//                    intent.putExtra("bean",bean);
//                    intent.putExtra("pos",position);
//                    startActivity(intent);
//                }
//            });
//        }
//    }
//
//    //Navigation drawer function START:
//    public void clickMenu(View view){
//        openDrawer(drawer);
//    }
//
//    public static void openDrawer(DrawerLayout drawer) {
//
//        drawer.openDrawer(GravityCompat.START);
//    }
//
//    public void clickIcon(View view){
//        closeDrawer(drawer);
//
//    }
//
//    public static void closeDrawer(DrawerLayout drawer) {
//
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }
//    }
//
//    public void clickHome(View view){
//        redirectActivity(this, Home.class);
//    }
//
//    public void clickAssignment(View view){
//        redirectActivity(this, AssignmentsActivity.class);
//    }
//
//    public void clickRoutine(View view){
//        redirectActivity(this, RoutinesActivity.class);
//    }
//
//    public void clickCourse(View view){
//        recreate();
//    }
//
//    //TODO: Make sure redirects go to desired activity
//    public void clickSetting(View view){
//        redirectActivity(this, Home.class);
//    }
//
//    public static void redirectActivity(Activity activity, Class aclass) {
//        Intent intent = new Intent(activity, aclass);
//        //Set flag
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //start activity
//        activity.startActivity(intent);
//
//        closeDrawer(drawer);
//    }
//    //END of navigation drawer functions
//}
//