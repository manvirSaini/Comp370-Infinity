package com.example.infinity_courseproject.routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.RoutinesActivity;

import java.util.List;
import java.util.Objects;

public class RoutineRecViewAdapter extends RecyclerView.Adapter<RoutineRecViewAdapter.ViewHolder> {

    private final OnRoutineClickListener onRoutineClickListener;
    private List<Routine> routineList;
    private RoutineViewModel routineViewModel;
    private Context context;

    public RoutineRecViewAdapter(List<Routine> routineList, Context context,
                                 RoutineViewModel routineViewModel,
                                 OnRoutineClickListener onRoutineClickListener) {
        this.routineList = routineList;
        this.context = context;
        this.routineViewModel = routineViewModel;
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

        if (filterRoutine(routine)) {
            holder.recyclerItem.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        else {
            holder.routineName.setText(routine.getTitle());
            holder.totalTime.setText(routine.getTotalTimeInHoursAndMinutes());
            boolean[] weekdays = routine.getWeekdays();
            if (weekdays[0])
                holder.sun.setVisibility(View.VISIBLE);
            if (weekdays[1])
                holder.mon.setVisibility(View.VISIBLE);
            if (weekdays[2])
                holder.tues.setVisibility(View.VISIBLE);
            if (weekdays[3])
                holder.wed.setVisibility(View.VISIBLE);
            if (weekdays[4])
                holder.thurs.setVisibility(View.VISIBLE);
            if (weekdays[5])
                holder.fri.setVisibility(View.VISIBLE);
            if (weekdays[6])
                holder.sat.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(routineList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnRoutineClickListener onRoutineClickListener;
        private final ViewGroup recyclerItem;
        private final TextView routineName;
        private final TextView totalTime;
        private final TextView sun;
        private final TextView mon;
        private final TextView tues;
        private final TextView wed;
        private final TextView thurs;
        private final TextView fri;
        private final TextView sat;


        public ViewHolder(@NonNull View itemView, OnRoutineClickListener onRoutineClickListener) {
            super(itemView);
            this.onRoutineClickListener = onRoutineClickListener;
            recyclerItem = itemView.findViewById(R.id.routine_reycler_item_container);
            routineName = itemView.findViewById(R.id.routine_title_textview);
            totalTime = itemView.findViewById(R.id.routine_recycler_item_total_time);
            sun = itemView.findViewById(R.id.sun_text);
            mon = itemView.findViewById(R.id.mon_text);
            tues = itemView.findViewById(R.id.tues_text);
            wed = itemView.findViewById(R.id.wed_text);
            thurs = itemView.findViewById(R.id.thurs_text);
            fri = itemView.findViewById(R.id.fri_text);
            sat = itemView.findViewById(R.id.sat_text);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRoutineClickListener.onRoutineLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onRoutineClickListener.onRoutineClick(getAdapterPosition());
        }

    }

    public interface OnRoutineClickListener {
        void onRoutineClick(int position);
        void onRoutineLongClick(int position);
    }

    private boolean filterRoutine(Routine r) {
        boolean applyFilter = false;
        boolean[] weekdays = r.getWeekdays();
        RoutinesActivity.RoutineFilterBy filter = RoutinesActivity.getFilter();
        switch (filter) {
            case ALL:
                break;
            case GENERAL:
                for (boolean b : weekdays) {
                    if (b) {
                        applyFilter = true;
                        break;
                    }
                }
                break;
            case SUN:
                if (!weekdays[0])
                    applyFilter = true;
                break;
            case MON:
                if (!weekdays[1])
                    applyFilter = true;
                break;
            case TUES:
                if (!weekdays[2])
                    applyFilter = true;
                break;
            case WED:
                if (!weekdays[3])
                    applyFilter = true;
                break;
            case THURS:
                if (!weekdays[4])
                    applyFilter = true;
                break;
            case FRI:
                if (!weekdays[5])
                    applyFilter = true;
                break;
            case SAT:
                if (!weekdays[6])
                    applyFilter = true;
                break;
        }
        return applyFilter;
    }

}
