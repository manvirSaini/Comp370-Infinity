package com.example.infinity_courseproject.roomDatabase;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class Converters {
    @TypeConverter
    public static boolean[] fromString(String value) {
        Type listType = new TypeToken<boolean[]>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBooleanArray(boolean[] list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long fromLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }

}
