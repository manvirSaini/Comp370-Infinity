package com.example.infinity_courseproject.routines;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;

public class RoutineRepo {
    private RoutineDao routineDao;
    private LiveData<List<Routine>> allRoutines;

    public RoutineRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        routineDao = db.routineDao();

        allRoutines = routineDao.getAllRoutines();
    }

    public void insert(Routine routine) {
        myStudyRoutineDB.inOrderDatabaseWriteExecutor.execute(() -> routineDao.insert(routine));
    }

    public void update(Routine routine) {
        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.update(routine));
    }

    public void delete(Routine routine) {

        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.delete(routine));
    }

    public void deleteAll() {

        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.deleteAll());
    }

    public LiveData<Routine> get(Routine routine) {
        return routineDao.get(routine.getTitle());
    }

    public RoutineDao getRoutineDao() {
        return routineDao;
    }

    public void setRoutineDao(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    public LiveData<List<Routine>> getAllRoutines() {
        return allRoutines;
    }

    public void setAllRoutines(LiveData<List<Routine>> allRoutines) {
        this.allRoutines = allRoutines;
    }
}
