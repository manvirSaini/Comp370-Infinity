package routines;

import java.util.ArrayList;

public class Routine implements Comparable<Routine> {
    //enums
    enum Weekday {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    //enum variables
    private Weekday[] weekdays;
    //other variables
    private String title;
    private ArrayList<Period> periods;
    private int startHour; //we will be using the 24h clock because I am lazy
    private int startMinute;
    private int totalTimeHour;
    private int totalTimeMinute;

    public Routine() {
        this.weekdays = new Weekday[7];
        this.periods = new ArrayList<>();
    }

    public Routine(Weekday[] weekdays, String title, ArrayList<Period> periods,
                   int startHour, int startMinute) {
        this.weekdays = weekdays;
        this.title = title;
        this.periods = periods;
        this.startHour = startHour;
        this.startMinute = startMinute;
        //for the sake of total time
        int[] totalTime = calculateTotalTimeinHoursAndMinutes();
        this.totalTimeHour = totalTime[0];
        this.totalTimeMinute = totalTime[1];
    }

    public Routine(Weekday[] weekdays, String title, ArrayList<Period> periods) {
        this.weekdays = weekdays;
        this.title = title;
        this.periods = periods;
        //24:00 does not exist (becomes 00:00 at midnight), therefore 24:00 will
        //represent a nonexistent start time
        this.startHour = 24;
        this.startMinute = 0;
        int[] totalTime = calculateTotalTimeinHoursAndMinutes();
        this.totalTimeHour = totalTime[0];
        this.totalTimeMinute = totalTime[1];
    }

    private int[] calculateTotalTimeinHoursAndMinutes() {
        int[] totalTime = new int[2];
        int totalMinutes = 0;
        for (Period p : periods) {
            totalMinutes += p.calculateTotalTimeInMinutes();
        }
        //totalTime[0] holds hours, totalTime[1] holds minutes
        totalTime[0] = totalMinutes / 60;
        totalTime[1] = totalMinutes % 60;
        return totalTime;
    }

    @Override
    public int compareTo(Routine routine) {
        int weight = 0;
        switch(RoutineDataManager.getOrderBy()) {
            case NAME:
                weight = this.getTitle().compareTo(routine.getTitle());
                break;
            case TOTAL_TIME:
                weight = this.calculateTotalTimeInMinutes() - routine.calculateTotalTimeInMinutes();
        }
        return weight;
    }

    /**
     *
     * @return - an int representing total minutes
     */
    public int calculateTotalTimeInMinutes() {
        return startHour * 60 + startMinute;
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

    public int getTotalTimeHour() {
        return totalTimeHour;
    }

    public void setTotalTimeHour(int totalTimeHour) {
        this.totalTimeHour = totalTimeHour;
    }

    public int getTotalTimeMinute() {
        return totalTimeMinute;
    }

    public void setTotalTimeMinute(int totalTimeMinute) {
        this.totalTimeMinute = totalTimeMinute;
    }
}
