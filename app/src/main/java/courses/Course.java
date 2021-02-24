package courses;

public class Course {
    private int id;
    private String title;
    private String professor;
    private String description;

    public Course() {

    }

    public Course(int id, String title, String professor, String description) {
        this.id = id;
        this.title = title;
        this.professor = professor;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
