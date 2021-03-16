package com.example.infinity_courseproject.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.infinity_courseproject.R;


public class AssignmentFragment extends Fragment {

    private AssignmentViewModel assignmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assignmentViewModel =
                new ViewModelProvider(this).get(AssignmentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_assignment, container, false);
        final TextView textView = root.findViewById(R.id.text_assignment);

        return root;
    }
}