package com.eb.basictrainingprep.layouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btnLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp_emailFb.class);
        intent.putExtra("status", "Sign In");
        intent.putExtra("btnName","SIGN In WITH GMAIL");
        startActivity(intent);
    }

    public void btnSignUp(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp_emailFb.class);
        intent.putExtra("status", "Sign Up");
        intent.putExtra("btnName","SIGN UP WITH GMAIL");
        startActivity(intent);

    }

}
