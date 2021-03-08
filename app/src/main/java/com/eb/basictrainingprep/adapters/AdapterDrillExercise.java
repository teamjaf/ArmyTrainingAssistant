package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelDrillExercise;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class AdapterDrillExercise extends RecyclerView.Adapter<AdapterDrillExercise.ExerciseViewHolder> {

    Context context;
    List<ModelDrillExercise> modelDrillExercises;

    public AdapterDrillExercise(Context context, List<ModelDrillExercise> modelDrillExercises) {
        this.context=context;
        this.modelDrillExercises=modelDrillExercises;
    }


    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drill_exercise,parent,false);

        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
             ModelDrillExercise model_exercise=modelDrillExercises.get(position);

             holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                 @Override
                 public void onReady(YouTubePlayer youTubePlayer) {

                     String videoId=model_exercise.getVideoId();

                     youTubePlayer.loadVideo(videoId,0);

                     youTubePlayer.pause();

                 }
             });
    }

    @Override
    public int getItemCount() {
        return modelDrillExercises.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youTubePlayerView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubePlayerView=itemView.findViewById(R.id.youtube_player_view);

        }


    }

}
