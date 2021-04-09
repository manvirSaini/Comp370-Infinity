package com.example.infinity_courseproject.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.domain.entity.Course;
import com.example.infinity_courseproject.data.database.dao.CourseDao;
import com.example.infinity_courseproject.data.database.MyStudyRoutineDB;

import java.util.List;

public class CourseRepo {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepo(Application application) {
        MyStudyRoutineDB db = MyStudyRoutineDB.getDatabase(application);
        courseDao = db.courseDao();

        allCourses = courseDao.getAllCourses();
    }

    public void insert(Course course) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> courseDao.insert(course));
    }

    public void delete(Course course) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> courseDao.delete(course));
    }

    public void deleteAll() {

        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> courseDao.deleteAll());
    }

    public void update(Course course) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> courseDao.update(course));
    }

    public LiveData<Course> getByTitle(String title) {
        return courseDao.getByTitle(title);
    }

    public LiveData<Course> get(int id) {
        return courseDao.get(id);
    }

    public Course getImmediate(int id) {
        return courseDao.getImmediate(id);
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
