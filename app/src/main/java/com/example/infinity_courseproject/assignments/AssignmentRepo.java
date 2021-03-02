package com.example.infinity_courseproject.assignments;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;


public class AssignmentRepo {
    private AssignmentDao assignmentDao;
    private LiveData<List<Assignment>> allAssignments;

    public AssignmentRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        assignmentDao = db.assignmentDao();

        allAssignments = assignmentDao.getAllAssignments();
    }

    public void insert(Assignment assignment) {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> assignmentDao.insert(assignment));
    }

    public void delete(Assignment assignment) {

        myStudyRoutineDB.databaseWriteExecutor.execute(() -> assignmentDao.delete(assignment));
    }

    public void deleteAll() {
        myStudyRoutineDB.databaseWriteExecutor.execute(() -> assignmentDao.deleteAll());
    }

    public AssignmentDao getAssignmentDao() {
        return assignmentDao;
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return allAssignments;
    }
}
