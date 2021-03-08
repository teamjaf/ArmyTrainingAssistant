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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterRanks;
import com.eb.basictrainingprep.model.ModelRanks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmyRanksDetails extends AppCompatActivity {

    private TextView txtTitle;
    private RecyclerView recyclerView;
    private AdapterRanks adapterRanks;
    List<ModelRanks> modelRanksList;
    private String categoryTitle;
    YouTubePlayerView playerView;
    YouTubePlayer yPlayer;
    FirebaseFirestore rootRef;
    private String btnName;
    Map<String, String> mapOfficers;
    String firstVideo = "";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_ranks_details);

        txtTitle = findViewById(R.id.txt_ranksTitle);
        recyclerView = findViewById(R.id.recycler_RanksButtons);
        categoryTitle = getIntent().getStringExtra("category");
        txtTitle.setText(categoryTitle);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        modelRanksList = new ArrayList<>();
        playerView = findViewById(R.id.youtube_player_viewRanks);
        rootRef = FirebaseFirestore.getInstance();

        getFirestoreVideos();

        LocalBroadcastManager.getInstance(this).

                registerReceiver(mMessageReceiver, new IntentFilter("rankVideos"));


        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(firstVideo, 0);
                if (youTubePlayer != null) {
                    youTubePlayer.pause();
                    yPlayer = youTubePlayer;
                    // youTubePlayer.addListener(ArmyRanksDetails.this);

                } else {

                }
            }
        });

    }

    void getFirestoreVideos() {
        DocumentReference docIdRef = rootRef.collection("armyKnowledge").document("army_ranks");
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    mapOfficers = (Map<String, String>) document.get(categoryTitle);
                    for (Map.Entry map : mapOfficers.entrySet()) {
                        count++;
                        if (count == 1) {
                            firstVideo = map.getValue().toString();
                        }
                        modelRanksList.add(new ModelRanks(map.getKey().toString(), map.getValue().toString()));

                    }
                    adapterRanks = new AdapterRanks(getApplicationContext(), modelRanksList);
                    recyclerView.setAdapter(adapterRanks);

                }
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            btnName = intent.getStringExtra("vId");

            playVideo(btnName);               // playVideo(videoUrl);
//            checkCompletedVideos();
//            checkTotalCounts();

        }


    };

    public void btn_back(View view) {
        finish();
    }

    private void playVideo(String videoId) {

        if (yPlayer != null) {
            try {
                yPlayer.cueVideo(mapOfficers.get(videoId), 0);

            } catch (Exception e) {
                Log.e("VideoScreen", e.toString());

            }
        } else {

        }
    }
}