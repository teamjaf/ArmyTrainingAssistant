package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.RankAdapter;
import com.eb.basictrainingprep.model.RankRow;
import com.eb.basictrainingprep.model.Ranks;
import com.eb.basictrainingprep.model.RanksModel;

import java.util.ArrayList;
import java.util.List;

public class ArmyRanks extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_ranks);

    }


    public void btn_back(View view) {
        finish();
    }

    public void btnQuiz(View view) {
        Intent intent = new Intent(getApplicationContext(), Quiz.class);
        intent.putExtra("screenName", "army_knowledge");
        startActivity(intent);
    }

    public void btn_enlistedOfficers(View view) {
        Intent intent = new Intent(ArmyRanks.this,ArmyRanksDetails.class);
        intent.putExtra("category","Enlisted Officers");
        startActivity(intent);

    }

    public void btn_warrantedOfficers(View view) {
        Intent intent = new Intent(ArmyRanks.this,ArmyRanksDetails.class);
        intent.putExtra("category","Warranted Officers");
        startActivity(intent);
    }

    public void btn_commissionedOfficer(View view) {
        Intent intent = new Intent(ArmyRanks.this,ArmyRanksDetails.class);
        intent.putExtra("category","Commissioned Officers");
        startActivity(intent);
    }

    public void btn_rankTestKnowledge(View view) {
        Intent intent = new Intent(getApplicationContext(), Quiz.class);
        intent.putExtra("screenName", "army_knowledge");
        startActivity(intent);
    }

    public void btn_rankLinks(View view) {
        startActivity(new Intent(getApplicationContext(),ArmyRankLink.class));
    }
}