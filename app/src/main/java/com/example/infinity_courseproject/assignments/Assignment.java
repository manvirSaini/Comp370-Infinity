package com.example.infinity_courseproject.assignments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.infinity_courseproject.courses.Course;

@Entity(tableName = "assignment_table", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "id", childColumns = "course_id", onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE)})
public class Assignment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "course_id", index = true)
    @Nullable
    private Integer courseId;

    @ColumnInfo(name = "due_time")
    @Nullable
    private Long dueTime;

    @ColumnInfo(name = "description")
    @Nullable
    private String description;

    @ColumnInfo(name = "mark_as_upcoming") //this int value corresponds to days
    private int markAsUpcoming; //0 if never mark as upcoming, 1 if mark as upcoming 1 day prior...

    public Assignment(@NonNull String title, @Nullable Integer courseId, @Nullable Long dueTime,
                      @Nullable String description, int markAsUpcoming) {
        this.title = title;
        this.courseId = courseId;
        this.dueTime = dueTime;
        this.description = description;
        this.markAsUpcoming = markAsUpcoming;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(@Nullable Integer courseId) {
        this.courseId = courseId;
    }

    @Nullable
    public Long getDueTime() {
        return dueTime;
    }

    public void setDueTime(@Nullable Long dueTime) {
        this.dueTime = dueTime;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getMarkAsUpcoming() {
        return markAsUpcoming;
    }

    public void setMarkAsUpcoming(int markAsUpcoming) {
        this.markAsUpcoming = markAsUpcoming;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
