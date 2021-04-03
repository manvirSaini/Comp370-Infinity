//package com.example.infinity_courseproject;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.graphics.drawable.BitmapDrawable;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.infinity_courseproject.base.BaseDialog;
//import com.example.infinity_courseproject.base.EventBus_Tag;
//import com.example.infinity_courseproject.courses.Course2;
//
//import org.greenrobot.eventbus.EventBus;
//
//
//public class CourseDialog extends BaseDialog {
//
//    TextView tv_commit, tv_cancel, tv_title;
//    EditText et_0, et_1, et_2;
//    Course2 bean;
//    int posi;
//
//    public CourseDialog(Activity activity, Course2 bean, int posi) {
//        super(activity);
//        this.bean = bean;
//        this.posi = posi;
//    }
//
//    @Override
//    public void initDialogEvent(Window window) {
//        window.setContentView(R.layout.dialog_course);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        //init view
//        et_0 = window.findViewById(R.id.et_0);
//        et_1 = window.findViewById(R.id.et_1);
//        et_2 = window.findViewById(R.id.et_2);
//        tv_commit = window.findViewById(R.id.tv_commit);
//        tv_cancel = window.findViewById(R.id.tv_cancel);
//        tv_title = window.findViewById(R.id.tv_title);
//        //set view
//        if (bean == null) {
//            tv_title.setText("add");
//        } else {
//            tv_title.setText("modify");
//            et_0.setText(bean.getTitle());
//            et_1.setText(bean.getProfessor());
//            et_2.setText(bean.getDescription());
//        }
//
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        tv_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (
//                        StrUtil.isEmpty(et_0.getText().toString()) ||
//                                StrUtil.isEmpty(et_1.getText().toString()) ||
//                                StrUtil.isEmpty(et_2.getText().toString())
//                ) {
//                    Toast.makeText(activity, " Incomplete content ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (bean == null) {
//                    Course2 tempBean = new Course2();
//                    tempBean.setmId(System.currentTimeMillis() + "");
//                    tempBean.setTitle(et_0.getText().toString());
//                    tempBean.setProfessor(et_1.getText().toString());
//                    tempBean.setDescription(et_2.getText().toString());
//                    EventBus.getDefault().post(new EventBus_Tag(2, tempBean));
//                } else {//updata
//                    bean.setTitle(et_0.getText().toString());
//                    bean.setProfessor(et_1.getText().toString());
//                    bean.setDescription(et_2.getText().toString());
//                    EventBus.getDefault().post(new EventBus_Tag(3, bean, posi));
//                }
//                dialog.dismiss();
//
//
//            }
//        });
//
//    }
//
//
//    @Override
//    public void showDialog() {
//        dialog = new AlertDialog.Builder(activity).create();
//
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnKeyListener(null);
//        dialog.show();
//
//        Window window = dialog.getWindow();
//        window.setLayout((int) (ScreenUtil.getScreenWidth(activity) * 0.8), (int) (ScreenUtil.getScreenHeight(activity) * 0.5));
//        window.setGravity(Gravity.CENTER);
//
//        window.setBackgroundDrawable(new BitmapDrawable());
//        initDialogEvent(window);
//    }
//}
//
