package routines;

import java.util.ArrayList;

public class Routine implements Comparable<Routine> {
    //enums
    enum Weekday {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    enum TimeSuffix {AM, PM}
    //enum variables
    private Weekday[] weekdays;
    private TimeSuffix suffix; //used with startHour and startMinute
    //other variables
    private String title;
    private ArrayList<Period> periods;
    private int startHour;
    private int startMinute;
    private int totalTime;

    public Routine(String title, int startHour, int startMinute, TimeSuffix suffix) {
        this.title = title;
        this.weekdays = new Weekday[7];
        this.periods = new ArrayList<>();
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.suffix = suffix;
    }

    @Override
    public int compareTo(Routine routine) {
        int weight = 0;
        switch(RoutineDataManager.getOrderBy()) {
            case NAME:
                weight = this.getTitle().compareTo(routine.getTitle());
                break;
            case TOTAL_TIME:
                weight = this.getTotalTime() - routine.getTotalTime();
        }
        return weight;
    }


    //Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Weekday[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(Weekday[] weekdays) {
        this.weekdays = weekdays;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<Period> periods) {
        this.periods = periods;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public TimeSuffix getSuffix() {
        return suffix;
    }

    public void setSuffix(TimeSuffix suffix) {
        this.suffix = suffix;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
