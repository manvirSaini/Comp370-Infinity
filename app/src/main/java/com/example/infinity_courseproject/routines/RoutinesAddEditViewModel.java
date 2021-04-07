package com.example.infinity_courseproject.routines;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;
import java.util.ArrayList;

/**
 * This is the viewmodel corresponding to the RoutinesAddActivity.
 */
public class RoutinesAddEditViewModel extends ViewModel {

    private int startHour = 24;
    private int startMin = 0;
    private int totalTimeInMinutes;

    private final MutableLiveData<ArrayList<Event>> eventLiveData;
    //copy of mutable data contents
    private final ArrayList<Event> eventCopiedData;

    //constructor
    public RoutinesAddEditViewModel() {
        eventLiveData = new MutableLiveData<>();
        eventCopiedData = new ArrayList<>();
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
        eventCopiedData.add(event);
        eventLiveData.postValue(eventCopiedData);
    }

    public void updateEvent(int index, Event event) {
        eventCopiedData.set(index, event);
        eventLiveData.postValue(eventCopiedData);
    }

    public void removeEvent(int recyclerviewPosition) {
        eventCopiedData.remove(recyclerviewPosition);
        eventLiveData.postValue(eventCopiedData);
    }

    public MutableLiveData<ArrayList<Event>> getEventLiveData() {
        return eventLiveData;
    }

    public void setEventLiveData(ArrayList<Event> eventList) {
        eventCopiedData.addAll(eventList);
        eventLiveData.postValue(eventCopiedData);
    }

    public int getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(int totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public ArrayList<Event> getEventCopiedData() {
        return eventCopiedData;
    }

    public Event get(int index) {
        return eventCopiedData.get(index);
    }

}

