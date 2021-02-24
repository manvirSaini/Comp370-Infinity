package questionLog;

public class Question {
    private int id;
    private String question;
    private int course_id; //you can get course by using this id

    public Question() {
    }

    public Question(int id, String question, int course_id) {
        this.id = id;
        this.question = question;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
