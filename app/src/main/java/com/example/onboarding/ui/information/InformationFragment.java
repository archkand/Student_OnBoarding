package com.example.onboarding.ui.information;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Info;
import com.example.onboarding.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends Fragment {


    private RecyclerView infoRecyclerView;
    private RecyclerView.Adapter infoAdapter;
    private RecyclerView.LayoutManager infoLayoutManager;
    List<Info> infoList = new ArrayList<>();
    View root;
    String informationURL = "http://192.168.118.2:3000/informationStudent/getAll";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_information, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        infoRecyclerView = root.findViewById(R.id.informationList);
        infoRecyclerView.setHasFixedSize(true);
        infoLayoutManager = new LinearLayoutManager(getContext());
        infoRecyclerView.setLayoutManager(infoLayoutManager);
        infoAdapter = new InfoAdapter(infoList,getActivity());
        infoRecyclerView.setAdapter(infoAdapter);

        try {
            new InformationAPI(informationURL,getActivity(),infoAdapter,infoList).execute();
            //   prodAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}