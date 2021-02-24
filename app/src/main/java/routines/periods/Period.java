package routines.periods;

public class Period {
    private int id;
    private int position;
    private int studyTimeHour;
    private int studyTimeMinute;
    private int breakTimeHour;
    private int breakTimeMinute;
    private int courseId;
    private int routineId;

    public Period() {

    }

    public Period(int id, int position, int studyTimeHour, int studyTimeMinute,
                  int breakTimeHour, int breakTimeMinute, int courseId, int routineId) {
        this.id = id;
        this.position = position;
        this.studyTimeHour = studyTimeHour;
        this.studyTimeMinute = studyTimeMinute;
        this.breakTimeHour = breakTimeHour;
        this.breakTimeMinute = breakTimeMinute;
        this.courseId = courseId;
        this.routineId = routineId;
    }

    public Period(int id, int position, int studyTimeHour, int studyTimeMinute,
                  int courseId, int routineId) {
        this.id = id;
        this.position = position;
        this.studyTimeHour = studyTimeHour;
        this.studyTimeMinute = studyTimeMinute;
        this.breakTimeHour = 0;
        this.breakTimeMinute = 0;
        this.courseId = courseId;
        this.routineId = routineId;
    }



    /**
     *
     * @return - an int representing total minutes
     */
    public int calculateTotalTimeInMinutes() {
        return (studyTimeHour + breakTimeHour) * 60 + breakTimeMinute+studyTimeMinute;
    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public int getStudyTimeHour() {
        return studyTimeHour;
    }

    public void setStudyTimeHour(int studyTimeHour) {
        this.studyTimeHour = studyTimeHour;
    }

    public int getStudyTimeMinute() {
        return studyTimeMinute;
    }

    public void setStudyTimeMinute(int studyTimeMinute) {
        this.studyTimeMinute = studyTimeMinute;
    }

    public int getBreakTimeHour() {
        return breakTimeHour;
    }

    public void setBreakTimeHour(int breakTimeHour) {
        this.breakTimeHour = breakTimeHour;
    }

    public int getBreakTimeMinute() {
        return breakTimeMinute;
    }

    public void setBreakTimeMinute(int breakTimeMinute) {
        this.breakTimeMinute = breakTimeMinute;
    }
}
