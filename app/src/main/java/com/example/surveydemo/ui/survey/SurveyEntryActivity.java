package com.example.surveydemo.ui.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.paxus.pay.host.ui.init.R;

@Route(path = "/Survey/Entry")
public class SurveyEntryActivity extends AppCompatActivity {

    public static final String ACTION_DISMISS_SURVEY = "ACTION_DISMISS_SURVEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_entry);
        Button startBtn = findViewById(R.id.survey_start_btn);
        Button dismissBtn = findViewById(R.id.survey_dismiss_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent surveyActivity = new Intent(SurveyEntryActivity.this, SurveyActivity.class);
                startActivity(surveyActivity);
                finish();
            }
        });
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO send braodcast
                Intent intent = new Intent();
                intent.setAction(ACTION_DISMISS_SURVEY);
                sendBroadcast(intent);
                finish();
            }
        });
    }

}
