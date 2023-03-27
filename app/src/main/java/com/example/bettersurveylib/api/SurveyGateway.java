package com.example.bettersurveylib.api;

import android.util.Log;

import com.example.bettersurveylib.ResponseCodes;
import com.example.bettersurveylib.api.register.TerminalRegisterClient;
import com.example.bettersurveylib.api.register.TerminalRegisterInterface;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * encapsulates the methods to enable interaction with Survey and TerminalRequest APIs
 *
 */
public class SurveyGateway {

    private static final String TAG = SurveyGateway.class.getSimpleName();

    TerminalRegisterInterface terminalRegisterApi = null;
    Authenticator auth = null;

    private void initializeApiInterface() {
        if (terminalRegisterApi == null) {
            terminalRegisterApi = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);
            auth = new Authenticator();
        }
    }

    /**
     * accepts a Request instance and Retrofit callback. Creates a call to the TerminalRegister API
     * and applies the provided callback to handle the result.
     * This is the new maybe bad way - check blocking_requestRegistrationData for old maybe bad way
     *
     * @param req
     */
    public void async_requestRegistrationData(GetRegisterDataReq req, Callback<GetRegisterDataRsp> callback) {
        initializeApiInterface();

        Log.i(TAG, "request obj: " + req);
        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterDataRsp> urlRequest = terminalRegisterApi.doGetRegisterData("fake time", "fake sign", req);
        urlRequest.enqueue(callback);
    }

    /**
     * sends provided request object to TerminalRegister API, returning response object with URL value
     *
     * this is the old maybe bad way
     * @param req
     * @return response object with `registerUrl` value
     */
    public GetRegisterDataRsp blocking_requestRegistrationData(GetRegisterDataReq req) {
        initializeApiInterface();

        Log.i(TAG, "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterDataRsp> urlRequest = terminalRegisterApi.doGetRegisterData("fake time", "fake sign", req);

        try {
            Response<GetRegisterDataRsp> urlResponse = urlRequest.execute();

            if (urlResponse.body() == null) {
                Log.w("TAG", "" + urlResponse.errorBody());
                return new GetRegisterDataRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }

            // check for already registered
            boolean isResponseUrlValid = urlResponse.body().registerUrl == null && urlResponse.body().responseMessage.equals(ResponseCodes.ALREADY_REGISTERED_MSG);
            if (isResponseUrlValid) return new GetRegisterDataRsp(ResponseCodes.ALREADY_REGISTERED_CODE, ResponseCodes.ALREADY_REGISTERED_MSG);

            // success case
            return urlResponse.body();

        } catch (IOException e) {
            return new GetRegisterDataRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }

    /**
     * performs registration of terminal and returns the encryption keys to authenticate later survey requests
     *
     * @param req
     * @return
     */
    public RegisterTerminalRsp registerTerminal(RegisterTerminalReq req) {
        initializeApiInterface();
        Call<RegisterTerminalRsp> registerRequest = terminalRegisterApi.doRegisterTerminal("timestamp", "signaturedata", req);

        try {
            Response<RegisterTerminalRsp> registerResponse = registerRequest.execute();

            if (registerResponse.body() == null) {
                Log.w("TAG", "" + registerResponse.errorBody());
                return new RegisterTerminalRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }

            // success case
            return registerResponse.body();

        } catch (IOException e) {
            return new RegisterTerminalRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }
}
