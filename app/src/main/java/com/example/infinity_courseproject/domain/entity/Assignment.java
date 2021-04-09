package com.example.infinity_courseproject.domain.entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

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
    private LocalDateTime dueTime;

    @ColumnInfo(name = "description")
    @Nullable
    private String description;

    @ColumnInfo(name = "mark_as_upcoming") //this int value corresponds to days
    private int daysPriorToUpcoming; //0 if never mark as upcoming, 1 if mark as upcoming 1 day prior...

    @ColumnInfo(name = "complete")
    private boolean complete;

    public enum Status {NEUTRAL, COMPLETE, UPCOMING, OVERDUE}

    public Assignment(@NonNull String title, @Nullable Integer courseId, @Nullable LocalDateTime dueTime,
                      @Nullable String description, int daysPriorToUpcoming, boolean complete) {
        this.title = title;
        this.courseId = courseId;
        this.dueTime = dueTime;
        this.description = description;
        this.daysPriorToUpcoming = daysPriorToUpcoming;
        this.complete = complete;
    }

    public Status determineStatus() {
        Status status = Status.NEUTRAL;
        LocalDateTime currentDate = LocalDateTime.now();
        if (complete)
            status = Status.COMPLETE;
        else if (dueTime != null) {
            if (dueTime.isAfter(currentDate)) {
                if (daysPriorToUpcoming != 0) {
                    LocalDateTime upcomingDate = dueTime.minusDays(daysPriorToUpcoming);
                    if(upcomingDate.isBefore(currentDate))
                        status = Status.UPCOMING;
                }
            }
            else
                status = Status.OVERDUE;
        }

        return status;
    }

    public String stringifyStatus() {
        Status status = determineStatus();
        String stringStatus = null;
        switch(status) {
            case NEUTRAL:
                stringStatus = "Neutral";
                break;
            case COMPLETE:
                stringStatus = "Complete";
                break;
            case OVERDUE:
                stringStatus = "Overdue";
                break;
            case UPCOMING:
                stringStatus = "Upcoming";
                break;
        }
        return stringStatus;
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
    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(@Nullable LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getDaysPriorToUpcoming() {
        return daysPriorToUpcoming;
    }

    public void setDaysPriorToUpcoming(int daysPriorToUpcoming) {
        this.daysPriorToUpcoming = daysPriorToUpcoming;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
