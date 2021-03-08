package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Recruit.WarriorEthos;
import com.eb.basictrainingprep.model.ModelWarriorEthos;

import java.util.List;

public class AdapterWarriorEthos extends RecyclerView.Adapter<AdapterWarriorEthos.WarriorEthosViewHolder> {

    Context context;
    List<ModelWarriorEthos> modelWarriorEthos;
    int index = -1;
    int firstCount=0;


    public AdapterWarriorEthos(Context context, List<ModelWarriorEthos> modelWarriorEthos) {
        this.context = context;
        this.modelWarriorEthos = modelWarriorEthos;
    }


    @NonNull
    @Override
    public WarriorEthosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_army_knowledge_btns, parent, false);

        return new WarriorEthosViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WarriorEthosViewHolder holder, int position) {
        ModelWarriorEthos warriorEthos = modelWarriorEthos.get(position);

        try {
            String btnname = warriorEthos.getBtnName();
            holder.btnImages.setText(btnname);




            holder.btnImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     index = position;
                    notifyDataSetChanged();
                    Intent intent = new Intent("allImages");
                    intent.putExtra("imgId", position);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                }
            });
            if (index == position) {
                holder.btnImages.setBackgroundResource(R.drawable.btn_bg_fill);
                holder.btnImages.setTextColor(Color.parseColor("#FFFFFF"));
            }else if(firstCount==0 && position==0) {
                holder.btnImages.setBackgroundResource(R.drawable.btn_bg_fill);
                holder.btnImages.setTextColor(Color.parseColor("#FFFFFF"));
                firstCount++;
            }else {
                holder.btnImages.setBackgroundResource(R.drawable.btn_shape);
                holder.btnImages.setTextColor(Color.parseColor("#000E03"));
            }
        } catch (Exception ignored) {

        }


    }

    @Override
    public int getItemCount() {
        return modelWarriorEthos.size();
    }

    public static class WarriorEthosViewHolder extends RecyclerView.ViewHolder {

        Button btnImages;

        public WarriorEthosViewHolder(@NonNull View itemView) {
            super(itemView);
            btnImages = itemView.findViewById(R.id.btn_songs);
        }
    }
}
