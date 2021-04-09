package com.example.infinity_courseproject.ui.recyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.domain.entity.Assignment;
import com.example.infinity_courseproject.ui.activity.AssignmentActivity;
import com.example.infinity_courseproject.ui.util.OnItemClickListener;
import com.example.infinity_courseproject.ui.viewModel.CourseViewModel;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class AssignmentRecViewAdapter extends RecyclerView.Adapter<AssignmentRecViewAdapter.ViewHolder> {

    private final OnItemClickListener onAssignmentClickListener;
    private final List<Assignment> assignmentList;
    private final CourseViewModel courseViewModel;

    public AssignmentRecViewAdapter(List<Assignment> assignmentList,
                                    CourseViewModel courseViewModel,
                                    OnItemClickListener onAssignmentClickListener) {
        this.assignmentList = assignmentList;
        this.courseViewModel = courseViewModel;
        this.onAssignmentClickListener = onAssignmentClickListener;
    }

    @NonNull
    @Override
    public AssignmentRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments_recyclerview_item, parent,false);

        return new ViewHolder(view, onAssignmentClickListener);
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
            else
                holder.courseTitle.setText("");

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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnItemClickListener onAssignmentClickListener;
        private final ViewGroup recyclerItem;
        private final TextView assignmentName;
        private final TextView courseTitle;
        private final TextView dueDateText;
        private final TextView dueDate;
        private final TextView status;


        public ViewHolder(@NonNull View itemView, OnItemClickListener onAssignmentClickListener) {
            super(itemView);
            this.onAssignmentClickListener = onAssignmentClickListener;
            recyclerItem = itemView.findViewById(R.id.assignment_recycler_item_container);
            assignmentName = itemView.findViewById(R.id.assignment_recycler_item_label_text);
            courseTitle = itemView.findViewById(R.id.assignment_recycler_item_course_title);
            dueDateText = itemView.findViewById(R.id.assignment_recycler_item_due_date_text);
            dueDate = itemView.findViewById(R.id.assignment_recycler_item_due_date);
            status = itemView.findViewById(R.id.assignment_recycler_item_status_text);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(v -> {
                onAssignmentClickListener.onItemLongClick(getAdapterPosition());
                return false;
            });
        }

        @Override
        public void onClick(View v) {
            onAssignmentClickListener.onItemClick(getAdapterPosition());
        }

    }

    private boolean filterRoutine(Assignment a) {
        boolean applyFilter = false;
        Assignment.Status status = a.determineStatus();
        AssignmentActivity.AssignmentFilterBy filter = AssignmentActivity.getFilter();
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
