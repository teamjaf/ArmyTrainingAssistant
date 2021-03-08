package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.RankAdapter;
import com.eb.basictrainingprep.model.RankRow;
import com.eb.basictrainingprep.model.Ranks;
import com.eb.basictrainingprep.model.RanksModel;

import java.util.ArrayList;
import java.util.List;

public class ArmyRankLink extends AppCompatActivity {

    List<RanksModel> ranksModels;
    RecyclerView recyclerViewRank;
    RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_rank_link);


        ranksModels=new ArrayList<>();
        recyclerViewRank=findViewById(R.id.recyclerViewRanks);
        recyclerViewRank.hasFixedSize();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerViewRank.setLayoutManager(layoutManager);


        setData();
    }
    private void setData() {

        Ranks[] nonCommissionedOffers = {
                new Ranks("Private", "PVT", "E-1", ""),
                new Ranks("Private", "PV2", "E-2", ""),
                new Ranks("Private First Class", "PFC", "E-3", ""),
                new Ranks("Specialist", "SPC", "E-4", ""),
                new Ranks("Corporal", "CPL", "E-4", "A SPC recognized with NCO authorities"),
                new Ranks("Sergeant", "SGT", "E-5", "Team leader"),
                new Ranks("Staff Sergeant", "SSG", "E-6", "Squad leader or section chief"),
                new Ranks("Sergeant First Class", "SFC", "E-7", "Senior NCO in a platoon"),
                new Ranks("Master Sergeant", "MSG", "E-8", "NCOIC at battalion and brigade"),
                new Ranks("First Sergeant", "1SG", "E-8 ", "Senior NCO in a company; advisor to the commander"),
                new Ranks("Sergeant Major", "SGM", "E-9 ", "Principal advisor on a battalion and higher HQs staff"),
                new Ranks("Command Sergeant Major", "CSM", "E-9 ", "Senior enlisted advisor at battalion and higher HQs"),
                new Ranks("Sergeant Major of the Army", "SMA", "E-9 ", "Senior NCO in the Army; advisor to the Chief of Staff of the Army"),

        };
        Ranks[] warrentOfficers = {
                new Ranks("Warrant Officer 1", "WO1", "W-1", "Company and battalion staffs"),
                new Ranks("Chief Warrant Officer 2", "CW2", "W-2", "Company and battalion staffs"),
                new Ranks("Chief Warrant Officer 3", "CW3", "W-3", "Company and higher staffs"),
                new Ranks("Chief Warrant Officer 4", "CW4", "W-4", "Battalion and higher staffs"),
                new Ranks("Chief Warrant Officer 5", "CW5", "W-5", "Brigade and higher staff"),
        };

        Ranks[] commissionedOfficers = {
                new Ranks("2nd Lieutenant", "2LT", "O-1", "Platoon leader"),
                new Ranks("1st Lieutenant", "1LT", "O-2", "Company Executive Officer"),
                new Ranks("Captain", "CPT", "O-3", "Company Executive OfficerCompany Commander; Battalion Staff Officer"),
                new Ranks("Major", "MAJ", "O-4", "Battalion Executive Officer; Brigade Staff Officer"),
                new Ranks("Lieutenant Colonel", "LTC", "O-5", "Battalion Commander; Division Staff Officer "),
                new Ranks("Colonel", "COL", "O-6", "Brigade Commander; Division Staff Officer"),
        };
        Ranks[] generalOfficer = {
                new Ranks("Brigadier General", "BG", "O-7", ""),
                new Ranks("Major General", "MG", "O-8", ""),
                new Ranks("Lieutenant General", "LTG", "O-9", ""),
                new Ranks("General", "GEN", "O-10", ""),

        };

        RankRow[] rankRows = {
                new RankRow(nonCommissionedOffers.clone(), "Enlisted and Non-Commissioned Offiers"),
                new RankRow(warrentOfficers.clone(), "Warrant Officer"),
                new RankRow(commissionedOfficers.clone(), "Commissioned Offiers"),
                new RankRow(generalOfficer.clone(), "General Officers")

        };

        for (int i = 0; i < rankRows.length; i++) {

            Ranks[] ranksName = rankRows[i].getRankCat();

            for (int j = 0; j < ranksName.length; j++) {
                RanksModel myModel = new RanksModel();
                myModel.setRankCatName(rankRows[i].getRankCatName());
                myModel.setRankTitle(ranksName[j].getTitle());
                myModel.setRankAbr(ranksName[j].getAbbrivation());
                myModel.setRankPayGrade(ranksName[j].getPayGrade());
                myModel.setRankRemarks(ranksName[j].getRemarks());

                ranksModels.add(myModel);

            }

        }
        rankAdapter = new RankAdapter(ArmyRankLink.this, ranksModels);
        recyclerViewRank.setAdapter(rankAdapter);

    }


    public void btn_back(View view) {
        finish();
    }
}