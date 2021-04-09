package com.example.infinity_courseproject.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.domain.entity.Course;
import com.example.infinity_courseproject.data.repository.CourseRepo;

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

    public static void update(Course course) {
        repo.update(course);
    }

    public LiveData<Course> getByTitle(String title) {
        return repo.getByTitle(title);
    }

    public LiveData<Course> get(int id) {
        return repo.get(id);
    }

    public Course getImmediate(int id) {return repo.getImmediate(id);}
}
