package routines;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "routine_table", primaryKeys = {"title"})
public class Routine {

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "weekdays")
    @NonNull
    private boolean[] weekdays;

    @ColumnInfo(name = "start_hour")
    @Nullable
    private Integer startHour; //24h clock; limit options to 0 - 23

    @ColumnInfo(name = "start_minute")
    @Nullable
    private Integer startMinute; //limit options to 15, 30, and 45; only allow to be null if startHour is null

    public Routine(@NonNull String title, @NonNull boolean[] weekdays, Integer startHour,
                   Integer startMinute) {
        this.weekdays = weekdays;
        this.title = title;
        this.startHour = startHour;
        this.startMinute = startMinute;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public boolean[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@NonNull boolean[] weekdays) {
        this.weekdays = weekdays;
    }

    @Nullable
    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(@Nullable Integer startHour) {
        this.startHour = startHour;
    }

    @Nullable
    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(@Nullable Integer startMinute) {
        this.startMinute = startMinute;
    }
}