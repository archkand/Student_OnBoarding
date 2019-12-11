package com.example.onboarding.ui.dashboard;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Profile;
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
    Profile profile;
    String workshopURL = "http://localhost:3000/workshopStudent/getAll";
    String stepURL = "http://localhost:3000/dashboardStudent/step";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profile = ((MainActivity)getActivity()).profile;
        new StepAPI(stepURL,profile).execute();

        View circleOne = root.findViewById(R.id.circle_one);
        View circleTwo = root.findViewById(R.id.circle_two);
        View circleThree = root.findViewById(R.id.circle_three);
        //Drawable mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.step_circle);
        Drawable greenCircle = ContextCompat.getDrawable(getContext(),R.drawable.circle_green);

        switch (profile.getStep()){
            case "1":
                circleOne.setBackground(greenCircle);
                break;
            case "2":
                circleOne.setBackground(greenCircle);
                circleTwo.setBackground(greenCircle);
                break;
            case "3":
                circleOne.setBackground(greenCircle);
                circleTwo.setBackground(greenCircle);
                circleThree.setBackground(greenCircle);
                break;
        }

        wRecyclerView = root.findViewById(R.id.workshopList);
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(getContext());
        wRecyclerView.setLayoutManager(wLayoutManager);
        workshopAdapter= new WorkshopAdapter(workshopList,getActivity(),profile);
        wRecyclerView.setAdapter(workshopAdapter);


        try {
            new WorkshopAPI(workshopURL,getActivity(),workshopAdapter,workshopList,profile).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}