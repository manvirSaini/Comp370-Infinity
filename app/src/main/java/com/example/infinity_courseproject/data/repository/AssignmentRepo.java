package com.example.infinity_courseproject.data.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.data.database.dao.AssignmentDao;import com.example.infinity_courseproject.data.database.MyStudyRoutineDB;
import com.example.infinity_courseproject.domain.entity.Assignment;

import java.util.List;


public class AssignmentRepo {
    private AssignmentDao assignmentDao;
    private LiveData<List<Assignment>> allAssignments;

    public AssignmentRepo(Application application) {
        MyStudyRoutineDB db = MyStudyRoutineDB.getDatabase(application);
        assignmentDao = db.assignmentDao();

        allAssignments = assignmentDao.getAssignmentsOrderByDueTime();
    }

    public void insert(Assignment assignment) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.insert(assignment));
    }

    public void delete(Assignment assignment) {

        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.delete(assignment));
    }

    public void deleteAll() {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.deleteAll());
    }

    public void update(Assignment assignment) {
        MyStudyRoutineDB.anyOrderDatabaseWriteExecutor.execute(() -> assignmentDao.update(assignment));
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
