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
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;

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
     *
     * use the response inside the onResponse to update UI as needed for success
     * @param req
     */
    public void getRegisterData(GetRegisterDataReq req) {
        Callback<GetRegisterDataRsp> requestCallbackExample = new Callback<GetRegisterDataRsp>() {
            @Override
            public void onResponse(Call<GetRegisterDataRsp> call, Response<GetRegisterDataRsp> response) {
                Log.i("TAG", "" + response);
                GetRegisterDataRsp rspBody = response.body();

                // access the necessary values from responseBody
                assert rspBody != null;
                textView.setText(rspBody.registerUrl);
            }

            @Override
            public void onFailure(Call<GetRegisterDataRsp> call, Throwable t) {
                Log.i("TAG", "it did not work good: " + t.getLocalizedMessage());
                call.cancel();
            }
        };

        gateway.async_requestRegistrationData(req, requestCallbackExample);
    }

    /**
     * Example function which calls the Gateway async_requestRegistrationData method to access SurveyServer API
     *
     * use the response inside the onResponse to update UI as needed for success
     * @param req
     */
    public void registerTerminalWithStore(RegisterTerminalReq req) {
        Callback<RegisterTerminalRsp> registerCallback = new Callback<RegisterTerminalRsp>() {
            @Override
            public void onResponse(Call<RegisterTerminalRsp> call, Response<RegisterTerminalRsp> response) {
                Log.i("TAG", "" + response);
                RegisterTerminalRsp rspBody = response.body();

                // eventually this needs to store the keys on the device for later use with SurveyAPI
                // for now just move forward in UI Flow
            }

            @Override
            public void onFailure(Call<RegisterTerminalRsp> call, Throwable t) {
                Log.i("TAG", "it did not work good: " + t.getLocalizedMessage());
                call.cancel();

                // don't move forward in UI Flow
            }
        };

        gateway.async_registerTerminalToStore(req, registerCallback);
    }
}