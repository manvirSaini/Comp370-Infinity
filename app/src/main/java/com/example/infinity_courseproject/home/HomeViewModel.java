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

    public MutableLiveData<ArrayList<Event>> getEventLiveData() {
        return eventLiveData;
    }

    public void setEventLiveData(ArrayList<Event> eventList) {
        eventCopiedData.addAll(eventList);
        eventLiveData.postValue(eventCopiedData);
    }

    public void addEvent() {
        Period studyPeriod = new Period(Period.Devotion.STUDY, 60);
        Period breakPeriod = new Period(Period.Devotion.BREAK, 15);
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(studyPeriod);
        periods.add(breakPeriod);

        Event event = new Event(periods, 0);
        eventCopiedData.add(event);
        eventLiveData.postValue(eventCopiedData);
    }

    public ArrayList<Event> getEventCopiedData() {
        return eventCopiedData;
    }



}
