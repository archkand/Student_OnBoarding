package com.example.onboarding.ui.profile;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Info;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;
import com.example.onboarding.ui.information.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class PastTaskFragment extends Fragment {

    View root;
    private RecyclerView pastRecyclerView;
    private RecyclerView.Adapter pastAdapter;
    private RecyclerView.LayoutManager pastLayoutManager;
    List<Task> pastTaskList = new ArrayList<>();
    Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_past_task, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profile = ((MainActivity)getActivity()).profile;
        pastTaskList = profile.getPastTask();
        Log.d("chella","Past Task in profile "+pastTaskList.toString());
        pastRecyclerView = root.findViewById(R.id.pastTaskList);
        pastRecyclerView.setHasFixedSize(true);
        pastLayoutManager = new LinearLayoutManager(getContext());
        pastRecyclerView.setLayoutManager(pastLayoutManager);
        pastAdapter = new PastTaskAdapter(pastTaskList,getActivity());
        pastRecyclerView.setAdapter(pastAdapter);
        pastAdapter.notifyDataSetChanged();

    }
}
