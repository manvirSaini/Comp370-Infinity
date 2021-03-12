package com.example.infinity_courseproject.routines.periods;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

/**
 * Dao class for the Period entity
 * Note that there is no updating periods; upon submitting an edited routine, all previous
 * routines are deleted and the new ones are entered
 */
@Dao
public interface PeriodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Period period);

    @Delete
    void delete(Period period);

    @Query("SELECT * FROM period_table WHERE routine_title LIKE :routineTitle AND" +
            " position = :periodPosition")
    LiveData<Period> get(String routineTitle, int periodPosition);

    @Query("DELETE FROM period_table")
    void deleteAll();

    @Query("SELECT * FROM period_table")
    LiveData<List<Period>> getAllPeriods();

    @Query("SELECT * FROM period_table WHERE routine_title = :routineTitle ORDER BY position ASC")
    LiveData<List<Period>> getRoutinePeriods(String routineTitle);

    @Query("SELECT * FROM period_table WHERE course_title = :courseTitle")
    LiveData<List<Period>> getCoursePeriods(String courseTitle);

}
