package com.eb.basictrainingprep.layouts.Recruiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eb.basictrainingprep.BuildConfig;
import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.MainActivity;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecruiterLayout extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private FirebaseUser firebaseUser;
    MyProgressBar progressBar;
     String userId;
    public static String uid ;
    public static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_layout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        uid=getIntent().getStringExtra("uid");
        category=getIntent().getStringExtra("category");

     }


    public void btn_inviteRecruit(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void btn_recruitList(View view) {
        startActivity(new Intent(getApplicationContext(),RecuriterList.class));
    }

    public void btn_facebookGroup(View view) {
    }

    public void btn_myProfile(View view) {
        startActivity(new Intent(getApplicationContext(),RecruiterProfile.class));
    }

    public void btnLogout(View view) {
        GoogleSignIn.getClient(
                getApplicationContext(),
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(RecruiterLayout.this, MainActivity.class));
        finish();
    }
}