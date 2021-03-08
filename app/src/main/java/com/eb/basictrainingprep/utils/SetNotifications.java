package com.eb.basictrainingprep.utils;

import androidx.annotation.NonNull;

import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetNotifications {



    public void saveNotification(String notficationText) {

        String  currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
         Map<String, Object> badgeNoti = new HashMap<>();
        badgeNoti.put("title", notficationText);
        badgeNoti.put("time",currentDateTimeString);
        firestore.collection("users").document("recruit").collection(RecruitWelcomeDrawer.uid)
                .document("notifications").collection("allNotifications")
                .add(badgeNoti).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isComplete()) {

                }
            }
        });
    }
}
