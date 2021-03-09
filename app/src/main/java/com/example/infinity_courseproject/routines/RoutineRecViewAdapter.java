package com.example.infinity_courseproject.routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.List;
import java.util.Objects;

public class RoutineRecViewAdapter extends RecyclerView.Adapter<RoutineRecViewAdapter.ViewHolder> {

    private OnRoutineClickListener onRoutineClickListener;
    private List<Routine> routineList;
    private RoutineViewModel routineViewModel;
    private PeriodViewModel periodViewModel;
    private Context context;

    public RoutineRecViewAdapter(List<Routine> routineList, Context context,
                                 RoutineViewModel routineViewModel, PeriodViewModel periodViewModel,
                                 OnRoutineClickListener onRoutineClickListener) {
        this.routineList = routineList;
        this.context = context;
        this.routineViewModel = routineViewModel;
        this.periodViewModel = periodViewModel;
        this.onRoutineClickListener = onRoutineClickListener;
    }

    @NonNull
    @Override
    public RoutineRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routines_recyclerview_item, parent,false);
        return new ViewHolder(view, onRoutineClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineRecViewAdapter.ViewHolder holder, int position) {
        Routine routine = Objects.requireNonNull(routineList).get(position);
        holder.routineName.setText(routine.getTitle());
        holder.totalTime.setText(String.valueOf(periodViewModel.getTotalRoutineTime(routine)));

    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(routineList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnRoutineClickListener onRoutineClickListener;
        private TextView routineName;
        private TextView totalTime;

        public ViewHolder(@NonNull View itemView, OnRoutineClickListener onRoutineClickListener) {
            super(itemView);
            this.onRoutineClickListener = onRoutineClickListener;
            routineName = itemView.findViewById(R.id.routine_title_textview);
            totalTime = itemView.findViewById(R.id.routine_recycler_item_total_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRoutineClickListener.onRoutineClick(getAdapterPosition());
        }
    }

    public interface OnRoutineClickListener {
        void onRoutineClick(int position);
    }


}
