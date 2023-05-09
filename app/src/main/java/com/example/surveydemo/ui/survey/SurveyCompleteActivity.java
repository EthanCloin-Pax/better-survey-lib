package com.example.surveydemo.ui.survey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paxus.pay.host.ui.init.R;

public class SurveyCompleteActivity extends AppCompatActivity {

    public static final String ACTION_DISMISS_SURVEY = "ACTION_DISMISS_SURVEY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_completed);
        Button btn = findViewById(R.id.survey_completed_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: re-route
                Intent intent = new Intent();
                intent.setAction(ACTION_DISMISS_SURVEY);
                sendBroadcast(intent);
                finish();
            }
        });
    }
}
