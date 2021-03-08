package com.eb.basictrainingprep.layouts.Recruiter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.layouts.Recruit.RecruitProfile;
import com.eb.basictrainingprep.layouts.Recruit.RecruitWelcomeDrawer;
import com.eb.basictrainingprep.utils.MyProgressBar;
import com.eb.basictrainingprep.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruiterProfile extends AppCompatActivity {

    private ImageView image_recruit;
    private ImageButton btnEditPic;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private String userID;
    MyProgressBar myProgressBar;
    private FirebaseFirestore myDB;
    private TextView txtRecruiterId, txtShipDate;
    private EditText edtFullname, edtCity, edtState, edtZip, edtEmail, edtPhone;
    private String gender = "";
    private String holdoverPlace;
    private Spinner spnRecruitingStation;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar, calendar;
    String currentDate;
    private String baseLocation, recruitingStation;
    private RadioGroup rgGender;
    private RadioButton radioButton;
    ArrayList baseArray = new ArrayList<>();
    ArrayList recruitingArray = new ArrayList<>();
    String strRecruitingLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_profile);

        myDB = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myProgressBar = MyProgressBar.getInstance();
        myProgressBar.showProgress(this);
        image_recruit = findViewById(R.id.recruit_img);
        btnEditPic = findViewById(R.id.btn_editPic);
        txtRecruiterId = findViewById(R.id.txt_recruiterId);
        edtFullname = findViewById(R.id.edt_fullName);
        edtCity = findViewById(R.id.edt_city);
        edtState = findViewById(R.id.edt_state);
        edtEmail = findViewById(R.id.edt_email);
        edtZip = findViewById(R.id.edt_zip);
        edtPhone = findViewById(R.id.edt_phone);

        spnRecruitingStation = findViewById(R.id.spinner_recruitingStations);
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = dateFormat.format(calendar.getTime());


        rgGender = findViewById(R.id.radioGroup_gender);

        myCalendar = Calendar.getInstance();


        getRecruitingStations();

        getProfileData();


        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rgGender.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                gender = radioButton.getText().toString();

            }
        });


        btnEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void getRecruitingStations() {
        myDB.collection("locations").document("recruiting_stations").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                List<String> recruitList = (List<String>) document.get("stations");

                                for (int i = 0; i < recruitList.size(); i++) {
                                    recruitingArray.add(recruitList.get(i));
                                }
                                spnRecruitingStation.setAdapter(new ArrayAdapter<>(RecruiterProfile.this, android.R.layout.simple_spinner_dropdown_item, recruitingArray));

                            }
                        }
                    }
                });


    }

    private void getProfileData() {
        Task<DocumentSnapshot> documentReference = myDB.collection("users").document("recruiter")
                .collection(userID).document("profile")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            try {
                                String picUrl = document.getString("photo");
                                Glide.with(RecruiterProfile.this)
                                        .load(picUrl)
                                        .circleCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .placeholder(R.drawable.placeholder)
                                        .into(image_recruit);
                                String fullName = document.getString("fullname");
                                edtFullname.setText(fullName);
                                String recruiterId = document.getString("Id");
                                txtRecruiterId.setText(recruiterId);
                                String email = document.getString("email");
                                edtEmail.setText(email);
                                String phone = document.getString("phone");
                                edtPhone.setText(phone);
                                String city = document.getString("city");
                                edtCity.setText(city);
                                String state = document.getString("state");
                                edtState.setText(state);
                                String zip = document.getString("zip");
                                edtZip.setText(zip);
                                if (!document.getString("gender").equals("")) {
                                    gender = document.getString("gender");

                                } else {
                                    gender = "";
                                }

                                recruitingStation = document.getString("recruiting_station");


                            } catch (Exception e) {
                                myProgressBar.hideProgress();
                            }


                            myProgressBar.hideProgress();
                        }
                        myProgressBar.hideProgress();

                    }
                });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RecruiterProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(RecruiterProfile.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // image_recruit.setImageBitmap(thumbnail);
        Glide.with(RecruiterProfile.this)
                .load(thumbnail)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.placeholder)
                .into(image_recruit);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Glide.with(RecruiterProfile.this)
                .load(bm)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.placeholder)
                .into(image_recruit);
    }

    public void btnUpdate(View view) {


        String fulName = edtFullname.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        String state = edtState.getText().toString().trim();
        String zip = edtZip.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String emial = edtEmail.getText().toString().trim();


        if (gender.equals("")) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fulName)) {
            Toast.makeText(this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please Enter City Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(state)) {
            Toast.makeText(this, "Please Enter State Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(zip)) {
            Toast.makeText(this, "Please Enter Zip Code", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }


        if (spnRecruitingStation.getSelectedItem() == null) {
            Toast.makeText(this, "Please Select Recruiting Stations", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadData(gender, fulName, city, state, zip, emial, phone);
    }

    private void uploadData(String gender, String fulName, String city, String state, String zip, String email, String phone) {
        myProgressBar.showProgress(this);
        String filePathAndName = "profile/" + "image_" + userID;
        strRecruitingLocation = spnRecruitingStation.getSelectedItem().toString();
        if (image_recruit.getDrawable() != null) {

            Bitmap bitmap = ((BitmapDrawable) image_recruit.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference raf = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            raf.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()) {

                                Map<String, Object> updatedData = new HashMap<>();
                                updatedData.put("photo", downloadUri);
                                updatedData.put("gender", gender);
                                updatedData.put("fullname", fulName);
                                updatedData.put("city", city);

                                updatedData.put("state", state);
                                updatedData.put("zip", zip);
                                updatedData.put("email", email);
                                updatedData.put("phone", phone);

                                updatedData.put("recruiting_station", strRecruitingLocation);
                                saveImageUrl(updatedData);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    myProgressBar.hideProgress();

                }
            });
        }
    }

    private void saveImageUrl(Map<String, Object> updatedData) {

        DocumentReference docRef = myDB.collection("users").document("recruiter").collection(userID).document("profile");
        docRef.update(updatedData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isComplete()) {
                    myProgressBar.hideProgress();
                    Toast.makeText(RecruiterProfile.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();
                }
                myProgressBar.hideProgress();

            }
        });


    }


}