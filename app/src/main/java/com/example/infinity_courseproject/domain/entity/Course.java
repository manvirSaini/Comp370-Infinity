package com.example.infinity_courseproject.domain.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
