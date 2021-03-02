package com.example.infinity_courseproject.courses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;

public class CourseRepo {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        courseDao = db.courseDao();

        allCourses = courseDao.getAllCourses();
    }

    public void insert(Course course) {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> courseDao.insert(course));
    }

    public void delete(Course course) {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> courseDao.delete(course));
    }

    public void deleteAll() {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> courseDao.deleteAll());
    }

    public CourseDao getCourseDao() {
        return courseDao;
    }

    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(LiveData<List<Course>> allCourses) {
        this.allCourses = allCourses;
    }
}
