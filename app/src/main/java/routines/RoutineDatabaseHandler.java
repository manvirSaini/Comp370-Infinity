package routines;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.infinity_courseproject.R;

import java.util.ArrayList;

/**
 * Manager for Routine database.
 */
public class RoutineDatabaseHandler extends SQLiteOpenHelper {

    public RoutineDatabaseHandler(Context context) {
        super(context, RoutineDatabaseUtil.DATABASE_NAME, null, RoutineDatabaseUtil.DATABASE_VERSION);
    }

    /**
     * This method creates the table. IT CURRENTLY INGORES PERIODS due to the 1:M relationship
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROUTINE_TABLE = "CREATE TABLE " + RoutineDatabaseUtil.TABLE_NAME + "("
                + RoutineDatabaseUtil.KEY_ID + " INTEGER PRIMARY KEY,"
                + RoutineDatabaseUtil.KEY_TITLE + " TEXT,"
                + RoutineDatabaseUtil.KEY_MONDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_TUESDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_WEDNESDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_THURSDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_FRIDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_SATURDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_SUNDAY + " TINYINT,"
                + RoutineDatabaseUtil.KEY_START_HOUR + " TINYINT,"
                + RoutineDatabaseUtil.KEY_START_MINUTE + " TINYINT,"
                + RoutineDatabaseUtil.KEY_TOTAL_TIME_HOUR + " TINYINT,"
                + RoutineDatabaseUtil.KEY_TOTAL_TIME_MINUTE + " TINYINT"
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
        db.execSQL(DROP_TABLE, new String[]{RoutineDatabaseUtil.DATABASE_NAME});
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

        values.put(RoutineDatabaseUtil.KEY_TITLE, routine.getTitle());

        //weekdays booleans is an array of 0's and 1's, serving as boolean values
        //find out if these integer values you are putting in the table need to be strings
        int[] weekdayBooleans = getWeekdayBooleans(routine);
        values.put(RoutineDatabaseUtil.KEY_MONDAY, weekdayBooleans[0]);
        values.put(RoutineDatabaseUtil.KEY_TUESDAY, weekdayBooleans[1]);
        values.put(RoutineDatabaseUtil.KEY_WEDNESDAY, weekdayBooleans[2]);
        values.put(RoutineDatabaseUtil.KEY_THURSDAY, weekdayBooleans[3]);
        values.put(RoutineDatabaseUtil.KEY_FRIDAY, weekdayBooleans[4]);
        values.put(RoutineDatabaseUtil.KEY_SATURDAY, weekdayBooleans[5]);
        values.put(RoutineDatabaseUtil.KEY_SUNDAY, weekdayBooleans[6]);

        values.put(RoutineDatabaseUtil.KEY_START_HOUR, routine.getStartHour());
        values.put(RoutineDatabaseUtil.KEY_START_MINUTE, routine.getStartMinute());
        values.put(RoutineDatabaseUtil.KEY_TOTAL_TIME_HOUR, routine.getTotalTimeHour());
        values.put(RoutineDatabaseUtil.KEY_TOTAL_TIME_MINUTE, routine.getTotalTimeMinute());

        //insert row
        db.insert(RoutineDatabaseUtil.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * addRoutine() helper method
     * @param routine
     * @return - array of 0's and 1's; if index 0 has int value 1, then Monday is included
     * in the list
     */
    private int[] getWeekdayBooleans(Routine routine) {
        Routine.Weekday[] weekdays = routine.getWeekdays();
        int[] weekdayBooleans = new int[7]; //0 index for monday... booleans are 1 or 0
        for (Routine.Weekday w : weekdays) {
            switch (w) {
                case MONDAY:
                    weekdayBooleans[0] = 1;
                    break;
                case TUESDAY:
                    weekdayBooleans[1] = 1;
                    break;
                case WEDNESDAY:
                    weekdayBooleans[2] = 1;
                    break;
                case THURSDAY:
                    weekdayBooleans[3] = 1;
                    break;
                case FRIDAY:
                    weekdayBooleans[4] = 1;
                    break;
                case SATURDAY:
                    weekdayBooleans[5] = 1;
                    break;
                case SUNDAY:
                    weekdayBooleans[6] = 1;
            }
        }
        return weekdayBooleans;
    }

    /**
     * Retrieve routine from table.
     * @param id
     * @return
     */
    public Routine getRoutine(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RoutineDatabaseUtil.TABLE_NAME,
                new String[]{RoutineDatabaseUtil.KEY_ID, RoutineDatabaseUtil.KEY_TITLE,
                        RoutineDatabaseUtil.KEY_MONDAY, RoutineDatabaseUtil.KEY_TUESDAY,
                        RoutineDatabaseUtil.KEY_WEDNESDAY, RoutineDatabaseUtil.KEY_THURSDAY,
                        RoutineDatabaseUtil.KEY_FRIDAY, RoutineDatabaseUtil.KEY_SATURDAY,
                        RoutineDatabaseUtil.KEY_SUNDAY, RoutineDatabaseUtil.KEY_START_HOUR,
                        RoutineDatabaseUtil.KEY_START_MINUTE, RoutineDatabaseUtil.KEY_TOTAL_TIME_HOUR,
                        RoutineDatabaseUtil.KEY_TOTAL_TIME_MINUTE},
                RoutineDatabaseUtil.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Routine routine = new Routine();
        //add setId to routine class - later I'm too tired for this
        //routine.setId(Integer.parseInt(cursor.getString(0)));
        routine.setTitle(cursor.getString(1));

        //set all days of the week! consider replacing original enumeration with 1's and 0's
        Routine.Weekday[] weekdays = routine.getWeekdays();
        for (int i = 0; i < weekdays.length; i++) {
            //weekdays[i] =
            //routine.setWeekdays(Integer.parseInt(cursor.getString(0)));
        }


        routine.setStartHour(Integer.parseInt(cursor.getString(9)));
        routine.setStartMinute(Integer.parseInt(cursor.getString(10)));
        routine.setTotalTimeHour(Integer.parseInt(cursor.getString(11)));
        routine.setTotalTimeMinute(Integer.parseInt(cursor.getString(12)));

        return routine;
    }


}