package com.example.surveydemo.api;

import android.util.Log;


import com.paxus.pay.host.ui.init.surveydemo.api.register.TerminalRegisterClient;
import com.paxus.pay.host.ui.init.surveydemo.api.register.TerminalRegisterInterface;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.ConnectStoreReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.GetRegisterDataReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.RegisterTerminalReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.requests.SearchStoreReq;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.ConnectStoreRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.GetRegisterDataRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.RegisterTerminalRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.register.responses.SearchStoreRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.SurveyClient;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.SurveyInterface;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.GetQuestionnairesReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.GetQuestionsReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.RegisterReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.requests.UploadAnswerReq;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.GetQuestionnairesRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.GetQuestionsRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.RegisterRsp;
import com.paxus.pay.host.ui.init.surveydemo.api.survey.responses.UploadAnswerRsp;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * encapsulates the methods to enable interaction with Survey and TerminalRequest APIs
 */

public class SurveyGateway {

    private static final String TAG = SurveyGateway.class.getSimpleName();

    TerminalRegisterInterface terminalRegisterApi = null;
    SurveyInterface surveyApi = null;
    Authenticator auth = null;

    public SurveyGateway() {
        initializeApiInterface();
    }

    private void initializeApiInterface() {
        terminalRegisterApi = TerminalRegisterClient.getClient().create(TerminalRegisterInterface.class);
        surveyApi = SurveyClient.getClient().create(SurveyInterface.class);
        auth = Authenticator.getInstance();
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

    /**
     * Request to register Terminal with Survey API. Response body within the provided callback will
     * contain important data which must be saved to SharedPreferences: registerRequestEncryptKey,
     * responseRequestEncryptKey, and deviceId.
     *
     * @param req
     * @param callback
     * @param registerEncryptKey
     */
    public void async_registerTerminalToSurvey(RegisterReq req, Callback<RegisterRsp> callback, String registerEncryptKey) {
        RegisterReq authenticatedReq = auth.addAuthToRegisterRequest(req, registerEncryptKey);

        Call<RegisterRsp> surveyRegisterRequest = surveyApi.doRegister(authenticatedReq);
        Log.i(TAG, "authenticated version is : " + authenticatedReq);
        surveyRegisterRequest.enqueue(callback);
        /*
    result on success!
    I/okhttp.OkHttpClient: {
        "Token":"fb794e0d1329b8427aab9f9c3ffa92d7d8571c64decc25a6620f7c26283e956728320a2526a3cb811b7b36f9ee6a4315b5d10ff278f048daa06ad5c6e63eef57",
        "DeviceID":"0000002",
        "StoreID":"467",
        "RequestEncryptKey":"HK6JPe3mNl59XyQHgLGOz27np0yMYnLOZSzhSpQPqOE=",
        "ResponseEncryptKey":"d7sB7Vbh6dohwbq6q3KNlm7np0yMYnLOZSzhSpQPqOE=",
        "ResultCode":"0000",
        "ResultMessage":"",
        "SignatureData":"oPMPuwkum4ct1TG4474f80Ru4es=",
        "TimeStamp":"20230403134851",
        "ExtData":null,"ExtDataList":null
    }
     */
    }


    public void async_requestQuestionnaires(GetQuestionnairesReq req, Callback<GetQuestionnairesRsp> callback, String requestEncryptKey) {
        GetQuestionnairesReq authenticatedReq = (GetQuestionnairesReq) auth.addAuthToSurveyRequest(req, requestEncryptKey);

        Log.i(TAG, "authenticated request: " + authenticatedReq);
        Call<GetQuestionnairesRsp> getQuestionnairesRequest = surveyApi.doGetQuestionnaires(authenticatedReq);
        getQuestionnairesRequest.enqueue(callback);
    }

    public void async_requestQuestions(GetQuestionsReq req, Callback<GetQuestionsRsp> callback, String requestEncryptKey) {
        GetQuestionsReq authenticatedReq = (GetQuestionsReq) auth.addAuthToSurveyRequest(req, requestEncryptKey);
        Call<GetQuestionsRsp> getQuestionsRequest = surveyApi.doGetQuestions(authenticatedReq);
        getQuestionsRequest.enqueue(callback);
    }

    public void async_uploadAnswers(UploadAnswerReq req, Callback<UploadAnswerRsp> callback, String requestEncryptKey) {
        UploadAnswerReq authenticatedReq = (UploadAnswerReq) auth.addAuthToSurveyRequest(req, requestEncryptKey);

        Call<UploadAnswerRsp> uploadAnswerRequest = surveyApi.doUploadAnswers(authenticatedReq);
        uploadAnswerRequest.enqueue(callback);
    }
}
