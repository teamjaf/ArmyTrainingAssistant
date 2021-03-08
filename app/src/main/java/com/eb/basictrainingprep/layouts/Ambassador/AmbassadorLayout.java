package com.eb.basictrainingprep.layouts.Ambassador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AmbassadorLayout extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private FirebaseUser firebaseUser;
    MyProgressBar progressBar;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambassador_layout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = MyProgressBar.getInstance();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();

      //  progressBar.showProgress(this);
       // setPreps(true);
    }


}