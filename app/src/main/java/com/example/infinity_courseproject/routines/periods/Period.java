package com.example.infinity_courseproject.routines.periods;

import android.os.Parcel;
import android.os.Parcelable;

public class Period implements Parcelable {

    private int position;

    private int studyMinutes; //time of study subperiod in minutes

    private int breakMinutes; //time of break subperiod in minutes

    private String courseTitle;

    public Period(int position, int studyMinutes, int breakMinutes, String courseTitle) {
        this.position = position;
        this.studyMinutes = studyMinutes;
        this.breakMinutes = breakMinutes;
        this.courseTitle = courseTitle;
    }

    protected Period(Parcel in) {
        position = in.readInt();
        studyMinutes = in.readInt();
        breakMinutes = in.readInt();
        courseTitle = in.readString();
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
        dest.writeString(courseTitle);
    }

    public String getStudyTimeInHoursAndMinutes() {
        String totalTime = null;
        int hours = studyMinutes/60;
        int minutes = studyMinutes%60;

        if (hours == 0)
            totalTime = minutes + "min";
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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
