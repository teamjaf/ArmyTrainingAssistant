package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.eb.basictrainingprep.adapters.AdapterVideoButtons;
import com.eb.basictrainingprep.model.Model_VideosButtons;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VideoScreen extends AppCompatActivity implements YouTubePlayerListener {

    String screenName;
    TextView txtScreenName, txtWatchCount;
    AdapterVideoButtons adapterVideoButtons;
    private RecyclerView recyclerButtons;
    MyProgressBar myProgressBar;

    List<Model_VideosButtons> modelButtons;

    FirebaseFirestore rootRef;
    private int btnName;
    private String uid = RecruitWelcomeDrawer.uid;
    private String category = RecruitWelcomeDrawer.category;
    String completedCount;
    Calendar calendar;
    String currentDate;
    List<String> group;
    YouTubePlayerView playerView;
    YouTubePlayer yPlayer;
    int currentSeconds;
    int totalDuration;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_screen);

        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        txtScreenName = findViewById(R.id.txt_screenName);
        txtWatchCount = findViewById(R.id.txt_watchCount);
        playerView = findViewById(R.id.youtube_player_view);

        screenName = getIntent().getStringExtra("videoScreen");
        txtScreenName.setText(screenName);

        rootRef = FirebaseFirestore.getInstance();


        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        modelButtons = new ArrayList<>();


        if (screenName.equals("Army Songs")) {
            getSongs("army_songs", "songs1");

        } else if (screenName.equals("Army Values")) {

            getSongs("army_values", "values1");

        } else if (screenName.equals("Soldiers Creed")) {
            getSongs("soldier_creed", "soldier_creed1");

        } else if (screenName.equals("Army Ranks")) {
            getSongs("army_ranks", "ranks1");
        }


        recyclerButtons = findViewById(R.id.recycler_videosButtons);
        recyclerButtons.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LocalBroadcastManager.getInstance(this).

                registerReceiver(mMessageReceiver, new IntentFilter("AllVideos"));


        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(group.get(0), 0);
                if (youTubePlayer != null) {
                    youTubePlayer.pause();
                    yPlayer = youTubePlayer;
                    youTubePlayer.addListener(VideoScreen.this);

                } else {

                }
            }
        });
    }


    private void getSongs(String docName, String arrayName) {


        DocumentReference docIdRef = rootRef.collection("armyKnowledge").document(docName);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    group = (List<String>) document.get(arrayName);

                    for (int i = 0; i < Objects.requireNonNull(group).size(); i++) {
                        Model_VideosButtons model_videosButtons = new Model_VideosButtons();
                        model_videosButtons.setLinks(group.get(i));
                        model_videosButtons.setBtnName(String.valueOf(i + 1));
                        modelButtons.add(model_videosButtons);

                    }

                    adapterVideoButtons = new AdapterVideoButtons(getApplicationContext(), modelButtons);
                    recyclerButtons.setAdapter(adapterVideoButtons);
                    checkTotalCounts();

                    myProgressBar.hideProgress();

                }
                myProgressBar.hideProgress();
            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            btnName = intent.getIntExtra("vId", 0);

            playVideo(btnName);               // playVideo(videoUrl);
            checkCompletedVideos();
            checkTotalCounts();


        }


    };

    private void checkTotalCounts() {
        rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                .document("totalWatched").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        if (document.get("completed") != null) {

                            completedCount = document.get("completed").toString();
                            txtWatchCount.setText("Watch Count : " + completedCount);
                            updateMainVideoCounts();
                        }

                    }
                }
            }
        });
    }

    private void checkFirestore() {
        rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                .document("videos").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    videoDataUpdate();
                } else {
                    videoDataCreate();
                }
            }
        });

    }


    private void videoDataUpdate() {


        rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                .document("videos").update("videosWatched", FieldValue.arrayUnion(String.valueOf(btnName))).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DocumentReference docIdRef = rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                            .document("videos");
                    docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                List<String> videoArray = (List<String>) document.get("videosWatched");
                                assert videoArray != null;
                                if (videoArray.size() == modelButtons.size()) {
                                    rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                                            .document("totalWatched").update("completed", FieldValue.increment(1))
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        checkVideoStorage();
                                                        deleteArray();
                                                    }

                                                }


                                            });
                                }
                            }

                        }
                    });

                }
            }
        });

    }

    private void checkVideoStorage() {

        rootRef.collection("users").document(category).collection(uid).document("VideosCompletionDate")
                .collection("completedVideos").document(currentDate)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if (documentSnapshot.exists()) {
                        updateVideoStorage();
                    } else {
                        createVideoStorage();
                    }
                }
            }
        });
    }

    private void updateVideoStorage() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(screenName, "completed");
        rootRef.collection("users").document(category).collection(uid).document("VideosCompletionDate")
                .collection("completedVideos").document(currentDate)
                .update(objectMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    private void createVideoStorage() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(screenName, "completed");
        rootRef.collection("users").document(category).collection(uid).document("VideosCompletionDate")
                .collection("completedVideos").document(currentDate)
                .set(objectMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    private void updateMainVideoCounts() {
        rootRef.collection("users").document(category).collection(uid)
                .document("totalWatched").update(screenName, completedCount)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {

                        }
                    }
                });
    }

    private void deleteArray() {

        rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName)
                .document("videos").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                checkTotalCounts();

            }
        });

    }


    private void videoDataCreate() {

        Map<String, Object> videos = new HashMap<>();
        String[] array = {String.valueOf(btnName)};
        videos.put("videosWatched", Arrays.asList(array));
        rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName).document("videos")
                .set(videos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    checkCompletedVideos();
                }
            }
        });

    }

    private void checkCompletedVideos() {
        rootRef.collection("users").document(category).collection(uid).document("videosInProgress")
                .collection(screenName).document("totalWatched")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {

                } else {
                    Map<String, Object> videos = new HashMap<>();
                    videos.put("completed", "0");
                    rootRef.collection("users").document(category).collection(uid).document("videosInProgress").collection(screenName).document("totalWatched")
                            .set(videos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                rootRef.collection("users").document(category).collection(uid)
                                        .document("totalWatched").update(screenName, completedCount)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });

    }

    public void btn_back(View view) {

        finish();
    }


    private void playVideo(int videoId) {

        if (yPlayer != null) {
            try {
                yPlayer.cueVideo(group.get(videoId), 0);

            } catch (Exception e) {
                Log.e("VideoScreen", e.toString());

            }
        } else {

        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void btnQuiz(View view) {

        Intent intent = new Intent(getApplicationContext(), Quiz.class);
        intent.putExtra("screenName", "army_knowledge");
        startActivity(intent);
    }

    @Override
    public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

    }

    @Override
    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

        currentSeconds = (int) v;
        Log.e("VideoDuration", String.valueOf(currentSeconds));
        if (currentSeconds == totalDuration) {
            count++;
            if (count == 1) {
                checkFirestore();
                checkTotalCounts();
                Log.e("VideoCompletion", "completed");
                currentSeconds = 0;
                totalDuration = 0;
                count = 0;
            }

        }

    }

    @Override
    public void onError(@NonNull YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

    }

    @Override
    public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

    }

    @Override
    public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

    }

    @Override
    public void onReady(@NonNull YouTubePlayer youTubePlayer) {

    }

    @Override
    public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

    }

    @Override
    public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {
        totalDuration = (int) v;


    }

    @Override
    public void onVideoId(YouTubePlayer youTubePlayer, String s) {

    }

    @Override
    public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

    }


}
