package com.example.onboarding.ui.profile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;
import com.example.onboarding.ui.information.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PastTaskAdapter extends RecyclerView.Adapter<PastTaskAdapter.ViewHolder> {

    List<Task> pastTasks = new ArrayList<>();
    Task pastTask;
    FragmentActivity context;
    Profile profile;

    public PastTaskAdapter(List<Task> pastTasks,FragmentActivity context) {
        this.pastTasks = pastTasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_task_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("chella","Tasks list"+ pastTasks.toString());
        final Task task = pastTasks.get(position);
        holder.pastTaskName.setText(task.getTaskName());
    }

    @Override
    public int getItemCount() {
        return pastTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pastTaskName,pastDueDate,pastRewards;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastTaskName =itemView.findViewById(R.id.pastTaskName);

        }
    }
}
