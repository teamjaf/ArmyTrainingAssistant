package com.eb.basictrainingprep.layouts.Recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterMyReport;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.model.ModelActivitiesReport;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecruitActivities extends AppCompatActivity {

    String recruitName;
    String uid;

    private List<ModelActivitiesReport> modelActivitiesReportList;
    private RecyclerView recyclerView;
    private AdapterMyReport adapterMyReport;
    private FirebaseFirestore firestore;


    MyProgressBar myProgressBar;
    TextView txtScreenName;
    TextView txtArmySongs, txtArmyValues, txtSoliderCreed, txtArmyRanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_activities);
        myProgressBar = MyProgressBar.getInstance();

        recruitName = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");

        txtArmySongs = findViewById(R.id.txt_armySongs);
        txtArmyValues = findViewById(R.id.txt_armyValues);
         txtSoliderCreed = findViewById(R.id.txt_soliderCreed);
        txtArmyRanks = findViewById(R.id.txt_armyRanks);

        txtScreenName = findViewById(R.id.txt_screen);
        txtScreenName.setText(recruitName);

        firestore = FirebaseFirestore.getInstance();
        modelActivitiesReportList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerReport);
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        getFirestoreData();

        getArmyKnowledge();
    }

    private void getArmyKnowledge() {

        firestore.collection("users").document("recruit").collection(uid).document("totalWatched")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("Army Songs") != null) {
                            String armySongs = document.get("Army Songs").toString();
                            txtArmySongs.setText(String.valueOf(armySongs));
                        }
                        if (document.get("Army Values") != null) {
                            String armyValues = document.get("Army Values").toString();
                            txtArmyValues.setText(String.valueOf(armyValues));
                        }
                        if (document.get("Soldiers Creed") != null) {
                            String soliderCreed = document.get("Soldiers Creed").toString();
                            txtSoliderCreed.setText(String.valueOf(soliderCreed));
                        }
                        if (document.get("Army Ranks") != null) {
                            String armyRanks = document.get("Army Ranks").toString();
                            txtArmyRanks.setText(String.valueOf(armyRanks));

                        }

                    }
                }
            }
        });

    }

    private void getFirestoreData() {
        firestore.collection("users").document("recruit").collection(uid).document("DailyActivities")
                .collection("physical_training")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    assert document != null;
                    for (DocumentChange documentChange : document.getDocumentChanges()) {

                        ModelActivitiesReport myModel = new ModelActivitiesReport();

                        String date = documentChange.getDocument().getData().get("date").toString();
                        myModel.setDate(date);

                        String mileRun = documentChange.getDocument().getData().get("mileRun").toString();
                        myModel.setMileRun(mileRun);

                        String pushUps = documentChange.getDocument().getData().get("pushups").toString();
                        myModel.setPushups(pushUps);

                        String situps = documentChange.getDocument().getData().get("situps").toString();
                        myModel.setSitups(situps);

                        String notes = documentChange.getDocument().getData().get("notes").toString();
                        myModel.setNote(notes);
                        modelActivitiesReportList.add(myModel);

                    }
                    adapterMyReport = new AdapterMyReport(modelActivitiesReportList, getApplicationContext());
                    recyclerView.setAdapter(adapterMyReport);
                    myProgressBar.hideProgress();

                }

            }
        });
    }

    public void btn_back(View view) {
        finish();
    }
}