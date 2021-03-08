package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DailyActivities extends AppCompatActivity implements View.OnClickListener {

    private TextView txtPushUps, txtSitups, txtRun;
    private ImageButton btnPushUps, btnSitups, btnRun;
    private Button btnSave, btnWatch;
    private EditText edtNotes;

    int pushUps = 0;
    int situps = 0;
    int mileRun = 0;
    String note;
    FirebaseFirestore rootRef;
    Calendar calendar;
    String currentDate;
    String category = RecruitWelcomeDrawer.category;
    String uid = RecruitWelcomeDrawer.uid;

    MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_activities);

        rootRef = FirebaseFirestore.getInstance();

        txtPushUps = findViewById(R.id.txt_pushups);
        txtSitups = findViewById(R.id.txt_sitUps);
        txtRun = findViewById(R.id.txt_mileRun);

        myProgressBar = MyProgressBar.getInstance();

        edtNotes = findViewById(R.id.edt_notes);

        btnPushUps = findViewById(R.id.btn_PushUps);
        btnPushUps.setOnClickListener(this);

        btnSitups = findViewById(R.id.btn_sitUps);
        btnSitups.setOnClickListener(this);

        btnRun = findViewById(R.id.btn_mileRun);
        btnRun.setOnClickListener(this);

        btnSave = findViewById(R.id.btn_saveExercise);
        btnSave.setOnClickListener(this);

        btnWatch = findViewById(R.id.btn_watchVideos);
        btnWatch.setOnClickListener(this);

        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());
    }

    public void btn_back(View view) {
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_PushUps:
                pushUps++;
                txtPushUps.setText(String.valueOf(pushUps));

                break;
            case R.id.btn_sitUps:
                situps++;
                txtSitups.setText(String.valueOf(situps));
                break;

            case R.id.btn_mileRun:
                mileRun++;
                txtRun.setText(String.valueOf(mileRun));
                break;


            case R.id.btn_saveExercise:
                note = edtNotes.getText().toString().trim();
                if (TextUtils.isEmpty(note)) {
                    edtNotes.setError("Please Enter Note");
                    return;
                }
                myProgressBar.showProgress(this);
                saveData(note);
                break;

            case R.id.btn_watchVideos:
                Intent intent = new Intent(getApplicationContext(), FitnessVideos.class);
                intent.putExtra("fitnessVideos", "Pushup/Situp/Run");
                startActivity(intent);
                break;

        }
    }

    private void saveData(String note) {

        Map<String, Object> activities = new HashMap<>();
        activities.put("pushups", pushUps);
        activities.put("situps", situps);
        activities.put("mileRun", mileRun);
        activities.put("notes", note);
        activities.put("date", currentDate);

        rootRef.collection("users").document(category)
                .collection(uid).document("DailyActivities").collection("physical_training").document(currentDate)
                .set(activities).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myProgressBar.hideProgress();
                    resetFields();
                    Toast.makeText(DailyActivities.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(getApplicationContext(),MyActivityReport.class);
                    startActivity(intent);
                }
                myProgressBar.hideProgress();
            }
        });
        myProgressBar.hideProgress();

    }

    private void resetFields() {
        pushUps=0;
        situps=0;
        mileRun=0;
        txtPushUps.setText("0");
        txtRun.setText("0");
        txtSitups.setText("0");
        edtNotes.setText("");

    }
}