package com.eb.basictrainingprep.layouts.Vateran_Mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VateranMentorLayout extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private FirebaseUser firebaseUser;
    MyProgressBar progressBar;
    String userId;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vateran_mentor_layout);

         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = MyProgressBar.getInstance();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();

        progressBar.showProgress(this);
        setPreps();
    }
    private void setPreps() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("uid", userId);
        editor.putBoolean("login", true);
        editor.putString("category", Constants.VATERAN);
        editor.apply();
        progressBar.hideProgress();
    }
}