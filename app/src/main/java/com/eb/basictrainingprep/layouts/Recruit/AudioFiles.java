package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.media.MediaPlayer;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterAudioFiles;
import com.eb.basictrainingprep.adapters.AudioAdapter;
import com.eb.basictrainingprep.model.ModelAudioFiles;
 import com.eb.basictrainingprep.utils.MyProgressBar;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AudioFiles extends AppCompatActivity implements OnInvalidPathListener, JcPlayerManagerListener {


    FirebaseFirestore rootRef;
    MyProgressBar myProgressBar;

    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    ArrayList<JcAudio> jcAudios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_files);

        myProgressBar=MyProgressBar.getInstance();
         jcAudios = new ArrayList<>();
        player = findViewById(R.id.jcplayer);
        recyclerView=findViewById(R.id.recycler_videosButtons);

        getAudiosFiles();


    }



   private void getAudiosFiles() {
        rootRef=FirebaseFirestore.getInstance();

        rootRef.collection("audioFiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    assert document != null;
                    for (DocumentChange documentChange : document.getDocumentChanges()) {

                       // ModelAudioFiles myAudios = new ModelAudioFiles();

                        String audioLink = documentChange.getDocument().getData().get("audio_link").toString();
                        String audioName = documentChange.getDocument().getData().get("audio_name").toString();

                        jcAudios.add(JcAudio.createFromURL(audioName,audioLink));

//

                     }

                    player.initPlaylist(jcAudios, null);
                    adapterSetup();

                     myProgressBar.hideProgress();
                }
                 myProgressBar.hideProgress();

            }
        });
    }
    protected void adapterSetup() {
        audioAdapter = new AudioAdapter(player.getMyPlaylist());
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                player.playAudio(player.getMyPlaylist().get(position));
            }

        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.createNotification();
    }
    @Override
    public void onPause() {
        super.onPause();
        player.createNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.kill();
    }

    @Override
    public void onPathError(JcAudio jcAudio) {
        Toast.makeText(this, jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();
//        player.removeAudio(jcAudio);
//        player.next();
    }


    @Override
    public void onPreparedAudio(@NonNull JcStatus status) {

    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onPaused(@NonNull JcStatus status) {

    }

    @Override
    public void onContinueAudio(@NonNull JcStatus status) {

    }

    @Override
    public void onPlaying(@NonNull JcStatus status) {

    }

    @Override
    public void onTimeChanged(@NonNull JcStatus status) {
        updateProgress(status);
    }

    @Override
    public void onJcpError(@NonNull Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void updateProgress(final JcStatus jcStatus) {
//        Log.d(TAG, "Song duration = " + jcStatus.getDuration()
//                + "\n song position = " + jcStatus.getCurrentPosition());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // calculate progress
                float progress = (float) (jcStatus.getDuration() - jcStatus.getCurrentPosition())
                        / (float) jcStatus.getDuration();
                progress = 1.0f - progress;
                audioAdapter.updateProgress(jcStatus.getJcAudio(), progress);
            }
        });
    }


    @Override
    public void onStopped(@NonNull JcStatus status) {

    }


    public void btn_back(View view) {
        finish();

    }
}