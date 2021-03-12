package com.example.infinity_courseproject.roomDatabase;

import androidx.room.TypeConverter;

import com.example.infinity_courseproject.routines.periods.Period;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
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
    public static ArrayList<Period> fromStringToPeriodArrayList(String value) {
        Type listType = new TypeToken<ArrayList<Period>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromPeriodArrayListToString(ArrayList<Period> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

//    @TypeConverter
//    public static Timestamp fromTimestamp(Long value) {
//        return value == null ? null : new Timestamp(value);
//    }
//
//    @TypeConverter
//    public static Long fromLong(Timestamp timestamp) {
//        return timestamp == null ? null : timestamp.getTime();
//    }

}
