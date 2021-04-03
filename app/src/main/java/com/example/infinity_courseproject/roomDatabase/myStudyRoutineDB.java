package com.example.infinity_courseproject.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.infinity_courseproject.assignments.Assignment;
import com.example.infinity_courseproject.assignments.AssignmentDao;
import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.courses.CourseDao;
import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineDao;

@Database(entities = {Routine.class, Course.class, Assignment.class},
        version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class myStudyRoutineDB extends RoomDatabase {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String DB_NAME = "mystudy_routine_DB";

    public static final int THREADS_FOR_IN_ORDER_THREADING = 1;
    public static final int THREADS_FOR_ANY_ORDER_THREADING = 4;

    public abstract RoutineDao routineDao();
    public abstract CourseDao courseDao();
    public abstract AssignmentDao assignmentDao();

    private static volatile myStudyRoutineDB INSTANCE;

    /**
     * Executor services - used to run extra threads in the background
     */
    //this executor is used when the order of inputs matters (useful for testing,
    //as it makes sure that a course is entered into the DB before associated assignments)
    public static final ExecutorService inOrderDatabaseWriteExecutor
            = Executors.newFixedThreadPool(THREADS_FOR_IN_ORDER_THREADING);

    //for cases when input into the tables can occur in any order
    public static final ExecutorService anyOrderDatabaseWriteExecutor
            = Executors.newFixedThreadPool(THREADS_FOR_ANY_ORDER_THREADING);

    /**
     * Method to create singleton database - stay away from callbacks for prepopulation of tables
     * (migrations seem to prevent them from working)
     * @param context
     * @return
     */
    public static myStudyRoutineDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (myStudyRoutineDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            myStudyRoutineDB.class, DB_NAME)
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //title, prof, description
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'course_table' " +
                    "('id' INTEGER PRIMARY KEY, 'title' TEXT NOT NULL, " +
                    "'professor' TEXT, 'description' TEXT)");
        }
    };

    //title, course_title, dueTime, descrip, markasupcoming
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'assignment_table' ('id' INTEGER" +
                    " PRIMARY KEY, 'title' TEXT NOT NULL, 'course_id' INTEGER, 'due_time' TEXT, " +
                    "'description' TEXT, 'mark_as_upcoming' INTEGER NOT NULL," +
                    " 'complete' TINYINT NOT NULL, " +
                    "FOREIGN KEY('course_id') REFERENCES 'course_table'('id')" +
                    "ON DELETE SET NULL ON UPDATE CASCADE)");
        }
    };
}
