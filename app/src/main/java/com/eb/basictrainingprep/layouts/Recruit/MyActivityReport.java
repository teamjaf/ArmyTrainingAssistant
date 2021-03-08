package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterMyReport;
import com.eb.basictrainingprep.adapters.AdapterPhoneticAlphabetic;
import com.eb.basictrainingprep.model.ModelActivitiesReport;
import com.eb.basictrainingprep.model.ModelPhoneticAlphabetic;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyActivityReport extends AppCompatActivity {


    private List<ModelActivitiesReport> modelActivitiesReportList;
    private RecyclerView recyclerView;
    private TextView txtNoRecord;
    private AdapterMyReport adapterMyReport;
    private FirebaseFirestore firestore;
    private MyProgressBar myProgressBar;
    private String category=RecruitWelcomeDrawer.category;
    private String uid=RecruitWelcomeDrawer.uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        myProgressBar=MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        txtNoRecord=findViewById(R.id.txt_activity_noRecord);
        firestore=FirebaseFirestore.getInstance();
        modelActivitiesReportList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerReport);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

         recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        getFirestoreData();


    }

    private void getFirestoreData() {
        firestore.collection("users").document(category).collection(uid).document("DailyActivities")
                .collection("physical_training")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot document = task.getResult();

                    for (DocumentChange documentChange : document.getDocumentChanges())
                    {
                        ModelActivitiesReport myModel=new ModelActivitiesReport();

                        String date =  documentChange.getDocument().getData().get("date").toString();
                        myModel.setDate(date);

                        String mileRun =  documentChange.getDocument().getData().get("mileRun").toString();
                        myModel.setMileRun(mileRun);

                        String pushUps =  documentChange.getDocument().getData().get("pushups").toString();
                        myModel.setPushups(pushUps);

                        String situps =  documentChange.getDocument().getData().get("situps").toString();
                        myModel.setSitups(situps);

                        String notes =  documentChange.getDocument().getData().get("notes").toString();
                        myModel.setNote(notes);


                        modelActivitiesReportList.add(myModel);
                    }
                    adapterMyReport=new AdapterMyReport(modelActivitiesReportList, getApplicationContext());
                    recyclerView.setAdapter(adapterMyReport);
                    myProgressBar.hideProgress();

                    if(modelActivitiesReportList.size()==0){
                        txtNoRecord.setVisibility(View.VISIBLE);
                    }

                }

            }
        });
    }

    public void btn_back(View view) {
        finish();
    }
}