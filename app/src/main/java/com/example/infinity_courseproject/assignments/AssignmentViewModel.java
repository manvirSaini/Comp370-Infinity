package com.example.infinity_courseproject.assignments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class AssignmentViewModel extends AndroidViewModel {
    public static AssignmentRepo repo;
    public final LiveData<List<Assignment>> allAssignments;
    private MutableLiveData text;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        repo = new AssignmentRepo(application);
        allAssignments = repo.getAllAssignments();
        text = new MutableLiveData<>();
        text.setValue("Assignments Fragment");
    }

    public LiveData<List<Assignment>> getAllAssignments() {
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

    public LiveData<String> getText(){return text;}
}
