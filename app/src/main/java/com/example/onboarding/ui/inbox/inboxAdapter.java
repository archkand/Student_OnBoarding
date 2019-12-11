package com.example.onboarding.ui.inbox;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;


import java.util.ArrayList;
import java.util.List;

public class inboxAdapter extends RecyclerView.Adapter<com.example.onboarding.ui.inbox.inboxAdapter.ViewHolder> {

    List<Task> tasks = new ArrayList<>();
    FragmentActivity context;

    public inboxAdapter(List<Task> task, FragmentActivity context) {
        this.tasks = task;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.onboarding.ui.inbox.inboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_row,parent,false);
        com.example.onboarding.ui.inbox.inboxAdapter.ViewHolder viewHolder = new com.example.onboarding.ui.inbox.inboxAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.onboarding.ui.inbox.inboxAdapter.ViewHolder holder, int position) {
        Log.d("stest","tasks"+tasks);
        final Task task = tasks.get(position);
        Log.d("stest","task one"+task.toString());
       holder.taskNameInbox.setText(task.getTaskName());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskNameInbox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameInbox =itemView.findViewById(R.id.taskNameInbox);
        }
    }
}
