package com.example.onboarding.ui.profile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onboarding.Pojo.Profile;
import com.example.onboarding.Pojo.Task;
import com.example.onboarding.Pojo.Workshop;
import com.example.onboarding.R;


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

public class pastWorkshopAdapter extends RecyclerView.Adapter<pastWorkshopAdapter.ViewHolder> {

    List<Workshop> pastWorkshops = new ArrayList<>();
    FragmentActivity context;

    public pastWorkshopAdapter(List<Workshop> pastWorkshop, FragmentActivity context) {
        this.pastWorkshops = pastWorkshop;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_workshop_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Workshop workshop = pastWorkshops.get(position);
        holder.pastWorkshopName.setText(workshop.getWorkshopName());
        holder.pastWorkshopDate.setText(workshop.getDate());
    }

    @Override
    public int getItemCount() {
        return pastWorkshops.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView pastWorkshopName,pastWorkshopDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastWorkshopName =itemView.findViewById(R.id.pastWorkshopName);
            pastWorkshopDate = itemView.findViewById(R.id.pastWorkshopDate);
        }
    }
}
