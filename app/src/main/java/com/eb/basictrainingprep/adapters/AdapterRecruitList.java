package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Recruit.MyPerfromance;
import com.eb.basictrainingprep.layouts.Recruit.Notification_recruit;
import com.eb.basictrainingprep.layouts.Recruiter.RecruitActivities;
import com.eb.basictrainingprep.model.ModelRecruitList;

import java.util.List;

public class AdapterRecruitList extends RecyclerView.Adapter<AdapterRecruitList.ViewHolderRecruitList> {

    Context context;
    List<ModelRecruitList>modelRecruitLists;

    public AdapterRecruitList(Context context, List<ModelRecruitList> modelRecruitLists) {
        this.context = context;
        this.modelRecruitLists = modelRecruitLists;
    }

    @NonNull
    @Override
    public ViewHolderRecruitList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruiter_list,parent,false);

        return new ViewHolderRecruitList(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecruitList holder, int position) {
        ModelRecruitList myModel=modelRecruitLists.get(position);

        String name=myModel.getRecruitName();
        holder.txtName.setText(name);
        holder.txtDate.setText(myModel.getRecruitEnrollDate());
        String uid=myModel.getRecruitUid();

        holder.btnActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context, MyPerfromance.class);
                i.putExtra("screenName",100);
                i.putExtra("name",name);
                    i.putExtra("uid",uid);
                context.startActivity(i);

            }
        });
        holder.btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Notification_recruit.class);
                i.putExtra("screenName",100);
                i.putExtra("uid",uid);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelRecruitLists.size();
    }

    public static class ViewHolderRecruitList extends RecyclerView.ViewHolder {

        TextView txtName,txtDate;
        Button btnActivities,btnMessages;

        public ViewHolderRecruitList(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txt_recruitName);
            txtDate=itemView.findViewById(R.id.txt_recruitEnrollDate);
            btnActivities=itemView.findViewById(R.id.btn_recruitActivity);
            btnMessages=itemView.findViewById(R.id.btn_recruitMessages);

        }

    }
}
