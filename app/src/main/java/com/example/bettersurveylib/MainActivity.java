package com.example.bettersurveylib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.bettersurveylib.api.TerminalRegisterClient;
import com.example.bettersurveylib.api.TerminalRegisterInterface;
import com.example.bettersurveylib.api.requests.GetRegisterUrlReq;
import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TerminalRegisterInterface terminalRegisterAPI;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        terminalRegisterAPI = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);

        Button btn = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(l -> makeUrlRequest());
    }

    // USEFUL
    private void makeUrlRequest() {
        GetRegisterUrlReq req = new GetRegisterUrlReq("PAX", "Aries8", "00000002", Arrays.asList("Survey"));
        Log.i("TAG", "request obj: " + req);

        Call<GetRegisterUrlRsp> requestUrlCall = terminalRegisterAPI.doGetRegisterUrl("timestamp", "signaturedata", req);
        requestUrlCall.enqueue(new Callback<GetRegisterUrlRsp>() {
            @Override
            public void onResponse(Call<GetRegisterUrlRsp> call, Response<GetRegisterUrlRsp> response) {
                Log.i("TAG", "" + response);
                GetRegisterUrlRsp rspBody = response.body();
                textView.setText(rspBody.registerUrl);
            }

            @Override
            public void onFailure(Call<GetRegisterUrlRsp> call, Throwable t) {
                Log.i("TAG", "it did not work good");
                call.cancel();
            }
        });
    }
}