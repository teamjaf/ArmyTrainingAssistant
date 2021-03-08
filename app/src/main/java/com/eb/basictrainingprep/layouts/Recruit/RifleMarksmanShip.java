package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterWarriorEthos;
import com.eb.basictrainingprep.model.ModelWarriorEthos;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import static com.eb.basictrainingprep.R.drawable.ic_picture_holder;
import static com.eb.basictrainingprep.R.drawable.placeholder;

public class RifleMarksmanShip extends AppCompatActivity {

    YouTubePlayerView youTubePlayer;
    FirebaseFirestore rootRef;
    MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifle_marksman_ship);


        youTubePlayer=findViewById(R.id.youtube_player_view);

        myProgressBar=MyProgressBar.getInstance();
       // myProgressBar.showProgress(this);
        rootRef = FirebaseFirestore.getInstance();


        youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                youTubePlayer.pause();
                youTubePlayer.cueVideo("fSKBs9RdN-I",0);

            }
        });


    }



    public void btn_back(View view) {
        finish();
    }




    public void btn_4Fundamentals(View view) {
        startActivity(new Intent(RifleMarksmanShip.this,FourFundamentals.class));
    }

    public void btn_8CycleFunction(View view) {
        startActivity(new Intent(RifleMarksmanShip.this,Eight_CycleFunction.class));

    }

    public void btn_test_knowledge(View view) {
        Intent i=new Intent(RifleMarksmanShip.this,Quiz.class);
        i.putExtra("screenName","rifle_marksmanShip");
        startActivity(i);
    }
}