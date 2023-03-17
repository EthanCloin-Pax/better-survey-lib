package com.example.bettersurveylib;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bettersurveylib.api.register.TerminalRegisterClient;
import com.example.bettersurveylib.api.register.TerminalRegisterInterface;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
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

//        btn.setOnClickListener(l -> makeUrlRequest());
    }

    // TODO: decide what format to accept parameters, maybe the GetRegisterUrlReq object
    private GetRegisterDataRsp requestRegistrationUrl() {

        GetRegisterDataReq req = new GetRegisterDataReq("PAX", "Aries8", "00000002", "fakecert", Arrays.asList("Survey"));
        Log.i("TAG", "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterDataRsp> urlRequest = terminalRegisterAPI.doGetRegisterData("timestamp", "signaturedata", req);

        try {
            Response<GetRegisterDataRsp> urlResponse = urlRequest.execute();

            if (urlResponse.body() == null) {
                Log.w("TAG", "" + urlResponse.errorBody());
                return new GetRegisterDataRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }
            return urlResponse.body();

        } catch (IOException e) {
            return new GetRegisterDataRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }

    // i think this supports async while below `execute` calls synchronously?
//        requestUrlCall.enqueue(new Callback<GetRegisterUrlRsp>() {
//            @Override
//            public void onResponse(Call<GetRegisterUrlRsp> call, Response<GetRegisterUrlRsp> response) {
//                Log.i("TAG", "" + response);
//                GetRegisterUrlRsp rspBody = response.body();
//                textView.setText(rspBody.registerUrl);
//            }
//
//            @Override
//            public void onFailure(Call<GetRegisterUrlRsp> call, Throwable t) {
//                Log.i("TAG", "it did not work good");
//                call.cancel();
//            }
//        });
}