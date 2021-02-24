package routines;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.infinity_courseproject.R;

/**
 * Manager for Routine database.
 */
public class RoutineDBHandler extends SQLiteOpenHelper {

    public RoutineDBHandler(Context context) {
        super(context, RoutineDBUtil.DATABASE_NAME, null, RoutineDBUtil.DATABASE_VERSION);
    }

    /**
     * This method creates the table. IT CURRENTLY INGORES PERIODS due to the 1:M relationship
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROUTINE_TABLE = "CREATE TABLE " + RoutineDBUtil.TABLE_NAME + "("
                + RoutineDBUtil.KEY_ID + " INTEGER PRIMARY KEY,"
                + RoutineDBUtil.KEY_TITLE + " TEXT,"
                + RoutineDBUtil.KEY_MONDAY + " TINYINT,"
                + RoutineDBUtil.KEY_TUESDAY + " TINYINT,"
                + RoutineDBUtil.KEY_WEDNESDAY + " TINYINT,"
                + RoutineDBUtil.KEY_THURSDAY + " TINYINT,"
                + RoutineDBUtil.KEY_FRIDAY + " TINYINT,"
                + RoutineDBUtil.KEY_SATURDAY + " TINYINT,"
                + RoutineDBUtil.KEY_SUNDAY + " TINYINT,"
                + RoutineDBUtil.KEY_START_HOUR + " TINYINT,"
                + RoutineDBUtil.KEY_START_MINUTE + " TINYINT,"
                + RoutineDBUtil.KEY_TOTAL_TIME_HOUR + " TINYINT,"
                + RoutineDBUtil.KEY_TOTAL_TIME_MINUTE + " TINYINT"
                + ")";

        db.execSQL(CREATE_ROUTINE_TABLE); //actual table creation
    }

    /**
     * Upgrade (in this case, refresh) the table
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{RoutineDBUtil.DATABASE_NAME});
        //recreate table
        onCreate(db);
    }

    /**
     * Add routine to table
     * @param routine
     */
    public void addRoutine(Routine routine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RoutineDBUtil.KEY_TITLE, routine.getTitle());
        //weekdays booleans is an array of 0's and 1's, serving as boolean values
        //find out if these integer values you are putting in the table need to be strings
        int[] weekdayBooleans = routine.getWeekdays();
        values.put(RoutineDBUtil.KEY_MONDAY, weekdayBooleans[0]);
        values.put(RoutineDBUtil.KEY_TUESDAY, weekdayBooleans[1]);
        values.put(RoutineDBUtil.KEY_WEDNESDAY, weekdayBooleans[2]);
        values.put(RoutineDBUtil.KEY_THURSDAY, weekdayBooleans[3]);
        values.put(RoutineDBUtil.KEY_FRIDAY, weekdayBooleans[4]);
        values.put(RoutineDBUtil.KEY_SATURDAY, weekdayBooleans[5]);
        values.put(RoutineDBUtil.KEY_SUNDAY, weekdayBooleans[6]);

        values.put(RoutineDBUtil.KEY_START_HOUR, routine.getStartHour());
        values.put(RoutineDBUtil.KEY_START_MINUTE, routine.getStartMinute());
        values.put(RoutineDBUtil.KEY_TOTAL_TIME_HOUR, routine.getTotalTimeHour());
        values.put(RoutineDBUtil.KEY_TOTAL_TIME_MINUTE, routine.getTotalTimeMinute());

        //insert row
        db.insert(RoutineDBUtil.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Retrieve routine from table.
     * @param id
     * @return
     */
    public Routine getRoutine(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RoutineDBUtil.TABLE_NAME,
                new String[]{RoutineDBUtil.KEY_ID, RoutineDBUtil.KEY_TITLE,
                        RoutineDBUtil.KEY_MONDAY, RoutineDBUtil.KEY_TUESDAY,
                        RoutineDBUtil.KEY_WEDNESDAY, RoutineDBUtil.KEY_THURSDAY,
                        RoutineDBUtil.KEY_FRIDAY, RoutineDBUtil.KEY_SATURDAY,
                        RoutineDBUtil.KEY_SUNDAY, RoutineDBUtil.KEY_START_HOUR,
                        RoutineDBUtil.KEY_START_MINUTE, RoutineDBUtil.KEY_TOTAL_TIME_HOUR,
                        RoutineDBUtil.KEY_TOTAL_TIME_MINUTE},
                RoutineDBUtil.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Routine routine = new Routine();
        routine.setId(Integer.parseInt(cursor.getString(0)));
        routine.setTitle(cursor.getString(1));

        //set all days of the week! consider replacing original enumeration with 1's and 0's
        int[] weekdayBooleans = routine.getWeekdays();
        for (int i = 0; i < weekdayBooleans.length; i++) {
            weekdayBooleans[i] = cursor.getInt(i+2);
        }

        routine.setStartHour(Integer.parseInt(cursor.getString(9)));
        routine.setStartMinute(Integer.parseInt(cursor.getString(10)));
        routine.setTotalTimeHour(Integer.parseInt(cursor.getString(11)));
        routine.setTotalTimeMinute(Integer.parseInt(cursor.getString(12)));

        return routine;
    }


}