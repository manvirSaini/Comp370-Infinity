package com.example.infinity_courseproject.routines;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.infinity_courseproject.routines.periods.Period;
import java.util.ArrayList;

public class RoutinesAddEditViewModel extends ViewModel {

    private int startHour = 24;
    private int startMin = 0;
    private int totalTimeInMinutes;

    private MutableLiveData<ArrayList<Period>> periodLiveData;
    //copy of mutable data contents
    private ArrayList<Period> periodCopiedData;

    //constructor
    public RoutinesAddEditViewModel() {
        periodLiveData = new MutableLiveData<>();
        periodCopiedData = new ArrayList<>();
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

    public void updateTotalTime(ArrayList<Period> periods) {
        totalTimeInMinutes = 0;
        for (Period p : periods)
            totalTimeInMinutes += p.getBreakMinutes() + p.getStudyMinutes();
    }

    public String getTotalTimeInHoursAndMinutes() {
        String totalTime = null;
        int hours = totalTimeInMinutes/60;
        int minutes = totalTimeInMinutes%60;
        if (hours == 0)
            totalTime = minutes + "min";
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

    public void addPeriod() {
        Period period = new Period(periodCopiedData.size() + 1,
                60, 15, null);
        periodCopiedData.add(period);
        periodLiveData.setValue(periodCopiedData);
    }

    public void removePeriod(int recyclerviewPosition) {
        periodCopiedData.remove(recyclerviewPosition);
        for (int i = recyclerviewPosition; i < periodCopiedData.size(); i++) {
            periodCopiedData.get(i).setPosition(i-1);
        }
        periodLiveData.setValue(periodCopiedData);
    }

    public MutableLiveData<ArrayList<Period>> getPeriodLiveData() {
        return periodLiveData;
    }

    public void setPeriodLiveData(ArrayList<Period> periodList) {
        periodLiveData.setValue(periodList);
    }

    public int getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes(int totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public ArrayList<Period> getPeriodCopiedData() {
        return periodCopiedData;
    }

    public void setPeriodCopiedData(ArrayList<Period> periodCopiedData) {
        this.periodCopiedData = periodCopiedData;
    }
}

