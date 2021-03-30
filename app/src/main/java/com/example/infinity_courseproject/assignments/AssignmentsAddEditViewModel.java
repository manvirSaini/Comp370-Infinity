package com.example.infinity_courseproject.assignments;

import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

public class AssignmentsAddEditViewModel extends ViewModel {

    private int dueHour = 24;
    private int dueMin = 0;
    private int daysPrior = 1;
    boolean markedAsComplete = false;

    //constructor
    public AssignmentsAddEditViewModel() {
    }

    public void incrementDueHour() {
        if (dueHour == 24) {
            dueHour = 0;
        }
        else
            dueHour++;
    }

    public void decrementDueHour() {
        if (dueHour == 0) {
            dueHour = 24;
        }
        else
            dueHour--;
    }

    public void incrementDueMinute() {
        if (dueMin == 59)
            dueMin = 0;
        else
            dueMin++;
    }

    public void decrementDueMinute() {
        if (dueMin == 0)
            dueMin = 59;
        else
            dueMin--;
    }

    public void incrementDaysPrior() {
        if (daysPrior == 99)
            daysPrior = 1;
        else
            daysPrior++;
    }

    public void decrementDaysPrior() {
        if (daysPrior == 1)
            daysPrior = 99;
        else
            daysPrior--;
    }

    /**
     * Method for making a string which may be parsed into a LocalDateTime obj
     * @param year - year in format yyyy
     * @param month - month in format MM
     * @param day - day in format dd
     * @param hour - hour in format HH
     * @param minute - minute in format mm
     * @return - a string which may be parsed into a LocalDateTime obj
     */
    @SuppressLint("DefaultLocale")
    public String convertToLocalDateTimeParsable(String year, String month, String day, String hour, String minute) {
        String fYear = String.format("%04d", Integer.parseInt(year));
        String fMonth = String.format("%02d", Integer.parseInt(month));
        String fDay = String.format("%02d", Integer.parseInt(day));
        String fHour = String.format("%02d", Integer.parseInt(hour));
        String fMinute = String.format("%02d", Integer.parseInt(minute));
        return fYear + "-" + fMonth + "-" + fDay + " " + fHour + ":" + fMinute;
    }

    /**
     * Method that checks a date's validity.
     * @param dateStr - date in string format; format is "yyyy-MM-ddTHH:mm"
     * @return - boolean; true if date is valid, false otherwise
     */
    @SuppressLint("SimpleDateFormat")
    public boolean dateIsValid(String dateStr) {
        boolean valid = false;
        //the string will have a space which partitions hours and minutes from the rest of the date
        dateStr = dateStr.substring(0, dateStr.indexOf(" "));
        String dateFormat = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);
        try {
            format.parse(dateStr);
            valid = true;
        }
        catch(ParseException pe){ }

        return valid;
    }

    /**
     * Determines if a date is in the future relative to now.
     * @param dateStr - date in string format; format is "yyyy-MM-dd HH:mm"
     * @return - boolean; true if date is in the future, false otherwise
     */
    public boolean dateIsInTheFuture(String dateStr) {
        boolean valid = false;
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, myStudyRoutineDB.formatter);
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(dateTime))
            valid = true;

        return valid;
    }


    public int getDueHour() {
        return dueHour;
    }

    public void setDueHour(int dueHour) {
        this.dueHour = dueHour;
    }

    public int getDueMin() {
        return dueMin;
    }

    public void setDueMin(int dueMin) {
        this.dueMin = dueMin;
    }

    public int getDaysPrior() {
        return daysPrior;
    }

    public void setDaysPrior(int daysPrior) {
        this.daysPrior = daysPrior;
    }

    public boolean isMarkedAsComplete() {
        return markedAsComplete;
    }

    public void setMarkedAsComplete(boolean markedAsComplete) {
        this.markedAsComplete = markedAsComplete;
    }
}
