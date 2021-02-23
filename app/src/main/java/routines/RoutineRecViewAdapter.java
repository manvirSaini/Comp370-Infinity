package routines;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for the RecyclerView in the 'Routines' section. Not complete.
 */

public class RoutineRecViewAdapter extends RecyclerView.Adapter<RoutineRecViewAdapter.ViewHolder> {
    private LiveData<List<Routine>> routine_list;
    private Context context;

    public RoutineRecViewAdapter(LiveData<List<Routine>> routine_list, Context context) {
        this.routine_list = routine_list;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineRecViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
