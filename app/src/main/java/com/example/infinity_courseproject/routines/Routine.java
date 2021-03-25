package com.example.infinity_courseproject.routines;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entity class defining a routine, where a routine is a sequence of events, which are themselves
 * composed of study and break periods. Routines can be associated with 0 or more weekdays,
 * and can have a specified start time (if set, the app will inform the user by means of a
 * notification at this time).
 */
@Entity(tableName = "routine_table")
public class Routine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "weekdays")
    @NonNull
    private boolean[] weekdays;

    @ColumnInfo(name = "start_hour")
    @Nullable
    private Integer startHour; //24h clock; limit options to 0 - 23; 24 is ignored

    @ColumnInfo(name = "start_minute")
    @Nullable
    private Integer startMinute; //only allow to be null if startHour is null

    @ColumnInfo(name = "events")
    @NonNull
    private ArrayList<Event> events;

    /**
     * Constructor for routine class
     * @param title - title of routine
     * @param weekdays - boolean array, where index 0 corresponds to the existence of an
     *                 association with the weekday that is Sunday (if false, not associated
     *                 with Sunday); this array therefore must be of size 7
     * @param startHour - an integer; if 24, treat as null
     * @param startMinute - an integer or null; if startHour is 24, treat as null
     */
    public Routine(@NonNull String title, @NonNull boolean[] weekdays, @Nullable Integer startHour,
                   @Nullable Integer startMinute, @NonNull ArrayList<Event> events) {
        this.title = title;
        this.weekdays = weekdays;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.events = events;
    }

    public int getTotalTimeInMinutes() {
        int sum = 0;
        for (Event e : events) {
            sum += e.getTotalTimeInMinutes();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", weekdays=" + Arrays.toString(weekdays) +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", events=" + events +
                '}';
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

    //getters and setters

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public boolean[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@NonNull boolean[] weekdays) {
        this.weekdays = weekdays;
    }

    //make sure that startHour and startMinute are both null or that neither are null
    @Nullable
    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(@Nullable Integer startHour) {
        this.startHour = startHour;
    }

    @Nullable
    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(@Nullable Integer startMinute) {
        this.startMinute = startMinute;
    }

    @NonNull
    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(@NonNull ArrayList<Event> events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}