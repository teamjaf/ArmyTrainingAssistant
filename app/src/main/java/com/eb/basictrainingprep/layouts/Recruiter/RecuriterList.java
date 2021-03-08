package com.eb.basictrainingprep.layouts.Recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterRecruitList;
import com.eb.basictrainingprep.model.ModelRecruitList;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecuriterList extends AppCompatActivity {

    String uid = RecruiterLayout.uid;
    String category = RecruiterLayout.category;
    FirebaseFirestore firestore;
    List<ModelRecruitList> modelRecruitLists;
    AdapterRecruitList adapterRecruitList;
    MyProgressBar myProgressBar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuriter_list);
        myProgressBar = MyProgressBar.getInstance();


        myProgressBar.showProgress(this);

        firestore = FirebaseFirestore.getInstance();

        modelRecruitLists = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_reruitActivity);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        firestore.collection("users").document(category).collection(uid).document("recruits_list")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> group = (List<String>) document.get("recruits_id");

                        for (int i = 0; i < group.size(); i++) {

                            getRecruitActivity(group.get(i));
                        }
                    } else {
                        myProgressBar.hideProgress();
                        Toast.makeText(RecuriterList.this, "No Recruiter Found", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void getRecruitActivity(String uid) {
        firestore.collection("users").document("recruit").collection(uid).document("profile")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ModelRecruitList myModel = new ModelRecruitList();

                        String name = document.get("fullname").toString();
                        myModel.setRecruitName(name);

                        if (document.get("date") != null) {
                            String enrollDate = document.get("date").toString();
                            myModel.setRecruitEnrollDate(enrollDate);

                        } else {
                            String enrollDate = document.get("date").toString();
                            myModel.setRecruitEnrollDate(enrollDate);

                        }
                        myModel.setRecruitUid(uid);

                        modelRecruitLists.add(myModel);
                    }

                    adapterRecruitList = new AdapterRecruitList(RecuriterList.this, modelRecruitLists);
                    recyclerView.setAdapter(adapterRecruitList);
                }
            }
        });
        myProgressBar.hideProgress();

    }
}