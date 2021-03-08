package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterDrillExercise;
import com.eb.basictrainingprep.adapters.AdapterVideoButtons;
import com.eb.basictrainingprep.model.ModelDrillExercise;
import com.eb.basictrainingprep.model.Model_VideosButtons;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FitnessVideos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ModelDrillExercise> modelDrillExercises;
    private AdapterDrillExercise adapterDrillExercise;
    private TextView txtFitness;
    private FirebaseFirestore rootRef;
    private MyProgressBar myProgressBar;
    private Button btnArmyFitnessSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_videos);

        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        recyclerView = findViewById(R.id.recylcerVideos);
        txtFitness = findViewById(R.id.txt_screenName);
        btnArmyFitnessSite=findViewById(R.id.btn_armyFitnessGuide);

        modelDrillExercises = new ArrayList<>();
        recyclerView.setItemViewCacheSize(20);
        String screenName = getIntent().getStringExtra("fitnessVideos");

        rootRef = FirebaseFirestore.getInstance();

        txtFitness.setText(screenName);

        if (screenName.equals("Baseline Test")) {
            getVideos("basline_test", "basline");

      //      new CountDownTimer(30000, 1000) {

//                public void onTick(long millisUntilFinished) {
//                  }
//
//                public void onFinish() {
//                   // mTextField.setText("done!");
//                     dialogeShow();
//                }

        //    }.start();

        } else if (screenName.equals("Army Fitness")) {
            getVideos("army_fitness", "fitness");
            btnArmyFitnessSite.setVisibility(View.VISIBLE);
        }else if(screenName.equals("Army Standards")){
            getVideos("army_standards","standard");
        }else if(screenName.equals("Suggested PT Training")){
             getVideos("suggested_videos","suggested");
        }else if(screenName.equals("Recovery Drill")){
              getVideos("recovery_drill","recover");
        }else if(screenName.equals("Prep Drill 1-10")){
            getVideos("prep_drill","prepDrill");

        }else if(screenName.equals("4 for the Core")){
            getVideos("4_the_core","4core");
        }else if(screenName.equals("Pushup/Situp/Run")){
            getVideos("pushups_situps_run","pushups");
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.hasFixedSize();


    }

    private void dialogeShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FitnessVideos.this);
        builder.setTitle("Suggestion")
                .setMessage("You can watch PT Training video by pressing 'Yes' ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
                        intent.putExtra("fitnessVideos","Suggested PT Training");
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
    }


    private void getVideos(String docName, String videosArray) {
        DocumentReference docIdRef = rootRef.collection("physicalTraining").document(docName);
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

    private void goToUrl (String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


    public void btn_back(View view) {
        finish();
    }

    public void btnQuiz(View view) {
        Intent intent=new Intent(getApplicationContext(),Quiz.class);
        intent.putExtra("screenName","physical_training");
        startActivity(intent);
    }

    public void btnArmyFitnessSite(View view) {
        goToUrl("https://www.army.mil/acft/?from=features_bar]");
    }
}