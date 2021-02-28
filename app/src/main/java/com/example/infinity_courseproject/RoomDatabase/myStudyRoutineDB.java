package com.example.infinity_courseproject.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import assignments.Assignment;
import assignments.AssignmentDao;
import courses.Course;
import courses.CourseDao;
import routines.periods.Period;
import routines.periods.PeriodDao;
import routines.Routine;
import routines.RoutineDao;

@Database(entities = {Routine.class, Course.class, Period.class, Assignment.class},
        version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class myStudyRoutineDB extends RoomDatabase {
    private static final String DB_NAME = "mystudy_routine_DB";
    public static final int NUM_OF_THREADS = 4;

    public abstract RoutineDao routineDao();
    public abstract CourseDao courseDao();
    public abstract PeriodDao periodDao();
    public abstract AssignmentDao assignmentDao();
    //public abstract QuestionDao questionDao();

    private static volatile myStudyRoutineDB INSTANCE;

    /**
     * Executor service - used to run extra threads in the background
     * Note that this is used to implement insert methods, and currently may lead to poorly ordered
     * thread executions resulting in database failure
     */
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUM_OF_THREADS);

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
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
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
                    "('title' TEXT PRIMARY KEY, 'professor' TEXT, 'description' TEXT)");
        }
    };

    //pos, study_minutes, break_minutes, course_title, routine_title
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'period_table' ('position' INTEGER," +
                    "'study_minutes' INTEGER NOT NULL, 'break_minutes' INTEGER NOT NULL," +
                    "'course_title' TEXT, 'routine_title' TEXT," +
                    "PRIMARY KEY('position', 'routine_title')," +
                    "FOREIGN KEY('course_title') REFERENCES 'course_table'('title')" +
                    "ON DELETE SET NULL ON UPDATE CASCADE," +
                    "FOREIGN KEY('routine_title') REFERENCES 'routine_table'('title')" +
                    "ON DELETE CASCADE ON UPDATE CASCADE)");
        }
    };

    //title, course_title, dueTime, descrip, markasupcoming
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'assignment_table' ('title' TEXT " +
                    "PRIMARY KEY, 'course_title' TEXT, 'due_time' TIMESTAMP, " +
                    "'description' TEXT, 'mark_as_upcoming' INTEGER NOT NULL," +
                    "FOREIGN KEY('course_title') REFERENCES 'course_table'('title')" +
                    "ON DELETE SET NULL ON UPDATE CASCADE)");
        }
    };

    /* In case we decide to store the questions
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'period_table' ('position' INTEGER," +
                    "'course_title' TEXT, 'routine_title' TEXT," +
                    "PRIMARY KEY('position', 'routine_title')," +
                    "FOREIGN KEY('course_title') REFERENCES 'course_table'('title')" +
                    "ON DELETE SET NULL ON UPDATE CASCADE," +
                    "FOREIGN KEY('routine_title') REFERENCES 'routine_table'('title')" +
                    "ON DELETE CASCADE ON UPDATE CASCADE)");
        }
    };
    */
}
