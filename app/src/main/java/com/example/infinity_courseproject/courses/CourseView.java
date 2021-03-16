package com.example.infinity_courseproject.courses;

import org.litepal.crud.DataSupport;


public class CourseView extends DataSupport {

    private String title;

    private String professor;

    private String description;

    private int ids;

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public CourseView(String title, String professor, String description, int ids) {
        this.title = title;
        this.professor = professor;
        this.description = description;
        this.ids = ids;
    }

    public String getTitle() {
        return title;
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
