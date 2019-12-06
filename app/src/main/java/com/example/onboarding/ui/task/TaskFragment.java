package com.example.onboarding.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    RecyclerView tRecyclerView;
    RecyclerView.Adapter taskAdapter;
    RecyclerView.LayoutManager tLayoutManager;
    List<Task> taskList = new ArrayList<>();
    Profile profile;
    View root;
    String verifyTaskURL = "http://localhost:3000/taskStudent/getStudentTask";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_task, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profile = ((MainActivity)getActivity()).profile;

        //Task List
        tRecyclerView = root.findViewById(R.id.taskList);
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(getContext());
        tRecyclerView.setLayoutManager(tLayoutManager);
        taskAdapter= new TaskAdapter(taskList,getActivity(), profile,taskAdapter);
        tRecyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        try {
            new TaskAPI(verifyTaskURL,getActivity(),taskAdapter,taskList,profile).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}