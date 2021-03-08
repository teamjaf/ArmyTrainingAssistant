package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eb.basictrainingprep.R;

public class PhysicalTraining extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_training);



    }

    public void btn_back(View view) {
        finish();
    }



    public void btn_BaslineTest(View view) {

        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","Baseline Test");
        startActivity(intent);

    }

    public void btn_standards(View view) {

        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","Army Standards");
        startActivity(intent);
    }

    public void btn_armyFitness(View view) {

        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","Army Fitness");
        startActivity(intent);
    }

    public void btn_prepDrill(View view) {
        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","Prep Drill 1-10");
        startActivity(intent);
    }

    public void btn_recoveryDrill(View view) {

        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","Recovery Drill");
        startActivity(intent);
    }

    public void btn_4ForCore(View view) {
        Intent intent=new Intent(getApplicationContext(),FitnessVideos.class);
        intent.putExtra("fitnessVideos","4 for the Core");
        startActivity(intent);
    }

    public void btn_pushupSitups(View view) {
        Intent intent=new Intent(getApplicationContext(),DailyActivities.class);
        startActivity(intent);
    }

    public void btn_activityReports(View view) {
        Intent intent=new Intent(getApplicationContext(),MyActivityReport.class);
        startActivity(intent);
    }
}