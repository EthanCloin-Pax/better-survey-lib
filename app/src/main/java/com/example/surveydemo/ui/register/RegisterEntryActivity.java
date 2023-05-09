package com.example.surveydemo.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.paxus.pay.host.ui.init.R;

@Route(path = "/Registration/Entry")
public class RegisterEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_entry);

        Button registerBtn = findViewById(R.id.register_splash_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterEntryActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button skipBtn = findViewById(R.id.register_splash_skip_btn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nextPath = "/HostIdle/Main";
                ARouter.getInstance().build(nextPath)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation();
                finish();
            }
        });
    }
}
