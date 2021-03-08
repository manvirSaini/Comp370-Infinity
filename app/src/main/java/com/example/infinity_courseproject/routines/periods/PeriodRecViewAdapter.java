package com.example.infinity_courseproject.routines.periods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.Routine;

import java.util.List;
import java.util.Objects;

public class PeriodRecViewAdapter extends RecyclerView.Adapter<PeriodRecViewAdapter.ViewHolder> {
    private List<Period> periodList;
    private Context context;

    public PeriodRecViewAdapter(List<Period> periodList, Context context) {
        this.periodList = periodList;
        this.context = context;
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
        return new PeriodRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodRecViewAdapter.ViewHolder holder, int position) {
        Period period = Objects.requireNonNull(periodList).get(position);
        holder.periodLabel.setText("Period " + period.getPosition());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(periodList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView periodLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            periodLabel = itemView.findViewById(R.id.period_label_textview);
        }
    }


}

