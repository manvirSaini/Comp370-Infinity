package routines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {

    public static RoutineRepo repo;
    public final LiveData<List<Routine>> allRoutines;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        repo = new RoutineRepo(application);
        allRoutines = repo.getAllRoutines();
    }

    public LiveData<List<Routine>> getAllRoutines() {
        return allRoutines;
    }

    public static void insert(Routine routine) {
        repo.insert(routine);
    }

    public static void delete(Routine routine) {
        repo.delete(routine);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }
}
