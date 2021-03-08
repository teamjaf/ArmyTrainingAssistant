package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelRanks;

import java.util.List;

public class AdapterRanks extends RecyclerView.Adapter<AdapterRanks.ViewHolder> {
    int index = -1;
    int firstCount = 0;
    Context context;
    List<ModelRanks> modelRanks;

    public AdapterRanks(Context context, List<ModelRanks> modelRanks) {
        this.context = context;
        this.modelRanks = modelRanks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_army_knowledge_btns,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRanks myModel=modelRanks.get(position);
        holder.btnSongs.setText(myModel.getRanksTitle());
        holder.btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
                Intent intent = new Intent("rankVideos");

                intent.putExtra("vId", myModel.getRanksTitle());
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
        return modelRanks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnSongs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSongs = itemView.findViewById(R.id.btn_songs);
        }
    }
}

