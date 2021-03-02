package courses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.appcompat.app.AppCompatActivity;
public class Course extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_main);
    }
}

@Entity(tableName = "course_table", primaryKeys = {"title"})
public class Course {

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "professor")
    @Nullable
    private String professor;

    @ColumnInfo(name = "description")
    @Nullable
    private String description;


    public Course(@NonNull String title, @Nullable String professor, @Nullable String description) {
        this.title = title;
        this.professor = professor;
        this.description = description;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public String getProfessor() {
        return professor;
    }

    public void setProfessor(@Nullable String professor) {
        this.professor = professor;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
