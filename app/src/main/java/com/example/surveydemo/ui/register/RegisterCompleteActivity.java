package com.example.surveydemo.ui.register;


import static com.example.surveydemo.util.SurveyUtil.getSN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.R;
import com.paxus.view.BaseAppCompatActivity;

public class RegisterCompleteActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completed);
        TextView snTv = findViewById(R.id.register_completed_sn);
        snTv.setText("Questionnaire ID: " + getSN(this));
        Button doneBtn = findViewById(R.id.register_completed_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: go back to idle
                String nextPath = "/HostIdle/Main";
                ARouter.getInstance().build(nextPath)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
                finish();
            }
        });
    }
}
