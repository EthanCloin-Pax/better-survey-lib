package com.example.bettersurveylib.api;

import android.util.Log;

import com.example.bettersurveylib.api.register.TerminalRegisterClient;
import com.example.bettersurveylib.api.register.TerminalRegisterInterface;
import com.example.bettersurveylib.api.register.requests.ConnectStoreReq;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.requests.SearchStoreReq;
import com.example.bettersurveylib.api.register.responses.ConnectStoreRsp;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;
import com.example.bettersurveylib.api.register.responses.SearchStoreRsp;
import com.example.bettersurveylib.api.survey.SurveyClient;
import com.example.bettersurveylib.api.survey.SurveyInterface;
import com.example.bettersurveylib.api.survey.requests.GetQuestionnairesReq;
import com.example.bettersurveylib.api.survey.requests.GetQuestionsReq;
import com.example.bettersurveylib.api.survey.requests.RegisterReq;
import com.example.bettersurveylib.api.survey.requests.UploadAnswerReq;
import com.example.bettersurveylib.api.survey.responses.GetQuestionnairesRsp;
import com.example.bettersurveylib.api.survey.responses.GetQuestionsRsp;
import com.example.bettersurveylib.api.survey.responses.RegisterRsp;
import com.example.bettersurveylib.api.survey.responses.UploadAnswerRsp;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * encapsulates the methods to enable interaction with Survey and TerminalRequest APIs
 *
 */
public class SurveyGateway {

    private static final String TAG = SurveyGateway.class.getSimpleName();

    TerminalRegisterInterface terminalRegisterApi = null;
    SurveyInterface surveyApi = null;
    Authenticator auth = null;

    private void initializeApiInterface() {
            terminalRegisterApi = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);
            surveyApi = SurveyClient.getClient().create(SurveyInterface.class);
            auth = new Authenticator();
    }

    /**
     * accepts a Request instance and Retrofit callback. Creates a call to the TerminalRegister API
     * and applies the provided callback to handle the result.
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

    public void async_registerTerminalToStore(RegisterTerminalReq req, Callback<RegisterTerminalRsp> callback) {
        Call<RegisterTerminalRsp> registerTerminal = terminalRegisterApi.doRegisterTerminal("fake time", "fake sign", req);
        registerTerminal.enqueue(callback);
    }

    public void async_requestSearchStore(SearchStoreReq req, Callback<SearchStoreRsp> callback) {
        Call<SearchStoreRsp> connectStoreRequest = terminalRegisterApi.doSearchStore("fake time", "fake sign", req);
        connectStoreRequest.enqueue(callback);
    }


    public void async_requestConnectStore(ConnectStoreReq req, Callback<ConnectStoreRsp> callback) {
        Call<ConnectStoreRsp> connectStoreRequest = terminalRegisterApi.doConnectStore("fake time", "fake sign", req);
        connectStoreRequest.enqueue(callback);
    }

    public void async_registerTerminalToSurvey(RegisterReq req, Callback<RegisterRsp> callback) {
        initializeApiInterface();
        Log.i("EMC GATEWAY: ", "oye here is what i got: " + req.toString() + " " + callback);
        req.setAppKeyIdentity("TestForPAX");
        RegisterReq authenticatedReq = auth.addAuthToRegisterRequest(req, "ce9d7c64b8dc3344");

        Call<RegisterRsp> surveyRegisterRequest = surveyApi.doRegister(authenticatedReq);
        Log.i(TAG, "authenticated version is : " + authenticatedReq);
        surveyRegisterRequest.enqueue(callback);
    }

    public void async_requestQuestionnaires(GetQuestionnairesReq req, Callback<GetQuestionnairesRsp> callback) {
        Call<GetQuestionnairesRsp> getQuestionnairesRequest = surveyApi.doGetQuestionnaires(req);
        getQuestionnairesRequest.enqueue(callback);
    }

    public void async_requestQuestions(GetQuestionsReq req, Callback<GetQuestionsRsp> callback) {
        Call<GetQuestionsRsp> getQuestionsRequest = surveyApi.doGetQuestions(req);
        getQuestionsRequest.enqueue(callback);
    }

    public void async_uploadAnswers(UploadAnswerReq req, Callback<UploadAnswerRsp> callback) {
        Call<UploadAnswerRsp> uploadAnswerRequest = surveyApi.doUploadAnswers(req);
        uploadAnswerRequest.enqueue(callback);
    }
//    /**
//     * sends provided request object to TerminalRegister API, returning response object with URL value
//     *
//     * this is the old maybe bad way
//     * @param req
//     * @return response object with `registerUrl` value
//     */
//    public GetRegisterDataRsp blocking_requestRegistrationData(GetRegisterDataReq req) {
//        initializeApiInterface();
//
//        Log.i(TAG, "request obj: " + req);
//
//        // TODO: add authentication info generation. need a fxn to generate timestamp and signature with request body
//        Call<GetRegisterDataRsp> urlRequest = terminalRegisterApi.doGetRegisterData("fake time", "fake sign", req);
//
//        try {
//            Response<GetRegisterDataRsp> urlResponse = urlRequest.execute();
//
//            if (urlResponse.body() == null) {
//                Log.w("TAG", "" + urlResponse.errorBody());
//                return new GetRegisterDataRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
//            }
//
//            // check for already registered
//            boolean isResponseUrlValid = urlResponse.body().registerUrl == null && urlResponse.body().responseMessage.equals(ResponseCodes.ALREADY_REGISTERED_MSG);
//            if (isResponseUrlValid) return new GetRegisterDataRsp(ResponseCodes.ALREADY_REGISTERED_CODE, ResponseCodes.ALREADY_REGISTERED_MSG);
//
//            // success case
//            return urlResponse.body();
//
//        } catch (IOException e) {
//            return new GetRegisterDataRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
//        }
//    }
//
//    /**
//     * performs registration of terminal and returns the encryption keys to authenticate later survey requests
//     *
//     * @param req
//     * @return
//     */
//    public RegisterTerminalRsp registerTerminal(RegisterTerminalReq req) {
//        initializeApiInterface();
//        Call<RegisterTerminalRsp> registerRequest = terminalRegisterApi.doRegisterTerminal("timestamp", "signaturedata", req);
//
//        try {
//            Response<RegisterTerminalRsp> registerResponse = registerRequest.execute();
//
//            if (registerResponse.body() == null) {
//                Log.w("TAG", "" + registerResponse.errorBody());
//                return new RegisterTerminalRsp(ResponseCodes.NULL_RESPONSE_CODE, ResponseCodes.NULL_RESPONSE_MSG);
//            }
//
//            // success case
//            return registerResponse.body();
//
//        } catch (IOException e) {
//            return new RegisterTerminalRsp(ResponseCodes.SERVER_UNREACHABLE_CODE, ResponseCodes.SERVER_UNREACHABLE_MSG);
//        }
//    }
}
