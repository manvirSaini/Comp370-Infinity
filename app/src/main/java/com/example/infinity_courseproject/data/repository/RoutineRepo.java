package com.example.infinity_courseproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.data.database.MyStudyRoutineDB;
import com.example.infinity_courseproject.domain.entity.Routine;
import com.example.infinity_courseproject.data.database.dao.RoutineDao;

import java.util.List;

public class RoutineRepo {
    private RoutineDao routineDao;
    private LiveData<List<Routine>> allRoutines;

    public RoutineRepo(Application application) {
        MyStudyRoutineDB db = MyStudyRoutineDB.getDatabase(application);
        routineDao = db.routineDao();

        allRoutines = routineDao.getRoutinesOrderByName();
    }

    public void insert(Routine routine) {
        MyStudyRoutineDB.inOrderDatabaseWriteExecutor.execute(() -> routineDao.insert(routine));
    }

    public void update(Routine routine) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.update(routine));
    }

    public void delete(Routine routine) {

        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.delete(routine));
    }

    public void deleteAll() {

        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> routineDao.deleteAll());
    }

    public LiveData<Routine> getByTitle(String routineTitle) {
        return routineDao.getByTitle(routineTitle);
    }

    public LiveData<Routine> get(int id) {
        return routineDao.get(id);
    }

    public RoutineDao getRoutineDao() {
        return routineDao;
    }

    public void setRoutineDao(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    public LiveData<List<Routine>> getRoutinesOrderByName() {
        return allRoutines;
    }

    public void setAllRoutines(LiveData<List<Routine>> allRoutines) {
        this.allRoutines = allRoutines;
    }

    public Routine getImmediate(String title) {
        return routineDao.getImmediate(title);
    }
}
