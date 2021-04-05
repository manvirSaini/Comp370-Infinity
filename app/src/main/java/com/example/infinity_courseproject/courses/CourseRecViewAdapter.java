package com.example.infinity_courseproject.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;

import java.util.List;
import java.util.Objects;

public class CourseRecViewAdapter extends RecyclerView.Adapter<CourseRecViewAdapter.ViewHolder> {
    private final OnCourseClickListener onCourseClickListener;
    private final List<Course> courseList;
    private Context context;

    public CourseRecViewAdapter(List<Course> courseList, Context context,
                                    OnCourseClickListener onCourseClickListener) {
        this.courseList = courseList;
        this.context = context;
        this.onCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public CourseRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_recyclerview_item, parent,false);

        return new ViewHolder(view, onCourseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecViewAdapter.ViewHolder holder, int position) {
        Course course = Objects.requireNonNull(courseList).get(position);
        holder.courseTitle.setText(course.getTitle());

        String professor = course.getProfessor();
        if (professor !=  null)
            holder.professorName.setText(course.getProfessor());
        else
            holder.professorName.setText("");
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(courseList).size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CourseRecViewAdapter.OnCourseClickListener onCourseClickListener;
        private final TextView courseTitle;
        private final TextView professorName;

        public ViewHolder(@NonNull View itemView, CourseRecViewAdapter.OnCourseClickListener onCourseClickListener) {
            super(itemView);
            this.onCourseClickListener = onCourseClickListener;
            courseTitle = itemView.findViewById(R.id.course_title_textview);
            professorName = itemView.findViewById(R.id.course_professor_name_textview);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onCourseClickListener.onCourseLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onCourseClickListener.onCourseClick(getAdapterPosition());
        }

    }

    public interface OnCourseClickListener {
        void onCourseClick(int position);
        void onCourseLongClick(int position);
    }

}
