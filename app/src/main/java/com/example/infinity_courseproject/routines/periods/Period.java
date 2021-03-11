package com.example.infinity_courseproject.routines.periods;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.infinity_courseproject.courses.Course;
import com.example.infinity_courseproject.routines.Routine;

@Entity(tableName = "period_table", primaryKeys = {"position", "routine_title"}, foreignKeys =
        {@ForeignKey(entity = Course.class, parentColumns = "title", childColumns = "course_title",
                onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Routine.class, parentColumns = "title", childColumns = "routine_title",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class Period implements Parcelable {

    @ColumnInfo(name = "position")
    private int position;

    @ColumnInfo(name = "study_minutes")
    private int studyMinutes; //time of study subperiod in minutes

    @ColumnInfo(name = "break_minutes")
    private int breakMinutes; //time of break subperiod in minutes

    @ColumnInfo(name = "course_title", index = true)
    @Nullable
    private String courseTitle;

    @ColumnInfo(name = "routine_title", index = true)
    @NonNull
    private String routineTitle;

    public Period(int position, int studyMinutes, int breakMinutes,
                  @Nullable String courseTitle, @NonNull String routineTitle) {
        this.position = position;
        this.studyMinutes = studyMinutes;
        this.breakMinutes = breakMinutes;
        this.courseTitle = courseTitle;
        this.routineTitle = routineTitle;
    }

    @Ignore
    protected Period(Parcel in) {
        position = in.readInt();
        studyMinutes = in.readInt();
        breakMinutes = in.readInt();
        courseTitle = in.readString();
        routineTitle = in.readString();
    }

    public static final Creator<Period> CREATOR = new Creator<Period>() {
        @Override
        public Period createFromParcel(Parcel in) {
            return new Period(in);
        }

        @Override
        public Period[] newArray(int size) {
            return new Period[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeInt(studyMinutes);
        dest.writeInt(breakMinutes);
        dest.writeString(courseTitle);
        dest.writeString(routineTitle);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStudyMinutes() {
        return studyMinutes;
    }

    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes = studyMinutes;
    }

    public int getBreakMinutes() {
        return breakMinutes;
    }

    public void setBreakMinutes(int breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    @Nullable
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(@Nullable String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @NonNull
    public String getRoutineTitle() {
        return routineTitle;
    }

    public void setRoutineTitle(@NonNull String routineTitle) {
        this.routineTitle = routineTitle;
    }
}
