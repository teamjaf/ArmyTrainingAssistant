package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterImportantArmyDates;
import com.eb.basictrainingprep.adapters.AdapterPhoneticAlphabetic;
import com.eb.basictrainingprep.model.ModelImportantArmyDates;
import com.eb.basictrainingprep.model.ModelPhoneticAlphabetic;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ImportantArmyDates extends AppCompatActivity {

    private List<ModelImportantArmyDates> modelArmyDate;
    private RecyclerView recyclerArmyDates;
    private AdapterImportantArmyDates adapterArmyDates;
    private MyProgressBar myProgressBar;
    FirebaseFirestore rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_army_dates);

        modelArmyDate=new ArrayList<>();
        recyclerArmyDates=findViewById(R.id.recyclerImportantDate);
        recyclerArmyDates.hasFixedSize();
        recyclerArmyDates.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        myProgressBar=MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        rootRef = FirebaseFirestore.getInstance();

        getFirestoredata();
    }
    private void getFirestoredata() {
         rootRef.collection("armyKnowledge").document("important_army_dates").collection("allData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot document = task.getResult();

                    for (DocumentChange documentChange : document.getDocumentChanges())
                    {
                        ModelImportantArmyDates myModel=new ModelImportantArmyDates();

                        String date =  documentChange.getDocument().getData().get("date").toString();
                        myModel.setDate(date);

                        String desc =  documentChange.getDocument().getData().get("desc").toString();
                        myModel.setDesc(desc);

                        modelArmyDate.add(myModel);
                    }
                    adapterArmyDates=new AdapterImportantArmyDates(getApplicationContext(),modelArmyDate);
                    recyclerArmyDates.setAdapter(adapterArmyDates);
                    myProgressBar.hideProgress();
                }

            }
        });
        myProgressBar.hideProgress();

    }


    public void btn_back(View view) {
        finish();
    }
}