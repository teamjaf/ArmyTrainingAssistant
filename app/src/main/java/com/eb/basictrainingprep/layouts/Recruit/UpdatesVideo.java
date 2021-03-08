package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eb.basictrainingprep.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class UpdatesVideo extends AppCompatActivity{


     TextView txtScreenName;
   YouTubePlayerView youTubePlayer;
    String screenName;
    String videoLink;
    CardView cardConducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_video);

        screenName = getIntent().getStringExtra("screenName");
        videoLink=getIntent().getStringExtra("videoLink");
        cardConducts=findViewById(R.id.card_conduts);
        txtScreenName = findViewById(R.id.txt_screenNameVideo);
        txtScreenName.setText(screenName);

        youTubePlayer=findViewById(R.id.youtube_player_view);

        if(screenName.equals("Code of Conduct")){
            cardConducts.setVisibility(View.VISIBLE);
        }

        youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                youTubePlayer.pause();
                youTubePlayer.cueVideo(videoLink,0);

            }
        });
    }



    public void btn_back(View view) {
        finish();
    }
}