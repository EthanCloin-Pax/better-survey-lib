package com.example.bettersurveylib.api.register.responses;

import com.google.gson.annotations.SerializedName;

public class RegisterTerminalRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("registerRequestEncryptKey")
    public String requestKey;

    @SerializedName("registerResponseEncryptKey")
    public String responseKey;

    public RegisterTerminalRsp(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
