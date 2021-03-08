package com.eb.basictrainingprep.layouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Ambassador.AmbassadorLayout;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.layouts.Recruiter.RecruiterLayout;
import com.eb.basictrainingprep.layouts.Vateran_Mentor.VateranMentorLayout;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_emailFb extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private Button sigUpGmail;
    private int RC_SIGN_IN = 1;
    TextView txtStatus;
    MyProgressBar progressBar;
    String categoryName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email_fb);

        sigUpGmail = findViewById(R.id.btnGmail);
        txtStatus = findViewById(R.id.txt_status);

        String status = getIntent().getStringExtra("status");
        String btnName=getIntent().getStringExtra("btnName");
        txtStatus.setText(status);
        sigUpGmail.setText(btnName);
        progressBar = MyProgressBar.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        sigUpGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.showProgress(SignUp_emailFb.this);
                signIn();
            }
        });
    }

    private void signIn() {
         Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignResult(task);
        }


    }

    private void handleSignResult(Task<GoogleSignInAccount> completeTask) {

        try {

            GoogleSignInAccount account = completeTask.getResult(ApiException.class);
             assert account != null;
            firebaseGoogleAuth(account);

        } catch (Exception e) {
            Toast.makeText(this, "Signed Failed", Toast.LENGTH_SHORT).show();
            Log.e("loginError",e.getMessage());
//            firebaseGoogleAuth(null);

        }

    }


    private void firebaseGoogleAuth(GoogleSignInAccount account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);

                } else {
                    Toast.makeText(SignUp_emailFb.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUi(null);

                }

            }
        });
    }

    private void updateUi(FirebaseUser firebaseUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            assert firebaseUser != null;
            String uid = firebaseUser.getUid();
            checkFirestore(uid);


        }
    }

    private void checkFirestore(String uid) {

        FirebaseFirestore myDB = FirebaseFirestore.getInstance();
         DocumentReference docRef = myDB.collection("usersCategory").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    progressBar.hideProgress();
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        categoryName = (String) document.get("category");
                        assert categoryName != null;

                        if (categoryName.equals(Constants.RECRUIT)) {
                            sharedPreferences=getSharedPreferences("myPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Uid", uid);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), RecruitWelcomeDrawer.class);
                            intent.putExtra("category",categoryName);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            finishAffinity();

                        } else if (categoryName.equals(Constants.AMBASSADOR)) {
                            Intent intent = new Intent(getApplicationContext(), AmbassadorLayout.class);
                            intent.putExtra("category",categoryName);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            finishAffinity();
                        } else if (categoryName.equals(Constants.RECRUITER)) {
                            Intent intent = new Intent(getApplicationContext(), RecruiterLayout.class);
                            intent.putExtra("category",categoryName);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            finishAffinity();
                        } else if (categoryName.equals(Constants.VATERAN)) {
                            Intent intent = new Intent(getApplicationContext(), VateranMentorLayout.class);
                            intent.putExtra("category",categoryName);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            progressBar.hideProgress();
                            Intent intent = new Intent(getApplicationContext(), SignUpSelection.class);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }else {
                        progressBar.hideProgress();
                        Intent intent = new Intent(getApplicationContext(), SignUpSelection.class);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        finishAffinity();
                    }

                } else {
                    //Toast.makeText(SignUp_emailFb.this, "Task falied", Toast.LENGTH_SHORT).show();
                    progressBar.hideProgress();
                    Intent intent = new Intent(getApplicationContext(), SignUpSelection.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });
    }

}