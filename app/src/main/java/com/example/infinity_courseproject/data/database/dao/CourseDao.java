package com.example.infinity_courseproject.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.infinity_courseproject.domain.entity.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course_table")
    void deleteAll();

    @Query("SELECT * FROM course_table ORDER BY title ASC")
    LiveData<List<Course>> getAllCourses();

    @Update
    void update(Course course);

    @Query("SELECT * FROM course_table WHERE title LIKE :title")
    LiveData<Course> getByTitle(String title);

    @Query("SELECT * FROM course_table WHERE id = :id")
    LiveData<Course> get(int id);

    @Query("SELECT * FROM course_table WHERE id = :id")
    Course getImmediate(int id);
}
