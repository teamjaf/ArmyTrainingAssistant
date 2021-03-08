package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Modules extends AppCompatActivity implements View.OnClickListener {

    LinearLayout cardArmyKnowledge, cardPhysicalTraining, cardResiliency, cardRifleMarkmanship, cardReturningFromBasic;
    MyProgressBar myProgressBar;

    @Override
    protected void onStart() {
        super.onStart();
        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(Modules.this);
        checkHoldOVer();
    }

    private void checkHoldOVer() {
        TextView txtAlert = findViewById(R.id.txt_alertHolds);
        ScrollView scrollView = findViewById(R.id.scrollModule);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document("recruit").collection(FirebaseAuth.getInstance().getUid())
                .document("profile").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        if (document.getBoolean("holds") != null) {
                            boolean holds = document.getBoolean("holds");
                            if (holds) {
                                scrollView.setVisibility(View.GONE);
                                txtAlert.setVisibility(View.VISIBLE);
                                myProgressBar.hideProgress();
                            } else {
                                scrollView.setVisibility(View.VISIBLE);
                                txtAlert.setVisibility(View.GONE);
                                myProgressBar.hideProgress();
                            }
                        }
                    } else {
                        myProgressBar.hideProgress();

                    }

                }
            }
        });
        myProgressBar.hideProgress();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        cardArmyKnowledge = findViewById(R.id.card_armyKnowledge);
        cardArmyKnowledge.setOnClickListener(this);

        cardPhysicalTraining = findViewById(R.id.card_physicalTraining);
        cardPhysicalTraining.setOnClickListener(this);

        cardResiliency = findViewById(R.id.card_resiliency);
        cardResiliency.setOnClickListener(this);

        cardRifleMarkmanship = findViewById(R.id.card_rifleMarksmanship);
        cardRifleMarkmanship.setOnClickListener(this);

        cardReturningFromBasic = findViewById(R.id.card_returningFromBasic);
        cardReturningFromBasic.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_armyKnowledge:
                startActivity(new Intent(Modules.this, ArmyKnowledge.class));
                break;

            case R.id.card_physicalTraining:
                startActivity(new Intent(Modules.this, PhysicalTraining.class));
                break;

            case R.id.card_resiliency:
                Intent intent = new Intent(getApplicationContext(), ArmyResiliency.class);
                intent.putExtra("screenName", "Army Resiliancy");
                startActivity(intent);
                break;

            case R.id.card_rifleMarksmanship:
                startActivity(new Intent(Modules.this, RifleMarksmanShip.class));

                break;

            case R.id.card_returningFromBasic:
                Intent intent2 = new Intent(getApplicationContext(), ArmyResiliency.class);
                intent2.putExtra("screenName", "Returning From Basics");
                startActivity(intent2);
                break;


        }
    }

    public void btn_back(View view) {
        finish();
    }
}