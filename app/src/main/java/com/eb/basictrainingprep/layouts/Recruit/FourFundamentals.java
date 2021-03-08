package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eb.basictrainingprep.R;

public class FourFundamentals extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_fundamentals);
    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_steadyPosition(View view) {
        Intent i=new Intent(FourFundamentals.this,Fundamentals_details.class);
        i.putExtra("screenName","Steady Position");
        startActivity(i);
    }

    public void btn_aiming(View view) {
        Intent i=new Intent(FourFundamentals.this,Fundamentals_details.class);
        i.putExtra("screenName","Aiming");
        startActivity(i);
    }

    public void btn_breathControl(View view) {
        Intent i=new Intent(FourFundamentals.this,Fundamentals_details.class);
        i.putExtra("screenName","Breath Control");
        startActivity(i);
    }

    public void btn_triggerSqueeze(View view) {
        Intent i=new Intent(FourFundamentals.this,Fundamentals_details.class);
        i.putExtra("screenName","Trigger Squeeze");
        startActivity(i);
    }
}