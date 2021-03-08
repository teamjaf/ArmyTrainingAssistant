package com.eb.basictrainingprep.layouts.Recruit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.adapters.DrawerItemCustomAdapter;
import com.eb.basictrainingprep.layouts.MainActivity;
import com.eb.basictrainingprep.model.DrawerModel;
import com.eb.basictrainingprep.utils.Constants;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.eb.basictrainingprep.utils.SetNotifications;
import com.eb.basictrainingprep.utils.StopWatch;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecruitWelcomeDrawer extends AppCompatActivity implements View.OnClickListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    Button  btnResilienceAudio, btnModules;
    public static final String MyPREFERENCES = "myPref";
    DocumentReference docIdRef;
    SharedPreferences sharedpreferences;
    private FirebaseUser firebaseUser;
    MyProgressBar progressBar;
    String userId;
    private String levelBadge;
    TextView txtBadgeText;
    ImageView imgBadge1, imgBadge2, imgBadge3, imgBadge4, imgBadge5;
    private final String categoryName = Constants.RECRUIT;
    public static String uid = null;
    public static String category;
    FirebaseFirestore rootRef;
    long videoWatchCount = 0;
    StopWatch stopWatch;
    long countSeconds;
    SetNotifications setNotifications;
    MyProgressBar myProgressBar;


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("CheckDataDrawer","1");
        stopWatch = new StopWatch();
        setNotifications = new SetNotifications();
        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);

        stopWatch.resume();

        countSeconds = stopWatch.getElapsedTimeSecs();

        if (countSeconds >= 7200) {
            setLogBadge();
        }

        checkHoldOVer();

    }

    private void checkHoldOVer() {
        FrameLayout frameLayout = findViewById(R.id.content_frame);
        TextView txtAlert = findViewById(R.id.txt_alertHolds);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document("recruit").collection(FirebaseAuth.getInstance().getUid())
                .document("profile").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getBoolean("holds") != null) {
                            boolean holds = document.getBoolean("holds");
                            if (holds) {
                                txtAlert.setVisibility(View.VISIBLE);
                                frameLayout.setVisibility(View.GONE);
                                myProgressBar.hideProgress();
                            } else {
                                txtAlert.setVisibility(View.GONE);
                                frameLayout.setVisibility(View.VISIBLE);
                                myProgressBar.hideProgress();
                            }
                        }
                    } else {
                        myProgressBar.hideProgress();

                    }


                }
            }
        });
        myProgressBar.hideProgress();

    }

    @Override
    protected void onResume() {
        super.onResume();
        stopWatch.resume();
        countSeconds = stopWatch.getElapsedTimeSecs();
        if (countSeconds == 7200) {
            setLogBadge();
        }
    }

    private void setLogBadge() {
        rootRef.collection("users").document(categoryName).collection(userId).document("badges")
                .update("logged_for_2Hours", true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    setNotifications.saveNotification("Congrats....You Logged in for 2 Hours and Won the Badge");
                    countSeconds = 0;
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_welcome_drawer);

        rootRef = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        uid = getIntent().getStringExtra("uid");
        category = getIntent().getStringExtra("category");

        progressBar = MyProgressBar.getInstance();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();
         progressBar.showProgress(this);

        Log.e("CheckDataDrawer","6");

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        txtBadgeText = findViewById(R.id.txt_badges);
        imgBadge1 = findViewById(R.id.img_badge1);
        imgBadge2 = findViewById(R.id.img_badge2);
        imgBadge3 = findViewById(R.id.img_badge3);
        imgBadge4 = findViewById(R.id.img_badge4);
        imgBadge5 = findViewById(R.id.img_badge5);


        btnModules = findViewById(R.id.btn_modeules);
        btnModules.setOnClickListener(this);
        btnResilienceAudio=findViewById(R.id.btn_resilienceAudio);
        btnResilienceAudio.setOnClickListener(this);

        setupToolbar();

        DrawerModel[] drawerModel = new DrawerModel[7];
        drawerModel[0] = new DrawerModel(R.drawable.ic_module, "Module");
        drawerModel[1] = new DrawerModel(R.drawable.ic_performance, "My Performance");
        drawerModel[2] = new DrawerModel(R.drawable.ic_account_circle, "My Profile");
        drawerModel[3] = new DrawerModel(R.drawable.ic_music, "Audio Files");
        drawerModel[4] = new DrawerModel(R.drawable.ic_notifications, "Notifications");
        drawerModel[5] = new DrawerModel(R.drawable.ic_facebook, "Facebook Group");
        drawerModel[6] = new DrawerModel(R.drawable.ic_sign_out, "Logout");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_items, drawerModel);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerModelClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        checkBadges();
        checkVidosBadge();
        Log.e("CheckDataDrawer","9");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopWatch.pause();
        countSeconds = 0;
    }

    private void checkVidosBadge() {
        String myId = sharedpreferences.getString("Uid", "");
        if (myId.equals(userId)) {
            String videoCompletion = sharedpreferences.getString("videoCompletion", "false");
            if (videoCompletion.equals("false")) {
                docIdRef = rootRef.collection("users").document("recruit").collection(uid)
                        .document("totalWatched");
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            if (document.exists()) {
                                if (document.get("Army Songs") != null) {
                                    String  armySong =(String) document.get("Army Songs");
                                    videoWatchCount = videoWatchCount + Integer.parseInt(armySong);
                                }
                                if (document.get("Army Ranks") != null) {
                                    String armyRank =(String) document.get("Army Ranks");
                                    videoWatchCount = videoWatchCount + Integer.parseInt(armyRank);
                                }
                                if (document.get("Army Values") != null) {
                                    String armyValues =document.get("Army Values").toString();
                                    videoWatchCount = videoWatchCount + Integer.parseInt(armyValues);
                                }
                                if (document.get("Soldiers Creed") != null) {
                                    String soliderCreed =document.get("Soldiers Creed").toString();
                                      videoWatchCount = videoWatchCount + Integer.parseInt(soliderCreed);
                                }
                            }
                            updateVideoBadge(videoWatchCount);

                        }
                    }
                });
            }
        }


    }


    private void updateVideoBadge(long watchCount) {

        if (watchCount >= 5) {

            Map<String, Object> videoBadge = new HashMap<>();
            videoBadge.put("watched_videos", true);
            docIdRef = rootRef.collection("users").document(categoryName).collection(userId).document("badges");
            docIdRef.update(videoBadge).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("videoCompletion", "true");
                        editor.apply();
                        setNotifications.saveNotification("Congrats...You watched videos playlist 5 times and get a Badge");
                    }
                }
            });
        }
    }

    private void checkBadges() {
        docIdRef = rootRef.collection("users").document(categoryName).collection(userId).document("badges");
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete()) {
                    DocumentSnapshot document = task.getResult();

                    assert document != null;
                    if (document.exists()) {
                        if (document.getBoolean("quiz") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge1.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("watched_videos") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge2.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("logged_for_2Hours") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge3.setVisibility(View.VISIBLE);

                        }
                        if (document.getBoolean("logs_7_days") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge4.setVisibility(View.VISIBLE);
                        }
                        if (document.getBoolean("refer_friend") != null) {
                            txtBadgeText.setText("Congrats___! You've achieved badges.");
                            imgBadge5.setVisibility(View.VISIBLE);
                        }

                    } else {
                        txtBadgeText.setText("You've not achieved any Badge yet.");
                    }
                }
            }
        });
        progressBar.hideProgress();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_modeules:
                Intent intent4 = new Intent(getApplicationContext(), Modules.class);
                startActivity(intent4);
                break;

            case R.id.btn_resilienceAudio:
                Intent intent = new Intent(getApplicationContext(), ResilinceAudio.class);
                startActivity(intent);
                break;
        }
    }


    private class DrawerModelClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                // fragment = new ConnectFragment();
                startActivity(new Intent(RecruitWelcomeDrawer.this, Modules.class));
                break;
            case 1:
                startActivity(new Intent(RecruitWelcomeDrawer.this, MyPerfromance.class));

                break;
            case 2:
                startActivity(new Intent(RecruitWelcomeDrawer.this, RecruitProfile.class));
                break;
            case 3:
                startActivity(new Intent(RecruitWelcomeDrawer.this, AudioFiles.class));
                break;
            case 4:
                startActivity(new Intent(RecruitWelcomeDrawer.this, Notification_recruit.class));
                break;
            case 5:
                break;
            case 6:
                GoogleSignIn.getClient(
                        getApplicationContext(),
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                ).signOut();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(RecruitWelcomeDrawer.this, MainActivity.class));
                finish();

                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);

            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }


}
