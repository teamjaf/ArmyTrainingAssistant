package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eb.basictrainingprep.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MiliteryTime extends AppCompatActivity {

    Calendar calendar;
    String currentDate;
    TextView txtMilitaryTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_militery_time);

        txtMilitaryTime=findViewById(R.id.txt_militaryTime);

        calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        currentDate = dateFormat.format(calendar.getTime());

        txtMilitaryTime.setText(currentDate);

    }

    public void btn_back(View view) {
        finish();
    }
}