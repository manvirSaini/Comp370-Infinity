package com.example.infinity_courseproject.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentViewModel;
import com.example.infinity_courseproject.base.BaseActivity;
import com.example.infinity_courseproject.base.BaseRecyclerAdapter;
import com.example.infinity_courseproject.base.DateUtil;
import com.example.infinity_courseproject.base.EventBus_Tag;
import com.example.infinity_courseproject.base.MyRVViewHolder;
import com.example.infinity_courseproject.courses.CourseViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationAtivity extends BaseActivity {


    @BindView(R.id.lv)
    RecyclerView lv;


    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.layout0)
    LinearLayout layout0;

    private AssignmentViewModel assignmentViewModel;
    private CourseViewModel courseViewModel;
    private LiveData<List<Assignment>> assignmentLiveData;

    private List<Assignment> itemBeanList = new ArrayList();
    private MyAdapter myAdapter;


    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {

        @SuppressLint("WrongConstant")
        LinearLayoutManager manager = new LinearLayoutManager(NotificationAtivity.this, LinearLayoutManager.VERTICAL, false);
        if (null == manager)
            return;
        lv.setLayoutManager(manager);
        myAdapter = new MyAdapter(NotificationAtivity.this, itemBeanList, R.layout.item_tongzhi);
        lv.setAdapter(myAdapter);


    }

    @Override
    protected void initListener() {

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    class MyAdapter extends BaseRecyclerAdapter<Assignment> {

        private TextView tv_name, tv_content, tv_time;
        private int selPosi;

        public void setSelPosi(int selPosi) {
            this.selPosi = selPosi;
        }

        public MyAdapter(Context context, List<Assignment> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final Assignment bean, int position) {
            if (null == holder || null == bean)
                return;
            //init view

            tv_name = holder.getView(R.id.tv_name);
            tv_content = holder.getView(R.id.tv_content);
            tv_time = holder.getView(R.id.tv_time);

            TextView tv_time2 = holder.getView(R.id.tv_time2);

            //set view

            tv_name.setText(bean.getTitle());
            tv_content.setText(bean.getDescription());
//            String tt= bean.getDueTime().get+"";
            Log.v("----------111", bean.getDueTime() + "");
            String tt = bean.getDueTime() + "";
            tt = tt.substring(0, 10) + " " +
                    DateUtil.getFullTime(bean.getDueTime().getHour()) + ":" +
                    DateUtil.getFullTime(bean.getDueTime().getMinute()) + ":" +
                    DateUtil.getFullTime(bean.getDueTime().getSecond());
            String td = "";
            try {
                td = DateUtil.dateToStamp(tt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.v("----------222", tt + "");

            Long sy = Long.parseLong(td) - System.currentTimeMillis();
            if (sy > 0) {
                tv_time.setText("Remaining Time：" + DateUtil.getStringDate((sy * 1)));
                Log.v("----------333" + position, tv_time.getText().toString());
                int day = (int) (sy / 1000 / 60 / 60 / 24);
                int hour = (int) (sy / 1000 / 60 / 60 % 24);
                int minute = (int) (sy / 1000 / 60 % 60);
                int second = (int) (sy / 1000 % 60);
//                String dsd = (day + " 天 " + hour + " 时 " + minute + " 分 " + second + " 秒 ");
                if (day == 0 && hour == 1 && minute == 0 && second == 0) {
                    Log.v("----------11113333" + position, tv_time.getText().toString());
                    Toast.makeText(NotificationAtivity.this, bean.getTitle() + "\nThere is one hour left before the deadline", Toast.LENGTH_LONG).show();
                }
            } else
                tv_time.setText("Overdue");


            tv_time2.setText("Due date：" + DateUtil.stampToDate(td));

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBus_Tag event) {
        switch (event.getTag()) {
            case 1:
                //initialize viewmodels
                assignmentViewModel = new ViewModelProvider.AndroidViewModelFactory(
                        this.getApplication()).create(AssignmentViewModel.class);
                //get and observe routines
                assignmentLiveData = assignmentViewModel.getAssignmentsOrderByDueTime();
                assignmentLiveData.observe(this, new Observer<List<Assignment>>() {
                    @Override
                    public void onChanged(List<Assignment> assignments) {
                        Log.v("----------", assignments.toString());
                        //init listview
                        itemBeanList.clear();
                        itemBeanList.addAll(assignments);
                        myAdapter.notifyDataSetChanged();

                    }
                });
                break;

        }
    }


    boolean isShow = true;
    Timer timer;


    String oldTime = "--", nowTome = "123";


    private void startCycle() {

        if (timer == null)
            timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isShow) {
                    return;
                }
                nowTome = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
                if (!oldTime.equals(nowTome)) {
                    oldTime = nowTome;

                    startCycle();
                    EventBus.getDefault().post(new EventBus_Tag(1));
                }

                Log.v("-----------", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            }
        }, 0, 1000);//

    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        startCycle();
    }


}
