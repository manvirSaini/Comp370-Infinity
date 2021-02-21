package routines;

public class Period {
    //private Course course;
    private int study_time;
    private int break_time;

    public Period(int study_time, int break_time) {
        this.study_time = study_time;
        this.break_time = break_time;
    }

    //getters and setters

    public int getStudy_time() {
        return study_time;
    }

    public void setStudy_time(int study_time) {
        this.study_time = study_time;
    }

    public int getBreak_time() {
        return break_time;
    }

    public void setBreak_time(int break_time) {
        this.break_time = break_time;
    }
}
