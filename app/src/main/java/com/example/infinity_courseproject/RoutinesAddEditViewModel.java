package com.example.infinity_courseproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;

public class RoutinesAddEditViewModel extends ViewModel {
    private Integer startHour;
    private Integer startMin;
    private int totalTimeInMinutes;
    private MutableLiveData<ArrayList<Period>> periodLiveData;

    //private DecimalFormat formatter = new DecimalFormat("00");


    //constructors
    public RoutinesAddEditViewModel() {
        periodLiveData = new MutableLiveData<>();
    }

    public RoutinesAddEditViewModel(Integer startHour, Integer startMin, int totalTimeInMinutes,
                                    ArrayList<Period> periodArrayList) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.totalTimeInMinutes = totalTimeInMinutes;
        this.periodLiveData.setValue(periodArrayList);
    }

    /**
     * The following will be checked upon adding a routine:
     * 1 - If startHour is null and startMin is not, startHour = 0
     * 2 - If startMin is null and startHour is not, startMin = 0
     * 3 - If both fields are null, startHour = 24, startMin = 0
     * 4 - If neither are null, proceed as is
     */

    public void incrementStartHour() {
        if (startHour == 24 || startHour == null) {
            startHour = 0;
        }
        else
            startHour++;
    }

    public void decrementStartHour() {
        if (startHour == null) {
            startHour = 23;
        }
        else if (startHour == 0)
            startHour = 24;
        else
            startHour--;
    }

    public void incrementStartMinute() {
        if (startMin == null) {
            startMin = 1;
        }
        else if (startMin == 59)
            startMin = 0;
        else
            startMin++;
    }

    public void decrementStartMinute() {
        if (startMin == 0 || startMin == null)
            startMin = 59;
        else
            startMin--;
    }

    public void updateTotalTime(ArrayList<Period> periods) {
        for (Period p : periods)
            totalTimeInMinutes += p.getBreakMinutes() + p.getStudyMinutes();
    }

    public String getTotalTimeInHoursAndMinutes() {
        int hours = totalTimeInMinutes/60;
        int minutes = totalTimeInMinutes%60;

        return hours + "h " + minutes + "min";

    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMin() {
        return startMin;
    }

    public void setStartMin(Integer startMin) {
        this.startMin = startMin;
    }

    public MutableLiveData<ArrayList<Period>> getPeriodLiveData() {
        return periodLiveData;
    }

    public void setPeriodLiveData(ArrayList<Period> periodList) {
        periodLiveData.setValue(periodList);
    }

}

