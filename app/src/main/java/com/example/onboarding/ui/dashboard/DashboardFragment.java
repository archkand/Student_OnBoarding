package com.example.onboarding.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Workshop;
import com.example.onboarding.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView wRecyclerView;
    private RecyclerView.Adapter workshopAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    List<Workshop> workshopList = new ArrayList<>();
    View root;
    String workshopURL = "http://localhost:3000/workshopStudent/getAll";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wRecyclerView = root.findViewById(R.id.workshopList);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(getContext());
        wRecyclerView.setLayoutManager(wLayoutManager);
        workshopAdapter= new WorkshopAdapter(workshopList,getActivity());
        wRecyclerView.setAdapter(workshopAdapter);

        try {
            new WorkshopAPI(workshopURL,getActivity(),workshopAdapter,workshopList).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}