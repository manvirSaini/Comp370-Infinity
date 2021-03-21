package com.example.infinity_courseproject.routines.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinity_courseproject.R;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.List;
import java.util.Objects;

public class EventRecViewAdapter extends RecyclerView.Adapter<EventRecViewAdapter.ViewHolder> {

    private final OnEventClickListener onEventClickListener;
    private List<Event> eventList;
    private Context context;

    public EventRecViewAdapter(List<Event> eventList, Context context,
                               OnEventClickListener onEventClickListener) {
        this.eventList = eventList;
        this.context = context;
        this.onEventClickListener = onEventClickListener;
    }

    /**
     * Change to recview item accustomed to periods
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public EventRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_recyclerview_item, parent,false);
        return new EventRecViewAdapter.ViewHolder(view, onEventClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecViewAdapter.ViewHolder holder, int position) {
        Event event = Objects.requireNonNull(eventList).get(position);
        holder.eventLabel.setText("Event " + position);
        //holder.courseTitle.setText("");
        holder.studyTime.setText(String.valueOf(event.getStudyMinutes()));
        holder.breakTime.setText(String.valueOf(event.getBreakMinutes()));
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(eventList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnEventClickListener onEventClickListener;
        private final TextView eventLabel;
        private final TextView courseTitle;
        private final TextView studyTime;
        private final TextView breakTime;


        public ViewHolder(@NonNull View itemView, OnEventClickListener onEventClickListener) {
            super(itemView);
            this.onEventClickListener = onEventClickListener;
            eventLabel = itemView.findViewById(R.id.event_label_textview);
            courseTitle = itemView.findViewById(R.id.event_course_title);
            studyTime = itemView.findViewById(R.id.event_study_time_hours_and_minutes);
            breakTime = itemView.findViewById(R.id.event_break_time_hours_and_minutes);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onEventClickListener.onEventLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onEventClickListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventClickListener {
        void onEventClick(int position);
        void onEventLongClick(int position);
    }
}

