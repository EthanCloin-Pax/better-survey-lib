package com.example.bettersurveylib.api.register.responses;

import com.google.gson.annotations.SerializedName;

public class BaseRegisterResponse {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    public BaseRegisterResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
