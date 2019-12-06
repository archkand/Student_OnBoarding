package com.example.onboarding.ui.task;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.R;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<Task> tasks;
    FragmentActivity context;
    Profile profile;
    String notifyURL ="http://localhost:3000/taskStudent/notify";
    String sendURL = "http://localhost:3000/taskStudent/sendVerify";
    List<Task> notifyTasks;
    RecyclerView.Adapter taskAdapter;
    String Progress;
    String notifySend="OFF";
    public TaskAdapter(List<Task> taskList, FragmentActivity con, Profile profile,RecyclerView.Adapter adapter) {
        tasks = taskList;
        context = con;
        this.profile = profile;
        notifyTasks = tasks;
        taskAdapter = adapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d("chella","Tasks list"+ tasks.toString());
        final Task task = tasks.get(position);
        Progress="not_send";
        holder.taskName.setText(task.getTaskName());
        holder.dueDateText.setText(task.getDueDate());
        Log.d("stest","task "+task.toString());
        Log.d("stest","task "+task.getNotify());
        if(task.getNotify().equalsIgnoreCase("OFF")){
            Log.d("stest","Inside off notify");
            notifySend="ON";
            holder.taskNotify.setTag("ON");
            holder.taskNotify.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }else{
            holder.taskNotify.setTag("OFF");
        }
        if(task.getStatus()=="InProgress"){
            Progress="send";
        }

        holder.taskNotify.setClickable(true);
        holder.taskNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(holder.taskNotify.getTag().toString().equalsIgnoreCase("ON")){
                        tasks.get(position).setNotify("ON");
                        holder.taskNotify.setImageResource(R.drawable.ic_notifications_black_24dp);
                    }else{
                        tasks.get(position).setNotify("OFF");
                        holder.taskNotify.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                    }
                 //   Log.d("stest","notifysend value"+holder.taskNotify.getTag());

                    notifyDataSetChanged();
                    new NotifyAPI(task,profile,notifyURL,context,notifyTasks,taskAdapter,"notify",holder.taskNotify.getTag().toString()).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.downArrow.setClickable(true);
        holder.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chella","On the click of register");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(task.getTaskName());
                Log.d("chella","Inside Builder Message"+task.getTaskName());
                builder.setTitle(task.getTaskName());

                builder.setMessage(task.getDescription()+"\n"+"\n"+"Due Date: "+task.getDueDate()+"\n"+"\n"+"Rewards: "+task.getRewards());

                if(Progress.equalsIgnoreCase("send")){
                    builder.setMessage("Sent for Verfification");
                }else{
                    builder.setPositiveButton(R.string.sendButton, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                new NotifyAPI(task,profile,sendURL,context,notifyTasks,taskAdapter,"verify",notifySend).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }

                // Create the AlertDialog object and return it
                builder.create();
                builder.show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskName, dueDateText;
        ImageView taskNotify, downArrow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName =itemView.findViewById(R.id.taskName);
            dueDateText = itemView.findViewById(R.id.dueDateText);
            taskNotify = itemView.findViewById(R.id.taskNotify);
            downArrow = itemView.findViewById(R.id.downArrow);

        }
    }
}
