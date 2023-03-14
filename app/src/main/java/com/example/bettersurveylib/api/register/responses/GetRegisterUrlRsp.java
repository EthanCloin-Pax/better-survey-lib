package com.example.bettersurveylib.api.register.responses;

import com.google.gson.annotations.SerializedName;

public class GetRegisterUrlRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("registerURL")
    public String registerUrl;

    public GetRegisterUrlRsp(){}
    public GetRegisterUrlRsp(String responseCode, String responseMessage){
        registerUrl = "";
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

}
