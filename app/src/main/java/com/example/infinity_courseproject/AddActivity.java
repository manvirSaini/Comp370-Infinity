package com.example.infinity_courseproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infinity_courseproject.base.EventBus_Tag;
import com.example.infinity_courseproject.courses.Course2;

import org.greenrobot.eventbus.EventBus;

public class AddActivity extends AppCompatActivity {
    TextView tv_done, tv_cancel, tv_title;
    EditText et_0, et_1, et_2;
    Course2 bean;
    int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //init view
        et_0 = findViewById(R.id.et_0);
        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        tv_done = findViewById(R.id.tv_done);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_title = findViewById(R.id.tv_title);
        //set view
        posi = getIntent().getIntExtra("pos", 0);
        bean = (Course2) getIntent().getSerializableExtra("bean");
        if (bean == null) {
            tv_title.setText("add");
        } else {
            tv_title.setText("modify");
            et_0.setText(bean.getTitle());
            et_1.setText(bean.getProfessor());
            et_2.setText(bean.getDescription());
        }

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        StrUtil.isEmpty(et_0.getText().toString()) ||
                                StrUtil.isEmpty(et_1.getText().toString()) ||
                                StrUtil.isEmpty(et_2.getText().toString())
                ) {
                    Toast.makeText(AddActivity.this, " Incomplete content ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (bean == null) {
                    Course2 tempBean = new Course2();
                    tempBean.setmId(System.currentTimeMillis() + "");
                    tempBean.setTitle(et_0.getText().toString());
                    tempBean.setProfessor(et_1.getText().toString());
                    tempBean.setDescription(et_2.getText().toString());
                    EventBus.getDefault().post(new EventBus_Tag(2, tempBean));
                } else {//updata
                    bean.setTitle(et_0.getText().toString());
                    bean.setProfessor(et_1.getText().toString());
                    bean.setDescription(et_2.getText().toString());
                    EventBus.getDefault().post(new EventBus_Tag(3, bean, posi));
                }
                finish();


            }
        });

    }
}