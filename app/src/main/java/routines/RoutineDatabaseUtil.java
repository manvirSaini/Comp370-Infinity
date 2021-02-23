package routines;

import java.util.ArrayList;

import static routines.Routine.*;

public class RoutineDatabaseUtil {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "routine_db";
    public static final String TABLE_NAME = "routine";

    //contact table col names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MONDAY = "monday"; //true or false; same for all weekdays
    public static final String KEY_TUESDAY = "tuesday";
    public static final String KEY_WEDNESDAY = "wednesday";
    public static final String KEY_THURSDAY = "thursday";
    public static final String KEY_FRIDAY = "friday";
    public static final String KEY_SATURDAY = "saturday";
    public static final String KEY_SUNDAY = "sunday";
    //public static final String KEY_PERIODS = "periods"; 1 to many relationship... :(
    public static final String KEY_START_HOUR = "start_hour";
    public static final String KEY_START_MINUTE = "start_minute";
    public static final String KEY_TOTAL_TIME_HOUR = "total_time_hour";
    public static final String KEY_TOTAL_TIME_MINUTE = "total_time_minute";
}
