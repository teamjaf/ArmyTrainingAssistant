package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelNotifications;

import java.util.List;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.NotificationViewHolder> {

    Context context;
    List<ModelNotifications> modelNotifications;

    public AdapterNotifications(Context context, List<ModelNotifications> modelNotifications) {
        this.context = context;
        this.modelNotifications = modelNotifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_notifications,parent,false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        ModelNotifications myModel=modelNotifications.get(position);

        holder.txtTitle.setText(myModel.getNotificatonTitle());
        holder.txtDate.setText(myModel.getNotificatonDate());

    }

    @Override
    public int getItemCount() {
        return modelNotifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle,txtDate;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle=itemView.findViewById(R.id.txt_titleNotification);
            txtDate=itemView.findViewById(R.id.txt_noti_date);

        }
    }
}
