package com.example.onboarding.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Workshop;
import com.example.onboarding.R;

import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.ViewHolder> {

    List<Workshop> workshops;
    FragmentActivity context;
    Profile profile;

    public WorkshopAdapter(List<Workshop> workshopList, FragmentActivity con, Profile p) {
        workshops = workshopList;
        context = con;
        profile=p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d("chella","Information list"+ workshops.toString());
        final Workshop workshop = workshops.get(position);
        holder.WorkshopName.setText(workshop.getWorkshopName());
        holder.register.setClickable(true);
        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chella","On the click of register");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(workshop.getWorkshopName());
                Log.d("chella","Inside Builder Message"+workshop.getWorkshopName());
                builder.setMessage(workshop.getDescription()+"\n"+"\n"+"Date: "+workshop.getDate())
                        .setPositiveButton(R.string.registerButton, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                workshops.remove(position);
                                notifyDataSetChanged();
                                new RegisterAPI(context,workshop,profile).execute();

                             //   Toast.makeText(context, "Registered for "+workshop.getWorkshopName(), Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();

            }
        });
        //   notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return workshops.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView WorkshopName;
        Button register;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            WorkshopName =itemView.findViewById(R.id.workshopName);
            register = itemView.findViewById(R.id.registerButton);
        }
    }
}
