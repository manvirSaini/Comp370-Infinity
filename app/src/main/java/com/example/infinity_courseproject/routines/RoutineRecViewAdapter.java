package com.example.infinity_courseproject.routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.List;
import java.util.Objects;

public class RoutineRecViewAdapter extends RecyclerView.Adapter<RoutineRecViewAdapter.ViewHolder> {
    private List<Routine> routineList;
    private Context context;

    public RoutineRecViewAdapter(List<Routine> routineList, Context context) {
        this.routineList = routineList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routines_recyclerview_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineRecViewAdapter.ViewHolder holder, int position) {
        Routine routine = Objects.requireNonNull(routineList).get(position);
        holder.routineName.setText(routine.getTitle());

        if (RoutineViewModel.isOrderByTotalTime()) {
            holder.totalTime.setText("");
        }



    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(routineList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView routineName;
        private TextView totalTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routine_name_textview);
            totalTime = itemView.findViewById(R.id.total_time_textview);
        }
    }


}
