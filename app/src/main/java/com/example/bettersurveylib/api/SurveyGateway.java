package com.example.bettersurveylib.api;

import android.accounts.AuthenticatorException;
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
    public GetRegisterUrlRsp requestRegistrationUrl(GetRegisterUrlReq req) {

        Log.i("TAG", "request obj: " + req);

        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
        Call<GetRegisterUrlRsp> urlRequest = terminalRegisterApi.doGetRegisterUrl(auth.generateTimeStamp(), auth.generateSignature(req.toString(), "key"), req);

        try {
            Response<GetRegisterUrlRsp> urlResponse = urlRequest.execute();

            if (urlResponse.body() == null) {
                Log.w("TAG", "" + urlResponse.errorBody());
                return new GetRegisterUrlRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
            }

            // check for already registered
            boolean isResponseUrlValid = urlResponse.body().registerUrl == null && urlResponse.body().responseMessage.equals(ResponseCodes.ALREADY_REGISTERED_MSG);
            if (isResponseUrlValid) return new GetRegisterUrlRsp(ResponseCodes.ALREADY_REGISTERED_CODE, ResponseCodes.ALREADY_REGISTERED_MSG);

            return urlResponse.body();

        } catch (IOException e) {
            return new GetRegisterUrlRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
        }
    }


}
