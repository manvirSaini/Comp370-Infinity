package com.example.infinity_courseproject.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.infinity_courseproject.domain.entity.Assignment;

import java.util.List;

@Dao
public interface  AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assignment assignment);

    @Delete
    void delete(Assignment assignment);

    @Query("DELETE FROM assignment_table")
    void deleteAll();

    @Update
    void update(Assignment assignment);

    @Query("SELECT * FROM assignment_table WHERE title LIKE :title")
    LiveData<Assignment> getByTitle(String title);

    @Query("SELECT * FROM assignment_table WHERE id = :id")
    LiveData<Assignment> get(int id);

    @Query("SELECT * FROM assignment_table WHERE id = :id")
    Assignment getImmediate(int id);

    @Query("SELECT * FROM assignment_table ORDER BY due_time IS NULL, due_time ASC, title")
    LiveData<List<Assignment>> getAssignmentsOrderByDueTime();
}
