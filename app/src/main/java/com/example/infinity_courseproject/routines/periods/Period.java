package com.example.infinity_courseproject.routines.periods;

import android.os.Parcel;
import android.os.Parcelable;

public class Period implements Parcelable {

    private int position;

    private int studyMinutes; //time of study subperiod in minutes

    private int breakMinutes; //time of break subperiod in minutes

    private int courseId; //will be 0 if there is no associated course

    public Period(int position, int studyMinutes, int breakMinutes, int courseId) {
        this.position = position;
        this.studyMinutes = studyMinutes;
        this.breakMinutes = breakMinutes;
        this.courseId = courseId;
    }

    protected Period(Parcel in) {
        position = in.readInt();
        studyMinutes = in.readInt();
        breakMinutes = in.readInt();
        courseId = in.readInt();
    }

    public static final Creator<Period> CREATOR = new Creator<Period>() {
        @Override
        public Period createFromParcel(Parcel in) {
            return new Period(in);
        }

        @Override
        public Period[] newArray(int size) {
            return new Period[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeInt(studyMinutes);
        dest.writeInt(breakMinutes);
        dest.writeInt(courseId);
    }

    public String getStudyTimeInHoursAndMinutes() {
        String totalTime = null;
        int hours = studyMinutes/60;
        int minutes = studyMinutes%60;

        if (hours == 0)
            totalTime = minutes + "min";
        else if (minutes == 0)
            totalTime = hours + "h";
        else
            totalTime = hours + "h " + minutes + "min";

        return totalTime;

    }

    public String getBreakTimeInHoursAndMinutes() {
        String totalTime = null;
        int hours = breakMinutes/60;
        int minutes = breakMinutes%60;

        if (hours == 0)
            totalTime = minutes + "min";
        else if (minutes == 0)
            totalTime = hours + "h";
        else
            totalTime = hours + "h " + minutes + "min";

        return totalTime;

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes = studyMinutes;
    }

    public int getBreakMinutes() {
        return breakMinutes;
    }

    public void setBreakMinutes(int breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
