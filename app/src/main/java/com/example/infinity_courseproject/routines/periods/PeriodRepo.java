package com.example.infinity_courseproject.routines.periods;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;

import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.routines.Routine;

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
        myStudyRoutineDB.inOrderDatabaseWriteExecutor.execute(() ->
        periodDao.insert(period));
    }

    public void delete(Period period) {

        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() ->
                periodDao.delete(period));
    }

    public void deleteAll() {
        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() ->
                periodDao.deleteAll());
    }

    public LiveData<Period> get(Routine routine, Period period) {
        return periodDao.get(routine.getTitle(), period.getPosition());
    }

    public PeriodDao getPeriodDao() {
        return periodDao;
    }

    public LiveData<List<Period>> getAllPeriods() {
        return allPeriods;
    }
}
