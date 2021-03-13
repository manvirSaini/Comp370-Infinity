package com.example.infinity_courseproject.routines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {

    public static RoutineRepo repo;
    public final LiveData<List<Routine>> allRoutines;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        repo = new RoutineRepo(application);
        allRoutines = repo.getRoutinesOrderByName();
    }

    public LiveData<Routine> getByTitle(String routineTitle) {

        return repo.getByTitle(routineTitle);
    }

    public LiveData<Routine> get(int id) {
        return repo.get(id);
    }

    public LiveData<List<Routine>> getRoutinesOrderByName() {
        return allRoutines;
    }

    public static void insert(Routine routine) {
        repo.insert(routine);
    }

    public static void delete(Routine routine) {
        repo.delete(routine);
    }

    public static void update(Routine routine) {
        repo.update(routine);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }
}
