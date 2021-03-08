package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelActivitiesReport;

import java.util.List;

public class AdapterMyReport extends RecyclerView.Adapter<AdapterMyReport.ViewHolderMyReport> {
    Context context;

    List<ModelActivitiesReport> modelMyReport;

    public AdapterMyReport(List<ModelActivitiesReport> modelMyReport, Context context) {
        this.modelMyReport = modelMyReport;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMyReport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_activities_report,parent,false);

        return new ViewHolderMyReport(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMyReport holder, int position) {

        ModelActivitiesReport modelActivitiesReport=modelMyReport.get(position);
        holder.txtDate.setText(modelActivitiesReport.getDate());
        holder.txtSitups.setText(modelActivitiesReport.getSitups());
        holder.txtPushups.setText(modelActivitiesReport.getPushups());
        holder.txtMileRun.setText(modelActivitiesReport.getMileRun());


    }

    @Override
    public int getItemCount() {
        return modelMyReport.size();
    }

    public static class ViewHolderMyReport extends RecyclerView.ViewHolder {

        TextView txtDate,txtPushups,txtSitups,txtMileRun;

        public ViewHolderMyReport(@NonNull View itemView) {
            super(itemView);

            txtDate=itemView.findViewById(R.id.txt_reportDate);
            txtPushups=itemView.findViewById(R.id.txt_reportPushUps);
            txtSitups=itemView.findViewById(R.id.txt_reportSitups);
            txtMileRun=itemView.findViewById(R.id.txt_reportRun);
         }
    }
}
