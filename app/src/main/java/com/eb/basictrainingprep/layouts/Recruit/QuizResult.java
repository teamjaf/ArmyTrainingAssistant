package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterRightQuestions;
import com.eb.basictrainingprep.model.ModelRightAnswers;
import com.eb.basictrainingprep.model.QuizMarksModel;
import com.eb.basictrainingprep.utils.SetNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizResult extends AppCompatActivity {

    TextView txtResult, txtMarks, txtCorrectQuestions, txtWrongQuestions;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String uid = RecruitWelcomeDrawer.uid;
    String category = RecruitWelcomeDrawer.category;
    String quizName;
    Calendar calendar;
    String currentDate;
    String currentDateTimeString;
    int correct;
    int wrong;
    int correctIndex;
    int wrongIndex;
    DocumentReference docRef;
    List<ModelRightAnswers> modelRightAnswersList;
    RecyclerView recyclerViewAnswers;
    CardView cardRightAnswers;
    AdapterRightQuestions adapterRightQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        correct = getIntent().getIntExtra("correctMarks", 0);
        wrong = getIntent().getIntExtra("wrongMarks", 0);
        correctIndex = getIntent().getIntExtra("correctIndex", 0);
        wrongIndex = getIntent().getIntExtra("wrongIndex", 0);
        quizName = getIntent().getStringExtra("screenName");
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashMapWrongQues");


        txtResult = findViewById(R.id.txt_result);
        txtMarks = findViewById(R.id.txt_marks);
        txtCorrectQuestions = findViewById(R.id.txt_correctQuestions);
        txtWrongQuestions = findViewById(R.id.txt_wrongQuestions);
        cardRightAnswers = findViewById(R.id.card_right_questions);
        modelRightAnswersList = new ArrayList<>();
        recyclerViewAnswers = findViewById(R.id.recycler_rightAnswers);
        recyclerViewAnswers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewAnswers.hasFixedSize();
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        docRef = firestore.collection("users").document("recruit")
                .collection(uid).document("badges");


        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());


        if (correct < 80) {
            txtResult.setText("FAIL");
            txtMarks.setText("Your Scored is " + String.valueOf(correct) + "% of 100%");
            txtCorrectQuestions.setText("You answerd " + correctIndex + " of 10 correctly");
            txtWrongQuestions.setText("You answerd " + wrongIndex + " of 10 incorrectly");
            saveQuizData(txtResult.getText().toString());
        } else {
            txtResult.setText("PASS");
            txtMarks.setText("Your Scored is " + String.valueOf(correct) + "% of 100%");
            txtCorrectQuestions.setText("You answerd " + correctIndex + " of 10 correctly");
            txtWrongQuestions.setText("You answerd " + wrongIndex + " of 10 incorrectly");
            saveQuizData(txtResult.getText().toString());
            setBadge();

        }

        if (correct < 100) {
            cardRightAnswers.setVisibility(View.VISIBLE);
            for (Map.Entry<String, String> e : hashMap.entrySet()) {
                ModelRightAnswers myModel = new ModelRightAnswers();
                String key = e.getKey();
                String value = e.getValue();
                myModel.setQuestion(key);
                myModel.setAnswer(value);
                modelRightAnswersList.add(myModel);
            }
            adapterRightQuestions=new AdapterRightQuestions(this,modelRightAnswersList);
            recyclerViewAnswers.setAdapter(adapterRightQuestions);
        }

    }

    private void setBadge() {

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        updateBadge();
                    }
                }
            }
        });
    }

    private void updateBadge() {
        docRef.update("quiz", true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    SetNotifications setNotifications = new SetNotifications();
                    setNotifications.saveNotification("Congrats...You passed the Quiz and got the Badge");
                }
            }
        });

    }


    private void saveQuizData(String status) {
        Map<String, Object> quizMap = new HashMap<>();
        quizMap.put("total_questions", "10");
        quizMap.put("time", currentDateTimeString);
        quizMap.put("correct", correctIndex);
        quizMap.put("incorrect", wrongIndex);
        quizMap.put("score", correct + "%");
        quizMap.put("quiz_type", quizName);
        quizMap.put("quiz_result", txtResult.getText().toString());
        quizMap.put("date", currentDate);

        firestore.collection("users").document("recruit").collection(uid)
                .document("quiz_results").collection(quizName).document(currentDate)
                .set(quizMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(QuizResult.this, "Quiz result uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void btn_back(View view) {
        finish();
    }
}