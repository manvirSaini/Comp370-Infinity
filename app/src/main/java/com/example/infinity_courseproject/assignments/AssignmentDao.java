package com.example.infinity_courseproject.assignments;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.infinity_courseproject.routines.Routine;

import java.util.List;

@Dao
public interface AssignmentDao {
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

    @Query("SELECT * FROM assignment_table ORDER BY due_time DESC")
    LiveData<List<Assignment>> getAssignmentsOrderByDueTime();
}
