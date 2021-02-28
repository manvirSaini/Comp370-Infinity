package courses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    public static CourseRepo repo;
    public final LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public static void insert(Course course) {
        repo.insert(course);
    }

    public static void delete(Course course) {
        repo.delete(course);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }
}
