package com.example.infinity_courseproject.assignments;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Update;

import com.example.infinity_courseproject.roomDatabase.myStudyRoutineDB;

import java.util.List;


public class AssignmentRepo {
    private AssignmentDao assignmentDao;
    private LiveData<List<Assignment>> allAssignments;

    public AssignmentRepo(Application application) {
        myStudyRoutineDB db = myStudyRoutineDB.getDatabase(application);
        assignmentDao = db.assignmentDao();

        allAssignments = assignmentDao.getAssignmentsOrderByDueTime();
    }

    public void insert(Assignment assignment) {
        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.insert(assignment));
    }

    public void delete(Assignment assignment) {

        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.delete(assignment));
    }

    public void deleteAll() {
        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.deleteAll());
    }

    public void update(Assignment assignment) {
        myStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.update(assignment));
    }

    public LiveData<Assignment> getByTitle(String title) {
        return assignmentDao.getByTitle(title);
    }

    public LiveData<Assignment> get(int id) {
        return assignmentDao.get(id);
    }

    public Assignment getImmediate(int id) {
        return assignmentDao.getImmediate(id);
    }

    public AssignmentDao getAssignmentDao() {
        return assignmentDao;
    }

    public LiveData<List<Assignment>> getAssignmentsOrderByDueTime() {
        return allAssignments;
    }
}
