package com.example.infinity_courseproject.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.infinity_courseproject.routines.Routine;
import com.example.infinity_courseproject.routines.RoutineRepo;
import com.example.infinity_courseproject.routines.events.Event;
import com.example.infinity_courseproject.routines.periods.Period;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    public static RoutineRepo respository;
    public final  LiveData<List<Routine>> allRoutines;
    private  MutableLiveData<List<Routine>> listRoutines;

    private MutableLiveData<ArrayList<Event>> eventLiveData;
    //copy of mutable data contents
    private ArrayList<Event> eventCopiedData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        respository = new RoutineRepo(application);
        allRoutines = respository.getRoutinesOrderByName();
    }

    public LiveData<List<Routine>> getAllRoutines(){
        return allRoutines;
    }

    public static void insert(Routine routine){
        respository.insert(routine);
    }

    public static void deleteAll(){
        respository.deleteAll();
    }

    public LiveData <Routine> get(int id){
        return respository.get(id);
    }




}
