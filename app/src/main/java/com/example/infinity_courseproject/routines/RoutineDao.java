package com.example.infinity_courseproject.routines;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Routine routine);

    @Delete
    void delete(Routine routine);

    @Query("DELETE FROM routine_table")
    void deleteAll();

    @Query("SELECT * FROM routine_table")
    LiveData<List<Routine>> getAllRoutines();

}
