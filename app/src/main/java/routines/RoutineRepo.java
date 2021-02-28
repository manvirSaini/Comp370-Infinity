package routines;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.RoomDatabase.myStudyRoutineDB;

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
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> routineDao.insert(routine));
    }

    public void delete(Routine routine) {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> routineDao.delete(routine));
    }

    public void deleteAll() {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> routineDao.deleteAll());
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
