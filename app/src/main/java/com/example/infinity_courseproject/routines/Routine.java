package com.example.infinity_courseproject.routines;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * Entity class defining a routine, where a routine is a sequence of study and break periods. Routines
 * can be associated with 0 or more weekdays, and can have a specified start time (if set,
 * the app will inform the user by means of a notification).
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
     *                 association with the weekday that is Monday (if false, not associated
     *                 with Monday); this array therefore must be of size 7
     * @param startHour - an integer or null; if null, startMinute must also be null
     * @param startMinute - an integer or null; if null, startHour must also be null
     */
    public Routine(@NonNull String title, @NonNull boolean[] weekdays, @Nullable Integer startHour,
                   @Nullable Integer startMinute) throws IllegalStartTimeException,
            IllegalWeekdaysLengthException {
        this.title = title;
        if (weekdays.length != 7)
            throw new IllegalWeekdaysLengthException("Incorrect length for weekdays array: "
                    + weekdays.length + " - length must be 7");
        this.weekdays = weekdays;
        if ((startHour == null && startMinute != null) || (startHour != null && startMinute == null)) {
            throw new IllegalStartTimeException("Illegal start time - either both startHour and "
                    + "startMinute are null, or neither are null.");
        }
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    /**
     * Error raised when the length of the field array 'weekdays' != 7
     */
    public static class IllegalWeekdaysLengthException extends Exception {
        public IllegalWeekdaysLengthException(String errorMsg) {
            super(errorMsg);
        }
    }

    /**
     * Error raised when only one of startHour and startMinute is null
     */
    public static class IllegalStartTimeException extends Exception {
        public IllegalStartTimeException(String errorMsg) {
            super(errorMsg);
        }
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