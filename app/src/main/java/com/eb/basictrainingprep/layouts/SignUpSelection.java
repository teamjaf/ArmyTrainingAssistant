package com.eb.basictrainingprep.layouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.PriceChangeConfirmationListener;
import com.android.billingclient.api.PriceChangeFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
//import com.eb.basictrainingprep.InAppBillingActivity;
import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Ambassador.AmbassadorLayout;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.layouts.Recruiter.RecruiterLayout;
import com.eb.basictrainingprep.layouts.Vateran_Mentor.VateranMentorLayout;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SignUpSelection extends AppCompatActivity{

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String userId;
    private MyProgressBar progressBar;
    private LinearLayout linearPayment;
    private String radioBtnValue;
    private FirebaseFirestore myDB;
    private FirebaseUser firebaseUser;
    private String selectedCategory;
    private int selectedId;
    private String countId;
    private int incrementedId;
    SharedPreferences sharedPreferences;
    Calendar calendar;
    String currentDate;
    DocumentReference docRef;
    //////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_selection);

        radioGroup = findViewById(R.id.radio_group);
        linearPayment = findViewById(R.id.linear_btnPayment);
        linearPayment.setVisibility(View.GONE);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myDB = FirebaseFirestore.getInstance();

        userId = firebaseUser.getUid();

        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Uid", userId);
        editor.apply();

        progressBar = MyProgressBar.getInstance();

        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        getUserCountNo();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                linearPayment.setVisibility(View.VISIBLE);
                selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                if (selectedId == 1) {
                    selectedCategory = Constants.RECRUIT;
                    radioBtnValue = radioButton.getText().toString();

                } else if (selectedId == 2) {
                    selectedCategory = Constants.RECRUIT;
                    radioBtnValue = radioButton.getText().toString();
                } else if (selectedId == 3) {
                    selectedCategory = Constants.RECRUITER;
                    radioBtnValue = radioButton.getText().toString();
                }
//                }else if(selectedId==4){
//                    selectedCategory=Constants.RECRUITER;
//                    radioBtnValue=radioButton.getText().toString();
//                }else if(selectedId==5){
//                    selectedCategory=Constants.VATERAN;
//                    radioBtnValue=radioButton.getText().toString();
//                }

            }
        });


    }

    private void getUserCountNo() {
        DocumentReference docRef = myDB.collection("userAssignId").document("id");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    countId = Objects.requireNonNull(document.get("countNo")).toString();
                    incrementedId = Integer.parseInt(countId) + 1;
                }
            }
        });

    }

    public void btnNext(View view) {

        setFirestoreValues();

    }




    private void setFirestoreValues() {
        progressBar.showProgress(this);

        Map<Object, Object> userProfile = new HashMap<>();
        userProfile.put("status", radioBtnValue);
        userProfile.put("Id", String.valueOf(incrementedId));
        userProfile.put("level_badge", 0);
        userProfile.put("fullname", firebaseUser.getDisplayName());
        userProfile.put("email", firebaseUser.getEmail());
        userProfile.put("category", selectedCategory);
        userProfile.put("photo", String.valueOf(firebaseUser.getPhotoUrl()));
        userProfile.put("date", currentDate);
        userProfile.put("recruiter", "not_selected");

        myDB.collection("users").document(selectedCategory).collection(userId).document("profile")
                .set(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateCount();
                    createUser(userId, selectedCategory);
                    if (selectedId == 1) {
                        createVideosFields();

                    } else if (selectedId == 2) {
                        createVideosFields();

                    }
//                    else if (selectedId == 3) {
//                        Intent intent = new Intent(getApplicationContext(), AmbassadorLayout.class);
//                        intent.putExtra("category", selectedCategory);
//                        intent.putExtra("uid", userId);
//                        startActivity(intent);
//                        finish();
//                    }
                    else if (selectedId == 3) {
                        Intent intent = new Intent(getApplicationContext(), RecruiterLayout.class);
                        intent.putExtra("category", selectedCategory);
                        intent.putExtra("uid", userId);
                        startActivity(intent);
                        finish();
                    }
//                    else if (selectedId == 5) {
//                        Intent intent = new Intent(getApplicationContext(), VateranMentorLayout.class);
//                        intent.putExtra("category", selectedCategory);
//                        intent.putExtra("uid", userId);
//                        startActivity(intent);
//                        finish();
//                    }
                    progressBar.hideProgress();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.hideProgress();
                Toast.makeText(SignUpSelection.this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void createVideosFields() {
        Map<Object, Object> videoField = new HashMap<>();
        videoField.put("allVideo", 0);
        myDB.collection("users").document("recruit").collection(userId)
                .document("totalWatched").set(videoField).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    createBadges();
                    Intent intent = new Intent(getApplicationContext(), RecruitWelcomeDrawer.class);
                    intent.putExtra("category", selectedCategory);
                    intent.putExtra("uid", userId);
                    startActivity(intent);
                    finish();
                }
            }

            private void createBadges() {

                docRef = myDB.collection("users").document("recruit")
                        .collection(userId).document("badges");

                Map<String, Object> quizBadge = new HashMap<>();
                quizBadge.put("badges", true);
                docRef.set(quizBadge).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {

                        }
                    }
                });
            }
        });

    }

    private void createUser(String uid, String selectedCategory) {
        Map<Object, Object> userData = new HashMap<>();
        userData.put("category", selectedCategory);
        DocumentReference docRef = myDB.collection("usersCategory").document(uid);
        docRef.set(userData).isSuccessful();

    }

    private void updateCount() {
        DocumentReference docRef = myDB.collection("userAssignId").document("id");
        docRef.update("countNo", String.valueOf(incrementedId));
    }




}