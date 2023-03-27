package com.example.bettersurveylib;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bettersurveylib.api.SurveyGateway;
import com.example.bettersurveylib.api.register.TerminalRegisterClient;
import com.example.bettersurveylib.api.register.TerminalRegisterInterface;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TerminalRegisterInterface terminalRegisterAPI;
    TextView textView;
    SurveyGateway gateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        terminalRegisterAPI = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);
        gateway = new SurveyGateway();
        Button btn = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(l -> getRegisterData(new GetRegisterDataReq("PAX", "Aries8", "00000002", "fakecert", Arrays.asList("Survey"))));
    }

    /**
     * Example function which calls the Gateway async_requestRegistrationData method to access SurveyServer API
     * @param req
     */
    public void getRegisterData(GetRegisterDataReq req) {
        Callback<GetRegisterDataRsp> requestCallbackExample = new Callback<GetRegisterDataRsp>() {
            @Override
            public void onResponse(Call<GetRegisterDataRsp> call, Response<GetRegisterDataRsp> response) {
                Log.i("TAG", "" + response);
                GetRegisterDataRsp rspBody = response.body();
                textView.setText(rspBody.registerUrl);
            }

            @Override
            public void onFailure(Call<GetRegisterDataRsp> call, Throwable t) {
                Log.i("TAG", "it did not work good");
                call.cancel();
            }
        };

        gateway.async_requestRegistrationData(req, requestCallbackExample);
    }
}