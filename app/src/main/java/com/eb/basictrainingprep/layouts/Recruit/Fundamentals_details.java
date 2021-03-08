package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eb.basictrainingprep.R;

public class Fundamentals_details extends AppCompatActivity {

    private TextView txtScreenName;
    ImageView img_fundamentals;
    TextView txtFundamentals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundamentals_details);

        String screenName=getIntent().getStringExtra("screenName");

        txtScreenName=findViewById(R.id.txt_screenNameF);
        txtScreenName.setText(screenName);
        txtFundamentals=findViewById(R.id.txt_fundamentals);
        img_fundamentals=findViewById(R.id.img_fundamental);

        if(screenName.equals("Steady Position")){
            img_fundamentals.setImageResource(R.drawable.steady_position);

        }else if(screenName.equals("Aiming")){
            img_fundamentals.setImageResource(R.drawable.aiming_position);

        }else if(screenName.equals("Breath Control")){
            img_fundamentals.setImageResource(R.drawable.breath_control);
            txtFundamentals.setText(getResources().getString(R.string.breath_control));
            txtFundamentals.setVisibility(View.VISIBLE);

        }else if(screenName.equals("Trigger Squeeze")){
            img_fundamentals.setImageResource(R.drawable.trigger_squeeze);
            txtFundamentals.setText(getResources().getString(R.string.trigger_squeeze));
            txtFundamentals.setVisibility(View.VISIBLE);
        }
    }

    public void btn_back(View view) {
        finish();
    }
}