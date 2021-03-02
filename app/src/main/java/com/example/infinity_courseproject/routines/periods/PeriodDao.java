package com.example.infinity_courseproject.routines.periods;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PeriodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Period period);

    @Delete
    void delete(Period period);

    @Query("DELETE FROM period_table")
    void deleteAll();

    @Query("SELECT * FROM period_table")
    LiveData<List<Period>> getAllPeriods();

    @Query("SELECT * FROM period_table WHERE routine_title = :routineTitle")
    LiveData<List<Period>> getRoutinePeriods(String routineTitle);

    @Query("SELECT * FROM period_table WHERE course_title = :courseTitle")
    LiveData<List<Period>> getCoursePeriods(String courseTitle);

}
