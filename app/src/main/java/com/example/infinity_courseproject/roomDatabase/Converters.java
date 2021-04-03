package com.example.infinity_courseproject.roomDatabase;

import androidx.room.TypeConverter;

import com.example.infinity_courseproject.routines.events.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static boolean[] fromStringToBooleanArray(String value) {
        Type listType = new TypeToken<boolean[]>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBooleanArrayToString(boolean[] list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static ArrayList<Event> fromStringToEventArrayList(String value) {
        Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromEventArrayListToString(ArrayList<Event> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }

}
