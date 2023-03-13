package com.example.bettersurveylib.api;

import android.util.Log;

import com.example.bettersurveylib.ResponseCodes;
import com.example.bettersurveylib.api.requests.GetRegisterUrlReq;
import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

/**
 * encapsulates the methods to enable interaction with Survey and TerminalRequest APIs
 *
 */
public class SurveyGateway {
    private static final String TAG = SurveyGateway.class.getSimpleName();

    TerminalRegisterInterface terminalRegisterApi = null;

    private void initializeApiInterface() {
        if (terminalRegisterApi == null) {
            terminalRegisterApi = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);
        }
    }

    /**
     * sends provided request object to TerminalRegister API, returning response object with URL value
     *
     * @param req
     * @return response object with `registerUrl` value
     */
    public GetRegisterUrlRsp requestRegistrationUrl(GetRegisterUrlReq req) {

        Log.i("TAG", "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterUrlRsp> urlRequest = terminalRegisterApi.doGetRegisterUrl("timestamp", "signaturedata", req);

        try {
            Response<GetRegisterUrlRsp> urlResponse = urlRequest.execute();

            if (urlResponse.body() == null) {
                Log.w("TAG", "" + urlResponse.errorBody());
                return new GetRegisterUrlRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }
            return urlResponse.body();

        } catch (IOException e) {
            return new GetRegisterUrlRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }
}
