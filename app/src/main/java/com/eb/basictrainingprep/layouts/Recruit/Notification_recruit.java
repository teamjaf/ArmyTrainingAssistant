package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.AdapterNotifications;
import com.eb.basictrainingprep.model.ModelNotifications;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Notification_recruit extends AppCompatActivity {

    MyProgressBar myProgressBar;
    List<ModelNotifications> modelNotificationsList;
    RecyclerView recyclerView;
    TextView txtNoRecord,txtNoNotification;
    FirebaseFirestore firestore;
    String uid=RecruitWelcomeDrawer.uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_recruit);

        myProgressBar = MyProgressBar.getInstance();
        modelNotificationsList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        txtNoNotification=findViewById(R.id.txt_noNotification);

        txtNoRecord = findViewById(R.id.txt_noti_noData);

        recyclerView = findViewById(R.id.recycler_notification);
        int screenName=getIntent().getIntExtra("screenName",0);
        if(screenName==100){
            String myId=getIntent().getStringExtra("uid");
            uid=myId;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
       layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);



        getNotifications();

    }

    private void getNotifications() {

        firestore.collection("users").document("recruit").collection(uid)
                .document("notifications").collection("allNotifications").orderBy("time")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot document = task.getResult();

                    for (DocumentChange documentChange:document.getDocumentChanges()){
                        ModelNotifications myModel=new ModelNotifications();

                        String notificationTitle=documentChange.getDocument().getData().get("title").toString();
                        myModel.setNotificatonTitle(notificationTitle);

                        String notificationDate=documentChange.getDocument().getData().get("time").toString();
                        myModel.setNotificatonDate(notificationDate);

                        modelNotificationsList.add(myModel);

                    }
                    AdapterNotifications adapterNotifications=new AdapterNotifications(getApplicationContext(),modelNotificationsList);
                    recyclerView.setAdapter(adapterNotifications);

                    if(modelNotificationsList.size()==0){
                        txtNoNotification.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void btn_back(View view) {
        finish();
    }
}