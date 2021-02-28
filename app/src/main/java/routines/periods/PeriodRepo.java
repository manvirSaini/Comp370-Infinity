package routines.periods;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.RoomDatabase.myStudyRoutineDB;

import java.util.List;

import courses.Course;
import routines.Routine;

public class PeriodRepo {
    private PeriodDao periodDao;
    private LiveData<List<Period>> allPeriods;

    public PeriodRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        periodDao = db.periodDao();

        allPeriods = periodDao.getAllPeriods();
    }

    public LiveData<List<Period>> getRoutinePeriods(Routine routine) {
        return periodDao.getRoutinePeriods(routine.getTitle());
    }

    public LiveData<List<Period>> getCoursePeriods(Course course) {
        return periodDao.getCoursePeriods(course.getTitle());
    }

    public void insert(Period period) {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> periodDao.insert(period));
    }

    public void delete(Period period) {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> periodDao.delete(period));
    }

    public void deleteAll() {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> periodDao.deleteAll());
    }

    public PeriodDao getPeriodDao() {
        return periodDao;
    }

    public LiveData<List<Period>> getAllPeriods() {
        return allPeriods;
    }
}
