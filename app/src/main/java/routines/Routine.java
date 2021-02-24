package routines;

public class Routine implements Comparable<Routine> {
    private int id;
    //weekdays is an array of 1's and 0's, where 1 is bool value true, 0 is false, and
    //index 0 represents Monday; if weekdays[0] = 1, then this routine is associated with Monday
    //make sure this is always size 7
    private int[] weekdays;
    private String title;
    private int startHour; //we will be using the 24h clock
    private int startMinute;
    private int totalTimeHour;
    private int totalTimeMinute;

    public Routine() {
        this.weekdays = new int[7];
    }

    public Routine(int id, int[] weekdays, String title, int startHour, int startMinute) {
        this.id = id;
        this.weekdays = weekdays;
        this.title = title;
        this.startHour = startHour;
        this.startMinute = startMinute;
        //for the sake of total time
        //int[] totalTime = calculateTotalTimeInHoursAndMinutes();
        //this.totalTimeHour = totalTime[0];
        //this.totalTimeMinute = totalTime[1];
    }

    public Routine(int id, int[] weekdays, String title) {
        this.id = id;
        this.weekdays = weekdays;
        this.title = title;
        //24:00 does not exist (becomes 00:00 at midnight), therefore 24:00 will
        //represent a nonexistent start time
        this.startHour = 24;
        this.startMinute = 0;
        //int[] totalTime = calculateTotalTimeInHoursAndMinutes();
        //this.totalTimeHour = totalTime[0];
        //this.totalTimeMinute = totalTime[1];
    }

    //find a way to get the periods for routine using database

    /*
    private int[] calculateTotalTimeInHoursAndMinutes() {
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
    */
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
        return totalTimeHour * 60 + totalTimeMinute;
    }

    //Getters and Setters

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

    public int[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(int[] weekdays) {
        this.weekdays = weekdays;
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
