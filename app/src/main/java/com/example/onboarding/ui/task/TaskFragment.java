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

    RecyclerView verifyRecyclerView;
    RecyclerView.Adapter verifyAdapter;
    RecyclerView.LayoutManager verifyLayoutManager;
    List<Task> verifyTaskList = new ArrayList<>();
    Profile profile;
    View root;
    String taskURL = "http://192.168.118.2:3000/taskStudent/getAll";
    String verifyTaskURL = "http://192.168.118.2:3000/taskStudent/getStudentTask";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_task, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //verifyTask List
        verifyRecyclerView = root.findViewById(R.id.taskList);
        verifyRecyclerView.setHasFixedSize(true);
        verifyLayoutManager = new LinearLayoutManager(getContext());
        verifyRecyclerView.setLayoutManager(verifyLayoutManager);
        verifyAdapter= new VerifyTaskAdapter(taskList,getActivity(),((MainActivity)getActivity()).profile);
        verifyRecyclerView.setAdapter(verifyAdapter);
        verifyAdapter.notifyDataSetChanged();
        try {
            new VerifyTaskAPI(verifyTaskURL,getActivity(),verifyAdapter,verifyTaskList,((MainActivity)getActivity()).profile ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Task List
        tRecyclerView = root.findViewById(R.id.taskList);
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(getContext());
        tRecyclerView.setLayoutManager(tLayoutManager);
        taskAdapter= new TaskAdapter(taskList,getActivity(),((MainActivity)getActivity()).profile ,taskAdapter);
        tRecyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        try {
            new TaskAPI(taskURL,getActivity(),taskAdapter,taskList).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}