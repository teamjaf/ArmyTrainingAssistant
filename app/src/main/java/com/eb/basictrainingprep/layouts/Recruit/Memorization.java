package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eb.basictrainingprep.R;

public class Memorization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorization);
    }

    public void btn_back(View view) {
        finish();
    }

    public void btn_codeOfConduct(View view) {
        Intent intent=new Intent(Memorization.this, UpdatesVideo.class);
        intent.putExtra("videoLink","6rG472iBvTo");
        intent.putExtra("screenName","Code of Conduct");
        startActivity(intent);
    }

//    public void btn_RankInsengia(View view) {
//
//    }

    public void btn_numerals(View view) {
        Intent intent6=new Intent(Memorization.this, PhoneticAlphabet.class);
        startActivity(intent6);
    }

    public void btn_bct_ocut(View view) {
        Intent intent=new Intent(Memorization.this, UpdatesVideo.class);
        intent.putExtra("videoLink","6rG472iBvTo");
        intent.putExtra("screenName","BCT/OCUT");
        startActivity(intent);
    }

    public void btn_generalOrders(View view) {
        Intent intent3=new Intent(Memorization.this, UpdatesVideo.class);
        intent3.putExtra("videoLink","ZNsUgV8Hc04");
        intent3.putExtra("screenName","General Orders");
        startActivity(intent3);
    }

    public void btn_buddyResponsibilites(View view) {
        Intent intent3=new Intent(Memorization.this, UpdatesVideo.class);
        intent3.putExtra("videoLink","Ir-ZCPto9d0");
        intent3.putExtra("screenName","Buddy Responsibilities");
        startActivity(intent3);
    }

    public void btn_soliderCreed(View view) {
        Intent intent3=new Intent(Memorization.this, VideoScreen.class);
        intent3.putExtra("videoScreen","Soldiers Creed");
        startActivity(intent3);
    }

    public void btn_armyValues(View view) {
        Intent intent2=new Intent(Memorization.this, VideoScreen.class);
        intent2.putExtra("videoScreen","Army Values");
        startActivity(intent2);
    }

    public void btn_armySongs(View view) {
        Intent intent=new Intent(Memorization.this, VideoScreen.class);
        intent.putExtra("videoScreen","Army Songs");
        startActivity(intent);
    }

    public void btn_militaryTime(View view) {
        startActivity(new Intent(Memorization.this,MiliteryTime.class));
    }
}