package com.example.infinity_courseproject.questionLog;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;

public class QuestionRepo {
    private QuestionDao questionDao;
    private LiveData<List<Question>> allQuestions;

    public QuestionRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        //questionDao = db.questionDao();

        allQuestions = questionDao.getAllQuestions();
    }

    public void insert(Question question) {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> questionDao.insert(question));
    }

    public void delete(Question question) {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> questionDao.delete(question));
    }

    public void deleteAll() {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> questionDao.deleteAll());
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }
}
