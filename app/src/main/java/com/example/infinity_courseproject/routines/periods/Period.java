package com.example.infinity_courseproject.routines.periods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Period class is a period of time within an event, and may be devoted to a break or studying.
 * Position ensures the order of periods in an event.
 */
public class Period implements Parcelable {
    public enum Devotion {STUDY, BREAK}
    private Devotion devotion;
    private int minutes;

    public Period(Devotion devotion, int minutes) {
        this.devotion = devotion;
        this.minutes = minutes;
    }

    public String getTimeInHoursAndMinutes() {
        String totalTime = null;
        int hours = minutes/60;
        int remainingMinutes = minutes%60;
        if (hours == 0)
            totalTime = remainingMinutes + "min";
        else
            totalTime = hours + "h " + remainingMinutes + "min";
        return totalTime;
    }

    //parcelable functions
    protected Period(Parcel in) {
        devotion = Devotion.valueOf(in.readString());
        minutes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(devotion.name());
        dest.writeInt(minutes);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Devotion getDevotion() {
        return devotion;
    }

    public void setDevotion(Devotion devotion) {
        this.devotion = devotion;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
