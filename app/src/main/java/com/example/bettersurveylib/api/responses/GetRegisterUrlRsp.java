package com.example.bettersurveylib.api.responses;

import com.google.gson.annotations.SerializedName;

public class GetRegisterUrlRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("registerURL")
    public String registerUrl;

}
