package com.example.bettersurveylib.api.register.responses;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRegisterDataRsp {

    @SerializedName("responseCode")
    public String responseCode;

    @SerializedName("responseMessage")
    public String responseMessage;

    @SerializedName("registerURL")
    public String registerUrl;

    // below fields are optional, only included if device already registered
    @SerializedName("supportFeature")
    @Nullable
    public List<String> supportFeature;

    @SerializedName("registerRequestEncryptKey")
    @Nullable
    public String registerRequestEncryptKey;

    @SerializedName("responseRequestEncryptKey")
    @Nullable
    public String registerResponseEncryptKey;


    public GetRegisterDataRsp() {
    }

    public GetRegisterDataRsp(String responseCode, String responseMessage) {
        registerUrl = "";
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public boolean isTerminalRegistered() {
        return registerResponseEncryptKey == null
                        && registerRequestEncryptKey == null
                        && supportFeature == null;
    }

}
