package com.example.infinity_courseproject.routines.events;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;

/**
 * The Event class may be associated with a Course, and is composed of periods.
 */
public class Event implements Parcelable {
    private ArrayList<Period> periods;
    private int courseId;

    public Event(ArrayList<Period> periods, int courseId) {
        this.periods = periods;
        this.courseId = courseId;
    }

    public int getTotalTimeInMinutes() {
        int sum = 0;
        for (Period p : periods) {
            sum += p.getMinutes();
        }
        return sum;
    }

    public String getTotalTimeInHoursAndMinutes() {
        int totalTimeInMinutes = getTotalTimeInMinutes();
        String totalTime = null;
        int hours = totalTimeInMinutes/60;
        int minutes = totalTimeInMinutes%60;
        if (hours == 0)
            totalTime = minutes + "min";
        else
            totalTime = hours + "h " + minutes + "min";
        return totalTime;
    }

    public Period getPeriodAtIndex(int index) {
        return periods.get(index);
    }

    public int getStudyMinutes() {
        int studyMinutes = 0;
        for (Period p : periods) {
            if (p.getDevotion().equals(Period.Devotion.STUDY))
                studyMinutes += p.getMinutes();
        }
        return studyMinutes;
    }

    public int getBreakMinutes() {
        int breakMinutes = 0;
        for (Period p : periods) {
            if (p.getDevotion().equals(Period.Devotion.BREAK))
                breakMinutes += p.getMinutes();
        }
        return breakMinutes;
    }

    public String getStudyTimeInHoursAndMinutes() {
        int studyTimeInMinutes = getStudyMinutes();
        String totalTime = null;
        int hours = studyTimeInMinutes/60;
        int minutes = studyTimeInMinutes%60;
        if (hours == 0)
            totalTime = minutes + "min";
        else if (minutes == 0)
            totalTime = hours + "h";
        else
            totalTime = hours + "h " + minutes + "min";
        return totalTime;
    }

    public String getBreakTimeInHoursAndMinutes() {
        int breakTimeInMinutes = getBreakMinutes();
        String totalTime = null;
        int hours = breakTimeInMinutes/60;
        int minutes = breakTimeInMinutes%60;
        if (hours == 0)
            totalTime = minutes + "min";
        else if (minutes == 0)
            totalTime = hours + "h";
        else
            totalTime = hours + "h " + minutes + "min";
        return totalTime;
    }

    //parcelable functions
    protected Event(Parcel in) {
        periods = in.createTypedArrayList(Period.CREATOR);
        courseId = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(periods);
        dest.writeInt(courseId);
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<Period> periods) {
        this.periods = periods;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
