package assignments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import courses.Course;

@Entity(tableName = "assignment_table", primaryKeys = {"title"},
foreignKeys = {@ForeignKey(entity = Course.class, parentColumns = "title",
        childColumns = "course_title", onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE)})
public class Assignment {

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "course_title", index = true)
    @Nullable
    private String courseTitle;

    @ColumnInfo(name = "due_time")
    @Nullable
    private Long dueTime;

    @ColumnInfo(name = "description")
    @Nullable
    private String description;

    @ColumnInfo(name = "mark_as_upcoming") //this int value corresponds to days
    private int markAsUpcoming; //0 if never mark as upcoming, 1 if mark as upcoming 1 day prior...

    public Assignment(@NonNull String title, @Nullable String courseTitle, @Nullable Long dueTime,
                      @Nullable String description, int markAsUpcoming) {
        this.title = title;
        this.courseTitle = courseTitle;
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
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(@Nullable String courseTitle) {
        this.courseTitle = courseTitle;
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
}
