package com.example.infinity_courseproject.routines;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

/**
 * Entity class defining a routine, where a routine is a sequence of study and break periods. Routines
 * can be associated with 0 or more weekdays, and can have a specified start time (if set,
 * the app will inform the user by means of a notification). Routines may be ordered by their
 * total time (sum of all of their periods), but the default is alphabetical ordering.
 */
@Entity(tableName = "routine_table", primaryKeys = {"title"})
public class Routine {

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "weekdays")
    @NonNull
    private boolean[] weekdays;

    @ColumnInfo(name = "start_hour")
    @Nullable
    private Integer startHour; //24h clock; limit options to 0 - 23

    @ColumnInfo(name = "start_minute")
    @Nullable
    private Integer startMinute; //limit options to 15, 30, and 45; only allow to be null if startHour is null

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
                   @Nullable Integer startMinute) {
        this.title = title;
        this.weekdays = weekdays;
        this.startHour = startHour;
        this.startMinute = startMinute;
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
}