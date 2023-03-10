package com.example.bettersurveylib.api;

import com.example.bettersurveylib.api.responses.GetRegisterUrlRsp;

import retrofit2.Call;
import retrofit2.http.POST;

public interface TerminalRegisterInterface {
    @POST("v1/getRegisterURL")
    Call<GetRegisterUrlRsp> doGetRegisterUrl();


}
