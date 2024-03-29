package com.example.bettersurveylib.api;

import android.util.Log;

import com.example.bettersurveylib.ResponseCodes;
import com.example.bettersurveylib.api.register.TerminalRegisterClient;
import com.example.bettersurveylib.api.register.TerminalRegisterInterface;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.requests.GetTerminalInfoReq;
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.GetTerminalInfoRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;

import java.io.IOException;

import retrofit2.Call;
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
     * sends provided request object to TerminalRegister API, returning response object with URL value
     *
     * @param req
     * @return response object with `registerUrl` value
     */
    public GetRegisterDataRsp requestRegistrationUrl(GetRegisterDataReq req) {
        initializeApiInterface();

        Log.i(TAG, "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterDataRsp> urlRequest = terminalRegisterApi.doGetRegisterUrl(auth.generateTimeStamp(), auth.generateSignature(req.toString(), "key"), req);

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
     * requests terminal data from terminal register api
     *
     * @param req
     * @return
     */
    public GetTerminalInfoRsp requestTerminalInfo(GetTerminalInfoReq req) {
        initializeApiInterface();
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
