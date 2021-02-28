package routines.periods;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import courses.Course;
import routines.Routine;

public class PeriodViewModel extends AndroidViewModel {
    public static PeriodRepo repo;
    public final LiveData<List<Period>> allPeriods;

    public PeriodViewModel(@NonNull Application application) {
        super(application);
        repo = new PeriodRepo(application);
        allPeriods = repo.getAllPeriods();
    }

    public LiveData<List<Period>> getAllPeriods() {
        return allPeriods;
    }

    public LiveData<List<Period>> getRoutinePeriods(@NonNull Routine routine) {
        return repo.getRoutinePeriods(routine);
    }

    /**
     *
     * @param course - if null, returns all periods that do not have a course
     * @return - list of periods related to specified course
     */
    public LiveData<List<Period>> getCoursePeriods(Course course) {
        return repo.getCoursePeriods(course);
    }

    public static void insert(Period period) {
        repo.insert(period);
    }

    public static void delete(Period period) {
        repo.delete(period);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }

}
