package com.example.infinity_courseproject.routines.periods;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.RoutinesAddActivity;
import com.example.infinity_courseproject.routines.Routine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeriodRecViewAdapter extends RecyclerView.Adapter<PeriodRecViewAdapter.ViewHolder> {

    private final OnPeriodClickListener onPeriodClickListener;
    private List<Period> periodList;
    private Context context;

    public PeriodRecViewAdapter(List<Period> periodList, Context context,
                                OnPeriodClickListener onPeriodClickListener) {
        this.periodList = periodList;
        this.context = context;
        this.onPeriodClickListener = onPeriodClickListener;
    }

    /**
     * Change to recview item accustomed to periods
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PeriodRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.periods_recyclerview_item, parent,false);
        return new PeriodRecViewAdapter.ViewHolder(view, onPeriodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodRecViewAdapter.ViewHolder holder, int position) {
        Period period = Objects.requireNonNull(periodList).get(position);
        holder.periodLabel.setText("Period " + period.getPosition());

        //holder.courseTitle.setText("");
        holder.studyTime.setText(period.getStudyTimeInHoursAndMinutes());
        holder.breakTime.setText(period.getBreakTimeInHoursAndMinutes());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(periodList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnPeriodClickListener onPeriodClickListener;
        private final TextView periodLabel;
        private final TextView courseTitle;
        private final TextView studyTime;
        private final TextView breakTime;


        public ViewHolder(@NonNull View itemView, OnPeriodClickListener onPeriodClickListener) {
            super(itemView);
            this.onPeriodClickListener = onPeriodClickListener;
            periodLabel = itemView.findViewById(R.id.period_label_textview);
            courseTitle = itemView.findViewById(R.id.period_course_title);
            studyTime = itemView.findViewById(R.id.period_study_time_hours_and_minutes);
            breakTime = itemView.findViewById(R.id.period_break_time_hours_and_minutes);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onPeriodClickListener.onPeriodLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onPeriodClickListener.onPeriodClick(getAdapterPosition());
        }
    }

    public interface OnPeriodClickListener {
        void onPeriodClick(int position);
        void onPeriodLongClick(int position);
    }
}

