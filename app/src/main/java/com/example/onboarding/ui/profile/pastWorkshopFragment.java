package com.example.onboarding.ui.profile;

import android.content.Context;
import android.net.Uri;
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
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.Pojo.Workshop;
import com.example.onboarding.R;

import java.util.ArrayList;
import java.util.List;

public class pastWorkshopFragment extends Fragment {
    View root;
    private RecyclerView pastWorkshopRecyclerView;
    private RecyclerView.Adapter pastWorkshopAdapter;
    private RecyclerView.LayoutManager pastWorkshopLayoutManager;
    List<Workshop> pastWorkshopList = new ArrayList<>();
    Profile profile;
    public pastWorkshopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_past_workshop, container, false);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profile = ((MainActivity)getActivity()).profile;
        pastWorkshopList = profile.getPastWorkshop();
        Log.d("stest","pastworkshop list"+pastWorkshopList);
        pastWorkshopRecyclerView = root.findViewById(R.id.pastWorkshopRecyclerView);
        pastWorkshopRecyclerView.setHasFixedSize(true);
        pastWorkshopLayoutManager = new LinearLayoutManager(getContext());
        pastWorkshopRecyclerView.setLayoutManager(pastWorkshopLayoutManager);
        pastWorkshopAdapter = new pastWorkshopAdapter(pastWorkshopList,getActivity());
        pastWorkshopRecyclerView.setAdapter(pastWorkshopAdapter);
        pastWorkshopAdapter.notifyDataSetChanged();


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
