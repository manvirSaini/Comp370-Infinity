package com.example.infinity_courseproject.courses;


import java.io.Serializable;

public class Course2 implements Serializable {

    private String mId;
    private String title;
    private String professor;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
