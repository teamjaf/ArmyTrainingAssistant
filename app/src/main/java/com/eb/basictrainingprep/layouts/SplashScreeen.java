package com.eb.basictrainingprep.layouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Ambassador.AmbassadorLayout;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.layouts.Recruiter.RecruiterLayout;
import com.eb.basictrainingprep.layouts.Vateran_Mentor.VateranMentorLayout;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SplashScreeen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";
      Handler handler;


    private FirebaseAuth firebaseAuth;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screeen);

        firebaseAuth = FirebaseAuth.getInstance();


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (firebaseAuth.getCurrentUser() != null) {

                    Log.e("SplashCheck","1");
                    checkFirestore(firebaseAuth.getCurrentUser().getUid());


                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 2000);
    }


    private void checkFirestore(String uid) {

        FirebaseFirestore myDB = FirebaseFirestore.getInstance();

        DocumentReference docRef = myDB.collection("usersCategory").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        Log.e("SplashCheck","2");

                        categoryName = (String) document.get("category");
                        switch (categoryName) {
                            case Constants.RECRUIT: {
                                Intent intent = new Intent(getApplicationContext(), RecruitWelcomeDrawer.class);
                                intent.putExtra("category", categoryName);
                                Log.e("SplashCheck","3");

                                intent.putExtra("uid",uid);
                                startActivity(intent);
                                finish();
                                break;
                            }
                            case Constants.AMBASSADOR: {
                                Intent intent = new Intent(getApplicationContext(), AmbassadorLayout.class);
                                intent.putExtra("category", categoryName);
                                intent.putExtra("uid", firebaseAuth.getUid());
                                startActivity(intent);
                                finish();
                                break;
                            }
                            case Constants.RECRUITER: {
                                Intent intent = new Intent(getApplicationContext(), RecruiterLayout.class);
                                intent.putExtra("category", categoryName);
                                intent.putExtra("uid", firebaseAuth.getUid());
                                startActivity(intent);
                                finish();
                                break;
                            }
                            case Constants.VATERAN: {
                                Intent intent = new Intent(getApplicationContext(), VateranMentorLayout.class);
                                intent.putExtra("category", categoryName);
                                intent.putExtra("uid", firebaseAuth.getUid());
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), SignUpSelection.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}