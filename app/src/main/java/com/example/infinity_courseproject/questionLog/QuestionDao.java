package com.example.infinity_courseproject.questionLog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Delete
    void delete(Question question);

    @Query("DELETE FROM question_table")
    void deleteAll();

    @Query("SELECT * FROM question_table")
    LiveData<List<Question>> getAllQuestions();

}
