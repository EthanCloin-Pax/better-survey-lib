package com.example.bettersurveylib.api;

import com.example.bettersurveylib.api.requests.GetRegisterUrlReq;
import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TerminalRegisterInterface {
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("v1/getRegisterURL")
    Call<GetRegisterUrlRsp> doGetRegisterUrl(
            @Header("TimeStamp") String timestamp,
            @Header("SignatureData") String signatureData,
            @Body GetRegisterUrlReq request
    );


}
