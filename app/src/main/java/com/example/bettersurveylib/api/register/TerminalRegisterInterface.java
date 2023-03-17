package com.example.bettersurveylib.api.register;

import com.example.bettersurveylib.api.register.requests.ConnectStoreReq;
import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.requests.SearchStoreReq;
import com.example.bettersurveylib.api.register.responses.ConnectStoreRsp;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;
import com.example.bettersurveylib.api.register.responses.SearchStoreRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * definition of endpoints for TerminalRegister API. attach this to TerminalRegisterClient instance
 */
public interface TerminalRegisterInterface {
    /**
     * Request URL to web form allowing user to register their terminal with Seamless Commerce
     *
     * Returns additional terminal data if already registered.
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/getRegisterData")
    Call<GetRegisterDataRsp> doGetRegisterData(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetRegisterDataReq request
    );

    /**
     * Request data associated with provided terminal
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/TerminalRegister")
    Call<RegisterTerminalRsp> doRegisterTerminal(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body RegisterTerminalReq request
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/SearchStore")
    Call<SearchStoreRsp> doSearchStore(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body SearchStoreReq request
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/ConnectStore")
    Call<ConnectStoreRsp> doConnectStore(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body ConnectStoreReq request
    );


}
