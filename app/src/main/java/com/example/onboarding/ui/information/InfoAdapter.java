package com.example.onboarding.ui.information;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onboarding.Pojo.Info;
import com.example.onboarding.R;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder>{

    List<Info> infoList;
    FragmentActivity context;

    public InfoAdapter(List<Info> informationList, FragmentActivity con) {
        infoList = informationList;
        context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("chella","Information list"+ infoList.toString());
        final Info info = infoList.get(position);
        holder.InformationName.setText(info.getInfoName());
        holder.arrowImage.setClickable(true);
        holder.arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chella","On the click of arrowImage");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(info.getInfoName());
                Log.d("chella","Inside Builder Message"+info.getInfoName());
                builder.setMessage(info.getInfoDetail()+"\n"+"\n"+"Link: "+info.getInfoLink())
                        .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
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
        return infoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView InformationName;
        ImageView arrowImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InformationName =itemView.findViewById(R.id.informationName);
            arrowImage = itemView.findViewById(R.id.arrowImage);
        }
    }
}

