package com.example.infinity_courseproject.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.infinity_courseproject.domain.entity.Routine;

import java.util.List;

@Dao
public interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Routine routine);

    @Delete
    void delete(Routine routine);

    @Update
    void update(Routine routine);

    @Query("SELECT * FROM routine_table WHERE title LIKE :title")
    LiveData<Routine> getByTitle(String title);

    @Query("SELECT * FROM routine_table WHERE id = :id")
    LiveData<Routine> get(int id);

    @Query("DELETE FROM routine_table")
    void deleteAll();

    @Query("SELECT * FROM routine_table ORDER BY title ASC")
    LiveData<List<Routine>> getRoutinesOrderByName();

    @Query("SELECT * FROM routine_table WHERE title LIKE :title")
    Routine getImmediate(String title);
}
