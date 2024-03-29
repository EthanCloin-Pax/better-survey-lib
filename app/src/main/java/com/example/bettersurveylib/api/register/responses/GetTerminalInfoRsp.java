package com.example.bettersurveylib.api.register.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTerminalInfoRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("supportFeature")
    public List<String> supportFeature;

    @SerializedName("registerRequestEncryptKey")
    public String requestKey;

    @SerializedName("registerResponseEncryptKey")
    public String responseKey;

    public GetTerminalInfoRsp(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
