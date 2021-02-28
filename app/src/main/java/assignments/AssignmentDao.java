package assignments;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assignment assignment);

    @Delete
    void delete(Assignment assignment);

    @Query("DELETE FROM assignment_table")
    void deleteAll();

    @Query("SELECT * FROM assignment_table")
    LiveData<List<Assignment>> getAllAssignments();
}
