package assignments;

public class Assignment {
    private int id;
    private String title;
    private int courseId;
    private String dueDate;
    private int dueTimeHour; //24 hour clock
    private int dueTimeMinute;
    private String description;
    private int markAsUpcoming; //0 if never mark as upcoming, 1 if mark as upcoming 1 day prior...

    public Assignment() {
    }

    public Assignment(int id, String title, int courseId, String dueDate, int dueTimeHour,
                      int dueTimeMinute, String description, int markAsUpcoming) {
        this.id = id;
        this.title = title;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.dueTimeHour = dueTimeHour;
        this.dueTimeMinute = dueTimeMinute;
        this.description = description;
        this.markAsUpcoming = markAsUpcoming;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getDueTimeHour() {
        return dueTimeHour;
    }

    public void setDueTimeHour(int dueTimeHour) {
        this.dueTimeHour = dueTimeHour;
    }

    public int getDueTimeMinute() {
        return dueTimeMinute;
    }

    public void setDueTimeMinute(int dueTimeMinute) {
        this.dueTimeMinute = dueTimeMinute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMarkAsUpcoming() {
        return markAsUpcoming;
    }

    public void setMarkAsUpcoming(int markAsUpcoming) {
        this.markAsUpcoming = markAsUpcoming;
    }
}
