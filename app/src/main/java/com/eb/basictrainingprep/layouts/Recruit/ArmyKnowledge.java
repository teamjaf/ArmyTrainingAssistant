package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArmyKnowledge extends AppCompatActivity  implements View.OnClickListener{

    Button btnArmySongs,btnArmyValues,btnSolidersCreed,btnArmyRank,btnPhoneticAlphbets,btnWarriorEthos,btnArmyDates,btnMemorization,btnArmyExams;

    MyProgressBar myProgressBar;
    FirebaseFirestore firestore;
    String uid=RecruitWelcomeDrawer.uid;
    String category=RecruitWelcomeDrawer.category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_knowledge);

        myProgressBar=MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        firestore=FirebaseFirestore.getInstance();

        btnArmySongs=findViewById(R.id.btn_armySongs);
        btnArmySongs.setOnClickListener(this);

        btnArmyValues=findViewById(R.id.btn_armyValues);
        btnArmyValues.setOnClickListener(this);

        btnSolidersCreed=findViewById(R.id.btn_solidersCreed);
        btnSolidersCreed.setOnClickListener(this);

        btnArmyRank=findViewById(R.id.btn_armyRank);
        btnArmyRank.setOnClickListener(this);

        btnPhoneticAlphbets=findViewById(R.id.btn_phoneticAlphbets);
        btnPhoneticAlphbets.setOnClickListener(this);

        btnWarriorEthos=findViewById(R.id.btn_warriorEthos);
        btnWarriorEthos.setOnClickListener(this);

        btnArmyDates=findViewById(R.id.btn_importantArmyDates);
        btnArmyDates.setOnClickListener(this);

//        btnMemorization=findViewById(R.id.btn_memorization);
//        btnMemorization.setOnClickListener(this);
//
//        btnArmyExams=findViewById(R.id.btn_armyExam);
//        btnArmyExams.setOnClickListener(this);


        firestore.collection("users").document(category).collection(uid).document("totalWatched")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.get("Army Songs") !=null){
                            String armySongs = document.get("Army Songs").toString();
                            if(!armySongs.equals("0")){
                                btnArmySongs.setText("Complete");
                            }else {
                                btnArmySongs.setText("Continue");

                            }

                        }if(document.get("Army Values") !=null){
                            String armyValues = document.get("Army Values").toString();
                            if(!armyValues.equals("0")){
                                btnArmyValues.setText("Complete");
                            }else {
                                btnArmyValues.setText("Continue");
                            }
                        }if(document.get("Soldiers Creed") !=null){
                            String soliderCreed = document.get("Soldiers Creed").toString() ;
                            if(!soliderCreed.equals("0")){
                                btnSolidersCreed.setText("Complete");
                            }else {
                                btnSolidersCreed.setText("Continue");
                            }
                        }if(document.get("Army Ranks") !=null) {
                            String armyRanks=document.get("Army Ranks").toString();
                            if(!armyRanks.equals("0")){
                                btnArmyRank.setText("Complete");
                            }else {
                                btnArmyRank.setText("Continue");
                            }
                        }


                    }
                }
            }
        });


        myProgressBar.hideProgress();



    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_armySongs:
                Intent intent=new Intent(ArmyKnowledge.this, VideoScreen.class);
                intent.putExtra("videoScreen","Army Songs");
                startActivity(intent);
                break;

            case R.id.btn_armyValues:
                Intent intent2=new Intent(ArmyKnowledge.this, VideoScreen.class);
                intent2.putExtra("videoScreen","Army Values");
                startActivity(intent2);
                break;
            case R.id.btn_solidersCreed:
                Intent intent3=new Intent(ArmyKnowledge.this, VideoScreen.class);
                intent3.putExtra("videoScreen","Soldiers Creed");
                startActivity(intent3);
                break;
            case R.id.btn_armyRank:
                Intent intent4=new Intent(ArmyKnowledge.this, ArmyRanks.class);
                //intent4.putExtra("videoScreen","Army Ranks");
                startActivity(intent4);
                break;
            case R.id.btn_warriorEthos:
                Intent intent5=new Intent(ArmyKnowledge.this, WarriorEthos.class);
                intent5.putExtra("warriorEthos","Warrior Ethos");
                startActivity(intent5);
                break;

            case R.id.btn_phoneticAlphbets:
                Intent intent6=new Intent(ArmyKnowledge.this, PhoneticAlphabet.class);
                startActivity(intent6);
                break;
            case R.id.btn_importantArmyDates:
                Intent intent7=new Intent(ArmyKnowledge.this, ImportantArmyDates.class);
                startActivity(intent7);
                break;

//            case R.id.btn_armyExam:
//                Intent intent8=new Intent(ArmyKnowledge.this, Quiz.class);
//                intent8.putExtra("screenName","army_knowledge");
//                startActivity(intent8);
//                break;

//            case R.id.btn_memorization:
//                Intent intent9=new Intent(ArmyKnowledge.this, Memorization.class);
//                startActivity(intent9);
//                break;


        }
    }
}