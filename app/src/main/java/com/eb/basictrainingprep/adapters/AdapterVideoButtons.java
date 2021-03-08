package com.eb.basictrainingprep.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.Model_VideosButtons;

import java.util.List;

public class AdapterVideoButtons extends RecyclerView.Adapter<AdapterVideoButtons.ButtonViewHolder> {
    Context context;
    List<Model_VideosButtons> buttonsModelList;
    int index = -1;
    int firstCount = 0;

    public AdapterVideoButtons(Context context, List<Model_VideosButtons> buttonsModelList) {
        this.context = context;
        this.buttonsModelList = buttonsModelList;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_army_knowledge_btns, parent, false);

        return new ButtonViewHolder(view);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        final Model_VideosButtons model_buttons = buttonsModelList.get(position);
        final String buttonName = model_buttons.getBtnName();
        holder.btnSongs.setText(buttonName);


        holder.btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
                Intent intent = new Intent("AllVideos");

                intent.putExtra("vId", position);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }

        });
        if (index == position) {
            holder.btnSongs.setBackgroundResource(R.drawable.btn_bg_fill);
            holder.btnSongs.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(firstCount==0 && position ==0) {
            holder.btnSongs.setBackgroundResource(R.drawable.btn_bg_fill);
            holder.btnSongs.setTextColor(Color.parseColor("#FFFFFF"));
            firstCount++;
        }else {
                holder.btnSongs.setBackgroundResource(R.drawable.btn_shape);
                holder.btnSongs.setTextColor(Color.parseColor("#000E03"));
            }

    }


    @Override
    public int getItemCount() {
        return buttonsModelList.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {

        Button btnSongs;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSongs = itemView.findViewById(R.id.btn_songs);
        }
    }
}
