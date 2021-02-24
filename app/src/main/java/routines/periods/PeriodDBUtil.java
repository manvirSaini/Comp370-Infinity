package routines.periods;

public class PeriodDBUtil {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "period_db";
    public static final String TABLE_NAME = "period";

    //period table col names
    public static final String KEY_ID = "id";
    public static final String KEY_POSITION = "position";
    public static final String KEY_STUDY_TIME_HOUR = "study_time_hour";
    public static final String KEY_STUDY_TIME_MINUTE = "study_time_minute";
    public static final String KEY_BREAK_TIME_HOUR = "break_time_hour";
    public static final String KEY_BREAK_TIME_MINUTE = "break_time_minute";
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_ROUTINE_ID = "routine_id";
}
