package com.example.bettersurveylib.api.register;

import com.example.bettersurveylib.api.register.requests.GetRegisterDataReq;
import com.example.bettersurveylib.api.register.requests.GetTerminalInfoReq;
import com.example.bettersurveylib.api.register.requests.RegisterTerminalReq;
import com.example.bettersurveylib.api.register.responses.GetRegisterDataRsp;
import com.example.bettersurveylib.api.register.responses.GetTerminalInfoRsp;
import com.example.bettersurveylib.api.register.responses.RegisterTerminalRsp;

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
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/getRegisterURL")
    Call<GetRegisterDataRsp> doGetRegisterUrl(
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

    /**
     * Request authentication request and response keys for a successfully registered terminal
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/GetTerminalInform")
    Call<GetTerminalInfoRsp> doGetTerminalInfo(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetTerminalInfoReq request
    );


}
