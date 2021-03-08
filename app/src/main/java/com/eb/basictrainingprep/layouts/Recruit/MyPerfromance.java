package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterMyReport;
import com.eb.basictrainingprep.adapters.AdapterQuizReport;
import com.eb.basictrainingprep.model.ModelActivitiesReport;
import com.eb.basictrainingprep.model.QuizReportModel;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;  

public class MyPerfromance extends AppCompatActivity {

    private List<ModelActivitiesReport> modelActivitiesReportList;
    private List<QuizReportModel> quizReportModels;
    private RecyclerView recyclerView, recyclerQuiz;
    private AdapterMyReport adapterMyReport;
    private FirebaseFirestore firestore;
    MyProgressBar myProgressBar;
    String uid = RecruitWelcomeDrawer.uid;
    ImageView imgBadge1, imgBadge2, imgBadge3, imgBadge4, imgBadge5;
    TextView txtBadgeText;
    Calendar calendar;
    String currentDate;
    ProgressBar performanceProg;
    TextView txtArmySongs, txtArmyValues, txtSoliderCreed, txtArmyRanks, txtNoRecordPhysical, txtQuizNoRecord, txtProgress, txtScreenName;
    String[] quizNames = {"army_knowledge", "physical_training", "rifle_marksmanShip"};
    int quizCount;
    int count1 = 0;
    int totalDays = 0;
    AdapterQuizReport adapterQuizReport;
    int screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perfromance);
        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        txtProgress = findViewById(R.id.txt_Progress);
        performanceProg = findViewById(R.id.perfromanceProgress);
        performanceProg.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        txtArmySongs = findViewById(R.id.txt_armySongs);
        txtArmyValues = findViewById(R.id.txt_armyValues);
        txtSoliderCreed = findViewById(R.id.txt_soliderCreed);
        txtArmyRanks = findViewById(R.id.txt_armyRanks);
        txtNoRecordPhysical = findViewById(R.id.txt_pysical_noRecord);
        txtQuizNoRecord = findViewById(R.id.txt_quiz_noRecord);
        txtScreenName = findViewById(R.id.txt_screen);

        txtBadgeText = findViewById(R.id.txt_badges);
        imgBadge1 = findViewById(R.id.img_badge1);
        imgBadge2 = findViewById(R.id.img_badge2);
        imgBadge3 = findViewById(R.id.img_badge3);
        imgBadge4 = findViewById(R.id.img_badge4);
        imgBadge5 = findViewById(R.id.img_badge5);

        firestore = FirebaseFirestore.getInstance();

        screenName = getIntent().getIntExtra("screenName", 0);
        if (screenName == 100) {
            String userId = getIntent().getStringExtra("uid");
            String name = getIntent().getStringExtra("name");
            uid = userId;
            txtScreenName.setText(name);
        }
        modelActivitiesReportList = new ArrayList<>();
        quizReportModels = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerReport);
        recyclerQuiz = findViewById(R.id.recyclerQuizReport);

        recyclerQuiz.hasFixedSize();
        recyclerView.hasFixedSize();
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);

        LinearLayoutManager layoutManagerQuiz =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManagerQuiz.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerQuiz.setLayoutManager(layoutManagerQuiz);

        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            if(isConnected){    
                checkAllFunctions();
            }else {
                Toast.makeText(this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }else {
            checkAllFunctions();
        }
    }
    private void checkAllFunctions(){
        getFirestoreData();
        getArmyKnowledge();
        getQuizReport();
        getBadges();
        getQuizCount();
        getVideosScroe();
        getProfileDate();

    }

    private void getProfileDate() {
        firestore.collection("users").document("recruit").collection(uid)
                .document("profile").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String getEnrollDate = document.getString("date");
                        if (document.getString("holdOverStop") != null) {
                            String holdStart = document.getString("holdOverStart");
                            String holdStop = document.getString("holdOverStop");
                            getDate(getEnrollDate, holdStart, holdStop);
                        } else {
                            getDate(getEnrollDate, "noData", "");
                        }
                    } else {
                        myProgressBar.hideProgress();
                    }

                }

            }
        });
    }

    public void getDate(String enrollDate, String holdStart, String holdStop) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        if (holdStart.equals("noData")) {
            try {
                Date dateBefore = myFormat.parse(enrollDate);
                Date dateAfter = myFormat.parse(currentDate);

                long difference = dateAfter.getTime() - dateBefore.getTime();
                float daysBetween = (difference / (1000 * 60 * 60 * 24));
                int remain = (int) daysBetween;
                AllScoreCounts(0, remain+1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                Date dateBefore = myFormat.parse(holdStart);
                Date dateAfter = myFormat.parse(holdStop);
                Date dateEnroll = myFormat.parse(enrollDate);
                Date dateCurrent = myFormat.parse(currentDate);

                assert dateAfter != null;
                assert dateBefore != null;
                long differenceHolds = dateAfter.getTime() - dateBefore.getTime();

                float daysBetweenHolds = (differenceHolds / (1000 * 60 * 60 * 24));

                long diffrenceEnroll = dateCurrent.getTime() - dateEnroll.getTime();
                float daysEnrolls = (diffrenceEnroll / (1000 * 60 * 60 * 24));

                int remain = (int) daysEnrolls - (int) daysBetweenHolds;

                AllScoreCounts(0, remain+1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private void getQuizReport() {

        for (String qName : quizNames) {

            firestore.collection("users").document("recruit").collection(uid)
                    .document("quiz_results").collection(qName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isComplete()) {
                        QuerySnapshot document = task.getResult();
                        assert document != null;
                        if (!document.isEmpty()) {
                            for (DocumentChange documentChange : document.getDocumentChanges()) {
                                QuizReportModel myModel = new QuizReportModel();
                                String date = documentChange.getDocument().getData().get("date").toString();
                                myModel.setQuizDate(date);

                                String quizType = documentChange.getDocument().getData().get("quiz_type").toString();
                                myModel.setQuizType(quizType);

                                String quizScore = documentChange.getDocument().getData().get("score").toString();
                                myModel.setQuizScore(quizScore);

                                String quizResult = documentChange.getDocument().getData().get("quiz_result").toString();
                                myModel.setQuizStatus(quizResult);

                                quizReportModels.add(myModel);

                            }
                            adapterQuizReport = new AdapterQuizReport(getApplicationContext(), quizReportModels);
                            recyclerQuiz.setAdapter(adapterQuizReport);

                        } else {
                            myProgressBar.hideProgress();
                        }
                    }
                }
            });
        }

    }

    private void getQuizCount() {
        firestore.collection("users").document("recruit").collection(uid).document("quiz_results")
                .collection("army_knowledge").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    QuerySnapshot document = task.getResult();
                    if (document != null) {
                        int totalQuiz = document.size() * 30;
                        AllScoreCounts(totalQuiz, 0);
                    } else {
                        myProgressBar.hideProgress();

                    }
                }

            }
        });
    }

    private void AllScoreCounts(int quizScore, int days) {
        count1++;
        quizCount = quizCount + quizScore;
        totalDays = totalDays + days;

        if (count1 == 4) {
            int avgProg = quizCount / totalDays;
            if (avgProg < 50) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    performanceProg.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F70000")));
                }
                performanceProg.setProgress(avgProg);
              //  txtProgress.setText(String.valueOf(avgProg) + "/10");
                txtProgress.setText("3/10");
                return;
            }if (avgProg < 80) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    performanceProg.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#0092CD")));
                }
                performanceProg.setProgress(avgProg);
                //txtProgress.setText(String.valueOf(avgProg) + "%");
                txtProgress.setText("6/10");
                return;
            }if (avgProg > 79 && avgProg < 100) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    performanceProg.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#00B071")));
                }
                performanceProg.setProgress(avgProg);
               // txtProgress.setText(String.valueOf(avgProg) + "%");
                txtProgress.setText("8/10");

                return;
            }if (avgProg == 100) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    performanceProg.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#00B071")));
                }
                performanceProg.setProgress(avgProg);
               // txtProgress.setText("100%");
                txtProgress.setText("10/10");

                return;
            }if(avgProg >100){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    performanceProg.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#00B071")));
                }
                performanceProg.setProgress(avgProg);

                txtProgress.setText("10/10");
                return;
            }


        }
        myProgressBar.hideProgress();


    }


    private void getArmyKnowledge() {

        firestore.collection("users").document("recruit").collection(uid).document("totalWatched")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
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

                    } else {
                        myProgressBar.hideProgress();

                    }
                }
            }
        });

    }

    private void getBadges() {
        DocumentReference docIdRef = firestore.collection("users").document("recruit").collection(uid).document("badges");

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();

                    assert document != null;
                    if (document.exists()) {
                        if (document.getBoolean("quiz") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge1.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("watched_videos") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge2.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("logged_for_2Hours") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge3.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("logs_7_days") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge4.setVisibility(View.VISIBLE);
                        }
                        if (document.getBoolean("refer_friend") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge5.setVisibility(View.VISIBLE);
                        }

                    } else {
                        myProgressBar.hideProgress();
                        txtBadgeText.setText("You've not achieved any Badge yet.");
                    }
                }
            }
        });


    }

    private void getVideosScroe() {
        firestore.collection("users").document("recruit").collection(uid).document("VideosCompletionDate")
                .collection("completedVideos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (null != document) {
                        AllScoreCounts(document.size() * 30, 0);

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

                    AllScoreCounts(document.size() * 40, 0);

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

                    if (modelActivitiesReportList.size() == 0) {
                        txtNoRecordPhysical.setVisibility(View.VISIBLE);

                    }

                } else {
                    myProgressBar.hideProgress();
                }

            }
        });
    }

    public void btn_back(View view) {
        finish();
    }
}