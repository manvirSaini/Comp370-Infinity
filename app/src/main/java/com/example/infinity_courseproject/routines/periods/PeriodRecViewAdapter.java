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
    public static final int MAX_NUM_OF_CHILDREN = 3;
    private static int numOfVisibleChildren = 0;

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
//        if (holder.childrenDropdown.getVisibility() != ViewGroup.GONE) {
//            if (position == periodList.size() - 1) {
//                holder.breakTimeChild.setVisibility(ViewGroup.GONE);
//            }
//
//
//        }
//        else {
//
//        }

    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(periodList).size();
       // return Objects.requireNonNull(periodList).size() + numOfVisibleChildren;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView periodLabel;
        private final ViewGroup childrenDropdown;
        private final ViewGroup breakTimeChild;
        private final Spinner courseSpinner;
        private final Spinner studyTimeSpinner;
        private final Spinner breakTimeSpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            periodLabel = itemView.findViewById(R.id.period_label_textview);
            childrenDropdown = itemView.findViewById(R.id.period_recycler_item_dropdown);
            breakTimeChild = itemView.findViewById(R.id.period_child_break_recycler_item);
            courseSpinner = itemView.findViewById(R.id.period_course_spinner);
            studyTimeSpinner = itemView.findViewById(R.id.period_study_spinner);
            breakTimeSpinner = itemView.findViewById(R.id.period_break_spinner);

        }
    }

    public static int getNumOfVisibleChildren() {
        return numOfVisibleChildren;
    }

    public static void setNumOfVisibleChildren(int numOfVisibleChildren) {
        PeriodRecViewAdapter.numOfVisibleChildren = numOfVisibleChildren;
    }
}

