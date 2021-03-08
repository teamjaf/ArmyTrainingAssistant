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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterVideoButtons;
import com.eb.basictrainingprep.adapters.AdapterWarriorEthos;
import com.eb.basictrainingprep.model.ModelWarriorEthos;
import com.eb.basictrainingprep.model.Model_VideosButtons;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.eb.basictrainingprep.R.drawable.ic_picture;
import static com.eb.basictrainingprep.R.drawable.ic_picture_holder;
import static com.eb.basictrainingprep.R.drawable.placeholder;

public class WarriorEthos extends AppCompatActivity {

    TextView txtScreenName;
    private RecyclerView recyclerButtons;
    AdapterWarriorEthos adapterWarriorEthos;
    ImageView imageWarriorEthos;
    List<ModelWarriorEthos> modelWarriorEthos;
    FirebaseFirestore rootRef;
    MyProgressBar myProgressBar;
    List<String> group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warrior_ethos);
        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);


        txtScreenName = findViewById(R.id.txt_screenName);
        imageWarriorEthos = findViewById(R.id.img_warriorEthos);
        String screenName = getIntent().getStringExtra("warriorEthos");

        txtScreenName.setText(screenName);

        modelWarriorEthos = new ArrayList<>();


        rootRef = FirebaseFirestore.getInstance();


        recyclerButtons = findViewById(R.id.recycler_videosButtons);
        recyclerButtons.setLayoutManager(new

                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LocalBroadcastManager.getInstance(this).

                registerReceiver(mMessageReceiver, new IntentFilter("allImages"));


        getSongs();


    }

    private void getSongs() {
        modelWarriorEthos.clear();
        DocumentReference docIdRef = rootRef.collection("armyKnowledge").document("warrior_ethos");
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    group = (List<String>) document.get("ethos1");

                    for (int i = 0; i < group.size(); i++) {
                        ModelWarriorEthos warriorEthos = new ModelWarriorEthos();
                        warriorEthos.setImgLink(group.get(i));
                        warriorEthos.setBtnName(String.valueOf(i + 1));
                        modelWarriorEthos.add(warriorEthos);

                    }

                    adapterWarriorEthos = new AdapterWarriorEthos(getApplicationContext(), modelWarriorEthos);
                    recyclerButtons.setAdapter(adapterWarriorEthos);

                    Glide.with(getApplicationContext()).load(group.get(0))
                            .placeholder(ic_picture_holder).dontAnimate().into(imageWarriorEthos);


                    myProgressBar.hideProgress();

                }
                myProgressBar.hideProgress();
            }
        });

    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             try{

                int imgLink = intent.getIntExtra("imgId",0);

                Glide.with(getApplicationContext()).load(group.get(imgLink))
                        .placeholder(ic_picture_holder).dontAnimate().into(imageWarriorEthos);


             }catch (Exception e){
                Log.e("ImageError",e.getMessage());
            }


        }


    };

    public void btn_back(View view) {
        finish();
    }
}