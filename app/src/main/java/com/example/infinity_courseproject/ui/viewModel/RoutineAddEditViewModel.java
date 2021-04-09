package com.example.infinity_courseproject.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinity_courseproject.domain.nonEntity.Event;
import com.example.infinity_courseproject.domain.nonEntity.Period;
import java.util.ArrayList;

/**
 * This is the viewmodel corresponding to the RoutinesAddActivity.
 */
public class RoutineAddEditViewModel extends ViewModel {

    private int startHour = 24;
    private int startMin = 0;
    private int totalTimeInMinutes;

    private final MutableLiveData<ArrayList<Event>> eventLiveData;
    //copy of mutable data contents
    private final ArrayList<Event> eventList;

    //constructor
    public RoutineAddEditViewModel() {
        eventLiveData = new MutableLiveData<>();
        eventList = new ArrayList<>();
    }

    public void incrementStartHour() {
        if (startHour == 24) {
            startHour = 0;
        }
        else
            startHour++;
    }

    public void decrementStartHour() {
        if (startHour == 0) {
            startHour = 24;
        }
        else
            startHour--;
    }

    public void incrementStartMinute() {
        if (startMin == 59)
            startMin = 0;
        else
            startMin++;
    }

    public void decrementStartMinute() {
        if (startMin == 0)
            startMin = 59;
        else
            startMin--;
    }

    public void updateTotalTime(ArrayList<Event> events) {
        totalTimeInMinutes = 0;
        for (Event e : events)
            totalTimeInMinutes += e.getTotalTimeInMinutes();
    }

    public String getTotalTimeInHoursAndMinutes() {
        String totalTime = "null";
        int hours = totalTimeInMinutes/60;
        int minutes = totalTimeInMinutes%60;
        if (hours == 0)
            totalTime = minutes + "min";
        else if (minutes == 0)
            totalTime = hours + "h";
        else
            totalTime = hours + "h " + minutes + "min";
        return totalTime;

    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public void addEvent() {
        Period studyPeriod = new Period(Period.Devotion.STUDY, 60);
        Period breakPeriod = new Period(Period.Devotion.BREAK, 15);
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(studyPeriod);
        periods.add(breakPeriod);

        Event event = new Event(periods, 0);
        eventList.add(event);
        eventLiveData.postValue(eventList);
    }

    public void updateEvent(int index, Event event) {
        eventList.set(index, event);
        eventLiveData.postValue(eventList);
    }

    public void removeEvent(int recyclerviewPosition) {
        eventList.remove(recyclerviewPosition);
        eventLiveData.postValue(eventList);
    }

    public MutableLiveData<ArrayList<Event>> getEventLiveData() {
        return eventLiveData;
    }

    public void setEventLiveData(ArrayList<Event> eventList) {
        this.eventList.addAll(eventList);
        eventLiveData.postValue(this.eventList);
    }

    public int getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(int totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public Event get(int index) {
        return eventList.get(index);
    }

}

