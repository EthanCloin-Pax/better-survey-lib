package com.example.bettersurveylib.api;

import com.example.bettersurveylib.api.requests.GetRegisterUrlReq;
import com.example.bettersurveylib.api.requests.GetTerminalInfoReq;
import com.example.bettersurveylib.api.requests.GetTerminalKeysReq;
import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;
import com.example.bettersurveylib.api.responses.GetTerminalInfoRsp;
import com.example.bettersurveylib.api.responses.GetTerminalKeysRsp;

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
    Call<GetRegisterUrlRsp> doGetRegisterUrl(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetRegisterUrlReq request
    );

    /**
     * Request data associated with provided terminal
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/TerminalRegister")
    Call<GetTerminalKeysRsp> doGetTerminalKeys(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetTerminalKeysReq request
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
