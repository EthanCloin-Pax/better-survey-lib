package com.example.bettersurveylib.api;

import android.util.Log;

import com.example.bettersurveylib.ResponseCodes;
import com.example.bettersurveylib.api.requests.GetRegisterUrlReq;
import com.example.bettersurveylib.api.requests.GetTerminalInfoReq;
import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;
import com.example.bettersurveylib.api.responses.GetTerminalInfoRsp;

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

        Log.i(TAG, "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterUrlRsp> urlRequest = terminalRegisterApi.doGetRegisterUrl("timestamp", "signaturedata", req);

        try {
            Response<GetRegisterUrlRsp> urlResponse = urlRequest.execute();

            if (urlResponse.body() == null) {
                Log.w("TAG", "" + urlResponse.errorBody());
                return new GetRegisterUrlRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }

            // check for already registered
            boolean isResponseUrlValid = urlResponse.body().registerUrl == null && urlResponse.body().responseMessage.equals(ResponseCodes.ALREADY_REGISTERED_MSG);
            if (isResponseUrlValid) return new GetRegisterUrlRsp(ResponseCodes.ALREADY_REGISTERED_CODE, ResponseCodes.ALREADY_REGISTERED_MSG);

            // success case
            return urlResponse.body();

        } catch (IOException e) {
            return new GetRegisterUrlRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }

    public GetTerminalInfoRsp requestTerminalInfo(GetTerminalInfoReq req) {
        Call<GetTerminalInfoRsp> infoRequest = terminalRegisterApi.doGetTerminalInfo("timestamp", "signaturedata", req);

        // TODO: consider generifying this null check to work for all register requests
        try {
            Response<GetTerminalInfoRsp> terminalInfoResponse = infoRequest.execute();

            if (terminalInfoResponse.body() == null) {
                Log.w("TAG", "" + terminalInfoResponse.errorBody());
                return new GetTerminalInfoRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }

            // success case
            return terminalInfoResponse.body();
        } catch (IOException e) {
            return new GetTerminalInfoRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }
}
