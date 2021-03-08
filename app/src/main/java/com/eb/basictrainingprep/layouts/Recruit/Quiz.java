package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.utils.MyProgressBar;
import com.eb.basictrainingprep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    RadioButton rB1, rB2, rB3, rB4,rb5;
    RadioGroup rGroup;
    int index = 0;
    private Button btnNext;
    private TextView txtQuestion;
    String answer;
    int number2;
    List<String> quesList = new ArrayList<>();
    List<Integer> questionsListGenerate = new ArrayList<Integer>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String selectedChoice="null";
    MyProgressBar myProgressBar;
    int radioButtonID;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    int correctindex = 0;
    int wrongIndex = 0;
    ProgressBar progressBar;
    RadioButton radioButton, rb1;
    String quizName;
    Calendar calendar;
    String currentDate;
    String uid = RecruitWelcomeDrawer.uid;
    String ques;
    Map<String, String> mapWrongQuestions = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        rB1 = findViewById(R.id.rb1);
        rB2 = findViewById(R.id.rb2);
        rB3 = findViewById(R.id.rb3);
        rB4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);

        txtQuestion = findViewById(R.id.txt_question);
        btnNext = findViewById(R.id.btn_quizNext);
        progressBar = findViewById(R.id.progressBar);

        rGroup = findViewById(R.id.rbGroup);

        quizName = getIntent().getStringExtra("screenName");

        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());
        checkQuiz();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                progressBar.setProgress(index);

                if (index == 10) {
                    Intent intent = new Intent(getApplicationContext(), QuizResult.class);
                    intent.putExtra("correctMarks", correctAnswer);
                    intent.putExtra("wrongMarks", wrongAnswer);
                    intent.putExtra("correctIndex", correctindex);
                    intent.putExtra("wrongIndex", wrongIndex);
                    intent.putExtra("screenName", quizName);
                    intent.putExtra("hashMapWrongQues", (Serializable) mapWrongQuestions);
                    startActivity(intent);
                    finish();
                    return;
                }
                index++;
                myProgressBar.showProgress(Quiz.this);
                int mydoc = questionsListGenerate.get(index - 1);
                getFireStoreQuesion(mydoc);
                //  radioButton.setChecked(false);
                // btnNext.setVisibility(View.INVISIBLE);
                rb5.setChecked(true);
             }
        });

    }

    private void getView() {
        rGroup.setVisibility(View.VISIBLE);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{
                        Color.GRAY, //disabled
                        Color.WHITE //enabled
                }
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rB1.setButtonTintList(colorStateList);
            rB2.setButtonTintList(colorStateList);
            rB3.setButtonTintList(colorStateList);
            rB4.setButtonTintList(colorStateList);
        }

        clearRadioGroup();

        firestore.collection("quizzes_questions").document(quizName)
                .collection("quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        String quesId = queryDocumentSnapshot.getId();
                        quesList.add(quesId);
                    }

                    number2 = quesList.size();
                    int[] number = new int[10];
                    int count = 0;
                    int num;
                    Random r = new Random();
                    while (count < number.length) {
                        num = r.nextInt(number2);
                        boolean repeat = false;
                        do {
                            for (int i = 0; i < number.length; i++) {
                                if (num == number[i]) {
                                    repeat = true;
                                    break;
                                } else if (i == count) {
                                    number[count] = num;
                                    count++;
                                    repeat = true;
                                    break;
                                }
                            }
                        } while (!repeat);

                    }

                    for (int j = 0; j < number.length; j++) {
                        System.out.print(number[j]);
                        int genNum = number[j];
                        questionsListGenerate.add(genNum);
                        Log.e("randoms", String.valueOf(number[j]));

                    }
                    int mydoc = questionsListGenerate.get(index);
                    getFireStoreQuesion(mydoc);
                    index += 1;
                }
            }
        });

    }

    private void checkQuiz() {
        firestore.collection("users").document("recruit").collection(uid).document("quiz_results")
                .collection(quizName).document(currentDate)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        myProgressBar.hideProgress();
                        txtQuestion.setText("You already given the Quiz today.\n" +
                                "You can submit only one quiz in one day");
                        btnNext.setVisibility(View.GONE);
                    } else {
                        getView();
                    }

                }
            }
        });
    }

    private void checkAnswer() {
         if (selectedChoice.equals(answer)) {
            correctAnswer = correctAnswer + 10;
            correctindex++;
        } else {
            wrongAnswer = wrongAnswer + 10;
             mapWrongQuestions.put(ques,answer);
             wrongIndex++;
        }
    }

    private void getFireStoreQuesion(int mydoc) {


        DocumentReference docIdRef = firestore.collection("quizzes_questions").document(quizName)
                .collection("quiz").document(quesList.get(mydoc));

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ques = document.getString("question");
                        answer = document.getString("answer");
                        txtQuestion.setText(ques);
                        List<String> optionsList = (List<String>) document.get("options");
                        rB1.setText(optionsList.get(0));
                        rB2.setText(optionsList.get(1));
                        rB3.setText(optionsList.get(2));
                        rB4.setText(optionsList.get(3));
                        myProgressBar.hideProgress();
                    }

                }
                myProgressBar.hideProgress();

            }
        });
    }

    private void clearRadioGroup() {
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonID = rGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) rGroup.findViewById(radioButtonID);
                selectedChoice = (String) radioButton.getText();

            }
        });


    }


    public void btn_back(View view) {
        finish();
    }


}