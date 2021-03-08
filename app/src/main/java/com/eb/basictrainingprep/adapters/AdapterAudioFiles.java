package com.eb.basictrainingprep.adapters;

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
import com.eb.basictrainingprep.model.ModelAudioFiles;
import com.eb.basictrainingprep.utils.MyProgressBar;

import java.util.List;

public class AdapterAudioFiles extends RecyclerView.Adapter<AdapterAudioFiles.ViewHolderAudio> {

    Context context;
    List<ModelAudioFiles> modelAudioFiles;
    int index = -1;

    public AdapterAudioFiles(Context context, List<ModelAudioFiles> modelAudioFiles) {
        this.context = context;
        this.modelAudioFiles = modelAudioFiles;
    }

    @NonNull
    @Override
    public ViewHolderAudio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_army_knowledge_btns,parent,false);

        return new ViewHolderAudio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAudio holder, int position) {

        ModelAudioFiles myModel=modelAudioFiles.get(position);

        holder.btnImages.setText(String.valueOf(position+1));

       // String audioLink=myModel.getAudioLink();
        String audioName=myModel.getAudioName();
         holder.btnImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();

                Intent intent = new Intent("allAudio");
                 intent.putExtra("id", position);
                intent.putExtra("name", audioName);

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });

        if (index == position) {
            holder.btnImages.setBackgroundResource(R.drawable.btn_bg_fill);
            holder.btnImages.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.btnImages.setBackgroundResource(R.drawable.btn_shape);
            holder.btnImages.setTextColor(Color.parseColor("#000E03"));
        }

    }

    @Override
    public int getItemCount() {
        return modelAudioFiles.size();
    }

    public static class ViewHolderAudio extends RecyclerView.ViewHolder {
        Button btnImages;

        public ViewHolderAudio(@NonNull View itemView) {
            super(itemView);
            btnImages = itemView.findViewById(R.id.btn_songs);

        }
    }
}
