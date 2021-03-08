package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterPhoneticAlphabetic;
import com.eb.basictrainingprep.model.ModelPhoneticAlphabetic;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PhoneticAlphabet extends AppCompatActivity {

    private RecyclerView recyclerPhonetic;
    private List<ModelPhoneticAlphabetic> modelPhonetic;
    private AdapterPhoneticAlphabetic adapterPhoneticAlphabetic;
    MyProgressBar myProgressBar;
    FirebaseFirestore rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonetic_alphabet);

        myProgressBar = MyProgressBar.getInstance();

        modelPhonetic = new ArrayList<>();
        recyclerPhonetic = findViewById(R.id.recycler_phonetic);
        recyclerPhonetic.hasFixedSize();
        rootRef = FirebaseFirestore.getInstance();

        recyclerPhonetic.setLayoutManager(new

                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getFirestoredata();


    }

    private void getFirestoredata() {
            myProgressBar.showProgress(this);
              rootRef.collection("armyKnowledge").document("phonetic_alphabetic").collection("allData")
                      .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()){
                          QuerySnapshot document = task.getResult();

                          for (DocumentChange documentChange : document.getDocumentChanges())
                          {
                              ModelPhoneticAlphabetic myModel=new ModelPhoneticAlphabetic();

                              String date =  documentChange.getDocument().getData().get("date").toString();
                              myModel.setDate(date);

                              String event =  documentChange.getDocument().getData().get("event").toString();
                              myModel.setEvent(event);

                              String desc =  documentChange.getDocument().getData().get("desc").toString();
                              myModel.setDesc(desc);

                              modelPhonetic.add(myModel);
                          }
                          adapterPhoneticAlphabetic=new AdapterPhoneticAlphabetic(getApplicationContext(),modelPhonetic);
                          recyclerPhonetic.setAdapter(adapterPhoneticAlphabetic);
                          myProgressBar.hideProgress();
                      }

                  }
              });
    }

    public void btn_back(View view) {
        finish();
    }
}