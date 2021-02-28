package questionLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import courses.Course;

@Entity(tableName = "question_table", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "title", childColumns = "course_title",
        onDelete = ForeignKey.SET_NULL, onUpdate = ForeignKey.CASCADE)})
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "question")
    @NonNull
    private String question;

    @ColumnInfo(name = "course_title")
    @Nullable
    private String courseTitle;

    public Question(@NonNull String question, @Nullable String courseTitle) {
        this.question = question;
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @Nullable
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(@Nullable String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
