package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterDrillExercise;
import com.eb.basictrainingprep.model.ModelDrillExercise;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ArmyResiliency extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ModelDrillExercise> modelDrillExercises;
    private AdapterDrillExercise adapterDrillExercise;
    private FirebaseFirestore rootRef;
    private MyProgressBar myProgressBar;
    private TextView txtFitness;
    private CardView cardView;
    private Button btnGuide;
    String screenName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_resiliency);

        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        recyclerView = findViewById(R.id.recylcerVideos);
        txtFitness = findViewById(R.id.txt_screenName);
        cardView = findViewById(R.id.card_txtQuote);
        btnGuide=findViewById(R.id.btn_studyGuide);
        modelDrillExercises = new ArrayList<>();
        recyclerView.setItemViewCacheSize(20);
        screenName = getIntent().getStringExtra("screenName");
        rootRef = FirebaseFirestore.getInstance();
        txtFitness.setText(screenName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.hasFixedSize();
        if (screenName.equals("Army Resiliancy")) {
            getVideos("army_resiliancy", "allVideos", "resiliancy");
        } else if (screenName.equals("Rifle Marksmanship")) {
            cardView.setVisibility(View.GONE);
            btnGuide.setVisibility(View.VISIBLE);
            getVideos("rifle_marksmanship", "videos", "rifle_videos");
        }else if(screenName.equals("Returning From Basics")){
            cardView.setVisibility(View.GONE);
            btnGuide.setVisibility(View.GONE);
            getVideos("returning_from_basics", "videos", "basics");

        }
    }

    private void getVideos(String mainCollection, String docName, String videosArray) {

        DocumentReference docIdRef = rootRef.collection(mainCollection).document(docName);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> group = (List<String>) document.get(videosArray);

                    for (int i = 0; i < group.size(); i++) {
                        ModelDrillExercise modelVideos = new ModelDrillExercise();
                        modelVideos.setVideoId(group.get(i));
                        modelDrillExercises.add(modelVideos);

                    }

                    adapterDrillExercise = new AdapterDrillExercise(getApplicationContext(), modelDrillExercises);
                    recyclerView.setAdapter(adapterDrillExercise);
                    myProgressBar.hideProgress();

                }
                myProgressBar.hideProgress();
            }
        });
    }

    public void btn_back(View view) {
        finish();
    }

    public void btnQuiz(View view) {

        if (screenName.equals("Rifle Marksmanship")){
            Intent intent=new Intent(getApplicationContext(),Quiz.class);
            intent.putExtra("screenName","rifle_marksmanShip");
            startActivity(intent);

        }else if(screenName.equals("Army Resiliancy")){
            Toast.makeText(this, "No Quiz in database yet", Toast.LENGTH_SHORT).show();
        }else if(screenName.equals("Returning From Basics")){
            Toast.makeText(this, "No Quiz in database yet", Toast.LENGTH_SHORT).show();

        }

    }

    public void btnGuide(View view) {
        goToUrl("https://study.com/academy/lesson/the-four-fundamentals-of-marksmanship.html");

    }
    private void goToUrl (String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

}