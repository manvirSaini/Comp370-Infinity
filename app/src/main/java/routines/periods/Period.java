package routines.periods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import courses.Course;
import routines.Routine;

@Entity(tableName = "period_table", primaryKeys = {"position", "routine_title"}, foreignKeys =
        {@ForeignKey(entity = Course.class, parentColumns = "title", childColumns = "course_title",
                onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Routine.class, parentColumns = "title", childColumns = "routine_title",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class Period {

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
