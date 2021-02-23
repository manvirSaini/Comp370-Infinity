package routines;

public class Period {
    //private Course course;
    private int studyTimeHour;
    private int studyTimeMinute;
    private int breakTimeHour;
    private int breakTimeMinute;

    public Period(int studyTimeHour, int studyTimeMinute) {
        this.studyTimeHour = studyTimeHour;
        this.studyTimeMinute = studyTimeMinute;
        this.breakTimeHour = 0;
        this.breakTimeMinute = 0;
    }

    public Period(int studyTimeHour, int studyTimeMinute, int breakTimeHour, int breakTimeMinute) {
        this.studyTimeHour = studyTimeHour;
        this.studyTimeMinute = studyTimeMinute;
        this.breakTimeHour = breakTimeHour;
        this.breakTimeMinute = breakTimeMinute;
    }

    /**
     *
     * @return - an int representing total minutes
     */
    public int calculateTotalTimeInMinutes() {
        return (studyTimeHour + breakTimeHour) * 60 + breakTimeMinute+studyTimeMinute;
    }

    //getters and setters


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
