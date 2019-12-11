package com.example.onboarding.ui.inbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onboarding.MainActivity;
import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;
import com.example.onboarding.ui.profile.PastTaskAdapter;
import com.example.onboarding.ui.profile.ProfileAPI;
import com.example.onboarding.ui.task.NotifyAPI;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment {
    View root;
    private RecyclerView taskInboxRecycler;
    private RecyclerView.Adapter inboxAdapter;
    private RecyclerView.LayoutManager inboxLayoutManager;
    Profile profile;
    List<Task> taskList = new ArrayList<>();
    List<Task> notifytaskList = new ArrayList<>();
    String profileURL = "http://localhost:3000/login/student";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_inbox, container, false);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*PushNotifications.start(getActivity(), "7aecb41d-a52d-48e9-9cb6-35d5aef59e58");
        PushNotifications.addDeviceInterest("hello");*/
        new notifyListAPI(profileURL,profile,getActivity());

        profile = ((MainActivity)getActivity()).profile;
        taskList = profile.getNotifyTask();
        taskInboxRecycler = root.findViewById(R.id.inbox_recycler_view);
        taskInboxRecycler.setHasFixedSize(true);
        inboxLayoutManager = new LinearLayoutManager(getContext());
        taskInboxRecycler.setLayoutManager(inboxLayoutManager);
        inboxAdapter = new inboxAdapter(notifytaskList,getActivity());
        taskInboxRecycler.setAdapter(inboxAdapter);
        inboxAdapter.notifyDataSetChanged();


        PushNotifications.start(
                getActivity(),
                "7aecb41d-a52d-48e9-9cb6-35d5aef59e58"
        );

        for(int i=0;i<taskList.size();i++){
            Log.d("stest","tasklist subscribe"+taskList.get(i).getId());
            PushNotifications.subscribe(taskList.get(i).getId());
        }



        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(getActivity(), new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
                String body = remoteMessage.getNotification().getBody();
                Log.d("stest","push notification save"+body);

                Task t = new Task();
                Log.d("stest","body"+body);

                t.setTaskName(body);
                notifytaskList.add(t);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Notification for task");
                builder.setMessage(body);

                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        // Any UI task, example
                        // Create the AlertDialog object and return it
                        inboxAdapter.notifyDataSetChanged();
                        builder.create();
                        builder.show();

                    }
                };
                handler.sendEmptyMessage(1);




            }
        });



    }
}
