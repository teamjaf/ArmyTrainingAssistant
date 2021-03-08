package com.eb.basictrainingprep.layouts.Recruit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.eb.basictrainingprep.R;

public class ResilinceAudio extends AppCompatActivity implements View.OnClickListener {

    Button btnArmySong, btnArmyValues, btnSoldiersCreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resilince_audio);

        btnArmySong = findViewById(R.id.btn_armySong);
        btnArmySong.setOnClickListener(this);
        btnArmyValues = findViewById(R.id.btn_armyValue);
        btnArmyValues.setOnClickListener(this);
        btnSoldiersCreed = findViewById(R.id.btn_soliderCreed);
        btnSoldiersCreed.setOnClickListener(this);

    }

    public void btn_back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_armySong:
                Intent intent = new Intent(getApplicationContext(), VideoScreen.class);
                intent.putExtra("videoScreen", "Army Songs");
                startActivity(intent);
                break;

            case R.id.btn_armyValue:
                Intent intent2 = new Intent(getApplicationContext(), VideoScreen.class);
                intent2.putExtra("videoScreen", "Army Values");
                startActivity(intent2);
                break;
            case R.id.btn_soliderCreed:
                Intent intent3 = new Intent(getApplicationContext(), VideoScreen.class);
                intent3.putExtra("videoScreen", "Soldiers Creed");
                startActivity(intent3);
                break;
        }
    }
}