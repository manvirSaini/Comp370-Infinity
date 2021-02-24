package routines;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is temporary. It will likely be replaced by a viewmodel (looking into it).
 * NOTE THAT IN THE THE PARTITIONED_ROUTINES ARRAYLIST, INDEX 0 REPRESENTS MONDAY, NOT SUNDAY
 */

public class RoutineDataManager {
    //enumerations
    //the 'Show' enum is partially redundant (since the 'Routine' class contains an enum for weekdays); fixable?
    enum OrderingMethod {NAME, TOTAL_TIME}
    enum Show {ALL, GENERAL, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    //enum variables
    private static OrderingMethod orderBy = OrderingMethod.NAME;
    private static Show view = Show.ALL;
    //other variables
    private final ArrayList<ArrayList<Routine>> partitionedRoutines;
    private Routine currentRoutine;
    private Routine defaultWeekdayRoutine;
    private Routine defaultRoutine;

    /**
     * Constructor
     * @param currentRoutine - routine currently in use
     * @param defaultWeekdayRoutine - routine to be autoselected for the specified
     * @param defaultRoutine
     */
    public RoutineDataManager(Routine currentRoutine, Routine defaultWeekdayRoutine, Routine defaultRoutine) {
        this.partitionedRoutines = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            partitionedRoutines.add(new ArrayList<>());
        }
        this.currentRoutine = currentRoutine;
        this.defaultWeekdayRoutine = defaultWeekdayRoutine;
        this.defaultRoutine = defaultRoutine;
    }

    private void sortPartitionedRoutines() {
        for (ArrayList<Routine> array : partitionedRoutines) {
            Collections.sort(array);
        }
    }

    public void addRoutine(Routine routine) {
        int[] weekdays = routine.getWeekdays();
        ArrayList<Routine> partition = null;
        boolean weekdayFound = false;
        for (int i = 0; i < weekdays.length; i++) {
            if (weekdays[i] == 1) {
                partition = partitionedRoutines.get(i);
                partition.add(routine);
                weekdayFound = true;
            }
        }
        if (!weekdayFound) {
            partition = partitionedRoutines.get(7);
            partition.add(routine);
        }
        sortPartitionedRoutines();
    }

    public void deleteRoutine(Routine routine) {
        ArrayList<Routine> partition = getShownPartition();
        if (partition == null) {
            for (ArrayList<Routine> array : partitionedRoutines) {
                for (Routine r : array) {
                    if (r.equals(routine)) //may be a problem (might have to override equals method)
                        array.remove(r);
                }
            }
        }
        else {
            for (Routine r : partition) {
                if (r.equals(routine)) //may be a problem (might have to override equals method)
                    partition.remove(r);
            }
        }

    }

    /**
     *
     * @return - returns the selected partition within the partitionedRoutines ArrayList;
     * if returns null, then the entirety of partitionedRoutines is selected
     */
    public ArrayList<Routine> getShownPartition() {
        ArrayList<Routine> partition = null;
        switch(view) {
            case ALL:
                break;
            case GENERAL:
                partition = partitionedRoutines.get(7);
                break;
            case MONDAY:
                partition = partitionedRoutines.get(0);
                break;
            case TUESDAY:
                partition = partitionedRoutines.get(1);
                break;
            case WEDNESDAY:
                partition = partitionedRoutines.get(2);
                break;
            case THURSDAY:
                partition = partitionedRoutines.get(3);
                break;
            case FRIDAY:
                partition = partitionedRoutines.get(4);
                break;
            case SATURDAY:
                partition = partitionedRoutines.get(5);
                break;
            case SUNDAY:
                partition = partitionedRoutines.get(6);
        }
        return partition;
    }

    /**
     * Returns false if fails to find a routine to be the current routine.
     * @return
     */
    private boolean autoSelect() {
        if (currentRoutine == null) {
            if (defaultWeekdayRoutine == null) {
                if (defaultRoutine == null)
                    return false;
                else
                    currentRoutine = defaultRoutine;
            }
            else
                currentRoutine = defaultWeekdayRoutine;
        }
        return true;
    }

    //getters and setters

    public ArrayList<ArrayList<Routine>> getPartitionedRoutines() {
        return partitionedRoutines;
    }

    public Routine getCurrentRoutine() {
        return currentRoutine;
    }

    public void setCurrentRoutine(Routine currentRoutine) {
        this.currentRoutine = currentRoutine;
    }

    public Routine getDefaultWeekdayRoutine() {
        return defaultWeekdayRoutine;
    }

    public void setDefaultWeekdayRoutine(Routine defaultWeekdayRoutine) {
        this.defaultWeekdayRoutine = defaultWeekdayRoutine;
    }

    public Routine getDefaultRoutine() {
        return defaultRoutine;
    }

    public void setDefaultRoutine(Routine defaultRoutine) {
        this.defaultRoutine = defaultRoutine;
    }

    public static OrderingMethod getOrderBy() {
        return orderBy;
    }

    public static void setOrderBy(OrderingMethod orderBy) {
        RoutineDataManager.orderBy = orderBy;
    }

    public static Show getView() {
        return view;
    }

    public static void setView(Show view) {
        RoutineDataManager.view = view;
    }
}
