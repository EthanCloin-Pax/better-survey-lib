package com.example.bettersurveylib.api.register.responses;

import com.google.gson.annotations.SerializedName;

public class RegisterTerminalRsp extends BaseRegisterResponse {

    @SerializedName("registerRequestEncryptKey")
    public String requestKey;

    @SerializedName("registerResponseEncryptKey")
    public String responseKey;

    public RegisterTerminalRsp(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }
}
