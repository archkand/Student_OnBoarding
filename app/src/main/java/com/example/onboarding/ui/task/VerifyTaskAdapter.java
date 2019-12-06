package com.example.onboarding.ui.task;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class VerifyTaskAdapter extends RecyclerView.Adapter<VerifyTaskAdapter.ViewHolder>{

    List<Task> verifyTasks;
    FragmentActivity context;
    Profile profile;

    public VerifyTaskAdapter(List<Task> verifyTaskList, FragmentActivity con, Profile profile) {
        verifyTasks = verifyTaskList;
        context = con;
        this.profile = profile;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verifytask_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("chella","Tasks list"+ verifyTasks.toString());
        final Task task = verifyTasks.get(position);
        holder.verifyTaskName.setText(task.getTaskName());
        holder.verifyDueDate.setText(task.getDueDate());
        holder.verifyDownArrow.setClickable(true);
        holder.verifyDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(task.getTaskName());
                builder.setMessage(task.getDescription()+"\n"+"\n"+"Due Date: "+task.getDueDate()+"\n"+"\n"+"Rewards: "+task.getRewards())
                        .setPositiveButton(R.string.sendButton, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, task.getTaskName()+ " is sent to verify", Toast.LENGTH_SHORT).show();
                            }

                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView verifyTaskName, verifyDueDate;
        ImageView verifyNotify, verifyDownArrow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verifyTaskName =itemView.findViewById(R.id.verifyTaskName);
            verifyDueDate = itemView.findViewById(R.id.verifyDueDateText);
            verifyNotify = itemView.findViewById(R.id.verifyTaskNotify);
            verifyDownArrow = itemView.findViewById(R.id.verifyDownArrow);

        }
    }

}
