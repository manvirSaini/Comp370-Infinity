package com.example.infinity_courseproject.assignments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.AssignmentsActivity;
import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.RoutinesActivity;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseViewModel;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class AssignmentRecViewAdapter extends RecyclerView.Adapter<AssignmentRecViewAdapter.ViewHolder> {

    private final OnAssignmentClickListener onAssignmentClickListener;
    private List<Assignment> assignmentList;
    private AssignmentViewModel assignmentViewModel;
    private CourseViewModel courseViewModel;
    private Context context;

    public AssignmentRecViewAdapter(List<Assignment> assignmentList, Context context,
                                    AssignmentViewModel assignmentViewModel, CourseViewModel courseViewModel,
                                    AssignmentRecViewAdapter.OnAssignmentClickListener onAssignmentClickListener) {
        this.assignmentList = assignmentList;
        this.context = context;
        this.assignmentViewModel = assignmentViewModel;
        this.courseViewModel = courseViewModel;
        this.onAssignmentClickListener = onAssignmentClickListener;
    }

    @NonNull
    @Override
    public AssignmentRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments_recyclerview_item, parent,false);

        return new AssignmentRecViewAdapter.ViewHolder(view, onAssignmentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentRecViewAdapter.ViewHolder holder, int position) {
        Assignment assignment = Objects.requireNonNull(assignmentList).get(position);
        if (filterRoutine(assignment)) {
            holder.recyclerItem.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        else {
            holder.assignmentName.setText(assignment.getTitle());
            Integer courseId = assignment.getCourseId();
            if (courseId != null) {
                String title = courseViewModel.getImmediate(courseId).getTitle();
                holder.courseTitle.setText(title);
            }
            if (assignment.getDueTime() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                holder.dueDate.setText(assignment.getDueTime().format(formatter));
            }
            else
                holder.dueDateText.setVisibility(View.INVISIBLE);

            holder.status.setText(assignment.stringifyStatus());


        }



    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(assignmentList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnAssignmentClickListener onAssignmentClickListener;
        private final ViewGroup recyclerItem;
        private final TextView assignmentName;
        private final TextView courseTitle;
        private final TextView dueDateText;
        private final TextView dueDate;
        private final TextView status;


        public ViewHolder(@NonNull View itemView, OnAssignmentClickListener onAssignmentClickListener) {
            super(itemView);
            this.onAssignmentClickListener = onAssignmentClickListener;
            recyclerItem = itemView.findViewById(R.id.assignment_recycler_item_container);
            assignmentName = itemView.findViewById(R.id.assignment_recycler_item_label_text);
            courseTitle = itemView.findViewById(R.id.assignment_recycler_item_course_title);
            dueDateText = itemView.findViewById(R.id.assignment_recycler_item_due_date_text);
            dueDate = itemView.findViewById(R.id.assignment_recycler_item_due_date);
            status = itemView.findViewById(R.id.assignment_recycler_item_status_text);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onAssignmentClickListener.onAssignmentLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onAssignmentClickListener.onAssignmentClick(getAdapterPosition());
        }

    }

    public interface OnAssignmentClickListener {
        void onAssignmentClick(int position);
        void onAssignmentLongClick(int position);
    }

    private boolean filterRoutine(Assignment a) {
        boolean applyFilter = false;
        Assignment.Status status = a.determineStatus();
        AssignmentsActivity.AssignmentFilterBy filter = AssignmentsActivity.getFilter();
        switch (filter) {
            case ALL:
                break;
            case COMPLETE:
                if (!status.equals(Assignment.Status.COMPLETE))
                    applyFilter = true;
                break;
            case NEUTRAL:
                if (!status.equals(Assignment.Status.NEUTRAL))
                    applyFilter = true;
                break;
            case UPCOMING:
                if (!status.equals(Assignment.Status.UPCOMING))
                    applyFilter = true;
                break;
            case OVERDUE:
                if (!status.equals(Assignment.Status.OVERDUE))
                    applyFilter = true;
                break;
        }
        return applyFilter;
    }


}
