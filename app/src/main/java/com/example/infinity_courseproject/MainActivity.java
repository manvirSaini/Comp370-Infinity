package com.example.infinity_courseproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.infinity_courseproject.Base.BaseRecyclerAdapter;
import com.example.infinity_courseproject.Base.MyRVViewHolder;
import com.example.infinity_courseproject.courses.CourseView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView lv;
    private List<CourseView> itemBeanList = new ArrayList();
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        lv = findViewById(R.id.lv);
        @SuppressLint("WrongConstant")
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        if (null == manager)
            return;
        lv.setLayoutManager(manager);
        myAdapter = new MyAdapter(MainActivity.this, itemBeanList, R.layout.item_meal);
        lv.setAdapter(myAdapter);
        update();
        myAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

                itemBeanList.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });

    }

    void update() {

        if (itemBeanList.size() == 0) {
            itemBeanList.add(new CourseView("COMP370", "Opeyemi Adesina", "Software Engineering", 1));
            itemBeanList.add(new CourseView("COMP370", "Opeyemi Adesina", "Software Engineering", 2));
            itemBeanList.add(new CourseView("COMP370", "Opeyemi Adesina", "Software Engineering", 3));
            itemBeanList.add(new CourseView("COMP370", "Opeyemi Adesina", "Software Engineering", 4));
            itemBeanList.add(new CourseView("COMP370", "Opeyemi Adesina", "Software Engineering", 5));
        }

        myAdapter.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    class MyAdapter extends BaseRecyclerAdapter<CourseView> {


        public MyAdapter(Context context, List<CourseView> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final CourseView bean, int position) {
            if (null == holder || null == bean)
                return;
            //init view
            TextView tv_1 = holder.getView(R.id.tv_1);
            TextView tv_2 = holder.getView(R.id.tv_2);
            TextView tv_3 = holder.getView(R.id.tv_3);

            //set view
            tv_1.setText(bean.getTitle());
            tv_2.setText(bean.getProfessor());
            tv_3.setText(bean.getDescription());

        }
    }


}

