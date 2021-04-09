package com.example.infinity_courseproject.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.data.repository.AssignmentRepo;
import com.example.infinity_courseproject.domain.entity.Assignment;

import java.util.List;


public class AssignmentViewModel extends AndroidViewModel {
    public static AssignmentRepo repo;
    public final LiveData<List<Assignment>> allAssignments;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        repo = new AssignmentRepo(application);
        allAssignments = repo.getAssignmentsOrderByDueTime();
    }

    public LiveData<List<Assignment>> getAssignmentsOrderByDueTime() {
        return allAssignments;
    }

    public static void insert(Assignment assignment) {
        repo.insert(assignment);
    }

    public static void delete(Assignment assignment) {
        repo.delete(assignment);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }

    public static void update(Assignment assignment) {
        repo.update(assignment);
    }

    public LiveData<Assignment> getByTitle(String title) {
        return repo.getByTitle(title);
    }

    public LiveData<Assignment> get(int id) {
        return repo.get(id);
    }

    public Assignment getImmediate(int id) {
        return repo.getImmediate(id);
    }

}
