package com.example.infinity_courseproject.routines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.infinity_courseproject.routines.periods.Period;
import com.example.infinity_courseproject.routines.periods.PeriodViewModel;

import java.util.List;
import java.util.Objects;

public class RoutineViewModel extends AndroidViewModel {

    public static RoutineRepo repo;
    public final LiveData<List<Routine>> allRoutines;
    private static boolean orderByTotalTime = false; //if false, routines are ordered by title

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        repo = new RoutineRepo(application);
        allRoutines = repo.getAllRoutines();
    }

    /**
     * Method for ordering by routines by their total time.
     * @return - the resulting ordered routines list
     */
    public List<Routine> orderRoutinesByTotalTime(PeriodViewModel periodViewModel) {
        List<Routine> routines = Objects.requireNonNull(allRoutines.getValue());
        int[] totalTimeArray = new int[routines.size()];
        for (int i=0; i < totalTimeArray.length; i++) {
            totalTimeArray[i] = periodViewModel.getTotalRoutineTime(routines.get(i));
        }

        //selection sort
        for (int i = 0; i < totalTimeArray.length-1; i++)
        {
            // Find the minimum element in unsorted array
            int maxIndex = i;
            for (int j = i+1; j < totalTimeArray.length; j++)
                if (totalTimeArray[j] > totalTimeArray[maxIndex]) {
                    maxIndex = j;
                }
            //swap maxIndex with idx i for the routines array and the int array
            Routine longestRoutine = routines.get(maxIndex);
            routines.set(0, routines.get(i));
            routines.set(i, longestRoutine);

            int temp = totalTimeArray[maxIndex];
            totalTimeArray[maxIndex] = totalTimeArray[i];
            totalTimeArray[i] = temp;
        }

        return routines;
    }

    public LiveData<Routine> get(String routineTitle) {

        return repo.get(routineTitle);
    }

    public LiveData<List<Routine>> getAllRoutines() {

        return allRoutines;
    }

    public static void insert(Routine routine) {
        repo.insert(routine);
    }

    public static void delete(Routine routine) {
        repo.delete(routine);
    }

    public void update(Routine routine) {
        repo.update(routine);
    }

    public static void deleteAll() {
        repo.deleteAll();
    }

    public static boolean isOrderByTotalTime() {
        return orderByTotalTime;
    }

    public static void setOrderByTotalTime(boolean orderByTotalTime) {
        RoutineViewModel.orderByTotalTime = orderByTotalTime;
    }
}
